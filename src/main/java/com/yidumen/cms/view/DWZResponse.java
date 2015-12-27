package com.yidumen.cms.view;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author 蔡迪旻
 *         2015年11月28日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DWZResponse {
    private int statusCode;
    private String message;
    private String forwardUrl;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }
}
