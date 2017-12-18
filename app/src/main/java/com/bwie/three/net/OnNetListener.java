package com.bwie.three.net;

public interface OnNetListener<T> {
    void OnSuccess(T t);
    void OnFailed(Exception e);
}