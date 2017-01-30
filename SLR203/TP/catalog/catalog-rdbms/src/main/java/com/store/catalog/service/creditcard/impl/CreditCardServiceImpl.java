package com.store.catalog.service.creditcard.impl;

import java.util.regex.Pattern;

import com.store.catalog.common.exception.CheckException;
import com.store.catalog.model.CreditCardDTO;
import com.store.catalog.model.VerifCCResult;
import com.store.catalog.service.creditcard.CreditCardService;

/**
 * Created by zouheir on 02/12/2016.
 */
public class CreditCardServiceImpl implements CreditCardService {

	private final String INVALIDE_CARD_NUMBER = "invalide card number!";

    public VerifCCResult verifyCreditCard(final CreditCardDTO creditCardDto) throws CheckException {
    	
    	String strCard = creditCardDto.getCreditCardNumber();
    	strCard = strCard.replaceAll(" ", "");
    	if(strCard.equals("AAADE")){
    		throw new NumberFormatException(INVALIDE_CARD_NUMBER);
    	}
    	
    	// que de chiffre ou d'espace
    	if(! strCard.equals(strCard.replaceAll("[^\\d\\s]", ""))){
    		throw new NumberFormatException(INVALIDE_CARD_NUMBER);
    	}
    	
    	int sum = 0;
    	boolean doMul = false;
    	
    	for(int i = strCard.length() - 1; i >= 0 ; i--){
    		
    		int num = strCard.charAt(i) - '0';
    		
    		if( !doMul ){
    			sum += num;    			
    		}else{
    			
    			sum += ( ((2*num) > 9) ? (2*num - 9): 2*num );
    		}
    		doMul = ! doMul;
    	}
    	
    	if( ( sum % 10 ) != 0){
    		 return VerifCCResult.KO;
    	}else{
    		return VerifCCResult.OK;
    	}
    }

}
