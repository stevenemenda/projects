package com.store.catalog.service.creditcard;

import com.store.catalog.common.exception.CheckException;
import com.store.catalog.model.CreditCardDTO;
import com.store.catalog.model.VerifCCResult;

/**
 * Created by zouheir on 02/12/2016.
 */
public interface CreditCardService {


    VerifCCResult verifyCreditCard(final CreditCardDTO creditCardDto) throws CheckException;
}
