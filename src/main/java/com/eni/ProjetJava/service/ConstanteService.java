package com.eni.ProjetJava.service;

public class ConstanteService {

    //Succes
    public static final String CD_SUCCESS = "200";
    public static final String CD_CREATED = "201";

    //Erreur client
    public static final String CD_ERR_BAD_REQUEST = "400";
    public static final String CD_ERR_UNAUTHORIZED = "401";
    public static final String CD_ERR_FORBIDDEN = "403";
    public static final String CD_ERR_NOT_FOUND = "404";
    public static final String CD_ERR_CONFLICT = "409";
    public static final String CD_ERR_RATE_LIMIT = "429";

    //Erreur serveur
    public static final String CD_ERR_INTERNAL = "500";
    public static final String CD_ERR_UNAVAILABLE = "503";
}
