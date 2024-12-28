package test.lazy;

import com.iflove.simplespring.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;
import test.lazy.bean.Car;

import java.util.concurrent.TimeUnit;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class LazyInitTest {

    @Test
    public void testLazyInit() throws InterruptedException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:lazy-test.xml");
        System.out.println(System.currentTimeMillis() + ":applicationContext-over");
        TimeUnit.SECONDS.sleep(1);
        Car c = (Car) applicationContext.getBean("car");
        c.showTime();//显示bean的创建时间
    }
}
