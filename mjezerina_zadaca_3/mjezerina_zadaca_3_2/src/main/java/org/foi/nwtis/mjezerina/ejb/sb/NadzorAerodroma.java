package org.foi.nwtis.mjezerina.ejb.sb;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.ejb.Stateless;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/brojKorisnika")
@Stateless
public class NadzorAerodroma {
    static Queue<Session> queue = new ConcurrentLinkedQueue<>();
    
    public static void send(int brojKorisnika) {
        String msg = String.format("%d", brojKorisnika);
        try {
            for (Session session : queue) {
                session.getBasicRemote().sendText(msg);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @OnOpen
    public void openConnection(Session session) {
        queue.add(session);
        System.out.println("Otvorena veza.");
    }
    
    @OnClose
    public void closedConnection(Session session) {
        queue.remove(session);
        System.out.println("Zatvorena veza.");
    }
    
    @OnError
    public void error(Session session, Throwable t) {
        queue.remove(session);
        System.out.println("Zatvorena veza.");
         }    
}
