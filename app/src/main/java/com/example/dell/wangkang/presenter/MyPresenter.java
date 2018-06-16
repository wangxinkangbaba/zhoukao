package com.example.dell.wangkang.presenter;


import com.example.dell.wangkang.bean.CarBean;
import com.example.dell.wangkang.model.MyModel;
import com.example.dell.wangkang.model.MyModelCallBack;
import com.example.dell.wangkang.view.MyViewCallBack;

public class MyPresenter {
    MyModel myModel;
    MyViewCallBack myViewCallBack;

    public MyPresenter(MyViewCallBack myViewCallBack) {
        this.myModel = new MyModel();
        this.myViewCallBack = myViewCallBack;
    }
    public void select(){
        myModel.select(new MyModelCallBack() {
            @Override
            public void success(CarBean carBean) {
                myViewCallBack.success(carBean);
            }

            @Override
            public void failure() {
                myViewCallBack.failure();
            }
        });
    }
    public void detach(){
        this.myViewCallBack=null;
    }
}
