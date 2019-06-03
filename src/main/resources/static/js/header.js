var iconMenu = document.querySelector(".fas.fa-bars");
var menuMobile = document.querySelector(".nav-interna .menu-mobile");
var contMenu = 0;
var conteAreaCliente = 0;
var menuSticky = document.querySelector(".menu-internas");
var btnAreaCliente = document.querySelector(".area-cliente");


function alterTela(){
    var tamanhoJanela = window.innerWidth;
    if(tamanhoJanela < 780){
    }else if(tamanhoJanela > 780){
        menuMobile.classList.remove("closeMenu");
        menuMobile.classList.remove("openMenu");
        contMenu = 0;
    }
}


function OpenCloseMenu(){
    if(contMenu == 0){
        menuMobile.classList.add("openMenu");
        menuMobile.classList.remove("closeMenu");
        contMenu = 1;
    }else{
        menuMobile.classList.remove("openMenu");
        menuMobile.classList.add("closeMenu");
        contMenu = 0;
        
    }
    
}

function scrollTop(){
    var top  = window.pageYOffset || document.documentElement.scrollTop;
    console.log(top)
    if(top > 125){
    menuSticky.classList.add("stickyMenu");
    menuSticky.style.padding = "5px 0";
    menuMobile.style.top = "63px";
    document.querySelector(".wrap-content-area").classList.remove("openAreaCliente"); 
    document.querySelector(".wrap-content-area").classList.add("closeAreaCliente");
    conteAreaCliente = 0;
    }else{
        menuSticky.classList.remove("stickyMenu");
        menuSticky.style.padding = "20px 0";
        menuMobile.style.top = "95px";
    }
}

function openClientArea(){
    if(conteAreaCliente == 0){
    document.querySelector(".wrap-content-area").classList.add("openAreaCliente"); 
    document.querySelector(".wrap-content-area").classList.remove("closeAreaCliente");
    conteAreaCliente = 1;
        
    }else{
    document.querySelector(".wrap-content-area").classList.remove("openAreaCliente"); 
    document.querySelector(".wrap-content-area").classList.add("closeAreaCliente");
    conteAreaCliente = 0;        
        
    }

}

btnAreaCliente.addEventListener("click", function(){
        openClientArea();
})

window.addEventListener("scroll",function(){
    scrollTop();

})


iconMenu.addEventListener("click", function(){
    OpenCloseMenu();
    

})

window.addEventListener("resize", function(){
    alterTela();

})

