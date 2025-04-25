package com.gtfsapp.api.service.interfaces;

public interface DataProcessing<T> {
    void process(T dto);
}
