package com.ucl.imaginethisserver.DAO;

import com.ucl.imaginethisserver.Model.Conversion;

import java.util.List;
import java.util.UUID;


public interface ConversionDao {

    int addNewConversion(Conversion conversion);

    int updateConversion(UUID conversionID, Conversion conversion);

    List<ConversionDto> getConversions(String projectID);

}
