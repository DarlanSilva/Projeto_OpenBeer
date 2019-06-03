var pedidos = document.querySelectorAll(".wrap-meus-pedidos");
var pedidoCont = 0;


function effectPedido(){

pedidos.forEach(function(pedido){
        pedido.addEventListener("mouseover", function(){
            this.classList.add("effectPedido");

    })
        
         pedido.addEventListener("mouseout", function(){
             this.classList.remove("effectPedido");
         })
})    
    
}

function openClosePedido(pedido){
                        
        if(pedidoCont == 0){
           pedido.nextElementSibling.classList.add("openPedido");
            pedido.nextElementSibling.classList.remove("closePedido");
            pedidoCont = 1;
        }else{
           pedido.nextElementSibling.classList.add("closePedido");
            pedido.nextElementSibling.classList.remove("openPedido");
            pedidoCont = 0;            
        }

}



pedidos.forEach(function(pedido){
    pedido.addEventListener("click", function(e){
         if(e.target.id !== "status")
            openClosePedido(this); 
        })
})


effectPedido();