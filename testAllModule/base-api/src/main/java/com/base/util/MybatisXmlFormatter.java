package com.base.util;

import org.mybatis.generator.api.XmlFormatter;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.config.Context;

import java.util.Collections;
import java.util.List;

public class MybatisXmlFormatter implements XmlFormatter {
    protected Context context;
    
    public String getFormattedContent(Document document) {
        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); //$NON-NLS-1$

        XmlElement rootElement = document.getRootElement();
        if (document.getPublicId() != null && document.getSystemId() != null) {
            OutputUtilities.newLine(sb);
            sb.append("<!DOCTYPE "); //$NON-NLS-1$
            sb.append(rootElement.getName());
            sb.append(" PUBLIC \""); //$NON-NLS-1$
            sb.append(document.getPublicId());
            sb.append("\" \""); //$NON-NLS-1$
            sb.append(document.getSystemId());
            sb.append("\">"); //$NON-NLS-1$
        }

        OutputUtilities.newLine(sb);        
                
        String content = this.getFormattedContent(rootElement, 0);
        sb.append(content);
        return sb.toString();
    }
    
    private String getFormattedContent(XmlElement el, int indentLevel) {
        StringBuilder sb = new StringBuilder();
        
        // 格式化换行
        this.appendNewLineIfNeed(el, sb);
        
        OutputUtilities.xmlIndent(sb, indentLevel);
        sb.append('<');
        sb.append(el.getName());
        
        List<Attribute> attributes = el.getAttributes();
        Collections.sort(attributes);
        for (Attribute att : attributes) {
            sb.append(' ');
            sb.append(att.getFormattedContent());
        }
        
        List<Element> elements = el.getElements();
        if (elements.size() > 0) {
            sb.append(">"); //$NON-NLS-1$
            for (Element element : elements) {
                OutputUtilities.newLine(sb);
                if (element instanceof TextElement) {
                    sb.append(element.getFormattedContent(indentLevel + 1));
                } else {
                    sb.append(this.getFormattedContent((XmlElement)element, indentLevel + 1));
                }
            }
            OutputUtilities.newLine(sb);
            OutputUtilities.xmlIndent(sb, indentLevel);
            sb.append("</"); //$NON-NLS-1$
            sb.append(el.getName());
            sb.append('>');

        } else {
            sb.append(" />"); //$NON-NLS-1$
        }

        return sb.toString();
    }
    
    private void appendNewLineIfNeed(XmlElement el, StringBuilder sb) {
        switch (el.getName()) {
        case "select": 
            OutputUtilities.newLine(sb);
            break;
        case "delete": 
            OutputUtilities.newLine(sb);
            break;
        case "update": 
            OutputUtilities.newLine(sb);
            break;
        case "insert": 
            OutputUtilities.newLine(sb);
            break;
        case "sql": 
            OutputUtilities.newLine(sb);
            break;
        case "cache": 
            OutputUtilities.newLine(sb);
            break;
        case "cache-ref": 
            OutputUtilities.newLine(sb);
            break;
        case "parameterMap": 
            OutputUtilities.newLine(sb);
            break;
        case "resultMap": 
            OutputUtilities.newLine(sb);
            break;
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
        
}
