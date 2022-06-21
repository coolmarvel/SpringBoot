package com.example.shop.service_business;

import com.example.shop.model.dto.ChargeRequest;
import com.stripe.exception.*;
import com.stripe.model.Charge;

public interface PaymentService {
    Charge pay(ChargeRequest chargeRequest) throws CardException, APIException, AuthenticationException,
            InvalidRequestException, APIConnectionException;
}
