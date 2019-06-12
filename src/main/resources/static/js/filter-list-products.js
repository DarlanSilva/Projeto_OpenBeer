$('.searchByName').on('keypress', function (e) {
    if (e.keyCode == 13) {
        var buscar = $("#field-buscar").val();
        console.log(buscar);
        console.log("Entrou");
        buscarNome(buscar);
        e.preventDefault();
    }
});


$('.orderByName').change(function (e) {
    var filterName = $(this).children("option:selected").val();

    if (filterName == 1) {
        console.log("Entrou");
        orderProductByName(1);
    }

    if (filterName == 2) {
        console.log("Entrou");
        orderProductByName(2);
    }

});

$('.orderByPrice').change(function (e) {
    var filterPrice = $(this).children("option:selected").val();
    if (filterPrice == 1) {
        console.log("Entrou");
        orderProductByName(1);
    }

    if (filterPrice == 2) {
        console.log("Entrou");
        orderProductByName(2);
    }
});

function orderProductByPrice(orderPrice) {
    var order = orderPrice.valueOf();
    var json = {
        "order": order
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/OpenBeer/cerveja/Lista-de-Cervejas-ordem-Preco",
        data: JSON.stringify(json),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var html = montarHTML(data);
            $('#list-product').html(html);
        },
        error: function (e) {
            alert(e);
        }
    });
}
;

function orderProductByName(orderName) {
    var order = orderName.valueOf();
    var json = {
        "order": order
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/OpenBeer/cerveja/Lista-de-Cervejas-ordem-Nome",
        data: JSON.stringify(json),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var html = montarHTML(data);
            $('#list-product').html(html);
        },
        error: function (e) {
            alert(e);
        }
    });
}
;

function buscarNome(text) {
    var textoDigitado = text.valueOf();
    var json = {
        "textoDigitado": textoDigitado
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/OpenBeer/cerveja/Lista-de-Cervejas-busca",
        data: JSON.stringify(json),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var html = montarHTML(data);
            $('#list-product').html(html);
        },
        error: function (e) {
            alert(e);
        }
    });
}
;

function filterProduct(id) {

    var tipoCerveja = id.valueOf();
    console.log("Entrou");
    console.log(id);
    var json = {
        "tipoCerveja": tipoCerveja
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/OpenBeer/cerveja/Lista-de-Cervejas-por-Tipo",
        data: JSON.stringify(json),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var html = montarHTML(data);
            $('#list-product').html(html);
        },
        error: function (e) {
            alert(e);
        }
    });
}
;

function montarHTML(data) {
    var html = '';
    var len = data.length;

    for (var i = 0; i < len; i++) {
        if (data[i].quantidade > 0) {
            html += '<div class="single-product">'
                    + ' <div class="wrap-single-product">'
                    + '  <div class = "img-single-product">'
                    + '    <a href="/OpenBeer/cerveja/' + data[i].id + '/detalhe"><img src="/product-picture/' + data[i].imagem + '" alt="Imagem do Produto" width="130"></a>'
                    + '</div>'
                    + '<!--img-single-product-->'
                    + '<div class = "wrap-dispo">'
                    + '<div class = "desc-single-product">'
                    + '<div class = "desc-single-product">'
                    + '<div class = "wrap-single-name">'
                    + '<div class = "desc-single-name">'
                    + '<h4>' + data[i].nome + '</h4>'
                    + '</div>'
                    + '<!--desc-single-name-->'
                    + '<div class = "desc-single-ml">'
                    + '<h4> ' + data[i].mlCerveja + 'ml </h4>'
                    + '</div>'
                    + '<!--desc-single-name-->'
                    + '</div>'
                    + '<!--wrap-single-name-->'
                    + '<div class="wrap-single-price">'
                    + '<div class="desc-single-price">'
                    + '<h4 > R$ <span>' + data[i].valorCerveja + '</span>.00 </h4>'
                    + '</div>'
                    + '<!--desc-single-name-->'
                    + '<div class = "desc-single-desc">'
                    + '<h4>' + data[i].breveDescricao + '</h4>'
                    + '</div>'
                    + '<!--desc-single-name-->'
                    + '</div>'
                    + '<!--wrap-single-name-->'
                    + '</div>'
                    + '<!--desc-single-product-->'
                    + '<div class = "btn-comprar-list">'
                    + '<a href="/OpenBeer/Carrinho/' + data[i].id + '/AddCarrinho">COMPRAR</a>'
                    + '</div><!--btn-comprar-list-->'
                    + '</div><!--wrap-dispo-->'
                    + '</div>'
                    + '<!--wrap-single-product-->'
                    + '</div>'
                    + '</div>'
                    + '<!--single-product-->';
        } else {
            html += '<div class="single-product">'
                    + '<div class = "wrap-single-product">'
                    + '<div class = "img-single-product">'
                    + '<a href="/OpenBeer/cerveja/' + data[i].id + '/detalhe"><img src="/product-picture/' + data[i].imagem + '" alt="Imagem do Produto" width="130"></a>'
                    + '</div>'
                    + '<!--img-single-product-->'
                    + '<div class = "sem-estoque">'
                    + '<p> Ops! Já vendemos todo o estoque. </p>'
                    + '</div>'
                    + '</div>'
                    + '<!--wrap-single-product-->'
                    + '</div>'
                    + '</div>'
                    + '<!--single-product-->';
        }

    }

    return html;
}
;

