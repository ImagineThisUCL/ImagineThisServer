package com.ucl.imaginethisserver.DAO;

import com.google.gson.annotations.Expose;

/**
 *  Object that represents the Fills, no matter which component it is applied to.
 *  Contains most of the common values of the Fills type.
 */
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
