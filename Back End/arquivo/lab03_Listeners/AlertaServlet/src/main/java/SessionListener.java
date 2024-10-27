import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    private static final int MAX_SESSOES = 2;
	
	public SessionListener() {
    }
	
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        Integer sessoesAtivas = (Integer) context.getAttribute("sessoesAtivas");

        // Inicializa se não houver valor
        if (sessoesAtivas == null) {
            sessoesAtivas = 0;
        }

        // Verifica se o número máximo de sessões foi atingido
        if (sessoesAtivas >= MAX_SESSOES) {
//            se.getSession().invalidate();  // Invalida a sessão se o limite foi atingido
            System.out.println("Tentativa de criar mais de " + MAX_SESSOES + " sessões. Sessão rejeitada.");
        } else {
            sessoesAtivas++;
            context.setAttribute("sessoesAtivas", sessoesAtivas);  // Incrementa o contador de sessões ativas
            System.out.println("Sessão criada: ID = " + se.getSession().getId() + ". Sessões ativas: " + sessoesAtivas);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        Integer sessoesAtivas = (Integer) context.getAttribute("sessoesAtivas");

        if (sessoesAtivas != null && sessoesAtivas > 0) {
            sessoesAtivas--;
            context.setAttribute("sessoesAtivas", sessoesAtivas);  // Decrementa o contador de sessões ativas
            System.out.println("Sessão destruída: ID = " + se.getSession().getId() + ". Sessões ativas: " + sessoesAtivas);
        }
    }
	
}
