package enums;

public enum PayMethod {

    WEIXIN(1,"微信"),
    ALIPAY(2,"支付宝");
    private final   Integer type;
    private final String value;
   PayMethod(Integer type,String value){
       this.type=type;
       this.value=value;
   }
}
