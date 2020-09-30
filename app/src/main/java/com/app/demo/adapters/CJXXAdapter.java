package com.app.demo.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.app.demo.R;
import com.app.demo.beans.ScoreBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CJXXAdapter extends BaseQuickAdapter<ScoreBean, CJXXAdapter.ViewHolder> {


    public CJXXAdapter(int layout, @Nullable List<ScoreBean> data) {
        super(layout, data);
    }

    @Override
    protected void convert(ViewHolder holder, ScoreBean bean) {

        holder.tv_name.setText(bean.getTitle() + " (" + bean.getScore() + ")");
        holder.addOnClickListener(R.id.tv_del);
    }


    public class ViewHolder extends BaseViewHolder {
        TextView tv_name;
        TextView tv_del;


        public ViewHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_del = view.findViewById(R.id.tv_del);
        }
    }
}
