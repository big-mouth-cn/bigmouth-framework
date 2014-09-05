package org.bigmouth.framework.util.result;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认的方法执行返回集成结果接口实现类。
 */
public class DefaultResult implements Result {

    private static final long serialVersionUID = 1835589109398564199L;

    private boolean success = true;

    private boolean subSuccess = true;

    private Map<String, Object> models = new HashMap<String, Object>();

    private String errorMessage = "";

    private final static String DEFAULT_MODEL_KEY = "#default#";

    public boolean isSuccess() {

        return success;
    }

    public boolean isSubSuccess() {

        return subSuccess;
    }

    public void setSubSuccess(boolean subSuccess) {
        this.subSuccess = subSuccess;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getModel(String key) {

        return models.get(key);
    }

    public void setModel(String key, Object model) {
        models.put(key, model);
    }

    public Object getDefaultModel() {

        return models.get(DEFAULT_MODEL_KEY);
    }

    public void setDefaultModel(Object model) {
        models.put(DEFAULT_MODEL_KEY, model);

    }

    public String getErrorMessage() {

        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void fail() {
        setSuccess(false);
    }

    @Override
    public void success() {
        setSuccess(true);
    }

}
