package com.example.dell.wangkang.presenter;


import com.example.dell.wangkang.bean.DeleteBean;
import com.example.dell.wangkang.model.MyModel2;
import com.example.dell.wangkang.model.MyModelCallBack2;
import com.example.dell.wangkang.view.MyViewCallBack2;

public class MyPresenter2 {
    MyModel2 myModel2;
    MyViewCallBack2 myViewCallBack2;

    public MyPresenter2(MyViewCallBack2 myViewCallBack2) {
        this.myModel2 = new MyModel2();
        this.myViewCallBack2 = myViewCallBack2;
    }
    public void delete(String pid){
        myModel2.delete(pid,new MyModelCallBack2() {
            @Override
            public void success(DeleteBean deleteBean) {
                myViewCallBack2.success(deleteBean);
            }

            @Override
            public void failure() {
                myViewCallBack2.failure();
            }
        });
    }
    public void detach(){
        this.myViewCallBack2=null;
    }
}
