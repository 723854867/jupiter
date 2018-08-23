package org.jupiter.util.serializer;

public interface Serializer {

	byte[] serial(Object model);
	
	<ENTITY> ENTITY deserial(byte[] data, Class<ENTITY> clazz);
}
