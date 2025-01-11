package test.webmvc.controller;

import com.iflove.simplespring.beans.factory.annotation.Autowired;
import com.iflove.simplespring.stereotype.Controller;
import com.iflove.simplespring.webmvc.annotation.RequestMapping;
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

    @RequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response, String name) {
        return demoService.get(name);
    }
}
