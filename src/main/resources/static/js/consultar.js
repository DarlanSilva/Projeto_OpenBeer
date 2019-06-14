/*var modal = document.querySelector(".modal-delete");
var botaoDeletar = document.querySelector(".btn-modal-deletar");
var botaoCancelarModal = document.querySelector(".btn-modal-cancelar");

function modalRemover(id) {
    $("#modal-deletar-product-id").val(id);
    modal.style.visibility = "visible";


botaoCancelarModal.addEventListener("click", function () {
    modal.style.visibility = "hidden";
});

botaoDeletar.addEventListener("click", function () {
    var idCerveja = parseInt($("#modal-deletar-product-id").val());
    console.log(idCerveja);

    $.ajax({
        type: "POST",
        url: "../BackOffice/" + idCerveja + "/remover",
        success: function (data) {
            modal.style.visibility = "hidden";
            window.location.href = "http://localhost:8080/OpenBeer/BackOffice/Consultar-Produtos";
        }
    });
});
};*/
