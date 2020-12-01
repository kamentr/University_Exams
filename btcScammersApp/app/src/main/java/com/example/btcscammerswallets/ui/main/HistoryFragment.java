package com.example.btcscammerswallets.ui.main;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.btcscammerswallets.MainActivity;
import com.example.btcscammerswallets.R;
import com.example.btcscammerswallets.business.data.view.BTCAccountHistory;
import com.example.btcscammerswallets.business.service.BTCAccountHistoryService;
import com.example.btcscammerswallets.ui.main.adapter.BTCAccountHistoryListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private final BTCAccountHistoryService BTCAccountHistoryService = new BTCAccountHistoryService();
    private List<BTCAccountHistory> accountList = new ArrayList<>();
    private BTCAccountHistoryListAdapter accountListAdapter;
    private ListView listView;

    public static HistoryFragment newInstance(int index) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PageViewModel pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        pageViewModel.setIndex(index);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_history, container, false);

        FloatingActionButton refreshButton = root.findViewById(R.id.refreshButton);
        refreshContent(root);
        refreshButton.setOnClickListener(view -> refreshContent(root));
        listView = (ListView) root.findViewById(R.id.addressList);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            BTCAccountHistory account = (BTCAccountHistory) accountListAdapter.getItem(position);
            deleteAccountFromHistory(root, account.getId());
        });
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void deleteAccountFromHistory(View root, int id) {
        new AlertDialog.Builder(MainActivity.getContext())
                .setTitle(R.string.DELETE_TITLE_MESSAGE)
                .setMessage(R.string.DELETE_PROMT_MESSAGE)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    BTCAccountHistoryService.delete(id);
                    refreshContent(root);
                })
                .setNegativeButton(android.R.string.cancel, ((dialog, which) -> {
                })).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refreshContent(View root) {
        accountList = BTCAccountHistoryService.getBTCAccountHistory();
        accountListAdapter = new BTCAccountHistoryListAdapter(MainActivity.getContext(), R.layout.adapter_view_history, accountList);

        listView = (ListView) root.findViewById(R.id.addressList);
        listView.setAdapter(accountListAdapter);
    }
}
