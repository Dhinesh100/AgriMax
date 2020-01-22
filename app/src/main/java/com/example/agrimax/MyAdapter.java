package com.example.agrimax;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LoginFragment login = new LoginFragment();
                return login;
            case 1:
                SignupFragment signup = new SignupFragment();
                return signup;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}