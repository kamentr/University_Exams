package com.example.btcscammerswallets.business.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.btcscammerswallets.business.algorithm.ScammerScoreAlgorithm;
import com.example.btcscammerswallets.business.data.BTCAccount;
import com.example.btcscammerswallets.business.data.BTCTransaction;
import com.example.btcscammerswallets.business.repository.BTCAccountRepository;
import com.example.btcscammerswallets.business.repository.BTCTransactionRepository;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class BTCTransactionService {

    private final BTCTransactionRepository btcTransactionRepository = new BTCTransactionRepository();
    private final BTCAccountRepository btcAccountRepository = new BTCAccountRepository();
    private final ScammerScoreAlgorithm scammerScoreAlgorithm = new ScammerScoreAlgorithm();

    public int checkBTCAddress(String address) {
        List<BTCTransaction> allTransactions = btcTransactionRepository.getAllTransactions(address);
        BTCAccount account = btcAccountRepository.getBtcAccount(address);

        if (allTransactions.isEmpty() && account == null) {
            return 0;
        }
        int scammerScore = scammerScoreAlgorithm.calculateScammerScore(allTransactions, account);
        account.setScamScore((long) scammerScore);
        btcAccountRepository.save(account);
        return scammerScore;
    }

}
