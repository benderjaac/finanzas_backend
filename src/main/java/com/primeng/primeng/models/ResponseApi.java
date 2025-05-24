package com.primeng.primeng.models;

import java.util.Date;

public class ResponseApi<T> {
    private String title;
    private String status;
    private String message;
    private T data;
    private Date timestamp;

    public ResponseApi() {}

    public ResponseApi(String title, String status, String message, T data, Date timestamp) {
        this.title = title;
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    // Getters y setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Date gettimestamp() {
        return timestamp;
    }

    public void settimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
