function atualizaStatus(idPedido, status){
    var textoStatus = status.innerText;
    var idPedido = idPedido;

    console.log(textoStatus);
    console.log(idPedido);

    changeStatus(idPedido, textoStatus);
	};


function changeStatus(idPedido, textoStatus) {
    var pedido = idPedido.valueOf();
    var status = textoStatus.valueOf();

    var json = {
        "pedido": pedido,
        "status": status
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/OpenBeer/BackOffice/Alterar-Status-Pedido",
        data: JSON.stringify(json),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            console.log("Sucesso");
            window.location.href = "http://localhost:8080/OpenBeer/BackOffice/Relatorio-Pedidos";
        },
        error: function (e) {
            alert(e);
        }
    });
}
;