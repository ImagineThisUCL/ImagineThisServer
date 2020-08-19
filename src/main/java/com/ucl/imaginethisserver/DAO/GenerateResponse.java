package com.ucl.imaginethisserver.DAO;

import com.google.gson.annotations.Expose;

public class GenerateResponse {
    @Expose()
    boolean isSuccess;
    @Expose()
    String fileName;

    public GenerateResponse(boolean isSuccess, String fileName){
        this.isSuccess = isSuccess;
        this.fileName = fileName;
    }
}
