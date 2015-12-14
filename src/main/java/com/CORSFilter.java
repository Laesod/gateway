package com;

/*
 * #%L
 * Gateway
 * %%
 * Copyright (C) 2015 Powered by Sergey
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class CORSFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;

        if(req instanceof SecurityContextHolderAwareRequestWrapper){
            if(((SecurityContextHolderAwareRequestWrapper) req).getHeaders("Origin") != null && ((SecurityContextHolderAwareRequestWrapper) req).getHeaders("Origin").hasMoreElements()){
                String origin = ((SecurityContextHolderAwareRequestWrapper) req).getHeaders("Origin").nextElement().toString();

                if(origin.equals("http://localhost:63769") || origin.equals("http://localhost:1000") || origin.equals("http://localhost:1001")){
                    response.setHeader("Access-Control-Allow-Origin", origin);
                }
            }
        }

        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}
