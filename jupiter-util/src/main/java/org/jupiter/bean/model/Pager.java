package org.jupiter.bean.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pager<T> implements Serializable {

	private static final long serialVersionUID = 387439537008227162L;
	
	private long total;			// 总数据
	private long pages;			// 总页数
	private List<T> list;
	
	public static final <T> Pager<T> empty() {
		return new Pager<T>();
	}
}
