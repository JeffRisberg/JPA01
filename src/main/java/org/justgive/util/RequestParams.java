package org.justgive.util;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.*;

public class RequestParams {
    private static Logger jgLog = LoggerFactory.getLogger(RequestParams.class);

    private Map<String, List<String>> parameterMap = new HashMap<>();

    public RequestParams() {
    }

    public RequestParams(Map<String, String> parameterMap) {
        for (String param : parameterMap.keySet()) {
            setParam(param, parameterMap.get(param));
        }
    }

    public RequestParams(HttpServletRequest request) {
        for (Object key : request.getParameterMap().keySet()) {
            String param = (String) key;
            String[] values = request.getParameterValues(param);
            this.parameterMap.put(param, new ArrayList<>(Arrays.asList(values)));
        }
    }

    public RequestParams setParam(String key, String val) {
        if (key == null) throw new IllegalArgumentException("Null key");

        parameterMap.put(key, new ArrayList<>(Arrays.asList(val)));
        jgLog.trace("param added to url : " + key + "=" + val);
        return this;
    }

    public RequestParams appendParam(String key, String val) {
        if (key == null) throw new IllegalArgumentException("Null key");

        if (parameterMap.containsKey(key)) {
            List<String> values = parameterMap.get(key);
            values.add(val);
            jgLog.trace("param added to url : " + key + "=" + val);
        } else {
            setParam(key, val);
        }
        return this;
    }

    public RequestParams(String parameterString) {
        if (parameterString == null) return;
        parseParameterString(parameterString);
    }

    private void parseParameterString(String parameterString) {
        String[] params = parameterString.split("&");
        for (String param : params) {
            if (param.contains("=")) {
                String key = param.substring(0, param.indexOf('='));
                String value = param.substring(param.indexOf('=') + 1);
                appendParam(key, value);
            } else {
                appendParam(param, "");
            }
        }
    }

    public RequestParams addRequestAttributes(HttpServletRequest request) {
        jgLog.trace("Adding Request Attributes to parameterMap");
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attrName = attributeNames.nextElement();
            Object attrVal = request.getAttribute(attrName);
            if (attrVal instanceof String) setParam(attrName, getAttributeValue(request, attrName));
        }
        return this;
    }

    private String getAttributeValue(HttpServletRequest request, String param) {
        try {
            jgLog.trace("Adding Request Attribute value: " + param);
            return (String) request.getAttribute(param);
        } catch (Exception ex) {
            return null;
        }
    }

    public RequestParams maskSensitiveData() {
        for (String param : new ArrayList<>(parameterMap.keySet())) {
            if (paramRequiresMasking(param)) {
                List<String> values = parameterMap.remove(param);
                for (String value : values) {
                    String maskedValue = maskValue(value);
                    appendParam(param, maskedValue);
                }
            }
        }
        return this;
    }

    private boolean paramRequiresMasking(String paramName) {
        if (paramName == null) return false;
        String lowerParamName = paramName.toLowerCase();
        boolean requiresMasking = lowerParamName.contains("password") || lowerParamName.contains("name")
                || lowerParamName.contains("addressline") || lowerParamName.contains("city")
                || lowerParamName.contains("state") || lowerParamName.contains("zip")
                || lowerParamName.contains("csccode") || lowerParamName.contains("expmonth")
                || lowerParamName.contains("expyear") || lowerParamName.contains("hintanswer")
                || lowerParamName.contains("cardnumber");
        jgLog.trace(paramName + " requiresMasking?" + requiresMasking);
        return requiresMasking;
    }

    private String maskValue(String value) {
        return (value == null) ? "" : value.replaceAll(".", "*");
    }

    @Override
    public String toString() {
        return getQueryString();
    }

    private String getQueryString() {
        List<String> sortedParams = new ArrayList<>(Arrays.asList(parameterMap.keySet().toArray(new String[0])));
        Collections.sort(sortedParams);
        StringBuilder paramString = new StringBuilder("");
        for (String param : sortedParams) {
            List<String> values = parameterMap.get(param);
            if (values.size() > 1) {
                for (String value : values) {
                    paramString.append(getParamString(param, value));
                }
            } else {
                String value = values.get(0);
                paramString.append(getParamString(param, value));
            }
        }
        return (paramString.length() > 0)
                ? "?" + paramString.toString().substring(0, paramString.length() - 1)
                : paramString.toString();
    }

    private String getParamString(String param, String value) {
        StringBuilder paramString = new StringBuilder();
        paramString.append(URLEncoder.encode(param))
                .append("=")
                .append(URLEncoder.encode(value))
                .append("&");
        return paramString.toString();
    }

    public List<String> getValues(String param) {
        return parameterMap.get(param);
    }

    public String getValue(String param) {
        List<String> values = parameterMap.get(param);
        String value = (values == null || values.size() == 0) ? "" : values.get(0);
        jgLog.trace("Found " + param + " value: " + value);
        return value;
    }

    public boolean isEmpty() {
        return parameterMap.isEmpty();
    }

    public Map<String, String> getParameterMap() {
        Map<String, String> map = new HashMap<>();
        for (String param : parameterMap.keySet()) {
            map.put(param, getValue(param));
        }
        return map;
    }

    public String getXmlParams() {
        if (parameterMap.isEmpty()) return "";
        StringBuilder xmlParams = new StringBuilder();

        for (String param : parameterMap.keySet()) {
            xmlParams.append("    <param>").append(param).append("</param>\n");
            xmlParams.append("    <value>").append(getValue(param)).append("</value>\n");
        }
        return xmlParams.toString();
    }
}
