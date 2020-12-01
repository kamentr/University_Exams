package com.example.btcscammerswallets.business.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANResponse;
import com.example.btcscammerswallets.MainActivity;
import com.example.btcscammerswallets.business.data.BTCAccount;
import com.example.btcscammerswallets.business.repository.database.DatabaseHelper;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.SneakyThrows;
import lombok.val;

import static com.example.btcscammerswallets.business.data.BTCAccount.ADDRESS_TABLE_NAME;
import static com.example.btcscammerswallets.business.data.BTCAccount.ID_TABLE_NAME;
import static com.example.btcscammerswallets.business.data.BTCAccount.SCAM_SCORE_TABLE_NAME;
import static com.example.btcscammerswallets.business.data.BTCAccount.TABLE_NAME;
import static com.example.btcscammerswallets.business.data.BTCAccount.TOTAL_BALANCE_TABLE_NAME;
import static com.example.btcscammerswallets.business.data.BTCAccount.TOTAL_RECEIVED_TABLE_NAME;
import static com.example.btcscammerswallets.business.data.BTCAccount.TOTAL_SENT_TABLE_NAME;

public class BTCAccountRepository {

    private static final String GET_ACCOUNT_INFO_FOR_ADDRESS_URL = "https://blockchain.info/balance?active=";
    private final DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.getContext());

    public BTCAccountRepository() {
        AndroidNetworking.setParserFactory(new JacksonParserFactory());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public BTCAccount getBtcAccount(String address) {
        val request = AndroidNetworking
                .get(GET_ACCOUNT_INFO_FOR_ADDRESS_URL + address)
                .build();

        ANResponse<JSONObject> response = request.executeForJSONObject();

        if (response.isSuccess()) {
            val jsonResult = response.getResult();
            return new BTCAccount(jsonResult.getJSONObject(address), address);
        } else {
            Log.e("Something went wrong! ", response.getError().getErrorDetail());
            return null;
        }
    }

    public List<BTCAccount> getBTCAccountList(int limit) {
        ArrayList<BTCAccount> accounts = new ArrayList<>();
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor result = database.rawQuery("select * from " + TABLE_NAME + " limit " + limit, null);
        result.moveToFirst();
        while (!result.isAfterLast()) {
            accounts.add(createBTCAccount(result));
            result.moveToNext();
        }
        result.close();
        return accounts;
    }

    public BTCAccount getBTCAccountByAddress(String address) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor result = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ADDRESS_TABLE_NAME + " = " + "\"" + address + "\"", null);
        result.moveToFirst();

        if (!result.isAfterLast()) {
            return createBTCAccount(result);
        }
        result.close();
        return null;
    }

    @NotNull
    private BTCAccount createBTCAccount(Cursor result) {
        BTCAccount account;
        String resultAddress = result.getString(result.getColumnIndex(ADDRESS_TABLE_NAME));
        long sent = result.getLong(result.getColumnIndex(TOTAL_SENT_TABLE_NAME));
        long balance = result.getLong(result.getColumnIndex(TOTAL_BALANCE_TABLE_NAME));
        long scamScore = result.getLong(result.getColumnIndex(SCAM_SCORE_TABLE_NAME));
        long received = result.getLong(result.getColumnIndex(TOTAL_RECEIVED_TABLE_NAME));
        int id = result.getInt(result.getColumnIndex(ID_TABLE_NAME));

        account = new BTCAccount(resultAddress, received, sent, balance, scamScore, id);
        return account;
    }

    public List<BTCAccount> getBTCAccountList() {
        return getBTCAccountList(50);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void save(BTCAccount account) {
        if (isAlreadySaved(account.getAddress())) {
            update(account, this.getBTCAccountByAddress(account.getAddress()).getId());
        } else {
            SQLiteDatabase database = databaseHelper.getWritableDatabase();
            ContentValues content = prepareContentValues(account);
            database.insert(TABLE_NAME, null, content);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean isAlreadySaved(String address) {
        Optional<BTCAccount> optionalAccount = Optional.ofNullable(getBTCAccountByAddress(address));

        return optionalAccount.isPresent();
    }

    public void update(BTCAccount account, int id) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues content = prepareContentValues(account);

        database.update(TABLE_NAME, content, "id=?", new String[]{
                Integer.toString(id)
        });
    }

    private ContentValues prepareContentValues(BTCAccount account) {
        ContentValues content = new ContentValues();
        content.put(TOTAL_RECEIVED_TABLE_NAME, account.getTotalReceived());
        content.put(TOTAL_SENT_TABLE_NAME, account.getTotalSent());
        content.put(TOTAL_BALANCE_TABLE_NAME, account.getBalance());
        content.put(SCAM_SCORE_TABLE_NAME, account.getScamScore());
        content.put(ADDRESS_TABLE_NAME, account.getAddress());

        return content;
    }

    public void delete(int id) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.delete(TABLE_NAME, "id=?", new String[]{
                Integer.toString(id)
        });
    }
}
