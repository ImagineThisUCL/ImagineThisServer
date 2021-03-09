package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.DAO.ConversionDto;

import java.util.List;

public interface ConversionService {

    List<ConversionDto> getConversions(String projectID);
}
