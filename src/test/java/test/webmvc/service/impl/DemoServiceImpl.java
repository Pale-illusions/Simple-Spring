package test.webmvc.service.impl;

import test.webmvc.service.DemoService;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class DemoServiceImpl implements DemoService {
    @Override
    public String get(String name) {
        System.out.println("service 实现类中的name参数：" + name) ;
        return name;
    }
}
