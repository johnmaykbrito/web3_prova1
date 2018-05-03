var wsocket, form, serviceLocation;
var strArray = "0,0,0,0,0,0,0,0,0";
var strArraySplit;

function votando() {
    var opcao = document.getElementById("opcao").value;
    if (opcao === "Parar") {
        var mensagem = document.getElementById("mensagem");
        var str = "Votando";
        str += (dot % 3 === 0) ? "." : (dot % 3 === 1) ? ".." : "...";
        mensagem.innerHTML = str;
        dot++;
    } else {
        dot = 0;
        document.getElementById("mensagem").innerHTML = "";
    }
}

function executar() {
    var opcao = document.getElementById("opcao").value;
    if (opcao === "Parar") {
        document.getElementById("opcao").value = "Votar";
        clearInterval(t);
    } else {
        document.getElementById("opcao").value = "Parar";
        votar();
        var voto = document.getElementById("voto").value;
        t = setInterval("votar()", parseInt(voto) * 1000);
    }
}
function votar() {
    sendMessage();
}

function onMessageReceived(evt) {
    var msg = evt.data;
    strArray = msg;
    strArraySplit = strArray.split(",");
    var total = Number(strArraySplit[strArraySplit.length - 1]);
    var vencedor;
    var percents = new Array();
    strArraySplit.forEach(function (e, i) {
        if (i != strArraySplit.length - 1) {
            percents[i] = ((e / total) * 100).toFixed(2);
            document.getElementsByTagName("meter")[i].value = ((e / total) * 100).toFixed(2);
            document.getElementsByClassName("voto")[i].innerHTML = e + " votos";
            document.getElementsByClassName("percent")[i].innerHTML = ((e / total) * 100).toFixed(2) + " %";
        }
    });
    document.getElementById("total").innerHTML = strArraySplit[strArraySplit.length-1];
}

function sendMessage() {
    var msg = strArray;
    wsocket.send(msg);
}

function connectToChatserver() {
    var room = form.room.value;
    wsocket = new WebSocket(serviceLocation + room);
    wsocket.onmessage = onMessageReceived;
}

function leaveRoom() {
    wsocket.close();
}

onload = function () {
    form = document.forms[0];
    var index = document.location.pathname.lastIndexOf("/");
    var path = document.location.pathname.substring(0, index);
    serviceLocation = "ws://" + document.location.host + path + "/notepad/";
    connectToChatserver();

    var opcao = document.getElementById("opcao");
    opcao.onclick = executar;
    setInterval("votando()", 1000);
};

onbeforeunload = function () {
    leaveRoom();
};