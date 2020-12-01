package com.example.btcscammerswallets.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.btcscammerswallets.R;
import com.example.btcscammerswallets.business.data.view.BTCAccountHistory;

import java.util.List;

public class BTCAccountHistoryListAdapter extends ArrayAdapter<BTCAccountHistory> {

    private final Context context;
    private final int resource;

    public BTCAccountHistoryListAdapter(@NonNull Context context, int resource, @NonNull List<BTCAccountHistory> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String address = getItem(position).getAddress();
        String balance = getItem(position).getBalance();
        String score = getItem(position).getScamScore();

        BTCAccountHistory account = new BTCAccountHistory();
        account.setAddress(address);
        account.setBalance(balance);
        account.setScamScore(score);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);

        TextView addressTextView = (TextView) convertView.findViewById(R.id.accountAddressTextView);
        TextView balanceTextView = (TextView) convertView.findViewById(R.id.accountBalanceTextView);
        TextView scamScoreTextView = (TextView) convertView.findViewById(R.id.accountScoreTextView);

        addressTextView.setText(address);
        balanceTextView.setText(balance);
        scamScoreTextView.setText(score);

        return convertView;
    }
}
