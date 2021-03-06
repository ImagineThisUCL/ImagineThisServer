package com.ucl.imaginethisserver.DAO;

import com.ucl.imaginethisserver.Model.Conversion;

import java.util.UUID;


public interface ConversionDao {

    int addNewConversion(Conversion conversion);

    int updateConversion(UUID conversionID, Conversion conversion);

}
