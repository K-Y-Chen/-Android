package com.app.demo.activitys;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.demo.R;
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

public class AddScoreActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.edt_score)
    EditText edtScore;

    int from;
    ScoreBean bean;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);
        ButterKnife.bind(this);
        tvTitle.setText("成绩");
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        from = bundle.getInt("from");
        if (from == 1) {
            bean = (ScoreBean) bundle.getSerializable("bean");
            edtTitle.setText(bean.getTitle());
            edtScore.setText(bean.getScore());
            id = bean.getScore_id();
        }
    }

    private void save() {
        String content = edtTitle.getText().toString();
        String score = edtScore.getText().toString();

        if (StringUtil.isEmpty(content) || StringUtil.isEmpty(score)) {
            ToastUtil.showToast(this, "请完善信息");
            return;
        }
        if (from == 0) {
            ScoreBean bean = new ScoreBean();
            bean.setScore_id(System.currentTimeMillis() + "");
            bean.setTitle(content);
            bean.setScore(score);
            bean.setUser_name(UserManager.getUserName(this));
            bean.setUser_id(UserManager.getUserId(this));
            bean.save();
        } else {
            ContentValues values = new ContentValues();
            values.put("title", content);
            values.put("score", score);
            DataSupport.updateAll(ScoreBean.class, values, "score_id=?", id);
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
