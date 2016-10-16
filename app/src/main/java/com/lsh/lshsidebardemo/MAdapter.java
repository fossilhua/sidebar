package com.lsh.lshsidebardemo;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lsh.lshrecyclerviewadapter.BaseAdapter;
import com.lsh.lshrecyclerviewadapter.BaseViewHolder;

import java.util.List;

/**
 * Created by hua on 2016/10/13.
 */

public class MAdapter extends BaseAdapter<SideBean> {
    public MAdapter(Context mContext, List<SideBean> list) {
        super(mContext, list, R.layout.item_text);
    }

    @Override
    public void onBindData(BaseViewHolder holder, List<SideBean> data, int position) {
        TextView mTvFlag = holder.retrieveView(R.id.tv_flag);
        TextView mTvTitle = holder.retrieveView(R.id.tv_title);
        SideBean bean = data.get(position);
        mTvTitle.setText(bean.name);
        if (position == 0) {
            mTvFlag.setVisibility(View.VISIBLE);
            mTvFlag.setText(bean.flag);
        } else {
            SideBean preBean = data.get(position - 1);
            if (!TextUtils.equals(bean.flag, preBean.flag)) {
                mTvFlag.setVisibility(View.VISIBLE);
                mTvFlag.setText(bean.flag);
            } else {
                mTvFlag.setVisibility(View.GONE);
            }
        }
    }
}
