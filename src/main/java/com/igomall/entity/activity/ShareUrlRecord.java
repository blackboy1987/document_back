package com.igomall.entity.activity;

import com.igomall.entity.BaseEntity;
import com.igomall.wechat.entity.WeChatUser;

import javax.persistence.*;

@Entity
@Table(name = "edu_share_url_record")
public class ShareUrlRecord extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private WeChatUser weChatUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private ShareUrl shareUrl;

    private Long count1;

    private Long count2;

    private Integer status;


    public WeChatUser getWeChatUser() {
        return weChatUser;
    }

    public void setWeChatUser(WeChatUser weChatUser) {
        this.weChatUser = weChatUser;
    }

    public ShareUrl getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(ShareUrl shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Long getCount1() {
        return count1;
    }

    public void setCount1(Long count1) {
        this.count1 = count1;
    }

    public Long getCount2() {
        return count2;
    }

    public void setCount2(Long count2) {
        this.count2 = count2;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
