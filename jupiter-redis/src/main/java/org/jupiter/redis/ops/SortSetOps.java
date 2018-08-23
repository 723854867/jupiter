package org.jupiter.redis.ops;

import static org.jupiter.redis.RedisUtil.encode;

import java.util.Set;

import org.jupiter.callback.Callback;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

@Component
public class SortSetOps extends Ops {

	public Set<byte[]> zrange(Object key, long start, long end) {
		return invoke(new Callback<Jedis, Set<byte[]>>() {
			@Override
			public Set<byte[]> invoke(Jedis jedis) {
				return jedis.zrange(encode(key), start, end);
			}
		});
	}
}
