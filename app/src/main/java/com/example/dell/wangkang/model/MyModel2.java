package com.example.dell.wangkang.model;



import com.example.dell.wangkang.bean.DeleteBean;
import com.example.dell.wangkang.utils.APIFactory;
import com.example.dell.wangkang.utils.AbstractObserver;

import java.util.HashMap;
import java.util.Map;


public class MyModel2 {
    public void delete(String pid,final MyModelCallBack2 myModelCallBack2){
        final Map<String,String> map=new HashMap<>();
        map.put("source","android");
        map.put("pid",pid);
        map.put("uid","14927");
        APIFactory.getInstance().delete("/product/deleteCart", map, new AbstractObserver<DeleteBean>() {
            @Override
            public void onSuccess(DeleteBean deleteBean) {
                myModelCallBack2.success(deleteBean);
            }

            @Override
            public void onFailure() {

                myModelCallBack2.failure();
            }
        });
    }
}
