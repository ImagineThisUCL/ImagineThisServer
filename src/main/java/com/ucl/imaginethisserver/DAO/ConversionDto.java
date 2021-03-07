package com.ucl.imaginethisserver.DAO;

import com.ucl.imaginethisserver.Model.Conversion;

/**
 * Useful for getting extra information on Conversion like name of user
 * performing the conversion
 */
public class ConversionDto extends Conversion {

    private String userName;

    public String getUserName() { return userName; }

}
