package com.app.demo.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.app.demo.R;
import com.app.demo.beans.ClassBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class XKXXAdapter extends BaseQuickAdapter<ClassBean, XKXXAdapter.ViewHolder> {


    public XKXXAdapter(int layout, @Nullable List<ClassBean> data) {
        super(layout, data);
    }

    @Override
    protected void convert(ViewHolder holder, ClassBean bean) {

        holder.tv_name.setText(bean.getClass_name());
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
