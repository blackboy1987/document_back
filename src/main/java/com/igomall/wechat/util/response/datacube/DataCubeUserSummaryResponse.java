package com.igomall.wechat.util.response.datacube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.igomall.wechat.util.response.BaseResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataCubeUserSummaryResponse extends BaseResponse {

    private List<Data> list = new ArrayList<>();

    public List<Data> getList() {
        return list;
    }

    public void setList(List<Data> list) {
        this.list = list;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data implements Serializable{

        /**
         *数据的日期
         */
        @JsonProperty("ref_date")
        private Date refData;

        /**
         * 用户的渠道，数值代表的含义如下：
         *  0代表其他合计
         *  1代表公众号搜索
         *  17代表名片分享
         *  30代表扫描二维码
         *  43代表图文页右上角菜单
         *  51代表支付后关注（在支付完成页）
         *  57代表图文页内公众号名称
         *  75代表公众号文章广告
         *  78代表朋友圈广告
         *  161他人转载（原创文章被他人转载后跳转进入公众号主页）
         */
        @JsonProperty("user_source")
        private Long userSource;

        /**
         *新增的用户数量
         */
        @JsonProperty("new_user")
        private Long newUser;
        /**
         *取消关注的用户数量，new_user减去cancel_user即为净增用户数量
         */
        @JsonProperty("cancel_user")
        private Long cancelUser;

        /**
         * 总用户量
         */
        @JsonProperty("cumulate_user")
        private Long cumulateUser;

        public Date getRefData() {
            return refData;
        }

        public void setRefData(Date refData) {
            this.refData = refData;
        }

        public Long getUserSource() {
            return userSource;
        }

        public void setUserSource(Long userSource) {
            this.userSource = userSource;
        }

        public Long getNewUser() {
            return newUser;
        }

        public void setNewUser(Long newUser) {
            this.newUser = newUser;
        }

        public Long getCancelUser() {
            return cancelUser;
        }

        public void setCancelUser(Long cancelUser) {
            this.cancelUser = cancelUser;
        }

        public Long getCumulateUser() {
            return cumulateUser;
        }

        public void setCumulateUser(Long cumulateUser) {
            this.cumulateUser = cumulateUser;
        }
    }
}
