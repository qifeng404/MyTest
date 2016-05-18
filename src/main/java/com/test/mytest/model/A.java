package com.test.mytest.model;

import javax.annotation.Resource;

public class A {
	
	@Resource
	public B b;

	public String func1() {
		if (b.isTrue()) {
			return b.invoke();
		}

		if (C.isTrue()) {
			return C.invoke();
		}

		return func2() + ";" + func3();
	}

	private String func2() {
        return "A func2";
    }
	
	public String func3() {
		 return "A func3";
    }
}
