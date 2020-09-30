package com.app.demo.activitys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.demo.R;
import com.app.demo.adapters.CJXXAdapter;
import com.app.demo.beans.ScoreBean;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.EventMessage;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CJXXActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.recy)
    RecyclerView recy;

    List<ScoreBean> list = new ArrayList<>();
    CJXXAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        tvTitle.setText("成绩信息");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("添加");
        initData();
    }


    private void initData() {
        list.clear();
        List<ScoreBean> list_all = DataSupport.findAll(ScoreBean.class);
        for (int i = 0; i < list_all.size(); i++) {
            list.add(list_all.get(i));
        }
        adapter = new CJXXAdapter(R.layout.item_score, list);
        adapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_list_empty,null));
        recy.setLayoutManager(new LinearLayoutManager(this));
        recy.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("from", 1);
                bundle.putSerializable("bean", list.get(position));
                showActivity(CJXXActivity.this, AddScoreActivity.class, bundle);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DataSupport.deleteAll(ScoreBean.class, "score_id=?", list.get(position).getScore_id());
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);
        if (msg.getMessageType() == EventMessage.Refresh) {
            initData();
        }
    }

    @OnClick({R.id.imgv_return, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_return:
                onBackPressed();
                break;
            case R.id.tv_right:

                Bundle bundle = new Bundle();
                bundle.putInt("from", 0);
                showActivity(this, AddScoreActivity.class, bundle);
                break;

        }
    }

}
