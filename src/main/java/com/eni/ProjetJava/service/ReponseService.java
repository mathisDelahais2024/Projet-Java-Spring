package com.eni.ProjetJava.service;

public class ReponseService<T> {
    public String code;
    public String message;
    public T data;

    public static <T> ReponseService<T> construireReponse(String code, String message, T data){
        ReponseService<T> response = new ReponseService();
        response.code = code;
        response.message = message;
        response.data = data;

        System.out.println(String.format("Code : %s | Message : %s", code, message));

        return response;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
