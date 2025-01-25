package test.ioc;

import com.iflove.simplespring.context.ApplicationContext;
import com.iflove.simplespring.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;
import test.ioc.bean.HelloService;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class AwareInterfaceTest {

    @Test
    public void test() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:ioc.xml");
        HelloService helloService = applicationContext.getBean("helloService", HelloService.class);
        System.out.println(helloService.getApplicationContext());
        System.out.println(helloService.getBeanFactory());
    }
}
