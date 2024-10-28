

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RecordePontuacaoFiltro extends HttpFilter implements Filter {       

    public RecordePontuacaoFiltro() {
        super();
    }

    private ServletContext context;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
        // Inicializa o recorde de pontuação se ainda não estiver no contexto da aplicação
        if (context.getAttribute("recordePontuacao") == null) {
            context.setAttribute("recordePontuacao", 0);
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        // Obtém a pontuação atual do usuário da sessão
        Integer pontuacaoAtual = (Integer) session.getAttribute("pontuacao");
        if (pontuacaoAtual == null) {
            pontuacaoAtual = 0; // Inicializa a pontuação na sessão se não estiver definida
            session.setAttribute("pontuacao", pontuacaoAtual);
        }

        // Verifica e atualiza o recorde de pontuação, se necessário
        synchronized (context) {
            Integer recordePontuacao = (Integer) context.getAttribute("recordePontuacao");
            if (pontuacaoAtual > recordePontuacao) {
                context.setAttribute("recordePontuacao", pontuacaoAtual);
            }
        }

        // Continua para o próximo elemento na cadeia de filtros (ou servlet)
        chain.doFilter(request, response);
    }
    
    public void destroy() {
	}

}
