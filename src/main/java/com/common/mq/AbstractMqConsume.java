package com.common.mq;

public abstract class AbstractMqConsume<T> {

    public abstract String getChannelName();

    public abstract void consume(T t);

    public abstract T transform(String body);


}
