

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class labServlet_provaAntiga_exercicio3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private double precoGasolina, precoAlcool, precoDiesel;


    public labServlet_provaAntiga_exercicio3() {
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
		String tipoCombustivel;
		
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
		
		String tempo = "O tempo total em horas foi de: " + (int) calcularTempo(distancia,velocidade);
		System.out.println(tempo);
		
		if (calcularParametro != null) {
			consumo = validarCampo(consumoParametro,"Consumo médio (km/l)",response);
			if(consumo == -1) {
				return;
			}
			
			custoDaViagem = custoDoCombustivel(distancia,consumo,tipoDoCombustivel(combustivelParametro));
		}		
		
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
	
	String tipoDoCombustivel(String combustivelParametro){
		
		String tipoCombustivel="";
		
		switch(combustivelParametro) {
		case "Gasolina":
			tipoCombustivel = "gasolina";
			break;			
		case "Álcool":
			tipoCombustivel = "alcool";
			break;
		case "Diesel":
			tipoCombustivel = "diesel";
			break;
		default:
			System.out.println("Como chegamos aqui?");
			break;
		}
		
		return tipoCombustivel;
	}
	
	double custoDoCombustivel(double distancia, double consumo, String tipoGasolina){
		
		double qtdeCombustivel=0;
		
		if (tipoGasolina == "")
		
		return 0;
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
