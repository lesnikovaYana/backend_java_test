package ru.lesnikovaYana;

public class Endpoints {
    public static final String GET_ACCOUNT = "/account/{username}";
    public static final String UPLOAD_FILE = "/upload";
    public static final String DELETE_AUTHED = "/image/{imageHash}";
    public static final String DELETE_UN_AUTHED = "/image/{imageDeleteHash}";
    public static final String GET_IMAGE = "/image/{imageHash}";
}
