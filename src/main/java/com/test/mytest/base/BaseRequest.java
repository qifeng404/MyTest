package com.test.mytest.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // 忽略多传的参数
public class BaseRequest {

}
