

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class LoginFilter extends HttpFilter implements Filter {

    public LoginFilter() {
        super();

    }


	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession(false);
		if (session == null) {
			RequestDispatcher rd = request.getRequestDispatcher("LoginServlet");
			rd.forward(request,response);
		}
		else
			chain.doFilter(request,response);
	}


	public void init(FilterConfig fConfig) throws ServletException {

	}

}
