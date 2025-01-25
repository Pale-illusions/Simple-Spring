package test.ioc;

import com.iflove.simplespring.context.ApplicationContext;
import com.iflove.simplespring.context.ConfigurableApplicationContext;
import com.iflove.simplespring.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;
import test.ioc.event.CustomEvent;

public class EventAndEventListenerTest {

	@Test
	public void testEventListener() throws Exception {
		ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:event-and-event-listener.xml");

		applicationContext.publishEvent(new CustomEvent(applicationContext));

		// 关闭容器触发事件
		applicationContext.close();
	}
}
