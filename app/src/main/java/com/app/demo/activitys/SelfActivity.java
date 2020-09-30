package com.app.demo.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.demo.R;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.beans.UserBean;
import com.app.shop.mylibrary.utils.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelfActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_id)
    TextView tv_id;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;

    int from;
    UserBean userBean;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        from = bundle.getInt("from", 0);
        if (from == 1) {
            tvTitle.setText("用户资料");
            userBean = (UserBean) bundle.getSerializable("bean");
        } else {
            tvTitle.setText("个人资料");
        }
        setData();
    }


    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);
        if (msg.getMessageType() == EventMessage.Refresh) {
            userBean = (UserBean) msg.getmObject();
            setData();
        }
    }

    private void setData() {
        if (from == 1) {
            tv_id.setText(userBean.getUser_id());
            tvName.setText(userBean.getName());
            tvMobile.setText(userBean.getMobile());
        } else {
            tv_id.setText(UserManager.getUserId(this));
            tvName.setText(UserManager.getUserName(this));
            tvMobile.setText(UserManager.getMobile(this));
        }
    }

    @OnClick()
    public void onViewClicked() {
        onBackPressed();
    }

    @OnClick({R.id.imgv_return, R.id.tv_name, R.id.tv_mobile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_return:
                onBackPressed();
                break;
            case R.id.tv_name:
                bundle.putInt("type", 0);
                bundle.putInt("from", from);
                showActivity(this, ModifyActivity.class, bundle);
                break;
            case R.id.tv_mobile:
                bundle.putInt("type", 1);
                bundle.putInt("from", from);
                showActivity(this, ModifyActivity.class, bundle);
                break;
        }
    }
}
