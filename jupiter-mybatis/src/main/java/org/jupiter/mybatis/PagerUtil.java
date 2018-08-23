package org.jupiter.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.jupiter.bean.model.Pager;

import com.github.pagehelper.Page;

public class PagerUtil {

	public static final <T> Pager<T> page(List<T> list) {
		Pager<T> pager = new Pager<T>();
		if (list instanceof Page) {
			List<T> l = new ArrayList<T>(list.size());
			list.forEach(item -> l.add(item));
			Page<T> page = (Page<T>) list;
			pager.setPages(page.getPages());
			pager.setTotal(page.getTotal());
			pager.setList(l);
			page.close();
		} else
			pager.setList(list);
		return pager;
	}
}
