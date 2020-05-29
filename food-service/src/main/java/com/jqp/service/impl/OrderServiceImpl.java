package com.jqp.service.impl;

import com.jqp.bo.SubmitOrderBO;
import com.jqp.mapper.ItemsSpecMapper;
import com.jqp.mapper.OrderItemsMapper;
import com.jqp.mapper.OrderStatusMapper;
import com.jqp.mapper.OrdersMapper;
import com.jqp.pojo.*;
import com.jqp.service.AddressService;
import com.jqp.service.ItemsService;
import com.jqp.service.OrderService;
import com.jqp.vo.MerchantOrdersVO;
import com.jqp.vo.OrderVO;
import enums.OrderStatusEnum;
import enums.YesOrNo;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private Sid sid;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ItemsService itemService;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {

         String userId=submitOrderBO.getUserId();
         String itemSpecIds=submitOrderBO.getItemSpecIds();
         String addressId=submitOrderBO.getAddressId();
         Integer payMethod=submitOrderBO.getPayMethod();
        // 包邮费用设置为0
        Integer postAmount = 0;
        UserAddress userAddress=addressService.queryUserAddres(userId, addressId);
         String leftMsg=submitOrderBO.getLeftMsg();
        // 1. 新订单数据保存
         Orders orders=new Orders();
         String orderId=sid.nextShort();
         orders.setId(orderId);
         orders.setUserId(userId);
         orders.setReceiverMobile(userAddress.getMobile());
         orders.setReceiverName(userAddress.getReceiver());
         orders.setReceiverAddress(userAddress.getProvince()+" "+userAddress.getCity()+" "+userAddress.getDistrict()+" "+userAddress.getDetail());
        orders.setPostAmount(postAmount);
        orders.setPayMethod(payMethod);
        orders.setLeftMsg(leftMsg);
        orders.setIsComment(YesOrNo.NO.type);
        orders.setIsDelete(YesOrNo.NO.type);
        orders.setCreatedTime(new Date());
        orders.setUpdatedTime(new Date());
        // 2. 循环根据itemSpecIds保存订单商品信息表
        String itemSpecIdArr[] = itemSpecIds.split(",");
        Integer totalAmount = 0;    // 商品原价累计
        Integer realPayAmount = 0;  // 优惠后的实际支付价格累计
        for(String itemSpecId : itemSpecIdArr){
            // TODO 整合redis后，商品购买的数量重新从redis的购物车中获取
            int buyCounts = 1;
            OrderItems orderItems=new OrderItems();
            orderItems.setId(sid.nextShort());
            orderItems.setOrderId(orderId);
            // 2.1 根据规格id，查询规格的具体信息，主要获取价格
            ItemsSpec itemSpec = itemService.queryItemSpecById(itemSpecId);
            totalAmount += itemSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemSpec.getPriceDiscount() * buyCounts;
            // 2.2 根据商品id，获得商品信息以及商品图片
            String itemId = itemSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);
            orderItems.setItemId(itemId);
            orderItems.setItemImg(imgUrl);
            orderItems.setItemName(item.getItemName());
            orderItems.setItemSpecId(itemSpecId);
            orderItems.setItemSpecName(itemSpec.getName());
            orderItems.setBuyCounts(buyCounts);
            orderItems.setPrice(itemSpec.getPriceDiscount());
            orderItemsMapper.insert(orderItems);
            itemService.decreaseItemSpecStock(itemSpecId,buyCounts);
        }
        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayAmount);
        ordersMapper.insert(orders);
        // 3. 保存订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);

        // 4. 构建商户订单，用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(realPayAmount + postAmount);
        merchantOrdersVO.setPayMethod(payMethod);

        // 5. 构建自定义订单vo
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);

        return orderVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }
}
