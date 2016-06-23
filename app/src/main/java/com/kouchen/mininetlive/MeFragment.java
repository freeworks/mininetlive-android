package com.kouchen.mininetlive;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kouchen.mininetlive.base.BaseFragment;

/**
 * Created by cainli on 16/6/24.
 */
public class MeFragment extends BaseFragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me,container,false);
    }
}
