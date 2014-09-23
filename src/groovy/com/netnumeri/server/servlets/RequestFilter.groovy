package com.netnumeri.server.servlets

import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

public class RequestFilter implements Filter {

    private FilterConfig config;

    /** Creates new RequestFilter */
    public RequestFilter() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {

        this.config = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws java.io.IOException, ServletException {

        HttpServletRequestWrapper wrapper = null;
        ServletContext context = null;

        if (request instanceof HttpServletRequest)
            wrapper = new HttpServletRequestWrapper((HttpServletRequest) request);

        /*
        * use the ServletContext.log method to log param names/values
        */
        if (wrapper != null) {
            context = config.getServletContext();
            context.log("URI        : " + wrapper.getRequestURI());
            context.log("Method     : " + wrapper.getMethod());

            context.log("Headers :\n");
            Enumeration names = wrapper.getHeaderNames()
            while (names.hasMoreElements()) {
                String s = names.nextElement();
                context.log(s + " : " + wrapper.getHeader(s))
            }

            context.log("Parametri :\n");

            Enumeration names1 = wrapper.getParameterNames();
            while (names1.hasMoreElements()) {
                String s = names1.nextElement();
                context.log(s + " : " + wrapper.getParameter(s))
            }
        }

        //continue the request, response to next filter or servlet
        //destination
        if (wrapper != null)
            chain.doFilter(wrapper, response);
        else
            chain.doFilter(request, response);
    }

    public void destroy() {
        /*
        * called before the Filter instance is removed from service by the web
        * container
        */
    }
}

