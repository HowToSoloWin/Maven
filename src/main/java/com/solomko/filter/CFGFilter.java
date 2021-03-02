package com.solomko.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AccessFilter", urlPatterns = {"/selectproduct", "/cart"})
public class CFGFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;

        if ((httpServletRequest.getSession().getAttribute("customer") == null &&
                req.getParameter("customer") == null || httpServletRequest.getSession().getAttribute("customer")==null&&
                req.getParameter("customer").equals("")||
                (req.getParameter("acceptTerms") == null &&
                        (httpServletRequest.getSession().getAttribute("acceptTerms") == null)))){
            ((HttpServletResponse) resp).sendError(401);
        } else {
            httpServletRequest.getSession().setAttribute("acceptTerms", "accepted");
            filterChain.doFilter(req, resp);
        }
    }
    @Override
    public void destroy() {

    }
}
