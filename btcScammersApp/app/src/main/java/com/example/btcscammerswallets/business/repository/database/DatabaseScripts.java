package com.example.btcscammerswallets.business.repository.database;

import com.example.btcscammerswallets.business.data.BTCAccount;

import java.util.HashMap;

import lombok.Data;

@Data
public class DatabaseScripts {

    private final HashMap<String, String> sqlScripts = new HashMap<>();

    public DatabaseScripts() {
        sqlScripts.put(BTCAccount.SCRIPT_NAME, BTCAccount.CREATE_BTC_ACCOUNT_SCRIPT);
    }
}
