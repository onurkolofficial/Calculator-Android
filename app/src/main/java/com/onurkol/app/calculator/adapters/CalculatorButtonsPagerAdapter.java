package com.onurkol.app.calculator.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.onurkol.app.calculator.fragment.calculator.ButtonsPage1Fragment;
import com.onurkol.app.calculator.fragment.calculator.ButtonsPage2Fragment;

public class CalculatorButtonsPagerAdapter extends FragmentStateAdapter {
    public CalculatorButtonsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new ButtonsPage2Fragment();
            case 0:
            default:
                return new ButtonsPage1Fragment(); // Default
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
