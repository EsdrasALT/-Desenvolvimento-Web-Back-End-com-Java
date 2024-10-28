<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Jogo de Mounty Hall</title>
</head>
<body>
    <h1>Jogo de Mounty Hall</h1>
    <p>${sessionScope.mensagem}</p>
    <p>Escolha uma porta: 1, 2, ou 3</p>
    <form action="MountyServlet" method="post">
        <label for="escolha">Escolha:</label>
        <input type="number" id="escolha" name="escolha" min="1" max="3" required>
        <button type="submit">Enviar dados</button>
    </form>

    <p>Pontuação: ${sessionScope.pontuacao}</p>
    <a href="MountyServlet">Reiniciar</a>
</body>
</html>
