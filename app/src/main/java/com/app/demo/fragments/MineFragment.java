package com.app.demo.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.demo.R;
import com.app.demo.activitys.LoginActivity;
import com.app.demo.activitys.PassWordResetActivity;
import com.app.demo.activitys.SelfActivity;
import com.app.shop.mylibrary.base.BaseFragment;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.utils.DialogUtil;
import com.app.shop.mylibrary.utils.SharedPreferencesUtil;
import com.app.shop.mylibrary.utils.UserManager;
import com.app.shop.mylibrary.widgts.CustomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {


    @BindView(R.id.tv_username_mine)
    TextView tvUsernameMine;


    private String dialog_title = "退出登录";
    private String dialog_content = "是否确定退出登录？";
    private CustomDialog customDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        registerEventBus();
        setSelfView();
        return view;
    }

    private void setSelfView() {
        tvUsernameMine.setText(UserManager.getUserName(getActivity()));

    }

    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);

        if (msg.getMessageType() == EventMessage.Refresh) {
            setSelfView();
        }
    }


    @OnClick({R.id.rela_self, R.id.rela_modify, R.id.rela_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.rela_self:
                Bundle bundle = new Bundle();
                bundle.putInt("from", 0);
                skipActivity(getActivity(), SelfActivity.class, bundle);
                break;

            case R.id.rela_modify:
                skipActivity(getActivity(), PassWordResetActivity.class);
                break;

            case R.id.rela_logout:
                Logout();
                break;
        }
    }

    private void Logout() {
        customDialog = DialogUtil.showDialog(getActivity(), customDialog, 2, dialog_title, dialog_content, "取消", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SharedPreferencesUtil.removeAll(getContext(), "user");
                skipActivity(getActivity(), LoginActivity.class);
                getActivity().finish();
            }
        });

        if (customDialog != null && !customDialog.isShowing()) {
            customDialog.show();
        }

        customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                customDialog = null;
            }
        });
    }

}
