

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Prova01Prj_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private int valorAprovacao, peso1, peso2, peso3;
       
    public Prova01Prj_Servlet() {
        super();
    }


	public void init(ServletConfig config) throws ServletException {		
		
		try {

			peso1 = Integer.parseInt(config.getInitParameter("peso1"));
			peso2 = Integer.parseInt(config.getInitParameter("peso2"));
			peso3 = Integer.parseInt(config.getInitParameter("peso3"));
			valorAprovacao = Integer.parseInt(config.getInitParameter("valorAprovacao")); 
			
			if(peso1 < 1 || peso1 > 5) {
	    		System.out.println("Parâmetro inválido; o valor deve ser entre 1 e 5");
	    		return;
			}
			
			if(peso2 < 1 || peso2 > 5) {
	    		System.out.println("Parâmetro inválido; o valor deve ser entre 1 e 5");
	    		return;
			}
			
			if(peso3 < 1 || peso3 > 5) {
	    		System.out.println("Parâmetro inválido; o valor deve ser entre 1 e 5");
	    		return;
			}		
			
			if(valorAprovacao < 50 || peso3 > 80) {
	    		System.out.println("Parâmetro inválido; o valor de aprovação deve ser entre 50 e 80");
	    		return;
			}	
			
		} catch (NumberFormatException e) {
    		System.out.println("Parâmetro inválido; usando valor default");
			peso1 = 1;
			peso2 = 1;
			peso3 = 1;
			valorAprovacao = 50;
		}
				
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
		String mensagem = "";
	    gerarHTML(response, mensagem);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ola doPOST");
		
		String mensagem = "";
		int contador=1;
		double validador=-1;
		double mediaSuficiente;
		
		String primeiroFatorParam = request.getParameter("primeiroFator");			
		String segundoFatorParam = request.getParameter("segundoFator");	
		String terceiroFatorParam = request.getParameter("terceiroFator");
		String mediaPonderada = request.getParameter("mediaPonderada");
		
		double primeiroFator, segundoFator, tercerioFator;
				
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
	    primeiroFator = validarFatores(primeiroFatorParam,response,mensagem,contador);
	    if(primeiroFator == -1.0) {
	    	return;
	    }
	    
	    contador++;	    
	    segundoFator = validarFatores(segundoFatorParam,response,mensagem,contador);
	    if(segundoFator == -1.0) {
	    	return;
	    }
	    
	    contador++;
	    tercerioFator = validarFatores(terceiroFatorParam,response,mensagem,contador);
	    if(tercerioFator == -1.0) {
	    	return;
	    }
	    	    
	    if(mediaPonderada != null) {
	    	mediaSuficiente = calcularMediaPonderada(primeiroFator, segundoFator, tercerioFator);
	    } else {
	    	mediaSuficiente = calcularMediaAritimetica(primeiroFator, segundoFator, tercerioFator);
	    }
	    
	    System.out.println("Valor media achada: " + mediaSuficiente);
	    System.out.println("Valor media definida: " + valorAprovacao);
	    if (mediaSuficiente < valorAprovacao) {
	    	mensagem = "Insuficiencia da Media";
		    gerarHTML(response, mensagem);
	    } else {
	    	mensagem = "Suficiencia da Media";
		    gerarHTML(response, mensagem);
	    }
	    	    
	}
	
	double validarFatores(String fatorParametro, HttpServletResponse response, String mensagem, int contador) throws ServletException, IOException  {

		double valorFator=0;
		
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		
	    if (fatorParametro == null || fatorParametro.isEmpty()) {
	    	mensagem = "Erro: O campo Fator "+ contador +" está vazio";
		    gerarHTML(response, mensagem);
	        return -1;
	    }
	    
	    try {
	    	valorFator = Double.parseDouble(fatorParametro);
	    	
	    } catch (NumberFormatException e) {
	    	mensagem = "Erro: O valor do Fator " + contador + " deve ser um número válido.";
		    gerarHTML(response, mensagem);
	        return-1;
	    }
	    
	    if(valorFator < 0 || valorFator > 100) {
	    	mensagem = "Erro: O valor do Fator " + contador + " deve ser entre 0 e 100.";
		    gerarHTML(response, mensagem);
	        return-1;
	    }
	    		
		return valorFator;
	}
	
	double calcularMediaPonderada(double primeiroF,double segundoF,double terceiroF){ 
		
		double mediaPonderada, numeradorPonderado, denominadorPonderado; 
		
		numeradorPonderado = ( (primeiroF * peso1) + (segundoF * peso2) + (terceiroF * peso3) ); //numerador
				
		denominadorPonderado = (peso1 + peso2 + peso3);
		
		mediaPonderada = numeradorPonderado/denominadorPonderado;
		
		return mediaPonderada;
	}
	
	double calcularMediaAritimetica(double primeiroF,double segundoF,double terceiroF){ 
		
		double mediaAritimetica = (primeiroF + segundoF + terceiroF)/3;
		
		return mediaAritimetica;
	}
	
	protected void gerarHTML(HttpServletResponse response, String mensagem) throws ServletException, IOException {
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();	    
	    
	    out.println("<!DOCTYPE html>");
	    out.println("<html>");
	    out.println("<head>");
	    out.println("<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>");
	    out.println("<title>Calculo Media</title>");
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<h1>Calculo de media de tres fatores</h1>");
	    
	    out.println("<form action='CalcularMedia' method='post'>");
	    
	    out.println("<label for=\"primeiroFator\">Fator 1: </label>");
	    out.println("<input type=\"text\" name=\"primeiroFator\"></input>");
	    out.println("<p></p>");
	    
	    out.println("<label for=\"segundoFator\">Fator 2: </label>");
	    out.println("<input type=\"text\" name=\"segundoFator\"></input>");
	    out.println("<p></p>");
	    
	    out.println("<label for=\"terceiroFator\">Fator 3: </label>");
	    out.println("<input type=\"text\" name=\"terceiroFator\"></input>");
	    out.println("<p></p>");
	    
	    out.println("<label for=\"mediaPonderada\">Media Ponderada? </label>");
	    out.println("<input type=\"checkbox\" name=\"mediaPonderada\">");
	    
	    out.println("<p></p>");
	    
	    out.println("<input type='submit' value='Enviar'>");
	    
	    out.println("</form>");
	    
	    out.println("<h2>Resultado-> " + mensagem + "</h2>");
	    	    
	    out.println("</body>");
	    out.println("</html>");	    
	}

	public void destroy() {
	}
}
