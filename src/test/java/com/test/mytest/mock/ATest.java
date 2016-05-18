package com.test.mytest.mock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.test.mytest.model.A;
import com.test.mytest.model.B;
import com.test.mytest.model.C;

@RunWith(PowerMockRunner.class)
//如果要mock static, private函数，必须要有@PrepareForTest()
@PrepareForTest({C.class, A.class})   
public class ATest {

  @Test
  public void testFunc1() throws Exception {
	  A a = PowerMockito.spy(new A());
      //mock掉注入的bean B
      B b = PowerMockito.mock(B.class);
//      //mock B的函数调用返回值
      PowerMockito.when(b.isTrue()).thenReturn(true);
      PowerMockito.when(b.invoke()).thenReturn("调用B的invoke()");
      //替换掉b
      MemberModifier.field(com.test.mytest.model.A.class, "b").set(a, b);

      //mock static函数
      PowerMockito.mockStatic(C.class);
      PowerMockito.when(C.isTrue()).thenReturn(true);
      PowerMockito.when(C.invoke()).thenReturn("调用C的invoke()");

      //mock private函数
      PowerMockito.doReturn("调用A的func2()").when(a, "func2");

      //mock friendly函数
      PowerMockito.doReturn("调用A的func3()").when(a).func3();

      //=== 调用被测试函数  ======
      String result = a.func1();
      Assert.assertEquals("调用B的invoke()", result);

      //修改mock值，调用不同逻辑
      PowerMockito.when(b.isTrue()).thenReturn(false);
      result = a.func1();
      Assert.assertEquals("调用C的invoke()", result);

      PowerMockito.when(C.isTrue()).thenReturn(false);
      result = a.func1();
      Assert.assertEquals("调用A的func2();调用A的func3()", result);
  }

}
