
package com.test.mytest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * 
 * @ClassName: CheckpreloadController
 * 
 * @Description: TODO(这里用一句话描述这个类的作用)
 * 
 * @author houqin.fhq houqin.fhq@alibaba-inc.com
 * 
 * @date 2016年3月27日 下午11:40:10
 *
 * 
 */
@Controller
@RequestMapping("/checkpreload.htm")
public class CheckpreloadController {

	@RequestMapping
	@ResponseBody
	public String getAll() {
		return "hello world";
	}

}
