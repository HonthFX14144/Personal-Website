package com.kiss.personalproject.conn;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface Table<T> {
    List<T> getDocuments();
    List<T> getDocuments(String col, String val);
    T getDocument(String col, String val);
    void insertDocument(T t);
    void deleteDocument(String val);
    T updateDocument(T t, String col, String val) throws JsonProcessingException;
    T setFieldAndReturnBean();
}
