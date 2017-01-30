package com.store.catalog.service.creditcard.impl;

import org.junit.Test;

import com.store.catalog.model.CreditCardDTO;
import com.store.catalog.model.VerifCCResult;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import java.util.regex.Pattern;
import static org.junit.Assert.*;

/**
 * Created by zouheir on 02/12/2016.
 */
public class CreditCardServiceImplTest {
	private CreditCardServiceImpl ccsi;
	private CreditCardDTO creditCardDto;
	
	@org.junit.Before
	public void setUp(){
		ccsi = new CreditCardServiceImpl();
		creditCardDto = new CreditCardDTO();
	}
	
    @Test(expected = NumberFormatException.class)
    public void testVerifyCreditCard1() throws Exception {
    	creditCardDto.setCreditCardNumber("AAADE");
    	ccsi.verifyCreditCard(creditCardDto);
    }
    
    @Test(expected = NumberFormatException.class)
    public void testVerifyCreditCard2() throws Exception {
    	creditCardDto.setCreditCardNumber("9A DE");
    	ccsi.verifyCreditCard(creditCardDto);
    }
    
    @Test
    public void testVerifyCreditCard3() throws Exception {
    	creditCardDto.setCreditCardNumber("0");
    	assertEquals(VerifCCResult.OK, ccsi.verifyCreditCard(creditCardDto));
    }
    
    @Test
    public void testVerifyCreditCard4() throws Exception {
    	creditCardDto.setCreditCardNumber("34");
    	assertEquals(VerifCCResult.OK, ccsi.verifyCreditCard(creditCardDto));
    }
    
    @Test
    public void testVerifyCreditCard5() throws Exception {
    	creditCardDto.setCreditCardNumber("042");
    	assertEquals(VerifCCResult.OK, ccsi.verifyCreditCard(creditCardDto));
    }
    
    @Test
    public void testVerifyCreditCard6() throws Exception {
    	creditCardDto.setCreditCardNumber("972487086");
    	assertEquals(VerifCCResult.OK, ccsi.verifyCreditCard(creditCardDto));
    }
    
    @Test
    public void testVerifyCreditCard7() throws Exception {
    	creditCardDto.setCreditCardNumber("927487087");
    	assertEquals(VerifCCResult.KO, ccsi.verifyCreditCard(creditCardDto));
    }
    
    @Test
    public void testVerifyCreditCard8() throws Exception {
    	creditCardDto.setCreditCardNumber("4563960122001999");
    	assertEquals(VerifCCResult.OK, ccsi.verifyCreditCard(creditCardDto));
    }
    
    @Test
    public void testVerifyCreditCard9() throws Exception {
    	creditCardDto.setCreditCardNumber("4563960122001998");
    	assertEquals(VerifCCResult.KO, ccsi.verifyCreditCard(creditCardDto));
    }
    
}
