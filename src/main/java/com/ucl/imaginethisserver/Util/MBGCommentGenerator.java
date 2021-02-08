package com.ucl.imaginethisserver.Util;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Set;

public class MBGCommentGenerator extends DefaultCommentGenerator{
    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {

        String remarks = introspectedColumn.getRemarks();
        //
        if (StringUtility.stringHasValue(remarks)) {
            //
            if (remarks.contains("\"")) {
                remarks = remarks.replace("\"", "'");
            }
            //
            field.addJavaDocLine("@ApiModelProperty(value = \"" + remarks + "\")");
        }
    }
}
