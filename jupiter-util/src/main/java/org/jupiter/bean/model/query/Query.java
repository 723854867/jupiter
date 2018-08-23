package org.jupiter.bean.model.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jupiter.bean.model.Pair;

import lombok.Getter;


/**
 * 只支持简单的 and 和 or，不支持递归的 and 和 or
 * 
 * @author lynn
 *
 * @param <QUERY>
 */
@Getter
public class Query implements Serializable {

	private static final long serialVersionUID = 7213510348985683656L;

	private String andor;
	private boolean lock;
	private Integer page;
	private Integer limit;
	private Set<String> cols;
	private Integer pageSize;
	private String[] groupBys;
	private List<Condition> conditions;
	private List<Pair<String, Boolean>> orderBys;
	
	public Query() {
		this.cols = new HashSet<String>();
		this.orderBys = new ArrayList<Pair<String, Boolean>>();
	}
	
	public Query forUpdate() { 
		this.lock = true;
		return this;
	}
	
	public Query page(int page) { 
		this.page = page;
		return this;
	}
	
	public Query limit(int limit) { 
		this.limit = limit;
		return this;
	}
	
	public Query cols(String... cols) { 
		for (String col : cols)
			this.cols.add(col);
		return this;
	}
	
	public Query pageSize(int pageSize) { 
		this.pageSize = pageSize;
		return this;
	}
	
	
	public Query orderByAsc(String col) { 
		this.orderBys.add(new Pair<String, Boolean>(col, true));
		return this;
	}
	
	public Query orderByDesc(String col) { 
		this.orderBys.add(new Pair<String, Boolean>(col, false));
		return this;
	}
	
	public Query groupBy(String... cols) { 
		this.groupBys = cols;
		return this;
	}
	
	public Query or(Condition... conditions) { 
		this.andor = "OR";
		if (null == this.conditions)
			this.conditions = new ArrayList<Condition>();
		this.conditions.clear();
		for (Condition temp : conditions)
			this.conditions.add(temp);
		return this;
	}
	
	public Query or(Collection<Condition> conditions) { 
		this.andor = "OR";
		if (null == this.conditions)
			this.conditions = new ArrayList<Condition>();
		this.conditions.clear();
		this.conditions.addAll(conditions);
		return this;
	}
	
	public Query and(Condition... conditions) { 
		this.andor = "AND";
		if (null == this.conditions)
			this.conditions = new ArrayList<Condition>();
		this.conditions.clear();
		for (Condition temp : conditions)
			this.conditions.add(temp);
		return this;
	}
	
	public Query and(Collection<Condition> conditions) { 
		this.andor = "AND";
		if (null == this.conditions)
			this.conditions = new ArrayList<Condition>();
		this.conditions.clear();
		this.conditions.addAll(conditions);
		return this;
	}
}
