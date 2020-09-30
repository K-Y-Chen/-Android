package com.app.demo.activitys;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.demo.R;
import com.app.demo.beans.XjxxBean;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.beans.UserBean;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.utils.UserManager;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ModifyXjxxActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_name)
    EditText edtname;
    @BindView(R.id.edt_class)
    EditText edtClass;

    XjxxBean bean;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_xjxx);
        ButterKnife.bind(this);
        tvTitle.setText("修改信息");
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        user_id = UserManager.getUserId(this);
        bean = (XjxxBean) bundle.getSerializable("bean");

    }

    @OnClick({R.id.imgv_return, R.id.tv_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_return:
                onBackPressed();
                break;
            case R.id.tv_bt:

                String name = edtname.getText().toString();
                String grade = edtClass.getText().toString();

                if (StringUtil.isEmpty(name) || StringUtil.isEmpty(grade)) {
                    ToastUtil.showToast(this, "请输入内容");
                    return;
                }

                if (bean == null) {
                    XjxxBean bean = new XjxxBean();
                    bean.setUser_name(name);
                    bean.setGrade(grade);
                    bean.setUser_id(user_id);
                    bean.save();
                } else {
                    ContentValues values = new ContentValues();
                    values.put("user_name", name);
                    values.put("grade", grade);
                    DataSupport.updateAll(UserBean.class, values, "user_id=?", user_id);
                }

                ToastUtil.showToast(this, "操作成功");
                EventBus.getDefault().post(new EventMessage(EventMessage.Refresh));
                onBackPressed();
                break;
        }
    }
}
