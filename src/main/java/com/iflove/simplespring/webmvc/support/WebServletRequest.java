package com.iflove.simplespring.webmvc.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebServletRequest {

    final HttpServletRequest request;

    final HttpServletResponse response;

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public WebServletRequest(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
}
