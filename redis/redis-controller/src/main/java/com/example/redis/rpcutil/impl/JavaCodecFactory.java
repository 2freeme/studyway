package com.example.redis.rpcutil.impl;

import com.example.redis.rpcutil.CodecFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by zhaolp on 2017/7/17 0017.
 */
public class JavaCodecFactory implements CodecFactory {
	
	private final Logger logger = LoggerFactory.getLogger(JavaCodecFactory.class);
	
    @Override
    public <T> byte[] serialize(T value) throws IOException {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] result = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            result = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            close(os);
            close(bos);
        }
        return result;
    }

    @Override
    public <T> T deserialize(Class<T> resultClass, byte[] in) throws IOException {
        T result = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                result = (T) is.readObject();
                is.close();
                bis.close();
            }
        } catch (IOException e) {
        	logger.error(e.getMessage(), e);
            throw new IOException(String.format("Caught IOException decoding %d bytes of data", in.length), e);
        } catch (ClassNotFoundException e) {
        	logger.error(e.getMessage(), e);
            throw new IOException(String.format("Caught CNFE decoding %d bytes of data", in.length), e);
        } finally {
            close(is);
            close(bis);
        }
        return result;
    }
    public  void close(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                throw new IOException(e);
            }
        }
    }
}
