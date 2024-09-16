

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class projectServletLab02Faculdade extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
    public projectServletLab02Faculdade() {
        super();
    }


	public void init(ServletConfig config) throws ServletException {
		super.init(config);				
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Hello doGet");
	}

//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//	    String operando1Param = request.getParameter("operando1");
//	    String operando2Param = request.getParameter("operando2");
//	    double operando1,operando2;
//	    String operador;
//
//	    try {
//	        operando1 = Double.parseDouble(operando1Param);
//	        operando2 = Double.parseDouble(operando2Param);
//	    } catch (NumberFormatException e) {
//    		System.out.println("Parâmetro inválido; usando valores default");
//			operando1 = OPERANDO_DEFAULT;
//			operando2 = OPERANDO_DEFAULT;  
//	    }	
//	    
//	    String operadorParam = request.getParameter("operador");
//    	if (operadorParam != null && 
//    	   (operadorParam.equals("+") || 
//    	    operadorParam.equals("-") || 
//    	    operadorParam.equals("*") || 
//    	    operadorParam.equals("/"))) {
//    	    operador = operadorParam; 
//    	} else {
//    	    System.out.println("Operador inválido; usando operador default");
//    	    operador = OPERADOR_DEFAULT;  
//    	}
//	    
//	    String operadorDoPost = operadorEh(operador);
//	    double resultadoDoPost = calcular(operando1, operando2, operadorDoPost);
//	    
//		exibe(response,operadorDoPost, resultadoDoPost);
//	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String operando1Param = request.getParameter("operando1");
	    String operando2Param = request.getParameter("operando2");
	    double operando1 = 0, operando2 = 0;
	    String operador;

	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();

	    if (operando1Param == null || operando1Param.isEmpty()) {
	        out.println("Erro: O campo do primeiro operando está vazio.");
	    	out.println("<p/><a href='html/calculadora.html'>Voltar</a>");
	        return;
	    }
	    if (operando2Param == null || operando2Param.isEmpty()) {
	        out.println("Erro: O campo do segundo operando está vazio.");
	    	out.println("<p/><a href='html/calculadora.html'>Voltar</a>");
	        return;
	    }

	    try {
	        operando1 = Double.parseDouble(operando1Param);
	    } catch (NumberFormatException e) {
	        out.println("Erro: O primeiro operando deve ser um número válido.");
	    	out.println("<p/><a href='html/calculadora.html'>Voltar</a>");
	        return;
	    }

	    try {
	        operando2 = Double.parseDouble(operando2Param);
	    } catch (NumberFormatException e) {
	        out.println("Erro: O segundo operando deve ser um número válido.");
	    	out.println("<p/><a href='html/calculadora.html'>Voltar</a>");
	        return;
	    }

	    // Validação do operador
	    String operadorParam = request.getParameter("operador");
	    if (operadorParam != null && (operadorParam.equals("+") || operadorParam.equals("-") || operadorParam.equals("*") || operadorParam.equals("/"))) {
	        operador = operadorParam;
	    } else {
	        out.println("Erro: Operador inválido. Escolha entre +, -, * ou /.");
	    	out.println("<p/><a href='html/calculadora.html'>Voltar</a>");
	        return;
	    }

	    // Se o operador for divisão, evitar divisão por zero
	    if (operador.equals("/") && operando2 == 0) {
	        out.println("Erro: Não é possível dividir por zero.");
	    	out.println("<p/><a href='html/calculadora.html'>Voltar</a>");
	        return;
	    }

	    // Realiza o cálculo
	    String operadorDoPost = operadorEh(operador);
	    double resultadoDoPost = calcular(operando1, operando2, operadorDoPost);

	    // Exibe o resultado
	    exibe(response, operadorDoPost, resultadoDoPost);
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
	
    private void exibe(HttpServletResponse response, String operadorExibe, double resultadoExibe) throws IOException {
    	
    	PrintWriter out = response.getWriter();
    	response.setContentType("text/html");
    	
    	out.println("<html><head><title>");
    	out.println("Resposta");
    	out.println("</title></head><body>");
//    	out.println("<h1>A operação de " + operadorExibe + " entre " + operando1 + " e " + operando2 + " deu o resultado de:</h1>");    	
    	out.println("<h2>Resposta: </h2>");
    	out.println(resultadoExibe);
    	out.println("<p/><a href='html/calculadora.html'>Voltar</a>");
    	out.println("</body></html>");
    	
    	out.close();
    }
	
	public void destroy() {
		System.out.println("Metodo destroy() executado");
	}
}
