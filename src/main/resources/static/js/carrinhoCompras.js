$('.verificar-cupom').click(function (e) {
    var cupomDigitado = $("#cupom-desconto").val();
    console.log(cupomDigitado);

        var json = {
            "cupom": cupomDigitado
        };

    $.ajax({
        url: '/OpenBeer/Carrinho/VerificarCupom',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(json),
        dataType: 'json',
        error: onErrorValidarCupom,
        success: function(data) {
            if (data.id == null) {
                $('span.cupom-rejeitado').text("Cupom Inválido ou expirou.")
                $('span.totalCarrinho').text("0,00")
            }else{
              $('span.cupomValido').text(data.valorCupom + ".00")
              $('span.totalCarrinho').text(data.valorTotal + ".00")
              $('span.cupom-rejeitado').text("") 
          } 
    }});

    function onErrorValidarCupom() {
        console.log(arguments);
    }
});

$('.tipoEntrega').change((function (e) {
    var tipoEntrega = $(this).find("option:selected").val();
    console.log(tipoEntrega);
    
//    if(tipoEntrega == 1){
//        return;
//    }

        var json = {
            "tipoEntrega": tipoEntrega
        };

    $.ajax({
        url: '/OpenBeer/Carrinho/VerificarTipoEntrega',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(json),
        dataType: 'json',
        error: onErrorCalculoTotal,
        success: function(data) {
               if (data.id == null) {
                   return
               }else{
                   $('span.totalCarrinho').text(data.valorTotal + ".00")
                   $('span.prazoDias').text(data.prazoEntrega)
               }
               
    }});

    function onErrorCalculoTotal() {
        console.log(arguments);
    }
}));