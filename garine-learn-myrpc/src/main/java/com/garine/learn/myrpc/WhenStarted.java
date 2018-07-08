package com.garine.learn.myrpc;


import com.garine.learn.myrpc.server.RpcServerPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class WhenStarted implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            if (!CollectionUtils.isEmpty(RpcServerPublisher.handlerMap)){
                new Thread(() -> {
                    try {
                        new RpcServerPublisher().publish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }
}
