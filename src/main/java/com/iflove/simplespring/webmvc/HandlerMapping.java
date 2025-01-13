package com.iflove.simplespring.webmvc;

import org.springframework.core.Ordered;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public interface HandlerMapping extends Ordered {

    HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception;

}
