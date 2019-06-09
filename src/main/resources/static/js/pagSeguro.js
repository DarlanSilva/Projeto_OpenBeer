//Insira o código de checkout gerado no Passo 1
var URL = document.getElementById("pagSeguroURL");
var form = document.getElementById('formFinalizarCompra');
var code = URL.value;
//'1F69A3CF7878ED9994B3DF9DDC706796';

function pagSeguro() {

    var callback = {
        success: function (transactionCode) {
            //Insira os comandos para quando o usuário finalizar o pagamento. 
            //O código da transação estará na variável "transactionCode"
            window.location.href = "http://localhost:8080/OpenBeer/Pagamento/PedidoSucesso";
            
            console.log("Compra feita com sucesso, código de transação: " + transactionCode);
        },
        abort: function () {
            //Insira os comandos para quando o usuário abandonar a tela de pagamento.
            window.location.href = "http://localhost:8080/OpenBeer/cerveja/Lista-de-Cervejas";
            console.log("abortado");
        }
    };


    //Chamada do lightbox passando o código de checkout e os comandos para o callback
    var isOpenLightbox = PagSeguroLightbox(code, callback);

    // Redireciona o comprador, caso o navegador não tenha suporte ao Lightbox
    if (!isOpenLightbox) {
        location.href = "https://pagseguro.uol.com.br/v2/checkout/payment.html?code=" + code
    }
}

form.addEventListener('submit', function (e) {
    pagSeguro();

    e.preventDefault();

});
