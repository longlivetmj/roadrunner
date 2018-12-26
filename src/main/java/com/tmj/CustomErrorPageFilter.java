package com.tmj;

import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class CustomErrorPageFilter extends ErrorPageFilter implements ErrorViewResolver {

    @Override
    public void addErrorPages(ErrorPage... errorPages) {
        super.addErrorPages(errorPages);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
    }

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest httpServletRequest, HttpStatus httpStatus, Map<String, Object> map) {

        return null;
    }
}
