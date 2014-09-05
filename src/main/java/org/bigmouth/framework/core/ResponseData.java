package org.bigmouth.framework.core;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData implements Serializable {

    private static final long serialVersionUID = -9075429404577590761L;

    public final static int SUCCEED = 0;

    public final static int FAILED = -1;

    @Expose
    @SerializedName(value = "Flag")
    private int resultCode;
    @Expose
    @SerializedName(value = "Message")
    private String message;
    @Expose
    @SerializedName(value = "Data")
    private Object data;

    public ResponseData() {
    }

    public ResponseData(Object data) {
        this(SUCCEED, "", data);
    }

    public ResponseData(int resultCode, String message, Object data) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = null == data ? new Object() : data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
