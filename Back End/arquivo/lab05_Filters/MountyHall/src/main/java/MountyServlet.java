
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class MountyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MountyServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // Inicializa pontuação para um novo jogo
        session.setAttribute("pontuacao", 0);
        session.setAttribute("mensagem", "Bem-vindo ao Jogo de Mounty Hall!");

        // Redireciona para o JSP
        request.getRequestDispatcher("/mounty.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Obtenha a pontuação atual
        Integer pontuacaoAtual = (Integer) session.getAttribute("pontuacao");
        if (pontuacaoAtual == null) {
            pontuacaoAtual = 0;
        }

        // Lógica do jogo: atualizar pontuação conforme escolha do usuário
        // Exemplo fictício de atualização de pontuação
        int escolha = Integer.parseInt(request.getParameter("escolha"));
        boolean acertou = verificarEscolha(escolha); // suponha que essa função verifica se o jogador acertou

        if (acertou) {
            pontuacaoAtual += 10; // Ganha 10 pontos se acertar
            session.setAttribute("mensagem", "Parabéns, você ganhou!");
        } else {
            pontuacaoAtual /= 2; // Perde metade dos pontos se errar
            session.setAttribute("mensagem", "Infelizmente, você errou.");
        }

        // Atualiza a pontuação na sessão
        session.setAttribute("pontuacao", pontuacaoAtual);

        // Redireciona para o JSP
        request.getRequestDispatcher("/mounty.jsp").forward(request, response);
    }
    
    private boolean verificarEscolha(int escolha) {
        // Implementação fictícia de verificação
        int portaPremiada = 1; // exemplo fixo
        return escolha == portaPremiada;
    }
    
}
