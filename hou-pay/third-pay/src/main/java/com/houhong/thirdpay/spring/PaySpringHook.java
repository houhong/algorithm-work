package com.houhong.thirdpay.spring;

import com.houhong.thirdpay.core.PayManagers;
import com.houhong.thirdpay.core.common.AbstractPayManager;
import com.houhong.thirdpay.config.CommonConfig;
import com.houhong.thirdpay.core.RedisResourceFactory;
import com.houhong.thirdpay.util.PayArrayUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-10-06 16:33
 **/
@Component
public class PaySpringHook implements ApplicationContextAware, ApplicationListener<ApplicationEvent> {


    private static final Log log = LogFactory.getLog(PaySpringHook.class);

   private static  ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {

        this.applicationContext = context;
    }


    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof ApplicationReadyEvent) {
            /*
                注册支付统一调度器
             */
            Map<String,AbstractPayManager> managers = PaySpringHook.getBeansOfType(AbstractPayManager.class);
            Set<Map.Entry<String, AbstractPayManager>> entrySetManagers = managers.entrySet();
            Iterator<Map.Entry<String, AbstractPayManager>> iteratorManagers = entrySetManagers.iterator();
            Map.Entry<String, AbstractPayManager> entryManagers;
            List<String> managerNames = new ArrayList<>();
            while(iteratorManagers.hasNext()){
                entryManagers = iteratorManagers.next();
                PayManagers.register(entryManagers.getValue());
                managerNames.add(entryManagers.getKey());
            }
            log.info("支付统一调度器注册成功：" + PayArrayUtil.join(managerNames, ","));

            /*
                注册redis连接池
             */
            Map<String,RedisResourceFactory> redisResourceFactorys = PaySpringHook.getBeansOfType(RedisResourceFactory.class);
            Set<Map.Entry<String, RedisResourceFactory>> entrySetRedis = redisResourceFactorys.entrySet();
            Iterator<Map.Entry<String, RedisResourceFactory>> iteratorRedis = entrySetRedis.iterator();
            Map.Entry<String, RedisResourceFactory> entryRedis;
            List<String> redisNames = new ArrayList<>();
            while(iteratorRedis.hasNext()){
                entryRedis = iteratorRedis.next();
                CommonConfig.setJedisPool(entryRedis.getValue().getJedisPool());
                redisNames.add(entryRedis.getKey());
            }
            log.info("redis连接池注册成功：" + PayArrayUtil.join(redisNames, ","));

        }

        if (event instanceof ContextClosedEvent) {
            //do something...
        }

    }



    /**
     * 根据bean类型获取spring ioc管理的bean实例
     * @param type bean类型，class或interface
     * @return bean实例集合
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type){
        return applicationContext.getBeansOfType(type);
    }

    /**
     * 根据bean名称获取spring ioc管理的bean实例
     * @param name bean名称
     * @return bean实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        T t = null;
        try {
            t = (T) applicationContext.getBean(name);
        } catch (Exception e) {
            log.warn("can't load bean[" + name + "] from spring ioc container!", e);
        }
        return t;
    }
}