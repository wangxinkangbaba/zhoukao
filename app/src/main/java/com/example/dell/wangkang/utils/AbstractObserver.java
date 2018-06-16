package com.example.dell.wangkang.utils;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class AbstractObserver<T> implements Observer<String> {

    public abstract void onSuccess(T t);
    public abstract void onFailure();
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(String result) {

        Type type = getClass().getGenericSuperclass();
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        Class clazz = (Class) types[0];

        Gson gson = new Gson();
        T t = (T) gson.fromJson(result,clazz);


        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        try {
            onFailure();

        }catch (Exception e1){
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }
}
