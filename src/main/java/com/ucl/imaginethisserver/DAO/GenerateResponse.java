package com.ucl.imaginethisserver.DAO;

import com.google.gson.annotations.Expose;

/**
 * The response that the server send to the client side after generation process
 */
public class GenerateResponse {
    /**
     * If the generation process is succeed
     */
    @Expose()
    boolean isSuccess;
    /**
     * The name of generated zip file if the generation process is successful.
     */
    @Expose()
    String fileName;

    public GenerateResponse(boolean isSuccess, String fileName){
        this.isSuccess = isSuccess;
        this.fileName = fileName;
    }
}
