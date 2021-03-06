package com.ucl.imaginethisserver.DAO.DAOImpl;

import com.ucl.imaginethisserver.DAO.ConversionDao;
import com.ucl.imaginethisserver.Mapper.ConversionDynamicSqlSupport;
import com.ucl.imaginethisserver.Mapper.ConversionMapper;
import com.ucl.imaginethisserver.Model.Conversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;


@Repository
public class ConversionDaoImpl implements ConversionDao {

    private final ConversionMapper conversionMapper;

    @Autowired
    public ConversionDaoImpl(ConversionMapper conversionMapper) {
        this.conversionMapper = conversionMapper;
    }

    @Override
    public int addNewConversion(Conversion conversion) {
        conversion.setConversionId(UUID.fromString(conversion.getConversionId().toString()));
        conversion.setUserId(UUID.fromString(conversion.getUserId().toString()));
        return conversionMapper.insert(conversion);
    }

    @Override
    public int updateConversion(UUID conversionID, Conversion conversion) {
        return conversionMapper.update(c -> c
                .set(ConversionDynamicSqlSupport.conversionStatus).equalTo(conversion.getConversionStatus())
                .set(ConversionDynamicSqlSupport.publishStatus).equalTo(conversion.getPublishStatus())
                .where(ConversionDynamicSqlSupport.conversionId, isEqualTo(conversionID))
        );
    }
}
