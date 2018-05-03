package basic;

import java.util.ArrayList;
import java.util.List;
import javax.websocket.Session;

public class Sala {
    private String texto;
    private List<Session> usuarios = new ArrayList();

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public List<Session> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Session> usuarios) {
        this.usuarios = usuarios;
    }        
}
