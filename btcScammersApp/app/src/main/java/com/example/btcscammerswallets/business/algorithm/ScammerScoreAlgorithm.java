package com.example.btcscammerswallets.business.algorithm;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.btcscammerswallets.business.data.BTCAccount;
import com.example.btcscammerswallets.business.data.BTCTransaction;
import com.example.btcscammerswallets.business.data.CertaintyLevel;
import com.example.btcscammerswallets.business.data.ScammerScore;
import com.example.btcscammerswallets.business.data.SuspicionLevel;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class ScammerScoreAlgorithm {

    private static final Long TWO_PERCENT = 2L;

    private ScammerScore scammerScore;
    private CertaintyLevel certaintyLevel;

    public ScammerScoreAlgorithm() {
        scammerScore = new ScammerScore();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int calculateScammerScore(List<BTCTransaction> suspectedTransactions, BTCAccount suspectedAccount) {
        scammerScore = new ScammerScore();
        calculateCertainty(suspectedTransactions);
        generalAccountCheck(suspectedAccount);
        generalTransactionsCheck(suspectedTransactions);

        return scammerScore.getFinalScore(certaintyLevel);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void generalTransactionsCheck(List<BTCTransaction> suspectedTransactions) {
        List<BTCTransaction> sentTransactions = collectTransactionOfType(suspectedTransactions, BTCTransaction.TransactionType.SENDING);
        List<BTCTransaction> receivedTransactions = collectTransactionOfType(suspectedTransactions, BTCTransaction.TransactionType.RECEIVING);

        // The amount of IN and OUT transactions are approximately 0
        if (sentTransactions.size() - receivedTransactions.size() == 0) {
            scammerScore.add(SuspicionLevel.HIGH);
        } else if (sentTransactions.size() - receivedTransactions.size() < 2) {
            scammerScore.add(SuspicionLevel.SMALL);
        }

        // Look for IN and OUT transactions with the same amount transferred
        List<Long> amountsReceived = receivedTransactions.stream().mapToLong(BTCTransaction::getAmount).boxed().collect(Collectors.toList());
        List<Long> amountsSent = sentTransactions.stream().mapToLong(BTCTransaction::getAmount).boxed().collect(Collectors.toList());

        amountsReceived.retainAll(amountsSent);
        int suspiciousDoubleTransactions = amountsReceived.size();

        scammerScore.addMultiple(suspiciousDoubleTransactions, SuspicionLevel.SMALL);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NotNull
    private List<BTCTransaction> collectTransactionOfType(List<BTCTransaction> suspectedTransactions, BTCTransaction.TransactionType receiving) {
        return suspectedTransactions.stream().filter(transaction -> transaction.getTransactionType() == receiving).collect(Collectors.toList());
    }

    private void calculateCertainty(List<BTCTransaction> suspectedTransactions) {
        int amountOfData = suspectedTransactions.size();

        if (amountOfData < 5) certaintyLevel = CertaintyLevel.UNCERTAIN;
        else if (amountOfData < 10) certaintyLevel = CertaintyLevel.LOW;
        else if (amountOfData < 25) certaintyLevel = CertaintyLevel.MEDIUM;
        else if (amountOfData < 50) certaintyLevel = CertaintyLevel.HIGH;
        else if (amountOfData > 100) certaintyLevel = CertaintyLevel.CERTAIN;
    }

    private void generalAccountCheck(BTCAccount account) {
        if (account.getBalance() == 0) {
            scammerScore.add(SuspicionLevel.SMALL);
        } else if (percentageDifference(account.getTotalReceived(), account.getTotalSent()) < TWO_PERCENT) {
            scammerScore.add(SuspicionLevel.SMALL);
        }
    }

    private Long percentageDifference(Long a, Long b) {
        return Math.abs(((b - a) * 100) / a);
    }
}
