package com.common.mq;

import com.common.util.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MqConsumeDemo extends AbstractMqConsume<MqConsumeDemo.Demo> {

    private static final Logger logger = LoggerFactory.getLogger(MqConsumeDemo.class);

    private static String topic="SMS";

    @Override
    public String getChannelName() {
        return topic;
    }

    @Override
    public Demo transform(String body) {
        return GsonUtils.gson.fromJson(body, Demo.class);
    }

    @Override
    public void consume(Demo demo) {
        logger.error("-----------------");
    }


    public  static class Demo{

    }

}