package com.example.btcscammerswallets.business.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.btcscammerswallets.business.data.BTCAccount;
import com.example.btcscammerswallets.business.data.view.BTCAccountHistory;
import com.example.btcscammerswallets.business.repository.BTCAccountRepository;
import com.example.btcscammerswallets.business.transformer.BTCAccountHistoryTransformer;

import java.util.List;
import java.util.stream.Collectors;

public class BTCAccountHistoryService {

    private final BTCAccountRepository btcAccountRepository = new BTCAccountRepository();
    private final BTCAccountHistoryTransformer accountHistoryTransformer = new BTCAccountHistoryTransformer();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<BTCAccountHistory> getBTCAccountHistory(int limit) {
        List<BTCAccount> btcAccountList = btcAccountRepository.getBTCAccountList(limit);

        return btcAccountList
                .stream()
                .map(accountHistoryTransformer::convertToAccountHistory)
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<BTCAccountHistory> getBTCAccountHistory() {
        return getBTCAccountHistory(50);
    }

    public void delete(Integer id) {
        btcAccountRepository.delete(id);
    }
}
