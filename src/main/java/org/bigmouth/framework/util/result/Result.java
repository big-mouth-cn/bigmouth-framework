package org.bigmouth.framework.util.result;

import java.io.Serializable;

/**
 * 方法执行返回集成结果接口
 */
public interface Result extends Serializable {
	
	boolean isSuccess();

	boolean isSubSuccess();

	void setSubSuccess(boolean success);

	void setSuccess(boolean success);

	Object getModel(String key);

	void setModel(String key, Object model);

	Object getDefaultModel();

	void setDefaultModel(Object model);

	String getErrorMessage();

	void setErrorMessage(String errorMessage);
	
	void fail();
	
	void success();
}
