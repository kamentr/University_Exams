package com.example.btcscammerswallets.business.transformer;

import com.example.btcscammerswallets.business.data.BTCAccount;
import com.example.btcscammerswallets.business.data.view.BTCAccountHistory;

public class BTCAccountHistoryTransformer {

    public BTCAccountHistory convertToAccountHistory(BTCAccount account) {
        BTCAccountHistory btcAccountHistory = new BTCAccountHistory();
        btcAccountHistory.setAddress(account.getAddress());
        btcAccountHistory.setBalance(account.getBalance().toString());
        btcAccountHistory.setScamScore(account.getScamScore().toString());
        btcAccountHistory.setId(account.getId());

        return btcAccountHistory;
    }
}
