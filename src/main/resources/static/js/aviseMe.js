var form = document.getElementById("formMail")
var modal = document.querySelector(".modal-confirm");
var closeModal = document.querySelector(".close-modal");

form.addEventListener('submit', function (e) {
        console.log(e.target);
        modal.classList.add("mostrarModal");
        
    })
    
    closeModal.addEventListener("click", function(){
        modal.classList.remove("mostrarModal");
    })
