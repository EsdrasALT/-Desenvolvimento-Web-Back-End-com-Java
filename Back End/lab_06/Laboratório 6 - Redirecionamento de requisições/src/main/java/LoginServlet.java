import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalidate session if it exists
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Display login form
        out.println("<html><body>");
        out.println("<form action='LoginServlet' method='POST'>");
        out.println("Código: <input type='text' name='codigo'><br>");
        out.println("Senha: <input type='password' name='senha'><br>");
        out.println("<input type='submit' value='Login'>");
        out.println("</form>");
        out.println("</body></html>");
        
        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigo = request.getParameter("codigo");
        String senha = request.getParameter("senha");
        
        // Check if credentials are valid
        if ("usuario123".equals(codigo) && "senha123".equals(senha)) {
            // Create a new session and redirect to PotenciaServlet
            HttpSession session = request.getSession(true);
            session.setAttribute("user", codigo);
            response.sendRedirect("PotenciaServlet");
        } else {
            // Display an error message for invalid credentials
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h3>Credenciais inválidas. Tente novamente.</h3>");
            out.println("<a href='LoginServlet'>Voltar ao Login</a>");
            out.println("</body></html>");
            out.close();
        }
    }
}
