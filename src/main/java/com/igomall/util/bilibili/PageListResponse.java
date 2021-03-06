package com.igomall.util.bilibili;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PageListResponse implements Serializable {

    private Integer code;

    private String message;

    private Integer ttl;

    private List<Page> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public List<Page> getData() {
        return data;
    }

    public void setData(List<Page> data) {
        this.data = data;
    }
}
