

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class SessionListener implements HttpSessionListener {


    public SessionListener() {
    }


    public void sessionCreated(HttpSessionEvent se)  { 
        // Incrementa o número de sessões ativas
    	ServletAlerta.sessoesAtivas++;
    }


    public void sessionDestroyed(HttpSessionEvent se)  { 
        // Decrementa o número de sessões ativas
    	ServletAlerta.sessoesAtivas--;
    }
	
}
