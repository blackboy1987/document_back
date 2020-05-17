package com.igomall.wechat.entity.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.igomall.common.BaseAttributeConverter;
import com.igomall.wechat.util.response.BaseResponse;

import javax.persistence.Convert;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IpListResponse extends BaseResponse {

    @JsonProperty("ip_list")
    @Convert(converter = IpListConvert.class)
    private List<String> ipList = new ArrayList<>();

    public List<String> getIpList() {
        return ipList;
    }

    public void setIpList(List<String> ipList) {
        this.ipList = ipList;
    }

    @Convert
    public static class IpListConvert extends BaseAttributeConverter<List<String>>{

    }
}
