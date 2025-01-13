package test.webmvc;

import com.iflove.simplespring.webmvc.DispatcherServlet;
import com.iflove.simplespring.webmvc.HandlerMapping;
import com.iflove.simplespring.webmvc.handler.AbstractHandlerMapping;
import com.iflove.simplespring.webmvc.handler.HandlerMethod;
import com.iflove.simplespring.webmvc.handler.RequestMappingHandlerMapping;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import test.webmvc.controller.DemoController;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class DispatchServletTests {


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
    public void simpleRequest() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest(getServletContext(), "GET", "/invalid.do");
        MockHttpServletResponse response = new MockHttpServletResponse();
        simpleDispatcherServlet.service(request, response);
        String content = response.getContentAsString();
        System.out.println(content);
        assertEquals("Hello, world!", content);
    }


    @Test
    public void initTest() {
        // 观察 HandlerMappings HandlerAdapters handlerExceptionResolvers 是否成功初始化
        System.out.println("init");
        simpleDispatcherServlet.destroy();
    }

}
