package test.aop;

import com.iflove.simplespring.context.ApplicationContext;
import com.iflove.simplespring.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;
import test.aop.bean.WorldService;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class AutoProxyTest {

    @Test
    public void testAutoProxy() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:aop.xml");

        WorldService proxy = applicationContext.getBean(WorldService.class);
        proxy.explode();
        System.out.println(proxy.getNumber());
    }
}
