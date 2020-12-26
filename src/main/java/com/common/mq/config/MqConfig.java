package com.common.mq.config;

import com.common.mq.AbstractMqConsume;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SerializationUtils;

import java.util.List;

/**
 * @author guozhenhua
 * @date 2020/12/19
 */
@Configuration
public class MqConfig {

    private static final Logger logger = LoggerFactory.getLogger(MqConfig.class);

    @Autowired
    List<AbstractMqConsume> abstractMqConsumes;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        try {
            if (null!=abstractMqConsumes && !abstractMqConsumes.isEmpty()){
                abstractMqConsumes.forEach(item-> bind(connectionFactory, item));
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    private void bind(ConnectionFactory connectionFactory, AbstractMqConsume abstractMqConsume){
        try{
            String channelName=abstractMqConsume.getChannelName();
            String exchangeName=abstractMqConsume.getChannelName()+"-exchange";
            final Connection connection = connectionFactory.createConnection();
            Channel channel = connection.createChannel(true);
            channel.exchangeDeclare(exchangeName, "fanout", true, false, null);
            String queueName = channel.queueDeclare(channelName, true, false, false, null).getQueue();
            channel.queueBind( queueName, exchangeName,"");
            logger.info(queueName+" Waiting for messages... ");

            Consumer consumer = new LocalConsumer(channel, abstractMqConsume);
            channel.basicConsume(queueName, true, consumer);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    static class LocalConsumer extends DefaultConsumer {
        private static Logger logger = LoggerFactory.getLogger(LocalConsumer.class);

        AbstractMqConsume abstractMqConsume;

        public LocalConsumer(Channel channel, AbstractMqConsume abstractMqConsume) {
            super(channel);
            this.abstractMqConsume = abstractMqConsume;
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body){
            try{
                String deserialize = (String) SerializationUtils.deserialize(body);
                abstractMqConsume.consume(abstractMqConsume.transform(deserialize));
            } catch (Throwable t){
                logger.error("消息调用出错", t);
            }
        }
    }





}
