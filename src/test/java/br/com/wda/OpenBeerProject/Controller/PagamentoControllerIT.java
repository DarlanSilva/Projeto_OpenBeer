/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wda.OpenBeerProject.Controller;

import br.com.uol.pagseguro.api.checkout.CheckoutRegistrationBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.AddressBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PhoneBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.SenderBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.ShippingBuilder;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Darlan Silva
 * @author Wesley Moura
 * @author Alison Souza
 */

public class PagamentoControllerIT {
    
//    public PagamentoControllerIT() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//  
//    @Test
//    public void testFinalizarCompra() {
//        System.out.println("finalizarCompra");
//        HttpServletResponse response = null;
//        PagamentoController instance = new PagamentoController();
//        ModelAndView expResult = null;
//        ModelAndView result = instance.finalizarCompra(response);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//  
//    @Test
//    public void testConferirCarrinho() {
//        System.out.println("conferirCarrinho");
//        PagamentoController instance = new PagamentoController();
//        ModelAndView expResult = null;
//        ModelAndView result = instance.conferirCarrinho();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testRemoverCarrinho() {
//        System.out.println("removerCarrinho");
//        Integer id = null;
//        RedirectAttributes redirectAttributes = null;
//        PagamentoController instance = new PagamentoController();
//        ModelAndView expResult = null;
//        ModelAndView result = instance.removerCarrinho(id, redirectAttributes);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testPedidoSucesso() {
//        System.out.println("pedidoSucesso");
//        PagamentoController instance = new PagamentoController();
//        ModelAndView expResult = null;
//        ModelAndView result = instance.pedidoSucesso();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetSender() {
//        System.out.println("getSender");
//        PagamentoController instance = new PagamentoController();
//        SenderBuilder expResult = null;
//        SenderBuilder result = instance.getSender();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetPhone() {
//        System.out.println("getPhone");
//        PagamentoController instance = new PagamentoController();
//        PhoneBuilder expResult = null;
//        PhoneBuilder result = instance.getPhone();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetShipping() {
//        System.out.println("getShipping");
//        PagamentoController instance = new PagamentoController();
//        ShippingBuilder expResult = null;
//        ShippingBuilder result = instance.getShipping();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//
//    @Test
//    public void testGetAddresss() {
//        System.out.println("getAddresss");
//        PagamentoController instance = new PagamentoController();
//        AddressBuilder expResult = null;
//        AddressBuilder result = instance.getAddresss();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//
//    @Test
//    public void testGetCheckout() {
//        System.out.println("getCheckout");
//        PagamentoController instance = new PagamentoController();
//        CheckoutRegistrationBuilder expResult = null;
//        CheckoutRegistrationBuilder result = instance.getCheckout();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
    
}
