var modal = document.querySelector(".modal-delete");
var botaoDeletar = document.querySelector(".btn-modal-deletar");
var botaoCancelarModal = document.querySelector(".btn-modal-cancelar");


//botaoDeletar.addEventListener("click", function () {
//// delete function
//$(document.this).ready(function () {
//$('.btn-modal-deletar btn').on("click", function (e) {
//    e.preventDefault();
//
//    var Id = parseInt($("#modal-deletar-product-id").val());
//
//    $.ajax({
//        type: "POST",
//        url: "../BackOffice/" + Id + "/remover",
//        success: function (data) {
//            modal.style.visibility = "hidden";
//        }
//    });
//});
//});

//});

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
            location.reload();
        }
    });
});
};
