
package switchboard.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import switchboard.models.Administrator;


@WebFilter(filterName = "IsAuthFilter", urlPatterns = {"/login"})
public class IsAuthFilter implements Filter {
    
    public IsAuthFilter() {
    }    
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        
        HttpSession session = httpReq.getSession();
        
        Administrator user = (Administrator)session.getAttribute("user");
        
        if (user != null) {
            httpResp.sendRedirect("departments");
        } else {
            chain.doFilter(request, response);
        }
    }
     
    @Override
    public void destroy() {
         
    }   
    
}

