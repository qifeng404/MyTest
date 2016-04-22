package com.test.mytest.controller;

import org.apache.commons.codec.CharEncoding;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.test.mytest.controller.UserController;
import com.test.mytest.service.UserQueryServiceImpl;

/**
 * Test for {@link UserController}.
 *
 * @author Bert Lee
 * @version 2014-8-19
 */

public class UserControllerTest extends AbstractTestControllerTest{

	private MockMvc mvc;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
	}

	// mocked service (被依赖的服务)
	private UserQueryServiceImpl userQueryService;

	@Test
	public void getUserName() throws Exception {
		// 1. 定义"被依赖的服务"的方法行为
		Object[] params = new Object[] { "1" };
		String userName = "qifeng";
		String expectedContent = "{\"name\":\"qifeng\"}";

		userQueryService = PowerMockito.mock(UserQueryServiceImpl.class);
		PowerMockito.whenNew(UserQueryServiceImpl.class).withNoArguments().thenReturn(userQueryService);
		PowerMockito.when(this.userQueryService.getUserName(1)).thenReturn(userName);
		this.getMock(mvc, "/user/get_user_name?id={id}", params, expectedContent);
	}

	@Test
	public void updateUserName() throws Exception {
		// 1. 定义"被依赖的服务"的方法行为
		String paramsJson = "{\"id\":23,\"name\":\"Bert Lee\"}";
		Integer result = 0;
		String expectedContent = "{\"ret\":0,\"ret_msg\":\"ok\"}";
		userQueryService = PowerMockito.mock(UserQueryServiceImpl.class);
		PowerMockito.whenNew(UserQueryServiceImpl.class).withNoArguments().thenReturn(userQueryService);
		PowerMockito.when(this.userQueryService.updateUserName(23, "Bert Lee")).thenReturn(result);
		this.postMock(mvc, "/user/update_user_name", paramsJson, expectedContent);
	}

	/**
	 * Mocks the GET request.
	 * 
	 * @param url
	 * @param params
	 * @param expectedContent
	 * @throws Exception
	 */
	protected void getMock(MockMvc mockMvc, String url, Object[] params, String expectedContent) throws Exception {
		// 2. 构造GET请求
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(url, params);

		this.jsonRequestMock(mockMvc, requestBuilder, expectedContent);
	}

	/**
	 * Mocks the POST request.
	 * 
	 * @param url
	 * @param paramsJson
	 * @param expectedContent
	 * @throws Exception
	 */
	protected void postMock(MockMvc mockMvc, String url, String paramsJson, String expectedContent) throws Exception {
		// 2. 构造POST请求
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).content(paramsJson) // 设置请求体，服务于"@RequestBody"
		;

		this.jsonRequestMock(mockMvc, requestBuilder, expectedContent);
	}

	/**
	 * Mocks the request for "application/json;charset=UTF-8" Content-Type.
	 * 
	 * @param requestBuilder
	 * @param expectedContent
	 * @throws Exception
	 */
	private void jsonRequestMock(MockMvc mockMvc, MockHttpServletRequestBuilder requestBuilder, String expectedContent)
			throws Exception {
		// 2. 设置HTTP请求属性
		requestBuilder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.characterEncoding(CharEncoding.UTF_8);
		// 3. 定义期望响应行为
		mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()) // 打印整个请求与响应细节
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string(expectedContent)) // 校验是否是期望的结果
		;
	}

}
