package com.example.btcscammerswallets.business.transformer;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.btcscammerswallets.business.data.BTCTransaction;
import com.example.btcscammerswallets.business.data.response.BTCTransactionResponse;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class BTCTransactionTransformer {

    public List<BTCTransaction> convertToBBCTransactionList(BTCTransactionResponse btcTransactionResponse) {
        if (btcTransactionResponse == null) return new ArrayList<>();

        List<BTCTransactionResponse.Tx> allTransactionsResponse = btcTransactionResponse.getTxs();
        List<BTCTransactionResponse.Input> allReceivedTransactionsResponse = new ArrayList<>();
        List<BTCTransactionResponse.Out> allSentTransactionsResponse = new ArrayList<>();

        if (!allTransactionsResponse.isEmpty() && allTransactionsResponse.get(0) != null) {
            allReceivedTransactionsResponse = extractInputsOnly(allTransactionsResponse);
            allSentTransactionsResponse = extractOutputsOnly(allTransactionsResponse);
        }

        List<BTCTransaction> transactions = new ArrayList<>();

        if (!allReceivedTransactionsResponse.isEmpty()) {
            allReceivedTransactionsResponse.forEach(receivedTransactionResponse -> {
                BTCTransactionResponse.Prev_out transactionDetails = receivedTransactionResponse.getPrev_out();

                BTCTransaction transaction = new BTCTransaction();
                transaction.setReceiverAddress(btcTransactionResponse.getAddress());
                transaction.setSenderAddress(transactionDetails.getAddr());
                transaction.setAmount(transactionDetails.getValue());
                transaction.setTransactionType(BTCTransaction.TransactionType.RECEIVING);

                transactions.add(transaction);
            });
        }

        if (!allSentTransactionsResponse.isEmpty()) {
            allSentTransactionsResponse.forEach(sentTransactionResponse -> {
                BTCTransaction transaction = new BTCTransaction();

                transaction.setSenderAddress(btcTransactionResponse.getAddress());
                transaction.setReceiverAddress(sentTransactionResponse.getAddr());
                transaction.setAmount(sentTransactionResponse.getValue());
                transaction.setTransactionType(BTCTransaction.TransactionType.SENDING);
                transactions.add(transaction);
            });
        }

        return transactions;
    }

    private List<BTCTransactionResponse.Input> extractInputsOnly(List<BTCTransactionResponse.Tx> allTransactionsResponse) {
        List<BTCTransactionResponse.Input> inputList = new ArrayList<>(allTransactionsResponse.size());

        allTransactionsResponse.forEach(transaction -> inputList.addAll(transaction.getInputs()));

        return inputList;
    }

    private List<BTCTransactionResponse.Out> extractOutputsOnly(List<BTCTransactionResponse.Tx> allTransactionsResponse) {
        List<BTCTransactionResponse.Out> outputList = new ArrayList<>(allTransactionsResponse.size());

        allTransactionsResponse.forEach(transaction -> outputList.addAll(transaction.getOut()));

        return outputList;
    }
}
