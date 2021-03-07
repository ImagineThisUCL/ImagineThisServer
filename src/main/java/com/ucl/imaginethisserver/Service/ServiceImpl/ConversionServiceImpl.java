package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.DAO.ConversionDao;
import com.ucl.imaginethisserver.DAO.ConversionDto;
import com.ucl.imaginethisserver.Service.ConversionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ConversionServiceImpl implements ConversionService {

    private final ConversionDao conversionDao;

    private final Logger logger = LoggerFactory.getLogger(ConversionServiceImpl.class);

    @Autowired
    public ConversionServiceImpl(ConversionDao conversionDao) {
        this.conversionDao = conversionDao;
    }

    public List<ConversionDto> getConversions(String projectID) {
        return conversionDao.getConversions(projectID);
    }

}
