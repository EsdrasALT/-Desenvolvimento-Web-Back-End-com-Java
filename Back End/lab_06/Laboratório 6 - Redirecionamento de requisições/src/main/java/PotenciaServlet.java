import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet("/PotenciaServlet")
public class PotenciaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            double base = Double.parseDouble(request.getParameter("base"));
            int exponent = Integer.parseInt(request.getParameter("exponent"));
            double result = calculatePower(base, exponent);
            
            out.println("<html><body>");
            out.println("<h2>Resultado: " + base + "^" + exponent + " = " + result + "</h2>");
            out.println("</body></html>");
        } catch (NumberFormatException e) {
            out.println("<html><body>");
            out.println("<h2>Erro: Insira valores válidos para a base e o expoente.</h2>");
            out.println("</body></html>");
        } finally {
            out.close();
        }
    }

    private double calculatePower(double x, int n) {
        if (n == 0) {
            return 1;
        } else if (n < 0) {
            return 1 / calculatePower(x, -n);
        } else if (n % 2 == 0) {
            double halfPower = calculatePower(x, n / 2);
            return halfPower * halfPower;
        } else {
            return x * calculatePower(x, n - 1);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<form action='PotenciaServlet' method='POST'>");
        out.println("Base: <input type='text' name='base'><br>");
        out.println("Expoente: <input type='text' name='exponent'><br>");
        out.println("<input type='submit' value='Calcular'>");
        out.println("</form>");
        out.println("</body></html>");
        
        out.close();
    }
}
