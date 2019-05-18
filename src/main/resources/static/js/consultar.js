var modal = document.querySelector(".modal-delete");
var botaoDeletar = document.querySelector(".btn-deletar");
var botaoCancelarModal = document.querySelector(".btn-modal-cancelar");


botaoDeletar.addEventListener("click", function () {
// delete function
$(document.this).ready(function () {
    $('#deleteProduct').on("click", function (e) {
        e.preventDefault();

        var Id = parseInt($("#modal-deletar-product-id").val());

        $.ajax({
            type: "POST",
            url: "../OpenBeer/cerveja/" + Id + "/remover"
        });
        
        $("#tableCerveja").closest("tr").hide();
         modal.style.visibility = "hidden";
    });
});

})

function modalRemover(id) {
    $("#modal-deletar-product-id").val(id);
    modal.style.visibility = "visible";
}
;

botaoCancelarModal.addEventListener("click", function () {
    modal.style.visibility = "hidden";
})


/*
 
 botaoDeletar.addEventListener("click", function () {
 modal.style.visibility = "visible";
 })
 
 
 
 $(document.this).ready(function () {
 $('#btn-modal-deletar btn').on("click", function (e) {
 
 var Id = parseInt($("#cerveja-id").val());
 
 $.ajax({
 type: "DELETE",
 url: "../" + Id + "/remover",
 data: {id: Id},
 success: function (data) {
 swal("Excluído!",
 "Este registro foi excluído.",
 "success");
 }
 });
 modal.style.visibility = "hidden"
 
 })
 
 });
 
 function excluir(id) {
 $("#cerveja-id").val(id);
 }
 ;*/
