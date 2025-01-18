package test.boot.controller;

import com.iflove.simplespring.webmvc.annotation.*;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@RestController
@RequestMapping("/demo")
public class TestController {

    @RequestMapping("/test")
    public String test() {
        System.out.println("test");
        return "Hello, World";
    }

    @GetMapping("/param/{id}")
    public String param(@PathVariable("id") Integer id) {
        System.out.println(id);
        System.out.println(id.getClass());
        return "这是一个 PathVariable -> " + id;
    }

    @GetMapping("/bool")
    public String bool(@RequestParam("b") Boolean b) {
        System.out.println(b.getClass());
        if (b) {
            System.out.println(b);
            return "success";
        } else {
            System.out.println(b);
            return "fail";
        }
    }
}
