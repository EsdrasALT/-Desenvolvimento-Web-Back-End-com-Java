

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProjetoServletIndicadorSaude extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final double INDICE_TRADICIONAL_K = 1, INDICE_TRADICIONAL_E = 2;
	private double indiceK, indiceE;       

    public ProjetoServletIndicadorSaude() {
        super();
    }


	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("Hello init()");
		
		try {
			indiceK = Double.parseDouble(config.getInitParameter("indiceModernoK"));
			indiceE = Double.parseDouble(config.getInitParameter("indiceModernoE"));
    	}
    	catch (NumberFormatException e) {
    		System.out.println("Parâmetro inválido; usando valor default");
			indiceK = INDICE_TRADICIONAL_K;
			indiceE = INDICE_TRADICIONAL_E;
		}
		
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Hello service()");
		
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
		System.out.println("Hello doPost");
				
		String alturaParametro = request.getParameter("Altura");
		String massaParametro = request.getParameter("Peso");
		double altura,massa;
		String mensagem;
		
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
	    //VALIDAR ALTURA
	    if (alturaParametro == null || alturaParametro.isEmpty()) {
	    	mensagem = "Erro: O campo altura está vazio";
		    gerarHTML(response, mensagem);
	        return;
	    }
	    
	    try {
	    	altura = Double.parseDouble(alturaParametro);
	    } catch (NumberFormatException e) {
	    	mensagem = "Erro: A altura deve ser um número válido.";
		    gerarHTML(response, mensagem);
	        return;
	    }
	    
	    //VALIDAR PESO
	    if (massaParametro == null || massaParametro.isEmpty()) {
	    	mensagem = "Erro: O campo do peso está vazio.";
		    gerarHTML(response, mensagem);
	    	return;
	    }

	    try {
	    	massa = Double.parseDouble(massaParametro);	    	
	    } catch (NumberFormatException e) {
	    	mensagem = "Erro: O peso deve ser um número válido.";
		    gerarHTML(response, mensagem);
	        return;
	    }
	    
	    if(validarEntradas(altura,massa) != 0) {
	    	mensagem = "Erro: Acione o guinness book, temos um novo record";
		    gerarHTML(response, mensagem);
	        return;
	    }	    

	    mensagem = calcularIMC(altura,massa);    
	    
	    gerarHTML(response, mensagem);
	    
	}
	
	int validarEntradas(double alturaRef, double massaRef) {
		
//		Altura mínima registrada: 54,6 cm (Chandra Bahadur Dangi, Nepal).
//		Altura máxima registrada: 2,72 m (Robert Wadlow, EUA).
//		Peso mínimo registrado: 2,13 kg (Lucía Zarate, México, com 17 anos).
//		Peso máximo registrado: 635 kg (Jon Brower Minnoch, EUA).
		
		double alturaMinima = 0.54;
		double alturaMaxima = 2.72;
		double pesoMinimo = 2.13;
		double pesoMaximo = 635.0;		
		
		if (alturaRef < alturaMinima || alturaRef > alturaMaxima) {
			return -1;
		}
		
		if (massaRef < pesoMinimo || massaRef > pesoMaximo) {
			return -1;
		}		
		return 0;
	}
	
	String calcularIMC(double alturaRef, double massaRef) {
		
		double IMC;
		String mensagem = "";
		
		IMC = ((indiceK * massaRef) / Math.pow(alturaRef,indiceE));
		
		System.out.printf("Valor achado do IMC: %.2f",IMC);
				
       if (IMC < 18.5) {
    	   mensagem = "Abaixo do peso";
        } else if (IMC >= 18.6 && IMC <= 24.9) {
        	mensagem = "Saudável";
        } else if (IMC >= 25.0 && IMC <= 29.9) {
        	mensagem = "Sobrepeso";
        } else if (IMC >= 30.0 && IMC <= 34.5) {
        	mensagem = "Obesidade grau I";
        } else if (IMC >= 35.0 && IMC <= 39.9) {
        	mensagem = "Obesidade grau II (severa)";
        } else {
        	mensagem = "Obesidade grau III (mórbida)";
        }
		
		return mensagem;
	}
	
	protected void gerarHTML(HttpServletResponse response, String mensagem) throws ServletException, IOException {
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
	    out.println("<!DOCTYPE html>");
	    out.println("<html>");
	    out.println("<head>");
	    out.println("<meta charset=\"ISO-8859-1\">");
	    out.println("<title>Calculadora de IMC</title>");
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<h1>Calculo de IMC</h1>");
	    
	    out.println("<form action=\"servCalculoIMC\" method=\"post\">");
	    out.println("<label for=\"Altura\">Altura: </label>");
	    out.println("<input type=\"text\" name=\"Altura\"> em metros (ex: 1.70) </input>");
	    out.println("<p></p>");
	    
	    out.println("<label for=\"Peso\">Peso: </label>");
	    out.println("<input type=\"text\" name=\"Peso\"> em kg (ex: 85.5) </input>");
	    out.println("<p></p>");
	    
	    out.println("<label for=\"sexo\">Sexo masculino? </label>");
	    out.println("<input type=\"checkbox\" name=\"sexo\">");
	    out.println("<p></p>");
	    
	    out.println("<input type=\"submit\" name=\"Enviar\">");
	    out.println("<p></p>");
	    
	    out.println("<h2><label id=\"classificacao\">Resultado: " + mensagem + "</label></h2>");
	    out.println("</form>");
	    
	    out.println("</body>");
	    out.println("</html>");
	}

	
	public void destroy() {
		System.out.println("Hello destroy()");
	}

}
