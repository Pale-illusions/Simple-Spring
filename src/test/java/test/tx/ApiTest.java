package test.tx;


import com.alibaba.druid.pool.DruidDataSource;
import com.iflove.simplespring.aop.AdvisedSupport;
import com.iflove.simplespring.aop.TargetSource;
import com.iflove.simplespring.aop.framework.CglibAopProxy;
import com.iflove.simplespring.context.support.ClassPathXmlApplicationContext;
import com.iflove.simplespring.jdbc.datasource.DataSourceTransactionManager;
import com.iflove.simplespring.jdbc.support.JdbcTemplate;
import com.iflove.simplespring.tx.annotation.AnnotationTransactionAttributeSource;
import com.iflove.simplespring.tx.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import com.iflove.simplespring.tx.interceptor.TransactionAttribute;
import com.iflove.simplespring.tx.interceptor.TransactionInterceptor;
import org.junit.Before;
import org.junit.Test;
import test.tx.service.JdbcService;
import test.tx.service.impl.JdbcServiceImpl;

import javax.sql.DataSource;
import java.lang.reflect.Method;

public class ApiTest {

    private JdbcTemplate jdbcTemplate;

//    private JdbcService jdbcService;

    private DataSource dataSource;

    @Before
    public void init() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:tx.xml");
        jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
        dataSource = applicationContext.getBean(DruidDataSource.class);

//        jdbcService = applicationContext.getBean(JdbcServiceImpl.class);
    }

    @Test
    public void matchTransactionAnnotationTest() {
        JdbcService jdbcService = new JdbcServiceImpl();
        AnnotationTransactionAttributeSource transactionAttributeSource = new AnnotationTransactionAttributeSource();
        Method[] methods = jdbcService.getClass().getMethods();
        Method targetMethod = null;
        for (Method method : methods) {
            if (method.getName().equals("saveData")) {
                targetMethod = method;
                break;
            }
        }
        TransactionAttribute transactionAttribute = transactionAttributeSource.getTransactionAttribute(targetMethod, jdbcService.getClass());
        System.out.println(transactionAttribute.getName());
    }

    @Test
    public void jdbcWithTransaction() {

        JdbcService jdbcService = new JdbcServiceImpl();

        AnnotationTransactionAttributeSource transactionAttributeSource = new AnnotationTransactionAttributeSource();
        transactionAttributeSource.findTransactionAttribute(jdbcService.getClass());


        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        TransactionInterceptor interceptor = new TransactionInterceptor(transactionManager, transactionAttributeSource);

        BeanFactoryTransactionAttributeSourceAdvisor btas = new BeanFactoryTransactionAttributeSourceAdvisor();
        btas.setAdvice(interceptor);


        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(jdbcService));
        advisedSupport.addAdvisor(btas);
        advisedSupport.setProxyTargetClass(false);

        JdbcService proxyCglib = (JdbcServiceImpl) new CglibAopProxy(advisedSupport).getProxy();

        // Fixme: 事务注解失效
        proxyCglib.saveData(jdbcTemplate);
    }


}

