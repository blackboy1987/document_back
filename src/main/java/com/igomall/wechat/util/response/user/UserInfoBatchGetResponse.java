package com.igomall.wechat.util.response.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.igomall.wechat.util.response.BaseResponse;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoBatchGetResponse extends BaseResponse {

    @JsonProperty("user_info_list")
    private List<UserInfoResponse> userInfoList = new ArrayList<>();

    public List<UserInfoResponse> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<UserInfoResponse> userInfoList) {
        this.userInfoList = userInfoList;
    }
}
