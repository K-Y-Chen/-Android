package com.app.demo.activitys;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.demo.R;
import com.app.demo.beans.ClassBean;
import com.app.demo.beans.ScoreBean;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.utils.UserManager;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class AddClassActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_title)
    EditText edtTitle;

    int from;
    ClassBean bean;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        ButterKnife.bind(this);
        tvTitle.setText("选课");
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        from = bundle.getInt("from");
        if (from == 1) {
            bean = (ClassBean) bundle.getSerializable("bean");
            edtTitle.setText(bean.getClass_name());
            id = bean.getClass_id();
        }
    }

    private void save() {
        String content = edtTitle.getText().toString();

        if (StringUtil.isEmpty(content)) {
            ToastUtil.showToast(this, "请完善信息");
            return;
        }
        if (from == 0) {
            ClassBean bean = new ClassBean();
            bean.setClass_id(System.currentTimeMillis() + "");
            bean.setClass_name(content);
            bean.setUser_name(UserManager.getUserName(this));
            bean.setUser_id(UserManager.getUserId(this));
            bean.save();
        } else {
            ContentValues values = new ContentValues();
            values.put("class_name", content);
            DataSupport.updateAll(ScoreBean.class, values, "class_id=?", id);
        }

        EventBus.getDefault().post(new EventMessage(EventMessage.Refresh));
        onBackPressed();
    }

    @OnClick({R.id.imgv_return, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_return:
                onBackPressed();
                break;
            case R.id.tv_add:
                save();
                break;
        }
    }
}
