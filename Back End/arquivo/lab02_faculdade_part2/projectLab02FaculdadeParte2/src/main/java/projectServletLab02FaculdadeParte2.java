

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class projectServletLab02FaculdadeParte2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public projectServletLab02FaculdadeParte2() {
        super();
    }


	public void init(ServletConfig config) throws ServletException {
	}


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		switch (request.getMethod()) {
    	case "GET":
    		doGet(request, response);
    		break;
    		
    	case "POST":
    		doPost(request, response);	
    		break;
    		
    	default:
    		System.out.println("O sistema não trata request tipo: " + request.getMethod() + " ...desculpe =(");
    	}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Hello doGet");	
		String mensagem = "";
	    gerarHTML(response, mensagem);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String operando1Param = request.getParameter("operando1");
	    String operando2Param = request.getParameter("operando2");
	    double operando1 = 0, operando2 = 0;
	    String operador;
	    String mensagem;

	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();

	    if (operando1Param == null || operando1Param.isEmpty()) {
		    mensagem = "Erro: O campo do primeiro operando está vazio.";
	    	gerarHTML(response, mensagem);
	        return;
	    }
	    if (operando2Param == null || operando2Param.isEmpty()) {
		    mensagem = "Erro: O campo do segundo operando está vazio.";
	    	gerarHTML(response, mensagem);
	        return;
	    }

	    try {
	        operando1 = Double.parseDouble(operando1Param);
	    } catch (NumberFormatException e) {
		    mensagem = "Erro: O primeiro operando deve ser um número válido.";
	    	gerarHTML(response, mensagem);
	        return;
	    }

	    try {
	        operando2 = Double.parseDouble(operando2Param);
	    } catch (NumberFormatException e) {
		    mensagem = "Erro: O segundo operando deve ser um número válido.";
	    	gerarHTML(response, mensagem);
	        return;
	    }

	    // Validação do operador
	    String operadorParam = request.getParameter("operador");
	    if (operadorParam != null && (operadorParam.equals("+") || operadorParam.equals("-") || operadorParam.equals("*") || operadorParam.equals("/"))) {
	        operador = operadorParam;
	    } else {
		    mensagem = "Erro: Operador inválido. Escolha entre +, -, * ou /.";
	    	gerarHTML(response, mensagem);	    	
	        return;
	    }

	    // Se o operador for divisão, evitar divisão por zero
	    if (operador.equals("/") && operando2 == 0) {
		    mensagem = "Erro: Não é possível dividir por zero.";
	    	gerarHTML(response, mensagem);
	        return;
	    }

	    // Realiza o cálculo
	    String operadorDoPost = operadorEh(operador);
	    double resultadoDoPost = calcular(operando1, operando2, operadorDoPost);
	    mensagem = "é " + resultadoDoPost;
	   

	    // Exibe o resultado
	    gerarHTML(response, mensagem);
	}
	
	protected void gerarHTML(HttpServletResponse response, String mensagem) throws ServletException, IOException {
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();	    
	    
	    out.println("<!DOCTYPE html>");
	    out.println("<html>");
	    out.println("<head>");
	    out.println("<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>");
	    out.println("<title>Calculadora</title>");
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<h1>Calculadora</h1>");
	    
	    out.println("<form action='projServLab02FaculPart2' method='post'>");
	    out.println("<input type='text' name='operando1'>");
	    out.println("<select name='operador'>");
	    out.println("<option>+</option>");
	    out.println("<option>-</option>");
	    out.println("<option>*</option>");
	    out.println("<option>/</option>");
	    out.println("</select>");
	    out.println("<input type='text' name='operando2'>");
	    out.println("<input type='submit' value='Enviar Consulta'>");
	    out.println("</form>");
	    
	    out.println("<h2>Resultado -> " + mensagem + "</h2>");
	    
	    out.println("</body>");
	    out.println("</html>");	    
	}
	
	private String operadorEh(String operador) {
		
		String mensagem = "";
		
		switch (operador) {
			case "+":
				mensagem = "soma";
				break;
				
			case "-":
				mensagem = "substração";
				break;
				
			case "*":
				mensagem = "multiplicação";
				break;
				
			case "/":
				mensagem = "divisão";
				break;
		}	
		
		return mensagem;		
	}
	
	private double calcular(double operadorA, double operadorB, String operandoC){
		double resultado = 0.0;		
		
		switch (operandoC) {
			case "soma":
				resultado = operadorA + operadorB;
				break;
				
			case "substração":
				resultado = operadorA - operadorB;
				break;
				
			case "multiplicação":
				resultado = operadorA * operadorB;
				break;
				
			case "divisão":
				resultado = operadorA / operadorB;
				break;
		}						
		return resultado;
	}
		
	public void destroy() {
		System.out.println("Hello destroy");

	}

}
