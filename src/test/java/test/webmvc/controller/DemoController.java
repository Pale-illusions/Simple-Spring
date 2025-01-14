package test.webmvc.controller;

import com.iflove.simplespring.beans.factory.annotation.Autowired;
import com.iflove.simplespring.stereotype.Controller;
import com.iflove.simplespring.webmvc.annotation.RequestMapping;
import com.iflove.simplespring.webmvc.annotation.RequestMethod;
import test.webmvc.service.DemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/query", requestMethod = RequestMethod.GET)
    public String query(HttpServletRequest request, HttpServletResponse response, String name) {
        return demoService.get(name);
    }

    @RequestMapping(value = "/hello", requestMethod = RequestMethod.GET)
    public String hello() {
        return "hello";
    }
}
