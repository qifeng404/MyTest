package com.test.mytest.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ DrvierControllerTest.class, GreetingControllerTest.class, UserControllerTest.class})
public class ControllerAllTests {

}
