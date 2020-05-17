package com.igomall.wechat.util.response.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.igomall.wechat.util.response.BaseResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBatchUnBlacklistResponse extends BaseResponse {
}
