package test.boot.controller;

import com.iflove.simplespring.webmvc.annotation.*;
import test.boot.domain.User;

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

    @GetMapping("/path/{id}")
    public String path(@PathVariable("id") Integer id) {
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

    @PostMapping("/post/user")
    public User postUser(@RequestBody User user) {
        return user;
    }

    @PostMapping("/header")
    public String header(@RequestHeader String token, @RequestHeader Integer id) {
        System.out.println(token);
        System.out.println(id);
        return "token: " + token + ", id: " + id;
    }
}
