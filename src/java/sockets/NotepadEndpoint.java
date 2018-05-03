package sockets;

import basic.Sala;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.websocket.OnClose;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/notepad/{room}")
public class NotepadEndpoint {

    private static Map<String, Sala> sessions = new HashMap();
    private String message = "0,0,0,0,0,0,0,0,0";
    private Integer[] votesArray = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private Integer total = 0;

    @OnOpen
    public void open(final Session session, @PathParam("room") final String room) {
        try {
            System.out.println("open: " + room);
            if (sessions.containsKey(room)) {
                Sala sala = sessions.get(room);
                sala.getUsuarios().add(session);
                String salaTxt = sala.getTexto();
                if (sala.getTexto() != null) {
                    String txt = sala.getTexto();
                    session.getBasicRemote().sendText(txt);
                }
                System.out.println("Sessão existente");
            } else {
                Sala sala = new Sala();
                sala.getUsuarios().add(session);
                sessions.put(room, sala);
                System.out.println("Sessão criada");
            }
        } catch (IOException e) {
            System.out.println("onOpen failed: " + e.getMessage());
        }
    }

    @OnMessage
    public void onMessage(final Session session, final String message, @PathParam("room") final String room) {
        System.out.println("message: " + room);
        String mess = message;
        if (!mess.equals("0,0,0,0,0,0,0,0,0")) {
            mess = this.message;
        }
        String[] vetor = mess.split(",");

        int total = 0;
        for (int i = 0; i < votesArray.length; i++) {
            Random gerador = new Random();
            if (i < votesArray.length - 1) {
                int g = gerador.nextInt(100);
                if (g == 0) {
                    g++;
                }
                votesArray[i] += g;
                total += votesArray[i];
            }
        }

        votesArray[votesArray.length - 1] = total;
        String result = "";
        for (int i = 0; i < votesArray.length; i++) {
            result += votesArray[i];
            if (i < vetor.length - 1) {
                result += ",";
            }
        }
        mess = result;
        try {
            Sala sala = sessions.get(room);
            sala.setTexto(mess);
            for (Session s : sala.getUsuarios()) {
                s.getBasicRemote().sendText(mess);
            }
        } catch (IOException e) {
            System.out.println("onMessage failed: " + e.getMessage());
        }
    }

    @OnClose
    public void close(final Session session, @PathParam("room") final String room) {
        System.out.println("close: " + room);
        try {
            session.close();
            Sala sala = sessions.get(room);
            sala.getUsuarios().remove(session);
        } catch (IOException e) {
            System.out.println("onClose failed: " + e.getMessage());
        }
    }
}
