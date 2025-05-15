package com.gruposv.microservice_stock.dto;

public class ApiResponseDTO<T> {

    private String status;
    private int code;
    private T data;
    private String message;

    public ApiResponseDTO() {
    }

    public ApiResponseDTO(String status, int code, T data, String message) {
        this.status = status;
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
