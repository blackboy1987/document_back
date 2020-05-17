package com.igomall.util.bilibili;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data implements Serializable {

    private String cid;

    private Integer page;

    private String from;

    private String part;

    private Long duration;

    private String vid;

    private String weblink;

    private Dimension Dimension;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }

    public Data.Dimension getDimension() {
        return Dimension;
    }

    public void setDimension(Data.Dimension dimension) {
        Dimension = dimension;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Dimension implements Serializable{
        private Integer width;

        private Integer height;

        private Integer rotate;


        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public Integer getRotate() {
            return rotate;
        }

        public void setRotate(Integer rotate) {
            this.rotate = rotate;
        }
    }
}
