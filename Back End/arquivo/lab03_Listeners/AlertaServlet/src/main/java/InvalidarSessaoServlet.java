import java.io.IOException;
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


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperar a sessão atual
        HttpSession session = request.getSession(false);  // False significa que não cria uma nova sessão se não existir

        if (session != null) {
            // Invalida a sessão
            session.invalidate();
            response.getWriter().println("Sessão invalidada com sucesso!");
        } else {
            response.getWriter().println("Nenhuma sessão ativa para invalidar.");
        }
    }



}
