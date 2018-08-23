package org.jupiter.util.spring;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.jupiter.util.JupiterConsts;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class ConfigLoader {
	
	public static final ConfigurableConversionService CONVERTER = new DefaultConversionService();
	public static final PathMatchingResourcePatternResolver RESOLVER = new PathMatchingResourcePatternResolver();
	
	public static final Resource[] getResources(String locationPattern) {
		try {
			return RESOLVER.getResources(locationPattern);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static final ConfigMap load(String path) {
		ConfigMap map = new ConfigMap();
		try {
			Resource[] resources = RESOLVER.getResources(path);
			for (Resource resource : resources) {
				InputStream in = resource.getInputStream();
				Properties properties = new Properties();
				properties.load(new InputStreamReader(in, JupiterConsts.UTF_8));
				properties.entrySet().forEach(item -> map.put(item.getKey().toString().trim(), item.getValue()));
				in.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return map;
	}
}
