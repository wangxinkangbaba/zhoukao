package com.example.dell.wangkang.view;

import com.example.dell.wangkang.bean.CarBean;
import com.example.dell.wangkang.bean.DeleteBean;
import com.example.i.moontext.bean.CarBean;

/**
 * Created by 因为有个你i on 2018/5/31.
 */

public interface MyViewCallBack {
    public void success(CarBean carBean);

    void success(CarBean deleteBean);

    public void failure();
}
