import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AlertaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private String[] alertas = {"Alerta verde", "Alerta amarelo", "Alerta vermelho"};    
	
    public AlertaServlet() {
        super();
    }
    	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("Hello init");
		
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Hello service");
		
		switch(request.getMethod()) {
			case "GET":
				doGet(request,response);
				break;
				
			case "POST":
				doPost(request,response);
				break;
				
			default:
				System.out.println("Não tratamos este metodo: " + request.getMethod());
		}
	}
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Verifica o número de sessões ativas
        ServletContext context = getServletContext();
        Integer sessoesAtivas = (Integer) context.getAttribute("sessoesAtivas");

        // Inicializa sessoesAtivas caso ainda não tenha sido inicializado
        if (sessoesAtivas == null) {
            sessoesAtivas = 0;
            context.setAttribute("sessoesAtivas", sessoesAtivas);
        }

        if (sessoesAtivas >= 3) {
            // Redireciona o terceiro usuário para uma página de erro
            response.sendRedirect("erro.html");

            // Invalida a sessão do usuário após o redirecionamento
            HttpSession session = request.getSession(false); // Pega a sessão existente sem criar uma nova
            if (session != null) {
                session.invalidate();  // Invalida a sessão do usuário
            }

            return;
        }

        // Acesso à sessão atual
        HttpSession session = request.getSession();

        // Recupera o contador da sessão ou inicializa
        Integer contador = (Integer) session.getAttribute("contador");
        if (contador == null) {
            contador = 0;  // Inicia com 0
        }

        // Determina a mensagem de alerta com base no contador
        String alertaAtual = alertas[contador % 3];  // Usa o módulo para pegar o resto

        // Incrementa o contador e salva na sessão
        contador++;
        session.setAttribute("contador", contador);

        // Exibe a resposta HTML com a mensagem de alerta
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + alertaAtual + "</h1>");
        out.println("</body></html>");
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Hello doPost");
	}
	
	
	public void destroy() {
		System.out.println("Hello destroy");
	}
	

}
