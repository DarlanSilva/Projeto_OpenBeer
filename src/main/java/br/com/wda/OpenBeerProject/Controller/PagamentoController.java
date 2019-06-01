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
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.preapproval.PreApprovalRegistrationBuilder;
import br.com.uol.pagseguro.api.preapproval.RegisteredPreApproval;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;
import br.com.wda.OpenBeerProject.Entity.CarrinhoCompras;
import br.com.wda.OpenBeerProject.Entity.CarrinhoItem;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

    private final String sellerEmail = "wda.developers@hotmail.com";
    private final String sellerToken = "F30F162446F948AD85CBB3C73FC3EE52";
    private String URL = "";
    private String code[]  = new String[2];

    @RequestMapping(value = "/FinalizarCompra", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView finalizarCompra(HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("confirmacao");
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // MÉTODO PARA SALVA E DECREMENTAR DO ESTOQUE
        
        
        mv.addObject("pagSeguroURL", code[1]);
        return mv;
        
    }

    @GetMapping("/{id}/remover")
    public ModelAndView removerCarrinho(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        carrinho.remover(id);

        return new ModelAndView("redirect:/OpenBeer/Pagamento/ConferirCompra");
    }

    public SenderBuilder getSender() {
        SenderBuilder sender = new SenderBuilder()
                .withEmail("wda@sandbox.pagseguro.com.br")
                .withName("Jose Comprador")
                .withCPF("99999999999")
                .withPhone(getPhone());

        return sender;

    }

    public PhoneBuilder getPhone() {
        PhoneBuilder phone = new PhoneBuilder()
                .withAreaCode("99")
                .withNumber("99999999");

        return phone;
    }

    public ShippingBuilder getShipping() {
        ShippingBuilder shipping = new ShippingBuilder()
                .withType(ShippingType.Type.SEDEX)
                .withCost(BigDecimal.TEN)
                .withAddress(getAddresss());

        return shipping;
    }

    public AddressBuilder getAddresss() {
        AddressBuilder address = new AddressBuilder()
                .withPostalCode("99999999")
                .withCountry("BRA")
                .withState(State.SP)
                .withCity("Cidade Exemplo")
                .withComplement("99o andar")
                .withDistrict("Jardim Internet")
                .withNumber("9999")
                .withStreet("Av. PagSeguro");

        return address;
    }

    public void send301Redirect(HttpServletResponse response, String newUrl) {
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", newUrl);
        response.setHeader("Connection", "close");
    }

    public CheckoutRegistrationBuilder getCheckout() {
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
                                .withValue(new BigDecimal(10.00))
                        )
                )
                .addPaymentMethodConfig(new PaymentMethodConfigBuilder()
                        .withPaymentMethod(new PaymentMethodBuilder()
                                .withGroup(PaymentMethodGroup.BANK_SLIP)
                        )
                        .withConfig(new ConfigBuilder()
                                .withKey(ConfigKey.DISCOUNT_PERCENT)
                                .withValue(new BigDecimal(10.00))
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

}
