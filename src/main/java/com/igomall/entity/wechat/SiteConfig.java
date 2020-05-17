package com.igomall.entity.wechat;

import com.igomall.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "edu_site_config")
public class SiteConfig extends BaseEntity<Long> {

    @Column(length = 4000)
    private String yzm;


    public String getYzm() {
        return yzm;
    }

    public void setYzm(String yzm) {
        this.yzm = yzm;
    }
}
