package com.app.demo.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.app.demo.R;
import com.app.demo.activitys.CJXXActivity;
import com.app.demo.activitys.XJXXActivity;
import com.app.demo.activitys.XKXXActivity;
import com.app.shop.mylibrary.MyWebActivity;
import com.app.shop.mylibrary.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        registerEventBus();
        return view;
    }


    @OnClick({R.id.bt_1, R.id.bt_2, R.id.bt_3, R.id.bt_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                skipActivity(getActivity(), XJXXActivity.class);
                break;
            case R.id.bt_2:
                skipActivity(getActivity(), CJXXActivity.class);
                break;
            case R.id.bt_3:
                skipActivity(getActivity(), XKXXActivity.class);
                break;
            case R.id.bt_4:
                String url = "https://wenku.baidu.com/view/d00df35e031ca300a6c30c22590102020740f227.html";
                MyWebActivity.start(getActivity(), url, "课表信息");
                break;
        }
    }
}
