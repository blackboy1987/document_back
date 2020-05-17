package com.igomall.wechat.util.response.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.igomall.wechat.util.response.BaseResponse;
import org.omg.CORBA.INTERNAL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserGetResponse extends BaseResponse {

    /**
     * 关注该公众账号的总用户数
     */
    private Integer total;

    /**
     *拉取的OPENID个数，最大值为10000
     */
    private Integer count;

    /**
     *列表数据，OPENID的列表
     */
    private Data data;

    /**
     * 拉取列表的最后一个用户的OPENID
     */
    @JsonProperty("next_openid")
    private String nextOpenId;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getNextOpenId() {
        return nextOpenId;
    }

    public void setNextOpenId(String nextOpenId) {
        this.nextOpenId = nextOpenId;
    }

    public static class Data implements Serializable{

        @JsonProperty("openid")
        List<String> openIds = new ArrayList<>();

        public List<String> getOpenIds() {
            return openIds;
        }

        public void setOpenIds(List<String> openIds) {
            this.openIds = openIds;
        }
    }
}
