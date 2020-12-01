package com.example.btcscammerswallets.ui.main;

import android.content.Context;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.btcscammerswallets.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_check_address, R.string.tab_history};
    private static final int TABS_COUNT = 2;
    private final Context mContext;
    List<Fragment> fragments = new ArrayList<>();
    public TabsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void init() {
        fragments.add(CheckBTCAddressFragment.newInstance(1));
        fragments.add(HistoryFragment.newInstance(1));
    }

    @NotNull
    @SneakyThrows
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = CheckBTCAddressFragment.newInstance(position);
                break;
            case 1:
                fragment = HistoryFragment.newInstance(position);
                break;
            default:
                throw new Exception();
        }

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TABS_COUNT;
    }
}