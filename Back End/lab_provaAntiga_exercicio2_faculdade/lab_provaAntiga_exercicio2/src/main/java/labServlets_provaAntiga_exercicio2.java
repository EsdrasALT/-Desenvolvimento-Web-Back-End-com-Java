import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class labServlets_provaAntiga_exercicio2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String TIPO_CALCULO = "tradicional";
	private static final double INDICE_TRADICIONAL_K = 1, INDICE_TRADICIONAL_E = 2;
	private double indiceK, indiceE;
	private String tipoCalculo;

    public labServlets_provaAntiga_exercicio2() {
        super();
    }


	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("Hello init()");
		
		//Redefinir o parametro no XML para uma "string", onde o tratamento sera feito no init();
				
		tipoCalculo = config.getInitParameter("tipoCalculo");
		System.out.println(tipoCalculo);
		
		if (tipoCalculo == "tradicional") {
			indiceK = 1.0;			
			indiceE = 2.0;
		} else if (tipoCalculo == "moderno") {
			indiceK = 1.3;
			indiceE = 2.5;
		} else {
    		System.out.println("Parâmetro inválido; usando valores default (calculo tradicional)");
			tipoCalculo = TIPO_CALCULO;
			indiceK = INDICE_TRADICIONAL_K;
			indiceE = INDICE_TRADICIONAL_E;
		}
		
		System.out.println("Valor K " + indiceK);
		System.out.println("Valor E " + indiceE);
		
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
		String sexoMasculinoParametro = request.getParameter("sexoM");
		double altura=0,massa=0;
		boolean pesoIdeal = false;
		String mensagem;
			    
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
//	    if (massaParametro == null || massaParametro.isEmpty()) {
//	    	mensagem = "Erro: O campo do peso está vazio.";
//		    gerarHTML(response, mensagem);
//	    	return;
//	    }

	    try {
	    	massa = Double.parseDouble(massaParametro);	    	
	    } catch (NumberFormatException e) {
	    	
	    	if (massaParametro == null || massaParametro.isEmpty()) {
		    	pesoIdeal = true;	
		    	
	    	} else {
	    		
	    	    try {
	    	    	massa = Double.parseDouble(massaParametro);	    	
	    	    } catch (NumberFormatException e1) {
	    	    	mensagem = "Erro: O peso deve ser um número válido.";
	    		    gerarHTML(response, mensagem);
	    	        return;
	    	    }
	    	}    		    	
	    }
	    
	    if (pesoIdeal == true) {
	    	
	    	if((validarAltura(altura) != 0)) {
	    		mensagem = "Erro: Favor validar a altura, digite a medida em metros";
	    		gerarHTML(response, mensagem);
	    		return;
	    	}
	    		    	
	    	boolean sexoMasculino;	    	
			if (sexoMasculinoParametro == null) {
				sexoMasculino = false; //mulher
			} else {
				sexoMasculino = true; // homem
			}
			
			System.out.println(sexoMasculino);
	    	
	    	mensagem = "O peso ideal para a sua altura é " + calcularPesoIdeal(altura,sexoMasculino);
		    gerarHTML(response, mensagem);	    	
	    	
	    } else {
	    	
	    	if((validarAltura(altura) != 0) || (validarPeso(massa) != 0)) {
	    		mensagem = "Erro: Favor validar os valores, a altura em metros e o peso em kg";
	    		gerarHTML(response, mensagem);
	    		return;
	    	}
		    mensagem = calcularIMC(altura,massa);		    
		    gerarHTML(response, mensagem);
	    }
	}
	
	int validarAltura(double alturaRef) {
		
		double alturaMinima = 0.54;
		double alturaMaxima = 2.72;	
		
		if (alturaRef < alturaMinima || alturaRef > alturaMaxima) {
			return -1;
		}
			
		return 0;
	}
	
	int validarPeso(double massaRef) {
		
		double pesoMinimo = 2.13;
		double pesoMaximo = 635.0;		
		
		
		if (massaRef < pesoMinimo || massaRef > pesoMaximo) {
			return -1;
		}		
		return 0;
	}
	
	double calcularPesoIdeal(double alturaRef, boolean sexoRef) {
		
		double resultado=0;
		int S, alturaEmCentimetros = (int) (alturaRef * 100);		
		
		S = (sexoRef == false) ? 2 : 4;
		
		resultado = alturaEmCentimetros - 100 - ((alturaEmCentimetros-150)/S);
		
		return resultado;
	}
	
	String calcularIMC(double alturaRef, double massaRef) {
		
		double IMC;
		String mensagem = "";
		
		IMC = ((indiceK * massaRef) / Math.pow(alturaRef,indiceE));
		
		System.out.printf("Valor achado do IMC: %.2f",IMC);
				
       if (IMC < 18.5) {
    	   mensagem = "Abaixo do peso";
        } else if ((IMC >= 18.6) && (IMC <= 24.9)) {
        	mensagem = "Saudável";
        } else if ((IMC >= 25.0) && (IMC <= 29.9)) {
        	mensagem = "Sobrepeso";
        } else if ((IMC >= 30.0) && (IMC <= 34.5)) {
        	mensagem = "Obesidade grau I";
        } else if ((IMC >= 35.0) && (IMC <= 39.9)) {
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
	    
	    out.println("<form action=\"labServProvaAntigaEx2\" method=\"post\">");
	    out.println("<label for=\"Altura\">Altura: </label>");
	    out.println("<input type=\"text\" name=\"Altura\"> em metros (ex: 1.70) </input>");
	    out.println("<p></p>");
	    
	    out.println("<label for=\"Peso\">Peso: </label>");
	    out.println("<input type=\"text\" name=\"Peso\"> em kg (ex: 85.5) </input>");
	    out.println("<p></p>");
	    
	    out.println("<label for=\"sexo\">Sexo masculino? </label>");
	    out.println("<input type=\"checkbox\" name=\"sexoM\">");
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
