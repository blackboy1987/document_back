package com.igomall.util.bilibili;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AidResponse implements Serializable {

    private Integer code;

    private String message;

    private Integer ttl;

    private Data data;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data implements Serializable{
       private String bvid;
        private Long aid;
        private Long videos;
        private Long tid;
        private String tname;
        private Integer copyright;
        private String pic;
        private String title;
        private Long pubdate;
        private Long ctime;
        private String desc;
        private Integer state;
        private Integer attribute;
        private Integer duration;
        private Rights rights;
        private Owner owner;
        private Stat stat;
        private String dynamic;
        private Long cid;
        private Dimension dimension;
        private Boolean no_cache;
        private List<Page> pages;


        public String getBvid() {
            return bvid;
        }

        public void setBvid(String bvid) {
            this.bvid = bvid;
        }

        public Long getAid() {
            return aid;
        }

        public void setAid(Long aid) {
            this.aid = aid;
        }

        public Long getVideos() {
            return videos;
        }

        public void setVideos(Long videos) {
            this.videos = videos;
        }

        public Long getTid() {
            return tid;
        }

        public void setTid(Long tid) {
            this.tid = tid;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public Integer getCopyright() {
            return copyright;
        }

        public void setCopyright(Integer copyright) {
            this.copyright = copyright;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Long getPubdate() {
            return pubdate;
        }

        public void setPubdate(Long pubdate) {
            this.pubdate = pubdate;
        }

        public Long getCtime() {
            return ctime;
        }

        public void setCtime(Long ctime) {
            this.ctime = ctime;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public Integer getAttribute() {
            return attribute;
        }

        public void setAttribute(Integer attribute) {
            this.attribute = attribute;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public Rights getRights() {
            return rights;
        }

        public void setRights(Rights rights) {
            this.rights = rights;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public Stat getStat() {
            return stat;
        }

        public void setStat(Stat stat) {
            this.stat = stat;
        }

        public String getDynamic() {
            return dynamic;
        }

        public void setDynamic(String dynamic) {
            this.dynamic = dynamic;
        }

        public Long getCid() {
            return cid;
        }

        public void setCid(Long cid) {
            this.cid = cid;
        }

        public Dimension getDimension() {
            return dimension;
        }

        public void setDimension(Dimension dimension) {
            this.dimension = dimension;
        }

        public Boolean getNo_cache() {
            return no_cache;
        }

        public void setNo_cache(Boolean no_cache) {
            this.no_cache = no_cache;
        }

        public List<Page> getPages() {
            return pages;
        }

        public void setPages(List<Page> pages) {
            this.pages = pages;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Rights implements Serializable{
        private Integer bp;
        private Integer elec;
        private Integer download;
        private Integer movie;
        private Integer pay;
        private Integer hd5;
        private Integer no_reprint;
        private Integer autoplay;
        private Integer ugc_pay;
        private Integer is_cooperation;
        private Integer ugc_pay_preview;
        private Integer no_background;


        public Integer getBp() {
            return bp;
        }

        public void setBp(Integer bp) {
            this.bp = bp;
        }

        public Integer getElec() {
            return elec;
        }

        public void setElec(Integer elec) {
            this.elec = elec;
        }

        public Integer getDownload() {
            return download;
        }

        public void setDownload(Integer download) {
            this.download = download;
        }

        public Integer getMovie() {
            return movie;
        }

        public void setMovie(Integer movie) {
            this.movie = movie;
        }

        public Integer getPay() {
            return pay;
        }

        public void setPay(Integer pay) {
            this.pay = pay;
        }

        public Integer getHd5() {
            return hd5;
        }

        public void setHd5(Integer hd5) {
            this.hd5 = hd5;
        }

        public Integer getNo_reprint() {
            return no_reprint;
        }

        public void setNo_reprint(Integer no_reprint) {
            this.no_reprint = no_reprint;
        }

        public Integer getAutoplay() {
            return autoplay;
        }

        public void setAutoplay(Integer autoplay) {
            this.autoplay = autoplay;
        }

        public Integer getUgc_pay() {
            return ugc_pay;
        }

        public void setUgc_pay(Integer ugc_pay) {
            this.ugc_pay = ugc_pay;
        }

        public Integer getIs_cooperation() {
            return is_cooperation;
        }

        public void setIs_cooperation(Integer is_cooperation) {
            this.is_cooperation = is_cooperation;
        }

        public Integer getUgc_pay_preview() {
            return ugc_pay_preview;
        }

        public void setUgc_pay_preview(Integer ugc_pay_preview) {
            this.ugc_pay_preview = ugc_pay_preview;
        }

        public Integer getNo_background() {
            return no_background;
        }

        public void setNo_background(Integer no_background) {
            this.no_background = no_background;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Owner implements Serializable{
        private Long mid;

        private String name;

        private String face;


        public Long getMid() {
            return mid;
        }

        public void setMid(Long mid) {
            this.mid = mid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Stat implements Serializable{
        private Long aid;
        private Integer view;
        private Integer danmaku;
        private Integer reply;
        private Integer favorite;
        private Integer coin;
        private Integer share;
        private Integer now_rank;
        private Integer his_rank;
        private Integer like;
        private Integer dislike;
        private String  evaluation;

        public Long getAid() {
            return aid;
        }

        public void setAid(Long aid) {
            this.aid = aid;
        }

        public Integer getView() {
            return view;
        }

        public void setView(Integer view) {
            this.view = view;
        }

        public Integer getDanmaku() {
            return danmaku;
        }

        public void setDanmaku(Integer danmaku) {
            this.danmaku = danmaku;
        }

        public Integer getReply() {
            return reply;
        }

        public void setReply(Integer reply) {
            this.reply = reply;
        }

        public Integer getFavorite() {
            return favorite;
        }

        public void setFavorite(Integer favorite) {
            this.favorite = favorite;
        }

        public Integer getCoin() {
            return coin;
        }

        public void setCoin(Integer coin) {
            this.coin = coin;
        }

        public Integer getShare() {
            return share;
        }

        public void setShare(Integer share) {
            this.share = share;
        }

        public Integer getNow_rank() {
            return now_rank;
        }

        public void setNow_rank(Integer now_rank) {
            this.now_rank = now_rank;
        }

        public Integer getHis_rank() {
            return his_rank;
        }

        public void setHis_rank(Integer his_rank) {
            this.his_rank = his_rank;
        }

        public Integer getLike() {
            return like;
        }

        public void setLike(Integer like) {
            this.like = like;
        }

        public Integer getDislike() {
            return dislike;
        }

        public void setDislike(Integer dislike) {
            this.dislike = dislike;
        }

        public String getEvaluation() {
            return evaluation;
        }

        public void setEvaluation(String evaluation) {
            this.evaluation = evaluation;
        }
    }
}
