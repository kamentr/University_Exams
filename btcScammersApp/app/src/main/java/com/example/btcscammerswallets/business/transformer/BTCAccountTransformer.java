package com.example.btcscammerswallets.business.transformer;

import com.example.btcscammerswallets.business.data.BTCAccount;
import com.example.btcscammerswallets.business.data.response.BTCTransactionResponse;

public class BTCAccountTransformer {

    public BTCAccount convertToBTCAccount(BTCTransactionResponse transactionResponse) {
        if (transactionResponse == null) return null;

        BTCAccount account = new BTCAccount();
        account.setBalance(transactionResponse.getFinal_balance());
        account.setTotalReceived(transactionResponse.getTotal_received());
        account.setTotalSent(transactionResponse.getTotal_sent());
        account.setScamScore(0L);

        return account;
    }
}
