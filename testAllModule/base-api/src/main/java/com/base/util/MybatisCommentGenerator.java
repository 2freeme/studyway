package com.base.util;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

public class MybatisCommentGenerator implements CommentGenerator {
        
    @Override
    public void addFieldComment(Field field, IntrospectedTable table, IntrospectedColumn column) {
        field.addJavaDocLine("/**"); //$NON-NLS-1$        
        String remarks = column.getRemarks();
        if (StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));  //$NON-NLS-1$
            for (String remarkLine : remarkLines) {
                field.addJavaDocLine(" * " + remarkLine);  //$NON-NLS-1$
            }
        }
        field.addJavaDocLine(" * 字段名 : " + column.getActualColumnName());        
        field.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable table) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addClassComment(InnerClass clazz, IntrospectedTable table) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addClassComment(InnerClass clazz, IntrospectedTable table, boolean markAsDoNotDelete) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addComment(XmlElement arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addConfigurationProperties(Properties arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addEnumComment(InnerEnum arg0, IntrospectedTable arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addGeneralMethodComment(Method arg0, IntrospectedTable arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addGetterComment(Method arg0, IntrospectedTable arg1, IntrospectedColumn arg2) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addJavaFileComment(CompilationUnit arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addModelClassComment(TopLevelClass clazz, IntrospectedTable table) {
        clazz.addJavaDocLine("/**"); //$NON-NLS-1$
        clazz.addJavaDocLine(" * 表名： " + table.getFullyQualifiedTable());        
        clazz.addJavaDocLine(" */");
    }

    @Override
    public void addRootComment(XmlElement arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addSetterComment(Method arg0, IntrospectedTable arg1, IntrospectedColumn arg2) {
        // TODO Auto-generated method stub
        
    }

}
