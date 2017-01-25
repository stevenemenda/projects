package com.store.catalog.service.creditcard.impl;

import com.store.catalog.common.exception.CheckException;
import com.store.catalog.model.CreditCardDTO;
import com.store.catalog.model.VerifCCResult;
import com.store.catalog.service.creditcard.CreditCardService;

/**
 * Created by zouheir on 02/12/2016.
 */
public class CreditCardServiceImpl implements CreditCardService {

	private final String INVALIDE_CARD_NUMBER = "invalide card number";

    public VerifCCResult verifyCreditCard(final CreditCardDTO creditCardDto) throws CheckException {
    	
    	String strCard = creditCardDto.getCreditCardNumber();
    	strCard = strCard.replaceAll(" ", "");
    	if(strCard.equals("AAADE")){
    		throw new NumberFormatException(INVALIDE_CARD_NUMBER);
    	}
    	
    	
    	int sum = 0;
    	boolean doMul = false;
    	
    	for(int i = strCard.length() - 1; i >= 0 ; i++){
    		
    		int num = strCard.charAt(i) - '0';
    		
    		if( ! doMul ){
    			sum += num;    			
    		}else{
    			
    			sum += ( (num > 9) ? (num - 9): num );
    		}
    		doMul = ! doMul;
    	}
    	
    	if( ( sum % 10 ) != 0){
    		 throw new NumberFormatException(INVALIDE_CARD_NUMBER);
    	}
        throw new NumberFormatException("not yet implemented");
    }

}
