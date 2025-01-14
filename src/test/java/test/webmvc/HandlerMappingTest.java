package test.webmvc;

import com.iflove.simplespring.webmvc.DispatcherServlet;
import com.iflove.simplespring.webmvc.HandlerExecutionChain;
import com.iflove.simplespring.webmvc.annotation.RequestMethod;
import com.iflove.simplespring.webmvc.handler.AbstractHandlerMapping;
import com.iflove.simplespring.webmvc.handler.HandlerMethod;
import com.iflove.simplespring.webmvc.handler.RequestMappingHandlerMapping;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import test.webmvc.controller.DemoController;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class HandlerMappingTest {
    private final MockServletConfig servletConfig = new MockServletConfig(new MockServletContext(), "simple");

    private DispatcherServlet simpleDispatcherServlet;


    @Before
    public void setup() throws ServletException {
        MockServletConfig complexConfig = new MockServletConfig(getServletContext(), "complex");
        complexConfig.addInitParameter("publishContext", "false");
        complexConfig.addInitParameter("class", "notWritable");
        complexConfig.addInitParameter("unknownParam", "someValue");
        complexConfig.addInitParameter("jakarta.servlet.http.legacyDoHead", "true");

        simpleDispatcherServlet = new DispatcherServlet();
        simpleDispatcherServlet.init(servletConfig);
    }

    private ServletContext getServletContext() {
        return servletConfig.getServletContext();
    }

    @Test
    public void pathTest() {

        MockHttpServletRequest request = new MockHttpServletRequest(getServletContext(), "GET", "/mapping/test");
        TestMapping mapping = new TestMapping();
        String path = mapping.initLookupPath(request);
        System.out.println(path);
        System.out.println(request.getRequestURI());
    }

    @Test
    public void mappingTest() throws Exception {
        AbstractHandlerMapping mapping = new RequestMappingHandlerMapping();
        DemoController demoController = new DemoController();
        Method method = demoController.getClass().getMethod("hello");
        HandlerMethod handlerMethod = new HandlerMethod(demoController, method);


        MockHttpServletRequest request = new MockHttpServletRequest(getServletContext(), "GET", "/demo/hello");

        handlerMethod.setPath(new TestMapping().initLookupPath(request));
        handlerMethod.setRequestMethods(new RequestMethod[]{RequestMethod.GET});

        mapping.register(handlerMethod);

        HandlerExecutionChain chain = mapping.getHandler(request);

        System.out.println(chain);
        System.out.println(chain.getHandlerMethod());
        System.out.println(chain.getHandlerInterceptors());
    }

    class TestMapping extends AbstractHandlerMapping {
        @Override
        protected HandlerMethod getHandlerInternal(HttpServletRequest request) throws Exception {
            return null;
        }

        @Override
        public String initLookupPath(HttpServletRequest request) {
            return super.initLookupPath(request);
        }

        @Override
        protected void detectHandlerMethod(String name) throws Exception {

        }

        @Override
        protected boolean isHandler(Class type) {
            return false;
        }
    }
}
