package org.bigmouth.framework.interceptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.velocity.app.event.implement.EscapeHtmlReference;

public class VelocityEscapeHtmlReference extends EscapeHtmlReference {  
	  
    @Override  
    protected String escape(Object text) {  
        return escapeHtml(text);  
    }  
  
    private static String escapeHtml(Object value) {  
        if (value == null)  
            return null;  
  
        if (value instanceof String) {  
            String result = value.toString();  
            
            //对某些特殊的字段不错HTML的escape操作，比如(搜索高亮、分页代码)
            String regEx="skymobiEscapeHtml=\"false\"";  
            Pattern p=Pattern.compile(regEx); 
            Matcher m=p.matcher(result); 
            boolean rs=m.find(); 
            if(!rs){
            	 // ["、'、<、 、>、&] IE不支持单引号的&apos;
                result = result.replaceAll("&", "&#38;").replaceAll(">", "&#62;")  
                        .replaceAll("<", "&#60;").replaceAll("\"", "&#34;")
                        .replaceAll("\'", "&#39;"); 
            }else{
            	result = result.replaceAll("skymobiEscapeHtml=\"false\"", "");
            }
            
            //\r\n
            result = result.replaceAll("\\r\\n", "<br/>");
            
            return result;  
        } else {  
            return value.toString();  
        }  
    }  
} 