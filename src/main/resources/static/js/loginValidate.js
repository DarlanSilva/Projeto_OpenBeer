var senha = document.querySelector("#password-cadastro");
var confirmSenha = document.querySelector("#password-confirm");

function validarSenha(){
    if(senha.value == confirmSenha.value && senha.value != null && senha.value != "" ){
        console.log("ok");
        senha.style.border = "1px solid green";
        confirmSenha.style.border = "1px solid green";
    }else if(senha.value == "" && confirmSenha.value == ""){
        senha.style.border = "none";
        confirmSenha.style.border = "none";
}else{
        console.log("ainda não");
        confirmSenha.style.border = "1px solid red";
        senha.style.border = "none";
    }
    
}



confirmSenha.addEventListener("keyup", function(){
        validarSenha();
})

senha.addEventListener("keyup", function(){
        validarSenha();
})