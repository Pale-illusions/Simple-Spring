package test.ioc;

import com.iflove.simplespring.context.ApplicationContext;
import com.iflove.simplespring.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;
import test.ioc.bean.A;
import test.ioc.bean.B;


public class CircularReferenceWithoutProxyBeanTest {

	@Test
	public void testCircularReference() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:circular-reference-without-proxy-bean.xml");
		A a = applicationContext.getBean("a", A.class);
		B b = applicationContext.getBean("b", B.class);

		System.out.println(a.getB() == b);
		System.out.println(a.getB().getClass());
		System.out.println(b.getClass());
	}
}
