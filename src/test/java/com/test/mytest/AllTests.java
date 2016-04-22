package com.test.mytest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.test.mytest.controller.ControllerAllTests;
import com.test.mytest.service.powermock.ServiceAllTests;

@RunWith(Suite.class)
@SuiteClasses({ControllerAllTests.class, ServiceAllTests.class})
public class AllTests {

}
