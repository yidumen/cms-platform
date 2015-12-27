package com.yidumen.cms.view;

/**
 * @author 蔡迪旻
 *         2015年11月28日
 */
public final class DWZResponseBuilder {
    public DWZResponse response;

    public static DWZResponseBuilder initiate() {
        final DWZResponseBuilder result = new DWZResponseBuilder();
        result.setResponse(new DWZResponse());
        return result;
    }

    public DWZResponseBuilder success(String message) {
        response.setStatusCode(200);
        response.setMessage(message);
        return this;
    }

    public DWZResponseBuilder error(String message) {
        response.setStatusCode(300);
        response.setMessage(message);
        return this;
    }

    public DWZResponseBuilder forwardUrl(String url) {
        response.setForwardUrl(url);
        return this;
    }

    public DWZResponse builder() {
        return response;
    }

    public DWZResponse getResponse() {
        return response;
    }

    public void setResponse(DWZResponse response) {
        this.response = response;
    }
}
