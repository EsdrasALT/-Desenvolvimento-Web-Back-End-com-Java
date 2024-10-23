

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class InvalidarSessaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public InvalidarSessaoServlet() {
        super();
    }


	public void init(ServletConfig config) throws ServletException {
	}

	public void destroy() {
	}


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalida a sessão atual
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            response.getWriter().println("Sessão invalidada com sucesso.");
        } else {
            response.getWriter().println("Nenhuma sessão ativa para invalidar.");
        }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
