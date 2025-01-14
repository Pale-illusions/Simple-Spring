package test.webmvc;

import com.iflove.simplespring.webmvc.annotation.*;
import com.iflove.simplespring.webmvc.handler.HandlerMethod;
import com.iflove.simplespring.webmvc.metod.annotation.*;
import com.iflove.simplespring.webmvc.metod.support.HandlerMethodArgumentResolver;
import com.iflove.simplespring.webmvc.support.WebServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPart;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class HandlerMethodArgumentResolverTest {

    HandlerMethodArgumentResolverComposite resolver;
    ConversionService conversionService;

    @Before
    public void setup() {
        conversionService = new DefaultConversionService();

        resolver = new HandlerMethodArgumentResolverComposite();

        resolver.addResolvers(getDefaultArgumentResolver());
    }

    public List<HandlerMethodArgumentResolver> getDefaultArgumentResolver() {
        final List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
        resolvers.add(new PathVariableMethodArgumentResolver());
        resolvers.add(new PathVariableMapMethodArgumentResolver());
        resolvers.add(new RequestCookieMethodArgumentResolver());
        resolvers.add(new RequestHeaderMethodArgumentResolver());
        resolvers.add(new RequestHeaderMapMethodArgumentResolver());
        resolvers.add(new RequestPartMethodArgumentResolver());
        resolvers.add(new RequestParamMethodArgumentResolver());
        resolvers.add(new RequestParamMapMethodArgumentResolver());
        resolvers.add(new RequestRequestBodyMethodArgumentResolver());
        resolvers.add(new ServletRequestMethodArgumentResolver());
        resolvers.add(new ServletResponseMethodArgumentResolver());
        return resolvers;
    }

    @Test
    public void resolveTest() throws Exception {
        HttpServletRequest request = mockRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        HandlerMethod handlerMethod = new HandlerMethod(new Controller(), Controller.class.getMethod("test", String.class, String.class, int.class, String.class, MultipartFile.class, int.class, String.class, String.class, HttpServletRequest.class, User.class, User.class));
        handlerMethod.setRequestMethods(new RequestMethod[]{RequestMethod.GET});

        for (MethodParameter parameter : handlerMethod.getParameters()) {
            String annotations = Arrays.stream(parameter.getParameterAnnotations()).map(a -> a.annotationType().getSimpleName()).collect(Collectors.joining());
            String str = annotations.length() > 0 ? " @" + annotations + " " : " ";

            if (resolver.supportsParameter(parameter)) {
                Object object = resolver.resolveArgument(parameter, handlerMethod, new WebServletRequest(request, response), conversionService);

                System.out.println("[" + parameter.getParameterIndex() + "] " + str + parameter.getParameterType().getSimpleName() + " " + parameter.getParameterName() + "->" + object);
            } else {
                System.out.println("[" + parameter.getParameterIndex() + "] " + str + parameter.getParameterType().getSimpleName() + " " + parameter.getParameterName());
            }
        }

    }


    private static HttpServletRequest mockRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name1", "zhangsan");
        request.setParameter("name2", "lisi");
        request.addPart(new MockPart("file", "abc", "hello".getBytes(StandardCharsets.UTF_8)));
        Map<String, String> map = new AntPathMatcher().extractUriTemplateVariables("/test/{id}", "/test/123");
        System.out.println(map);
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, map);
        request.setContentType("application/json");
        request.setCookies(new Cookie("token", "123456"));
        request.setParameter("name", "张三");
        request.setParameter("age", "18");
        String json = "{\n" +
                "    \"name\":\"李四\",\n" +
                "    \"age\":20\n" +
                "}";
        request.setContent(json.getBytes(StandardCharsets.UTF_8));

        return new StandardServletMultipartResolver().resolveMultipart(request);
    }

    static class Controller {
        public void test(
                @RequestParam("name1") String name1, // name1=张三
                String name2,                        // name2=李四
                @RequestParam("age") int age,        // age=18
                @RequestParam(value = "home") String home1, // spring 获取数据
                @RequestParam("file") MultipartFile file, // 上传文件
                @PathVariable("id") int id,               //  /test/124   /test/{id}
                @RequestHeader("Content-Type") String header,
                @com.iflove.simplespring.webmvc.annotation.Cookie("token") String token,
                HttpServletRequest request,          // request, response, session ...
                User user2,                          // name=zhang&age=18
                @RequestBody User user3              // json
        ) {
        }
    }

    static class User {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
