package br.com.wda.OpenBeerProject.Controller;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.checkout.CheckoutRegistrationBuilder;
import br.com.uol.pagseguro.api.checkout.RegisteredCheckout;
import br.com.uol.pagseguro.api.common.domain.ShippingType;
import br.com.uol.pagseguro.api.common.domain.builder.AcceptedPaymentMethodsBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.AddressBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.ConfigBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentItemBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentMethodBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentMethodConfigBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PhoneBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.SenderBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.ShippingBuilder;
import br.com.uol.pagseguro.api.common.domain.enums.ConfigKey;
import br.com.uol.pagseguro.api.common.domain.enums.Currency;
import br.com.uol.pagseguro.api.common.domain.enums.PaymentMethodGroup;
import br.com.uol.pagseguro.api.common.domain.enums.State;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.exception.PagSeguroBadRequestException;
import br.com.uol.pagseguro.api.exception.PagSeguroException;
import br.com.uol.pagseguro.api.exception.PagSeguroServiceUnavailableException;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;
import br.com.wda.OpenBeerProject.Entity.CarrinhoCompras;
import br.com.wda.OpenBeerProject.Entity.CarrinhoItem;
import br.com.wda.OpenBeerProject.Entity.Cerveja;
import br.com.wda.OpenBeerProject.Entity.Cliente;
import br.com.wda.OpenBeerProject.Entity.Endereco;
import br.com.wda.OpenBeerProject.Entity.Pedido;
import br.com.wda.OpenBeerProject.Entity.PedidoItens;
import br.com.wda.OpenBeerProject.Entity.StatusPedido;
import br.com.wda.OpenBeerProject.Repository.CervejaRepository;
import br.com.wda.OpenBeerProject.Repository.PedidoItensRepository;
import br.com.wda.OpenBeerProject.Repository.PedidoRepository;
import br.com.wda.OpenBeerProject.Repository.StatusPedidoRepository;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Darlan Silva
 */
@Controller
@RequestMapping("/OpenBeer/Pagamento")
public class PagamentoController {

    @Autowired
    private CarrinhoCompras carrinho;

    @Autowired
    private StatusPedidoRepository statusRepo;

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private PedidoItensRepository pedidoItensRepo;

    @Autowired
    private CervejaRepository estoqueRepo;

    private final String sellerEmail = "wda.developers@hotmail.com";
    private final String sellerToken = "F30F162446F948AD85CBB3C73FC3EE52";
    private String URL = "";
    private String code[] = new String[2];

    @RequestMapping(value = "/FinalizarCompra", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView finalizarCompra(HttpServletResponse response) {
        
//        if(carrinho.getCliente() == null || carrinho.getCliente().getId() == null){
//            return new ModelAndView("redirect:/OpenBeer/Cliente/Cadastro-Login");
//        }
//        
        
        ModelAndView mv = new ModelAndView("carrinho/confirmacao");
        try {

            final PagSeguro pagSeguro = PagSeguro
                    .instance(new SimpleLoggerFactory(), new JSEHttpClient(),
                            Credential.sellerCredential(sellerEmail, sellerToken), PagSeguroEnv.SANDBOX);

            //Criando um checkout
            RegisteredCheckout registeredCheckout = pagSeguro.checkouts().register(
                    getCheckout());

            registeredCheckout.getRedirectURL();
            System.out.println(registeredCheckout.getRedirectURL());
            URL = registeredCheckout.getRedirectURL();
            code = URL.split("=");

        } catch (PagSeguroBadRequestException e) {
            e.printStackTrace();
            System.out.println(e.getErrors());
            System.out.println(e.getCause());
        }

        mv.addObject("pagSeguroURL", code[1]);
        return mv;

    }

    @GetMapping("/ConferirCompra")
    public ModelAndView conferirCarrinho() {
        ModelAndView mv = new ModelAndView("carrinho/confirmacao");

        return mv;
    }

    @GetMapping("/{id}/remover")
    public ModelAndView removerCarrinho(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        carrinho.remover(id);

        return new ModelAndView("redirect:/OpenBeer/Pagamento/ConferirCompra");
    }

    @GetMapping("/PedidoSucesso")
    public ModelAndView pedidoSucesso() {
        ModelAndView mv = new ModelAndView("cliente/meus-pedidos");

        // MÉTODO PARA SALVA E DECREMENTAR DO ESTOQUE
        salvarPedido();
        atualizarEstoque();
        
        List<Pedido> pedido = pedidoRepo.findAllByClienteID(carrinho.getCliente().getId());
        List<PedidoItens> itens = pedidoItensRepo.findAllByClienteID(carrinho.getCliente().getId());
        
        mv.addObject("pedido", pedido);
        mv.addObject("itens", itens);

        carrinho.getItens().clear();

        mv.addObject("quantidadeCarrinho", carrinho.getQuantidade());

        return mv;
    }

    public SenderBuilder getSender() {
        Cliente cliente = carrinho.getCliente();

        String email = cliente.getLogin().getEmail().trim();
        String nome = cliente.getNomeCompleto().trim();
        String cpf = cliente.getCpf().trim();
        String cpfClient = "";

        if (cpf.contains("-")) {
            cpfClient = cpf.replaceAll("-", "");
        }

        if (cpfClient.contains(".")) {
            cpfClient = cpfClient.replaceAll(".", "");
        }
        if (cpfClient.isEmpty()) {
            cpfClient = cpf;
        }

        SenderBuilder sender = new SenderBuilder()
                .withEmail("\"" + email + "\"")
                .withName("\"" + nome + "\"")
                .withCPF("\"" + cpfClient + "\"")
                .withPhone(getPhone());

        return sender;

    }

    public PhoneBuilder getPhone() {
        Cliente cliente = carrinho.getCliente();

        String telefone = cliente.getTelefone().trim();
        String tel = "";
        String dd = "";
        String phoneClient = "";

        if (telefone.contains("-")) {
            tel = telefone.replaceAll("-", "");
        }

        if (tel.contains(".")) {
            tel = tel.replaceAll(".", "");
        }

        if (tel.length() > 9) {
            phoneClient = tel.substring(2);
            dd = tel.substring(0, 2);
            System.out.println(phoneClient);
            System.out.println(dd);
        } else {
            dd = "99";
            phoneClient = tel;
        }

        PhoneBuilder phone = new PhoneBuilder()
                .withAreaCode("\"" + dd + "\"")
                .withNumber("\"" + phoneClient + "\"");

        return phone;
    }

    public ShippingBuilder getShipping() {
        ShippingBuilder shipping = new ShippingBuilder();

        if (carrinho.getIdTipoEntrega() == 1) {
            shipping.withType(ShippingType.Type.SEDEX);
            shipping.withCost(new BigDecimal(15.00));
        } else {
            shipping.withType(ShippingType.Type.PAC);
            shipping.withCost(new BigDecimal(25.00));

        }

        shipping.withAddress(getAddresss());

        return shipping;
    }

    public AddressBuilder getAddresss() {
        Endereco endereco = carrinho.getEndereco();
        //.withState(carrinho.getEndereco().getEstado())

        String cep = endereco.getCep().trim();
        String cepClient = cep.replaceAll(".", "");
        String numero = Integer.toString(endereco.getNumero()).trim();
        String cidade = endereco.getCidade().trim();
        String complemento = endereco.getComplemento().trim();
        String bairro = endereco.getBairro().trim();
        String logradouro = endereco.getLogradouro().trim();

        if (cep.contains("-")) {
            cepClient = cep.replaceAll("-", "");
        }

        if (cepClient.contains(".")) {
            cepClient = cepClient.replaceAll(".", "");
        }
        if (cepClient.isEmpty()) {
            cepClient = cep;
        }

        AddressBuilder address = new AddressBuilder()
                .withPostalCode("\"" + cepClient + "\"")
                .withCountry("BRA")
                .withState(State.SP)
                .withCity("\"" + cidade + "\"")
                .withComplement("\"" + complemento + "\"")
                .withDistrict("\"" + bairro + "\"")
                .withNumber("\"" + numero + "\"")
                .withStreet("\"" + logradouro + "\"");

        return address;
    }

    public void send301Redirect(HttpServletResponse response, String newUrl) {
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", newUrl);
        response.setHeader("Connection", "close");
    }

    public CheckoutRegistrationBuilder getCheckout() {
        // CALCULANDO DESCONTO(%)
        double valorDesconto;
        
        if (carrinho.getValorDesconto() == null){
            valorDesconto = 0;
        }else{
           valorDesconto = carrinho.getValorDesconto().doubleValue();
        }
        
        double valorTotal = carrinho.getTotal().doubleValue();
        BigDecimal porcDesconto = BigDecimal.ZERO;

        if (valorDesconto == 0) {
            porcDesconto = new BigDecimal(0.01);
        } else {
            porcDesconto = calcPorcentagem(valorDesconto, valorTotal);
            System.out.println(porcDesconto);
        }

        CheckoutRegistrationBuilder checkout = new CheckoutRegistrationBuilder()
                .withCurrency(Currency.BRL)
                .withExtraAmount(BigDecimal.ONE)
                .withReference("XXXXXX")
                .withSender(getSender())
                .withShipping(getShipping())
                //Para definir o a inclusão ou exclusão de um meio você deverá utilizar três parâmetros: o parâmetro que define a configuração do grupo,
                // o grupo de meios de pagamento e o nome do meio de pagamento.
                // No parâmetro que define a configuração do grupo você informará se o grupo ou o meio de pagamento será incluído ou excluído.
                // Já no grupo você informará qual o grupo de meio de pagamento que receberá a configuração definida anteriormente.
                // Você poderá incluir e excluir os grupos de meios de pagamento Boleto, Débito, Cartão de Crédito, Depósito Bancário e Saldo PagSeguro.
                // Já no parâmetro nome você informará qual o meio de pagamento que receberá a configuração definida anteriormente. Os meios são as bandeiras e bancos disponíveis.
                //Atenção:  - Não é possível incluir e excluir o mesmo grupo de meio de pagamento (Ex.: incluir e excluir o grupo CREDIT_CARD).
                // - Não é possível incluir um grupo e um meio do mesmo grupo (Ex.: incluir grupo cartão e bandeira visa na mesma chamada);
                // - Não é possível excluir um grupo e um meio do mesmo grupo (Ex.: excluir grupo cartão e bandeira visa na mesma chamada);
                // - Não é possível incluir e excluir o mesmo meio de pagamento (Ex.: incluir e excluir a bandeira VISA).

                .withAcceptedPaymentMethods(new AcceptedPaymentMethodsBuilder()
                        .addInclude(new PaymentMethodBuilder()
                                .withGroup(PaymentMethodGroup.BALANCE)
                        )
                        .addInclude(new PaymentMethodBuilder()
                                .withGroup(PaymentMethodGroup.BANK_SLIP)
                        )
                )
                //Para definir o percentual de desconto para um meio de pagamento você deverá utilizar três parâmetros: grupo de meios de pagamento, chave configuração de desconto e o percentual de desconto. No parâmetro de grupo você deve informar um dos meios de pagamento disponibilizados pelo PagSeguro. Após definir o grupo é necessário definir os a configuração dos campos chave e valor.
                .addPaymentMethodConfig(new PaymentMethodConfigBuilder()
                        .withPaymentMethod(new PaymentMethodBuilder()
                                .withGroup(PaymentMethodGroup.CREDIT_CARD)
                        )
                        .withConfig(new ConfigBuilder()
                                .withKey(ConfigKey.DISCOUNT_PERCENT)
                                .withValue(porcDesconto)
                        )
                )
                .addPaymentMethodConfig(new PaymentMethodConfigBuilder()
                        .withPaymentMethod(new PaymentMethodBuilder()
                                .withGroup(PaymentMethodGroup.BANK_SLIP)
                        )
                        .withConfig(new ConfigBuilder()
                                .withKey(ConfigKey.DISCOUNT_PERCENT)
                                .withValue(porcDesconto)
                        )
                )
                //Para definir o parcelamento você deverá utilizar três parâmetros: grupo, chave e valor.
                // No parâmetro grupo você informará qual o meio pagamento que receberá as configurações.
                // Para limitar o parcelamento você deve informar o meio de pagamento Cartão de crédito (CREDIT_CARD).
                //Após definir o grupo você deverá definir as configurações nos campos chave e valor.
                // Estes devem ser definidos com a chave MAX_INSTALLMENTS_LIMIT que define a configuração de limite de parcelamento e como valor o número de parcelas que você deseja apresentar ao cliente (de 2 a 18 parcelas).

                .addPaymentMethodConfig(new PaymentMethodConfigBuilder()
                        .withPaymentMethod(new PaymentMethodBuilder()
                                .withGroup(PaymentMethodGroup.CREDIT_CARD)
                        )
                        .withConfig(new ConfigBuilder()
                                .withKey(ConfigKey.MAX_INSTALLMENTS_LIMIT)
                                .withValue(new BigDecimal(10))
                        )
                )
                .addPaymentMethodConfig(new PaymentMethodConfigBuilder()
                        .withPaymentMethod(new PaymentMethodBuilder()
                                .withGroup(PaymentMethodGroup.CREDIT_CARD)
                        )
                        .withConfig(new ConfigBuilder()
                                .withKey(ConfigKey.MAX_INSTALLMENTS_NO_INTEREST)
                                .withValue(new BigDecimal(5))
                        )
                );

        for (CarrinhoItem item : carrinho.getItens()) {
            PaymentItemBuilder itens = new PaymentItemBuilder();

            itens.withId(String.valueOf(item.getCerveja().getId()));
            itens.withDescription(item.getCerveja().getNome());
            itens.withAmount(item.getCerveja().getValorCerveja());
            itens.withQuantity(carrinho.getQuantidade(item));

            checkout.addItem(itens);
        }

        return checkout;
    }

    public static BigDecimal calcPorcentagem(double base, double total) {
        double result = Math.round((base / total) * 100);
        BigDecimal porcentagem = BigDecimal.valueOf(result);
        return porcentagem;
    }

    public void salvarPedido() {
        Pedido pedido = new Pedido();
        Optional<StatusPedido> status = statusRepo.findById(new Integer(1));

        //SALVANDO PEDIDO
        pedido.setCliente(carrinho.getCliente());
        pedido.setTipoEntrega(carrinho.getTipoEntrega().get(carrinho.getIdTipoEntrega() - 1));
        pedido.setStatus(status.get());
        pedido.setInativo(0);
        pedido.setDhInclusao(LocalDateTime.now());
        pedido.setDhPrevisaoEntrega(LocalDateTime.now().plusDays(carrinho.getPrazoEntrega()));
        pedido.setDhPedidoFechado(LocalDateTime.now());
        pedido.setValorPedido(carrinho.getTotalCompra());

        Pedido pedidoSalvo = new Pedido();
        pedidoSalvo = pedidoRepo.save(pedido);

        //SALVANDO PEDIDOS ITENS
        for (CarrinhoItem item : carrinho.getItens()) {
            PedidoItens itens = new PedidoItens();

            itens.setPedido(pedidoSalvo);
            itens.setCerveja(item.getCerveja());
            itens.setVlUnitario(item.getCerveja().getValorCerveja());
            itens.setQuantidade(carrinho.getQuantidade(item));
            itens.setVlTotal(item.getTotal(carrinho.getQuantidade(item)));
            itens.setDhInclusao(LocalDateTime.now());

            pedidoItensRepo.save(itens);

        }

    }

    public void atualizarEstoque() {

        // ATUALIZANDO ESTOQUE
        for (CarrinhoItem item : carrinho.getItens()) {
            int quantidade = 0;
            Optional<Cerveja> estoque = estoqueRepo.findById(item.getCerveja().getId());
            quantidade = estoque.get().getQuantidade() - carrinho.getQuantidade(item);
            estoque.get().setQuantidade(quantidade);
            estoque.get().setDhAlteracao(LocalDateTime.now());

            estoqueRepo.save(estoque.get());

        }
    }
}
