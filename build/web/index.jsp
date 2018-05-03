<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Prova1 - John Lima</title>
        <meta charset="UTF-8" />
        <script type="text/javascript" src="index.js"></script>
    </head>
    <body>
        <h2>Prova1 - John Lima - 20161y6-rc0115</h2>
        <form>
            <input type="hidden" name="room" value="${room}" />
            <fieldset id="output"><legend>Votação:</legend>
                <ul>
                    <c:forTokens items="${initParam['disciplinas']}" delims="," var="disciplina" varStatus="status">
                        <li>${disciplina} &ndash; <span class="voto">&nbsp;</span><br />
                            <meter min="0" max="100">&nbsp;</meter> <span class="percent">&nbsp;</span>
                        </li>
                    </c:forTokens>
                </ul>
                <p style=""> Total de votos: <span id="total">0</span>.</p>
                <p>
                    <input type="hidden" value="Vote" name="enviar" id="trigger">
                        <div id="results"></div>
                        <p id="resposta">Votar automaticamente a cada
                            <input type="text" id="voto" value="2"/> segundos.
                        </p>
                        <input type="button" id="opcao" value="Votar" name="send"/>
                        <p id="mensagem"></p>
                </p>
        </form>
    </body>
</html>