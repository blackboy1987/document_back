package com.igomall.wechat.util.response.tag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.igomall.wechat.util.response.BaseResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteResponse extends BaseResponse {
}
