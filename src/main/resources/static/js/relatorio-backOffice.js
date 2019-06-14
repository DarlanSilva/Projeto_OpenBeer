$("#formFiltroByDate").submit((function () {
    var dataInicial = $("#date-de").val();
    var dataFinal = $("#date-ate").val();

    filter(dataInicial, dataFinal);

}));

$('div.status').click(function () {
    var textoStatus = $('#status option:selected').text();
    var idPedido = $('#status option:selected').val();

    console.log(textoStatus);
    console.log(idPedido);

    changeStatus(idPedido, textoStatus);

});



function filter(dataInicio, dataFinal) {
    var dataIni = dataInicio.valueOf();
    var dataFin = dataFinal.valueOf();

    var json = {
        "dataIni": dataIni,
        "dataFin": dataFin
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/OpenBeer/BackOffice/Relatorio-Pedidos",
        data: JSON.stringify(json),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            console.log("Sucesso");
            $("#date-de").val(dataIni);
            $("#date-ate").val(dataIni);
            location.reload();

        },
        error: function (e) {
            alert(e);
        }
    });
}
;

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
            location.reload();
        },
        error: function (e) {
            alert(e);
        }
    });
}
;