package com.demo.core.redis;

import com.fasterxml.jackson.databind.JavaType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class CompressJackson2JsonRedisSerializer<T> extends Jackson2JsonRedisSerializer<T> {
    private static Logger logger = LoggerFactory.getLogger(CompressJackson2JsonRedisSerializer.class);

    public CompressJackson2JsonRedisSerializer(Class<T> type) {
        super(type);
    }

    public CompressJackson2JsonRedisSerializer(JavaType javaType) {
        super(javaType);
    }

    public T deserialize(byte[] bytes) throws SerializationException {
        byte[] trueBytes = this.uncompress(bytes);
        return super.deserialize(trueBytes);
    }

    public byte[] serialize(Object t) throws SerializationException {
        byte[] jsonBytes = super.serialize(t);
        return this.compress(jsonBytes);
    }

    public byte[] compress(byte[] bytes) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(bytes);
            gzip.close();
        } catch (IOException var5) {
            logger.error("gzip compress error.", var5);
        }

        return out.toByteArray();
    }

    public byte[] uncompress(byte[] bytes) {
        if (bytes != null && bytes.length != 0) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);

            try {
                GZIPInputStream ungzip = new GZIPInputStream(in);
                byte[] buffer = new byte[1024];

                int n;
                while((n = ungzip.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
            } catch (IOException var7) {
                logger.error("gzip uncompress error.", var7);
            }

            return out.toByteArray();
        } else {
            return null;
        }
    }
}
