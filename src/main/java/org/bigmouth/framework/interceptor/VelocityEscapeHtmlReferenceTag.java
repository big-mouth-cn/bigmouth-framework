package org.bigmouth.framework.interceptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.velocity.app.event.implement.EscapeHtmlReference;


public class VelocityEscapeHtmlReferenceTag extends EscapeHtmlReference {  
	  
    @Override  
    protected String escape(Object text) {  
        return escapeHtml(text);  
    }  
    
    private static String escapeHtml(Object value) {  
        if (value == null)  
            return null;  
        /**
         * 对某些特殊的字段不错HTML的escape操作，比如(搜索高亮、分页代码)
         * skymobiEscapeTag标签使用方法
         * 1.skymobiEscapeTag标签内不能嵌套skymobiEscapeTag标签
         * 2.skymobiEscapeTag最后转为div使用
         */
        if (value instanceof String) {  
            String result = value.toString();  
            String result_1 = "";
            String result_2 = "";
            String result_3 = "";
        	String regEx = "([\\s\\S]*)(<skymobiEscapeTag[^>]*>[\\s\\S]*</skymobiEscapeTag>)([\\s\\S]*)";
        	Pattern p=Pattern.compile(regEx); 
            Matcher m=p.matcher(result);
            while(m.find()){
            	result_1 = getResult(m.group(1));
            	result_2 = getResultDiv(m.group(2));
            	result_3 = getResult(m.group(3));
            	result = result_1 + result_2 + result_3;
            }    
            
            //\r\n
            result = result.replaceAll("\\r\\n", "<br/>");
            
            return result;  
        } else {  
            return value.toString();  
        }  
    }  
    
    private static String getResult(String result){
    	result = result.replaceAll("&", "&#38;").replaceAll(">", "&#62;")  
        .replaceAll("<", "&#60;").replaceAll("\"", "&#34;")
        .replaceAll("\'", "&#39;"); 
    	return result;
    }
    
    private static String getResultDiv(String result){
    	result = result.replaceAll("skymobiEscapeTag", "div");
    	return result;
    }
    
} 