package br.com.foodapp.payments.dto;

import br.com.foodapp.payments.model.Status;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;

@Getter
@Setter
public class PaymentDTO {
    private Long id;
    private BigDecimal value;
    private String name;
    private String number;
    private String expireDate;
    private String securityCode;
    private Status status;
    private Long orderId;
    private Long paymentsMethods;
}
