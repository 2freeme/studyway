package com.example.redis.rpcutil.impl;

import com.caucho.hessian.io.HessianSerializerInput;
import com.caucho.hessian.io.HessianSerializerOutput;
import com.example.redis.rpcutil.CodecFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * User: zhaolp
 */
@Service
public class HessionCodecFactory implements CodecFactory {

	private final Logger logger = LoggerFactory.getLogger(HessionCodecFactory.class);
	/**
	 *
	 * 纯hessian序列化
	 * @param <T>
	 * @param object
	 * @return
	 * @throws Exception
	 */

	@Override
	public <T> byte[] serialize(T object) throws IOException {
		if (object == null) {
			throw new NullPointerException();
		}
		byte[] results = null;
		ByteArrayOutputStream os = null;
		HessianSerializerOutput hessianOutput = null;
		try {
			os = new ByteArrayOutputStream();
			hessianOutput = new HessianSerializerOutput(os);
			//write本身是线程安全的
			hessianOutput.writeObject(object);
			os.close();
			results = os.toByteArray();
		} catch (Exception e) {
			throw new IOException(e);
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return results;
	}


	/**
	 * 纯hessian反序列化
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> T deserialize(Class<T> resultClass, byte[] bytes) throws IOException {
		if (bytes == null) {
			throw new NullPointerException();
		}
		T result = null;
		ByteArrayInputStream is = null;
		try {
			is = new ByteArrayInputStream(bytes);
			HessianSerializerInput hessianInput = new HessianSerializerInput(is);
			result = (T) hessianInput.readObject();
		} catch (Exception e) {
			throw new IOException(e);
		} finally {
				if (is != null)
					is.close();
		}
		return result;
	}

}
