package com.jqp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Aspect
public class ServiceLogAspect {

    public static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    /**
     * aop的通知类型：
     *  1.前置通知：在方法调用前执行。
     *  2.后置通知：在方法正常调用后执行。
     *  3.环绕通知：在方法调用之前和之后，都可以执行。
     *  4.异常通知：方法发生异常，则通知。
     *  5.最终通知：在方法调用后通知。
     */

    /**
     * 切面表达式：
     * execution 代表所要执行的表达式主体
     * 第一处 * 代表方法返回类型 *代表所有类型
     * 第二处 包名代表aop监控的类所在的包
     * 第三处 .. 代表该包以及其子包下的所有类方法
     * 第四处 * 代表类名，*代表所有类
     * 第五处 *(..) *代表类中的方法名，(..)表示方法中的任何参数
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.jqp.service.impl..*.*(..))")
    @Transactional(propagation = Propagation.REQUIRED)
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable{
        logger.info("====开始执行{}.{}=====",joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());

        //记录开始时间
        Long begin = System.currentTimeMillis();

        //执行目标 sevice
        Object object = joinPoint.proceed();

        //记录结束时间
        Long end = System.currentTimeMillis();

        Long takeTime = end - begin;
        if(takeTime > 3000){
            logger.error("=====执行结束，耗时：{} 毫秒 ======", takeTime);
        } else if(takeTime > 2000){
            logger.warn("=====执行结束，耗时：{} 毫秒 ======", takeTime);
        }else {
            logger.info("=====执行结束，耗时：{} 毫秒 ======", takeTime);
        }

        return object;
    }

}
