package com.example.redis.rpcutil;

import java.io.IOException;

/**
 * User: zhaolp
 * 编码和解码工厂
 */
public interface CodecFactory {

	<T> byte[] serialize(T object) throws IOException;

	<T> T deserialize(Class<T> resultClass, byte[] bytes) throws IOException ;

}
