package com.example.btcscammerswallets.business.repository;

import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANResponse;
import com.example.btcscammerswallets.business.data.BTCTransaction;
import com.example.btcscammerswallets.business.data.response.BTCTransactionResponse;
import com.example.btcscammerswallets.business.transformer.BTCTransactionTransformer;
import com.jacksonandroidnetworking.JacksonParserFactory;

import java.util.List;

import lombok.val;

@RequiresApi(api = Build.VERSION_CODES.N)
public class BTCTransactionRepository {

    private static final String GET_TRANSACTIONS_FOR_ADDRESS_URL = "https://blockchain.info/rawaddr/";

    private final BTCTransactionTransformer btcTransactionTransformer = new BTCTransactionTransformer();

    public BTCTransactionRepository() {
        /* Use Jackson for JSON to Java Object conversion */
        AndroidNetworking.setParserFactory(new JacksonParserFactory());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @SuppressWarnings("unchecked")
    public List<BTCTransaction> getAllTransactions(String address) {
        val request = AndroidNetworking.get(GET_TRANSACTIONS_FOR_ADDRESS_URL + address)
                .addQueryParameter("limit", "100")
                .build();

        ANResponse<BTCTransactionResponse> response = request.executeForObject(BTCTransactionResponse.class);

        List<BTCTransaction> btcTransactions;
        if (response.isSuccess()) {
            btcTransactions = btcTransactionTransformer.convertToBBCTransactionList(response.getResult());
        } else {
            btcTransactions = null;
            Log.e("Something went wrong! ", response.getError().getErrorDetail());
        }

        return btcTransactions;
    }
}
