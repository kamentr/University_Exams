package com.example.btcscammerswallets.business.data;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BTCAccount {
    public static final String SCRIPT_NAME = "CREATE_BTC_ACCOUNT_TABLE";
    public static final String TABLE_NAME = "btc_account";
    public static final String TOTAL_RECEIVED_TABLE_NAME = "total_received";
    public static final String TOTAL_BALANCE_TABLE_NAME = "total_balance";
    public static final String SCAM_SCORE_TABLE_NAME = "scam_score";
    public static final String TOTAL_SENT_TABLE_NAME = "total_sent";
    public static final String ID_TABLE_NAME = "id";
    public static final String ADDRESS_TABLE_NAME = "address";

    public static final String CREATE_BTC_ACCOUNT_SCRIPT =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + ID_TABLE_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ADDRESS_TABLE_NAME + " VARCHAR(50), "
                    + TOTAL_BALANCE_TABLE_NAME + " INTEGER, "
                    + TOTAL_RECEIVED_TABLE_NAME + " INTEGER, "
                    + TOTAL_SENT_TABLE_NAME + " INTEGER, "
                    + SCAM_SCORE_TABLE_NAME + " INTEGER" + ");";

    private String address;
    private Long totalReceived;
    private Long totalSent;
    private Long balance;
    private Long scamScore;
    private Integer id;

    public BTCAccount(JSONObject jsonObject, String address) throws JSONException {
        this.balance = jsonObject.getLong("final_balance");
        this.totalReceived = jsonObject.getLong("total_received");
        this.totalSent = totalReceived - balance;
        this.scamScore = 0l;
        this.address = address;
    }
}
