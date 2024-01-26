package org.jxch.capital.utils;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;

public class JSONUtils {

    public static String toJsonAndNull(Object obj) {
        return JSONObject.toJSONString(obj, JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty);
    }

    public static String toJsonAndNullPretty(Object obj) {
        return JSONObject.toJSONString(obj, JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty, JSONWriter.Feature.PrettyFormat);
    }

}
