package com.example.btcscammerswallets.ui.main;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.btcscammerswallets.R;
import com.example.btcscammerswallets.business.service.BTCTransactionService;
import com.example.btcscammerswallets.util.BTCConstants;
import com.google.android.material.textfield.TextInputEditText;

import static java.text.MessageFormat.format;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CheckBTCAddressFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final boolean ANIMATE = true;

    private final BTCTransactionService btcTransactionService = new BTCTransactionService();
    private ProgressBar scammerBar;

    private PageViewModel pageViewModel;

    public static CheckBTCAddressFragment newInstance(int index) {
        CheckBTCAddressFragment fragment = new CheckBTCAddressFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_check_address, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        final TextView scoreKeeper = root.findViewById(R.id.scoreText);
        pageViewModel.getText().observe(this, textView::setText);

        TextInputEditText textInputEditText = (TextInputEditText) root.findViewById(R.id.address_input);

        // Scammer bar
        scammerBar = (ProgressBar) root.findViewById(R.id.progress_bar);
        scammerBar.setMax(BTCConstants.SCAMMER_BAR_MAX_VALUE);

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.toString().isEmpty()) {
                    scammerBar.setProgress(0, ANIMATE);
                    scoreKeeper.setText(format("Scam Meter -> Score: {0}", 0));
                    scammerBar.getProgressDrawable().setColorFilter(
                            Color.GRAY,
                            android.graphics.PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Check button
        Button checkButton = (Button) root.findViewById(R.id.check_button);
        checkButton.setOnClickListener(view -> {

            String addressInput = textInputEditText.getText().toString().trim();
            if (isValidBTCAddress(addressInput)) {
                int scammerScore = btcTransactionService.checkBTCAddress(addressInput);
                scammerBar.getProgressDrawable().setColorFilter(
                        getBackgroundColorForScammerBar(scammerScore),
                        android.graphics.PorterDuff.Mode.SRC_IN);
                scammerBar.setProgress(scammerScore, ANIMATE);
                scoreKeeper.setText(format("Scam Meter -> Score: {0}", scammerScore));
            } else {
                //TODO: Handle what happens here.
                textInputEditText.setText(R.string.invalid_address);
            }
        });

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private int getBackgroundColorForScammerBar(int scammerScore) {
        if (scammerScore < BTCConstants.SCAMMER_BAR_MAX_VALUE / 4) {
            return Color.GREEN;
        } else if (scammerScore < BTCConstants.SCAMMER_BAR_MAX_VALUE / 3) {
            return Color.YELLOW;
        } else {
            return Color.RED;
        }
    }

    private boolean isValidBTCAddress(String addressInput) {
        return addressInput.length() != 0 &&
                (addressInput.startsWith("1") || addressInput.startsWith("3"));

//        Bitcoin/Bitcoin Cash start with a 1 or a 3:
//        1GtKnGiH29ScASmLpkjRwDkVjMhXpYsy26
//        Etherum/Ethereum Classic starts with 0x:
//        0xFd3a935174aeb79B8d5d3935dE1188e37427561F
//        Ripple starts with r or R:
//        rD4sUVsaE9v6mFmSTS2ZHxMt7Q4PtG2PUN
    }
}
