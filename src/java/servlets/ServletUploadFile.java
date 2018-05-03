package servlets;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class ServletUploadFile extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Collection<Part> items = req.getParts();
        // Pega o diretório /logo dentro do diretório atual de onde a
        // aplicação está rodando
        String caminho = getServletContext().getRealPath("/files") + "/";

        // Cria o diretório caso ele não exista
        File diretorio = new File(caminho);
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }

        for (Part item : items) {
            // Mandar o arquivo para o diretório informado
            String nome = item.getSubmittedFileName();
            item.write(caminho + nome);
        }

        resp.setContentType("text/html");
        resp.getWriter().write("<h2>Successfully Uploaded Images</h2>");
        resp.getWriter().write("<p>Images stored at path: " + caminho + "</p>");
    }
}
