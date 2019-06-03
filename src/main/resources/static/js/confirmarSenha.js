var password = document.querySelector("password-cadastro");
var password_confirm = document.querySelector("password-confirm");

function validatePassword(){
  if(password-cadastro.value != password-confirm.value) {
    password-confirm.setCustomValidity("Senhas diferentes!");
  } else {
    password-confirm.setCustomValidity('');
  }
}

password-cadastro.addEventListener("change", function(){
    validatePassword();
})

password-confirm.addEventListener("change", function(){
    validatePassword();
})


