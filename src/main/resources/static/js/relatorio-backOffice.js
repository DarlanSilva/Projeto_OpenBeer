$("#filtrar").click()(function () {
    var dataInicial = $("#date-de").val();
    var dataFinal   = $("#date-ate").val();
    
    filterByDate(dataInicial, dataFinal);

});





function filterByDate(dataInicio, dataFinal) {
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
           $("#date-de").val(dataInicio);
           $("#date-ate").val(dataFinal);
        },
        error: function (e) {
            alert(e);
        }
    });
}
;