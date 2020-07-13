package com.example.redis.rpcutil.impl;

import com.alibaba.fastjson.JSON;
import com.example.redis.rpcutil.CodecFactory;

import java.io.IOException;

/**
 * Created by zhaolp on 2017/7/17 0017.
 */
public class JsonCodecFactory implements CodecFactory {
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        return JSON.toJSONString(object).toString().getBytes();
    }

    @Override
    public <T> T deserialize(Class<T> resultClass, byte[] bytes) throws IOException {
        return JSON.parseObject(new String(bytes,"UTF-8"),resultClass);
    }
}
