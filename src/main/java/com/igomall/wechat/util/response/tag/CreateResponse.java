package com.igomall.wechat.util.response.tag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.igomall.wechat.util.response.BaseResponse;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateResponse extends BaseResponse {

    private UserTag tag;

    public UserTag getTag() {
        return tag;
    }

    public void setTag(UserTag tag) {
        this.tag = tag;
    }

    public static class UserTag implements Serializable{

        private Long id;

        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
