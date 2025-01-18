package test.boot.config;

import com.iflove.simplespring.webmvc.annotation.EnableWebMvc;
import com.iflove.simplespring.webmvc.intercpetor.InterceptorRegistry;
import com.iflove.simplespring.webmvc.support.WebMvcConfigurer;
import org.springframework.stereotype.Component;
import test.boot.interceptor.DemoInterceptor;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@EnableWebMvc
@Component
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addIntercept(InterceptorRegistry registry) {
        registry.addInterceptor(new DemoInterceptor()).addIncludePatterns("/demo/**").addExcludePatterns("/demo/test");
    }
}
