package com.isesol.wis.operation.service;

import com.isesol.wis.serializable.SerializableService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

/**
 * 保存token关联的属性
 * @author wangxu
 */
@Service
public class TokenService {

    @Autowired
    private SerializableService serializableService;

    @Autowired
    private JedisConnectionFactory factory;

    private final static String TOKEN_NAMESPACE = "wis:token:%s";

    /**
     * 设置token对应的属性
     * @param name
     * @param value
     */
    public void setProperties(String token, String name, Object value) {
        RedisConnection connection = null;
        try {
            connection = factory.getConnection();
            connection.hSet(key(token).getBytes(), name.getBytes(), serializableService.toByteArray(value));
        } catch (Exception e) {
            throw new RuntimeException("保存token属性失败", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * 获取token对应的属性值
     * @param name
     * @return
     */
    public Object getProperties(String token, String name) {
        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException("获取Token属性失败:空的属性名");
        }
        RedisConnection connection = null;
        try {
            connection = factory.getConnection();
            byte[] bytes = connection.hGet(key(token).getBytes(), name.getBytes());
            if (bytes == null) {
                throw new RuntimeException("获取Token属性失败:" + name);
            }

            Object result = serializableService.toObject(bytes);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * 生成token
     * @return
     */
    public String generate(Long userId) {
        if (userId == null) {
            return null;
        }
        RedisConnection connection = null;
        try {
            connection = factory.getConnection();
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            String key = String.format(TOKEN_NAMESPACE, token);
            this.setProperties(token, "userId", userId);
            connection.expire(key.getBytes(), 2 * 24 * 60 * 60);
            return token;
        } catch (Exception e) {
            throw new RuntimeException("token生成失败", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * 验证token正确性
     * @return
     */
    public Long verify(String token) {
        try {
            Object obj = getProperties(token, "userId");
            if (obj != null) {
                return (Long) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 注销token
     */
    public void invalid(String token) {
        RedisConnection connection = null;
        try {
            connection = factory.getConnection();
            Set<byte[]> keys = connection.hKeys(key(token).getBytes());
            connection.hDel(key(token).getBytes(), keys.toArray(new byte[keys.size()][]));
        } catch (Exception e) {
            throw new RuntimeException("保存token属性失败", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * 获取token对应的存储key
     * @return
     */
    private String key(String userToken) {
        if (StringUtils.isEmpty(userToken)) {
            throw new RuntimeException("上下文中没有找到token");
        }
        return String.format(TOKEN_NAMESPACE, userToken);
    }
}
