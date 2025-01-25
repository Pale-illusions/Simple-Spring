package test.ioc;

import com.iflove.simplespring.context.ApplicationContext;
import com.iflove.simplespring.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;
import test.ioc.bean.A;
import test.ioc.bean.B;

public class CircularReferenceWithProxyBeanTest {

	@Test
	public void testCircularReference() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:circular-reference-with-proxy-bean.xml");
		A a = applicationContext.getBean("a", A.class);
		B b = applicationContext.getBean("b", B.class);

		System.out.println(b.getA() == a);
		System.out.println(b.getA().getClass());
		System.out.println(a.getClass());
	}
}
