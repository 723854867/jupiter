package org.jupiter.bean;

import java.io.Serializable;

public interface Identifiable<KEY> extends Serializable {

	KEY key();
}
