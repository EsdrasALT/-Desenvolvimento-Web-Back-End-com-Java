
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ServletAlerta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private static final String[] ALERTAS = {"Alerta verde", "Alerta amarelo", "Alerta vermelho"};

    // Contador de sessões ativas
    protected static int sessoesAtivas = 0;
    private static final int MAX_SESSOES = 2;

    public ServletAlerta() {
        super();
    }


	public void init(ServletConfig config) throws ServletException {
	}

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
        // Verifica se já atingiu o número máximo de sessões
        HttpSession session = request.getSession();
        if (!session.isNew() && sessoesAtivas >= MAX_SESSOES) {
            response.getWriter().println("Número máximo de sessões atingido");
            return;
        }

        if (session.isNew()) {
            sessoesAtivas++;
        }

        // Obtem o índice da última mensagem
        Integer indiceAtual = (Integer) session.getAttribute("indiceAlerta");
        if (indiceAtual == null) {
            indiceAtual = 0;
        }

        // Exibe a mensagem atual
        response.setContentType("text/html");
        response.getWriter().println("<h1 style='color:" + getCorAlerta(indiceAtual) + "'>" + ALERTAS[indiceAtual] + "</h1>");

        // Atualiza o índice para a próxima mensagem
        indiceAtual = (indiceAtual + 1) % ALERTAS.length;
        session.setAttribute("indiceAlerta", indiceAtual);
        
	}
	
    private String getCorAlerta(int indice) {
        switch (indice) {
            case 0: return "green";
            case 1: return "yellow";
            case 2: return "red";
            default: return "black";
        }
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	

	public void destroy() {
        sessoesAtivas--;
	}


}
