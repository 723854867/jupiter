package org.jupiter.redis.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jupiter.bean.Identifiable;
import org.jupiter.redis.RedisUtil;
import org.jupiter.redis.ops.HashOps;
import org.jupiter.util.serializer.Serializer;

/**
 * 一个 reids key 存放多个数据，一般是一个hash存放多个对象，hash的key是ENTITY的key，value是对象的序列化数据
 * 
 * @author lynn
 *
 * @param <KEY>
 * @param <ENTITY>
 */
public class OTMRedisDao<KEY, ENTITY extends Identifiable<KEY>> implements RedisDao<KEY, ENTITY> {

	protected byte[] key;
	@Resource
	protected HashOps hashOps;
	protected Class<ENTITY> clazz;
	protected Serializer serializer;
	
	@SuppressWarnings("unchecked")
	public OTMRedisDao(String key, Serializer serializer) {
		this.serializer = serializer;
		this.key = RedisUtil.encode(key);
		Type superType = getClass().getGenericSuperclass();   
		Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();  
		this.clazz = (Class<ENTITY>) generics[1];
	}

	@Override
	public boolean add(ENTITY entity) {
		byte[] data = serializer.serial(entity);
		long value = hashOps.set(key, entity.key(), data);
		return 1 == value;
	}
	
	@Override
	public void add(Collection<ENTITY> entities) { 
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (ENTITY entity : entities) 
			map.put(entity.key(), serializer.serial(entities));
		hashOps.mset(key, map);
	}
	
	@Override
	public ENTITY getByKey(KEY key) {
		byte[] data = hashOps.get(this.key, key);
		return null == data ? null : serializer.deserial(data, clazz);
	}
	
	@Override
	public Map<KEY, ENTITY> getByKeys(Collection<KEY> keys) {
		List<byte[]> list = hashOps.mget(key, keys.toArray());
		Map<KEY, ENTITY> map = new HashMap<KEY, ENTITY>();
		for (byte[] data : list) {
			if (null == data)
				continue;
			ENTITY entity = serializer.deserial(data, clazz);
			map.put(entity.key(), entity);
		}
		return map;
	}
	
	@Override
	public long deleteByKey(KEY key) {
		return hashOps.del(this.key, key);
	}
	
	@Override
	public long deleteByKeys(Collection<KEY> keys) {
		return hashOps.del(this.key, keys.toArray());
	}
}
