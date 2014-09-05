package org.bigmouth.framework.web;


import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.RequestUtils;
import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * 自定义URL格式
 */
public class RestfulActionMapper implements ActionMapper {
	protected static final Logger LOG = LoggerFactory
			.getLogger(RestfulActionMapper.class);

	String ACTION_EXT_NAME = ".shtml";

	public ActionMapping getMapping(HttpServletRequest request,
			ConfigurationManager configManager) {
		String nameSpace = "";
		String actionName = "";
		String method = "";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		String uri = RequestUtils.getServletPath(request);
		// 去掉后缀
		if (!uri.endsWith(ACTION_EXT_NAME)) {
			return null;
		} else {
			uri = uri.substring(0, uri.lastIndexOf(ACTION_EXT_NAME));
		}
		int nextSlash = uri.indexOf('/', 1);
		if (nextSlash == -1) {
			return null;
		}
		String[] parse = uri.split("[///]");
		if (1 < parse.length) {
			nameSpace = uri.substring(1, nextSlash);
		}
		if (2 < parse.length) {
			actionName = parse[2];
		}
		if (3 < parse.length) {
			method = parse[3];
			int beginIndex = ("/" + nameSpace + "/" + actionName + "/" + method)
					.length();
			try {
				if (uri.length() > beginIndex) {
					StringTokenizer st = new StringTokenizer(uri
							.substring(beginIndex + 1), "/");
					boolean isNameTok = true;
					String paramName = null;
					String paramValue;

					// check if we have the first parameter name
					if ((st.countTokens() % 2) != 0) {
						isNameTok = false;
						paramName = "id";
					}

					while (st.hasMoreTokens()) {
						if (isNameTok) {
							paramName = URLDecoder.decode(st.nextToken(),
									"UTF-8");
							isNameTok = false;
						} else {
							paramValue = URLDecoder.decode(st.nextToken(),
									"UTF-8");
							if ((paramName != null) && (paramName.length() > 0)) {
								parameters.put(paramName, paramValue);
							}
							isNameTok = true;
						}
					}
				}
			} catch (Exception e) {
				LOG.warn("Cannot determine url parameters", e);
			}
		}
//		bingingUrlParamsToAction();
		return new ActionMapping(actionName, "/" + nameSpace, method, parameters);
	}

	public ActionMapping getMappingFromActionName(String actionName) {
		return new ActionMapping(actionName, null, null, null);
	}

	public String getUriFromActionMapping(ActionMapping mapping) {
		String base = mapping.getNamespace() + "/" + mapping.getName();
		if (StringUtils.isNotBlank(mapping.getMethod())) {
			base = base + "/" + mapping.getMethod();
		}
		for (Iterator iterator = mapping.getParams().entrySet().iterator(); iterator
				.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String name = (String) entry.getKey();
			if (name.equals("id")) {
				base = base + "/" + entry.getValue();
			} else {
				base = base + "/" + name + "/" + entry.getValue();
			}
		}
		return base;
	}

}
