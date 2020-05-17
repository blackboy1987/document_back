package com.igomall.wechat.util.response.datacube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.igomall.wechat.util.response.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.INTERNAL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataCubeArticleSummaryResponse extends BaseResponse {

    private List<Data> list = new ArrayList<>();

    public List<Data> getList() {
        return list;
    }

    public void setList(List<Data> list) {
        this.list = list;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data implements Serializable {

        @JsonProperty("ref_date")
        private Date refDate;

        @JsonProperty("msgid")
        private String msgId;

        private String title;

        @JsonProperty("int_page_read_user")
        private Date intPageReadUser;

        @JsonProperty("int_page_read_count")
        private Date intPageReadCount;

        @JsonProperty("ori_page_read_user")
        private Date oriPageReadUser;

        @JsonProperty("ori_page_read_count")
        private Date oriPageReadCount;

        @JsonProperty("share_user")
        private Date shareUser;

        @JsonProperty("share_count")
        private Date shareCount;

        @JsonProperty("add_to_fav_user")
        private Date addToFavUser;

        @JsonProperty("ref_date")
        private List<Detail> details = new ArrayList<>();



        public static class Detail implements Serializable {

            @JsonProperty("stat_date")
            private Date statDate;

            @JsonProperty("target_user")
            private Integer targetUser;

            @JsonProperty("int_page_read_user")
            private Integer intPageReadUser;

            @JsonProperty("int_page_read_count")
            private Integer intPageReadCount;

            @JsonProperty("ori_page_read_user")
            private Integer oriPageReadUser;

            @JsonProperty("ori_page_read_count")
            private Integer oriPageReadCount;

            @JsonProperty("share_user")
            private Integer shareUser;

            @JsonProperty("share_count")
            private Integer shareCount;

            @JsonProperty("add_to_fav_user")
            private Integer addToFavUser;

            @JsonProperty("add_to_fav_count")
            private Integer addToFavCount;

            @JsonProperty("int_page_from_session_read_user")
            private Integer intPageFromSessionReadUser;

            @JsonProperty("int_page_from_session_read_count")
            private Integer intPageFromSessionReadCount;

            @JsonProperty("int_page_from_hist_msg_read_user")
            private Integer intPageFromHistMsgReadUser;

            @JsonProperty("int_page_from_hist_msg_read_count")
            private Integer intPageFromHistMsgReadCount;

            @JsonProperty("int_page_from_feed_read_user")
            private Integer intPageFromFeedReadUser;

            @JsonProperty("int_page_from_feed_read_count")
            private Integer intPageFromFeedReadCount;

            @JsonProperty("int_page_from_friends_read_user")
            private Integer intPageFromFriendsReadUser;

            @JsonProperty("int_page_from_friends_read_count")
            private Integer intPageFromFriendsReadUCount;

            @JsonProperty("int_page_from_other_read_user")
            private Integer intPageFromOtherReadUser;

            @JsonProperty("int_page_from_other_read_count")
            private Integer intPageFromOtherReadCount;

            @JsonProperty("feed_share_from_session_user")
            private Integer feedShareFromSessionUser;

            @JsonProperty("feed_share_from_session_cnt")
            private Integer feedShareFromSessionCnt;

            @JsonProperty("feed_share_from_feed_user")
            private Integer feedShareFromFeedUser;

            @JsonProperty("feed_share_from_feed_cnt")
            private Integer feedShareFromFeedCnt;

            @JsonProperty("feed_share_from_other_user")
            private Integer feedShareFromOtherUser;

            @JsonProperty("feed_share_from_other_cnt")
            private Integer feedShareFromOtherCnt;

        }
    }
}
