package test.ioc;

import com.iflove.simplespring.context.ApplicationContext;
import com.iflove.simplespring.context.ConfigurableApplicationContext;
import com.iflove.simplespring.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;
import test.ioc.bean.Car;
import test.ioc.bean.Person;

import static org.junit.Assert.assertThat;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class ApplicationContextTest {

	@Test
	public void testApplicationContext() throws Exception {
		ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:ioc.xml");

		Person person = applicationContext.getBean("person", Person.class);
		//name属性在CustomBeanFactoryPostProcessor中被修改为 苍镜月(改)
		System.out.println(person);

		Car car = applicationContext.getBean("car", Car.class);
		//brand属性在CustomerBeanPostProcessor中被修改为 五菱宏光
		System.out.println(car);

		// 手动关闭，触发销毁方法
		applicationContext.close();
	}
}
