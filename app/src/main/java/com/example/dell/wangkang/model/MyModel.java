package com.example.dell.wangkang.model;



import com.example.dell.wangkang.bean.CarBean;
import com.example.dell.wangkang.utils.APIFactory;
import com.example.dell.wangkang.utils.AbstractObserver;

import java.util.HashMap;
import java.util.Map;



public class MyModel {
    public void select(final MyModelCallBack myModelCallBack){
        final Map<String,String> map=new HashMap<>();
        map.put("source","android");
        map.put("uid","14927");
        APIFactory.getInstance().select("/product/getCarts", map, new AbstractObserver<CarBean>() {
            @Override
            public void onSuccess(CarBean carBean) {
                myModelCallBack.success(carBean);
            }

            @Override
            public void onFailure() {

                myModelCallBack.failure();
            }
        });
    }
}
