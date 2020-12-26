package com.common.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by guozhenhua
 * date 2020/7/17.
 */

@Component
public class MqSendClientUtil{

    @Autowired
    static RabbitTemplate rabbitTemplate;

    public  static  <T extends Serializable> void send(String topic, T message) {
        rabbitTemplate.convertAndSend(topic,message);
    }

}
