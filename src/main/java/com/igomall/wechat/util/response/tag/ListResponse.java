package com.igomall.wechat.util.response.tag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.igomall.wechat.util.response.BaseResponse;

import javax.naming.directory.SearchResult;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListResponse extends BaseResponse {

    private List<Tag> tags;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public static class Tag implements Serializable{
        private Long id;

        private String name;

        private Long count;

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

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }
    }
}
