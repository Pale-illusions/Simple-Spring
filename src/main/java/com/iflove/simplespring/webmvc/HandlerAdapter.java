package com.iflove.simplespring.webmvc;

import com.iflove.simplespring.webmvc.handler.HandlerMethod;
import org.springframework.core.Ordered;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public interface HandlerAdapter extends Ordered {

    boolean support(HandlerMethod handlerMethod);

    void handle(HttpServletRequest req, HttpServletResponse res, HandlerMethod handler) throws Exception;

}
