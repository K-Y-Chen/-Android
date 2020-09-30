package com.app.demo.activitys;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.demo.R;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.beans.UserBean;
import com.app.shop.mylibrary.utils.SharedPreferencesUtil;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.utils.UserManager;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ModifyActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_content)
    EditText edtContent;

    int type;
    int from;
    UserBean userBean;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type", 0);
        from = bundle.getInt("from", 0);

        if (from == 1) {
            userBean = (UserBean) bundle.getSerializable("bean");
            user_id = userBean.getUser_id();
        } else {
            user_id = UserManager.getUserId(this);
        }

        if (type == 0) {
            tvTitle.setText("修改昵称");
            if (from == 1) {
                edtContent.setText(userBean.getName());
            } else {
                edtContent.setText(UserManager.getUserName(this));
            }
        } else {
            tvTitle.setText("修改手机号");
            if (from == 1) {
                edtContent.setText(userBean.getMobile());
            } else {
                edtContent.setText(UserManager.getMobile(this));
            }
        }
    }

    @OnClick({R.id.imgv_return, R.id.tv_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_return:
                onBackPressed();
                break;
            case R.id.tv_bt:

                if (StringUtil.isEmpty(edtContent.getText().toString())) {
                    ToastUtil.showToast(this, "请输入内容");
                    return;
                }

                ContentValues values = new ContentValues();


                if (from == 0) {
                    if (type == 0) {
                        SharedPreferencesUtil.saveData(this, "user", "name", edtContent.getText().toString());
                        values.put("name", edtContent.getText().toString());
                    } else {
                        SharedPreferencesUtil.saveData(this, "user", "mobile", edtContent.getText().toString());
                        values.put("mobile", edtContent.getText().toString());
                    }
                } else {
                    if (type == 0) {
                        values.put("name", edtContent.getText().toString());
                        userBean.setName(edtContent.getText().toString());
                    } else {
                        values.put("mobile", edtContent.getText().toString());
                        userBean.setMobile(edtContent.getText().toString());
                    }
                }
                DataSupport.updateAll(UserBean.class, values, "user_id=?", user_id);
                ToastUtil.showToast(this, "修改成功");
                EventBus.getDefault().post(new EventMessage(EventMessage.Refresh, userBean));
                onBackPressed();
                break;
        }
    }
}
