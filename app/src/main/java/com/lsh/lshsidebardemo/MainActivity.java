package com.lsh.lshsidebardemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.lsh.lshsidebardemo.util.PinYin4jUtil;
import com.lsh.sidebar.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<String> mDataList = new ArrayList<>();
    private List<SideBean> mNewDataList = new ArrayList<>();

    PinYin4jUtil pinYin4jUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pinYin4jUtil = new PinYin4jUtil();
        SideBar mSideBar = (SideBar) findViewById(R.id.sideBar);
        final RecyclerView mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        initData();
        initRecycler(mRecyclerview);
        mSideBar.setOnSelectListener(new SideBar.OnSelectListener() {
            @Override
            public void onSelect(String value) {
                int pos = getPos(value);
                if (pos == -1) {
                    return;
                }
                ((LinearLayoutManager) mRecyclerview.getLayoutManager()).scrollToPositionWithOffset(pos, 0);
            }
        });
    }


    private void initRecycler(RecyclerView mRecyclerview) {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(new MAdapter(this, mNewDataList));
    }

    private void initData() {
        getDataList();//获取数据
        covertData();//转换
        dataSort();//排序
    }

    private void getDataList() {
        mDataList.add("张三");
        mDataList.add("张4");
        mDataList.add("张6");
        mDataList.add("王三");
        mDataList.add("王6");
        mDataList.add("李发");
        mDataList.add("李看看");
        mDataList.add("试试");
        mDataList.add("一天");
        mDataList.add("路大概");
        mDataList.add("具体谈");
        mDataList.add("秋天");
        mDataList.add("jd");
        mDataList.add("待定");
        mDataList.add("懂得多");
        mDataList.add("点点滴滴");
        mDataList.add("单点等");
        mDataList.add("哈哈哈");
        mDataList.add("www");
        mDataList.add("123");
        mDataList.add("4rr");
        mDataList.add("7ktgg");
        mDataList.add("111");
    }

    private void covertData() {
        for (String str : mDataList) {
            SideBean sideBean = new SideBean();
            sideBean.flag = pinYin4jUtil.convertHeadChar(str);
            sideBean.name = str;
            mNewDataList.add(sideBean);
        }
    }

    private void dataSort() {
        Collections.sort(mNewDataList, new Comparator<SideBean>() {
            @Override
            public int compare(SideBean o1, SideBean o2) {
                return pinYin4jUtil.getStrAllPinYin(o1.name).compareTo(pinYin4jUtil.getStrAllPinYin(o2.name));
            }
        });
    }

    private int getPos(String value) {
        for (int i = 0; i < mNewDataList.size(); i++) {
            SideBean sideBean = mNewDataList.get(i);
            if (TextUtils.equals(sideBean.flag, value)) {
                return i;
            }
        }
        return -1;
    }

    private void log(String msg) {
        Log.d("TAG", msg);
    }
}
