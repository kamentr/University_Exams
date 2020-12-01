package com.example.btcscammerswallets.business.data;

import lombok.Data;

@Data
public class BTCTransaction {

    String senderAddress;
    String receiverAddress;
    Long amount;
    TransactionType transactionType;

    public enum TransactionType {
        SENDING,
        RECEIVING
    }
}
