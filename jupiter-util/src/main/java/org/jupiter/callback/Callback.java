package org.jupiter.callback;

public interface Callback<P, V> {

	V invoke(P param);
}

