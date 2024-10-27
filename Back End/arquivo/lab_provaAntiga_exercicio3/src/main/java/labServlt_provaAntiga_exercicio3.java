

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class labServlt_provaAntiga_exercicio3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private double precoGasolina, precoAlcool, precoDiesel;


    public labServlt_provaAntiga_exercicio3() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("Hello init");
		
		try {
			precoGasolina = Double.parseDouble(config.getInitParameter("precoGasolina"));
			precoAlcool = Double.parseDouble(config.getInitParameter("precoAlcool"));
			precoDiesel = Double.parseDouble(config.getInitParameter("precoDiesel"));
		} catch (NumberFormatException e) {
			precoGasolina = 1.0;
			precoAlcool = 1.0;
			precoDiesel = 1.0;
		}
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
		System.out.println("Hello doGet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Hello doPost");
		
		String mensagem="";
		String distanciaParametro = request.getParameter("distancia");
		String velocidadeParametro = request.getParameter("velocidade");
		String calcularParametro = request.getParameter("calcular"); // null or true
		String consumoParametro = request.getParameter("consumo"); 
		String combustivelParametro = request.getParameter("combustivel");
		double distancia,velocidade,consumo,custoDaViagem;
		String tempo,custoEmReais;
		
		distancia = validarCampo(distanciaParametro,"Distância (km)",response);
		if(distancia == -1) {
			return;
		}		
		System.out.println("Distancia " + distancia);
		
		velocidade = validarCampo(velocidadeParametro,"Velocidade média (km/h)",response);
		if(velocidade == -1) {
			return;
		}		
		System.out.println("Velocidade " + velocidade);
		
		tempo = "O tempo total em horas foi de: " + (int) calcularTempo(distancia,velocidade) + " horas";
		
		if (!(calcularParametro != null)) {	//Calcular consumo? No	
			gerarHTML(response,tempo);
			return;
		}	
		consumo = validarCampo(consumoParametro,"Consumo médio (km/l)",response);
		if(consumo == -1) {
			return;
		}

		custoEmReais = "O custo em reais foi de R$" + custoDoCombustivel(distancia,consumo,combustivelParametro);

		mensagem = tempo + "<p></p>" + custoEmReais;
		
		gerarHTML(response,mensagem);
	}
	
	protected double validarCampo(String parametro,String msg, HttpServletResponse response) throws ServletException, IOException{
		
		double campo=0.0;
		String mensagem="";
		
		if (parametro == null || parametro.isEmpty()) {
			mensagem = "Erro: O campo "+ msg +" está vazio";
		    gerarHTML(response, mensagem);
	        return -1;
	    }
	    
	    try {
	    	campo = Double.parseDouble(parametro);
	    } catch (NumberFormatException e) {
	    	mensagem = "Erro: O campo " + msg + " deve ser um número válido maior que 0.";
		    gerarHTML(response, mensagem);
		    return -1;
	    }
	    
	    return campo;
	}
	
	double calcularTempo(double distancia, double velocidade){
		double tempo=0;		
		tempo = distancia/velocidade;
		return tempo;
	}
		
	double custoDoCombustivel(double distancia, double consumo, String tipoGasolina) {
	    double qtdeCombustivel = 0, precoCombustivel = 0;

	    System.out.println("O tipo de combustivel é " + tipoGasolina);

	    if (tipoGasolina.equals("Gasolina")) {
	        precoCombustivel = precoGasolina;
	    } else if (tipoGasolina.equals("Álcool")) {
	        precoCombustivel = precoAlcool;
	    } else if (tipoGasolina.equals("Diesel")) {
	        precoCombustivel = precoDiesel;
	    } else {
	    	System.out.println("Como chegamos aqui?");
	    }

	    qtdeCombustivel = (distancia / consumo);
	    double custoTotal = qtdeCombustivel * precoCombustivel;

	    return custoTotal;
	}
	
	protected void gerarHTML(HttpServletResponse response, String mensagem) throws ServletException, IOException {
		
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    	    
    	out.println("<html><head><title>");
    	out.println("Resposta");
    	out.println("</title></head><body>");	    
	    out.println("<h2><label>Resultado: " + mensagem + "</label></h2>");	    
    	out.println("<p/><a href='HTML/viagem.html'>Voltar</a>");    	
	    out.println("</body></html>");	    
	}
	

	public void destroy() {
		System.out.println("Hello destroy");
	}

}
