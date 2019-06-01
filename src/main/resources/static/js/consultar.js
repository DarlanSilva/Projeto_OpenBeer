    var modal = document.querySelector(".modal-delete");
    var botaoDeletar = document.querySelector(".btn-deletar");
    var botaoCancelarModal = document.querySelector(".btn-modal-cancelar");
    
    botaoDeletar.addEventListener("click", function(){
        modal.style.visibility = "visible";
    })
    
    botaoCancelarModal.addEventListener("click", function(){
        modal.style.visibility = "hidden";
    })
    