package com.terry.economicsimulator.models.input.expression;

import java.util.List;

public interface Expression<T> {
	T eq();
	T ne();
	T lt();
	T lte();
	T gt();
	T gte();
	List<T> in();
	List<T> notIn();
}
