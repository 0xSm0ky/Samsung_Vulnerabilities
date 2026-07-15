package com.samsung.oda.lib.message.data;

import java.io.Serializable;
import java.util.HashMap;

public class WebViewData implements Serializable {
    public static final long serialVersionUID = 7058073654229063220L;

    public String mUrl;
    public HttpMethod mMethodType;
    public String mBody;
    public HashMap<String, String> mHeaders;
}
