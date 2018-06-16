package com.example.dell.wangkang.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i.moontext.R;
import com.example.i.moontext.adapter.MyAdapter;
import com.example.i.moontext.bean.CarBean;
import com.example.i.moontext.presenter.MyPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MyViewCallBack{


    @BindView(R.id.bianji)
    TextView bianji;
    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    @BindView(R.id.quanxuan)
    CheckBox quanxuan;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.total_num)
    TextView totalNum;
    @BindView(R.id.quzhifu)
    TextView quzhifu;
    @BindView(R.id.shanchu)
    TextView shanchu;
    @BindView(R.id.linear_shanchu)
    LinearLayout linearShanchu;
    private MyPresenter myPresenter;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bianji.setTag(1);
        quanxuan.setTag(1);

        myPresenter = new MyPresenter(this);
        myPresenter.select();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setUpdateListener(new MyAdapter.UpdateListener() {
            @Override
            public void setTotal(String total, String num, boolean allCheck) {
                totalNum.setText("共"+num+"件商品");
                totalPrice.setText("总价:$"+total+"元");
                if (allCheck){
                   quanxuan.setTag(2);
                   quanxuan.setBackgroundResource(R.drawable.shopcart_unselected);
                }else {
                    quanxuan.setTag(1);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_unselected);

                }
                quanxuan.setChecked(allCheck);
            }
        });


    }


    @OnClick({R.id.quanxuan, R.id.quzhifu, R.id.shanchu, R.id.linear_shanchu,R.id.bianji})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.quanxuan:
                int tag = (int) quanxuan.getTag();
                if (tag==1){
                    quanxuan.setTag(2);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_unselected);
                }else {
                    quanxuan.setTag(1);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_unselected);

                }
                adapter.quanXuan(quanxuan.isChecked());
                break;
            case R.id.quzhifu:
                break;
            case R.id.shanchu:
                adapter.shanChu();
                break;
            case R.id.linear_shanchu:
                break;
            case R.id.bianji:
                int tag2= (int) bianji.getTag();
                if (tag2==1){

                    bianji.setText("完成");
                    bianji.setTag(2);
                    quzhifu.setVisibility(View.GONE);
                    linearShanchu.setVisibility(View.VISIBLE);
                }else {
                    bianji.setText("编辑");
                    bianji.setTag(1);
                    quzhifu.setVisibility(View.VISIBLE);
                    linearShanchu.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void success(CarBean carBean) {

        if (carBean!=null){
            adapter.add(carBean);
        }
    }

    @Override
    public void failure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,"网有点慢",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myPresenter.detach();
    }
}
