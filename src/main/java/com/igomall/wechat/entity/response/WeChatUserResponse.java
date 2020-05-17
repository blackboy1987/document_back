package com.igomall.wechat.entity.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.igomall.common.BaseAttributeConverter;
import com.igomall.wechat.util.response.BaseResponse;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeChatUserResponse extends BaseResponse {

    /**
     * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，
     * 拉取不到其余信息。
     */
    private Integer subscribe;

    /**
     * 用户的标识，对当前公众号唯一
     */
    private String openid;

    /**
     *用户的昵称
     */
    private String nickname;

    /**
     *用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private Integer sex;

    /**
     *用户的语言，简体中文为zh_CN
     */
    private String language;

    /**
     *用户所在城市
     */
    private String city;

    /**
     *用户所在省份
     */
    private String province;

    /**
     *用户所在国家
     */
    private String country;

    /**
     *用户头像，最后一个数值代表正方形头像大小
     * （有0、46、64、96、132数值可选，0代表640*640正方形头像），
     * 用户没有头像时该项为空。
     * 若用户更换头像，原有头像URL将失效。
     */
    @JsonProperty("headimgurl")
    private String headImgUrl;

    /**
     *用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    @JsonProperty("subscribe_time")
    private Long subscribeTime;

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    @JsonProperty("unionid")
    private String unionId;

    /**
     *公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
     */
    private String remark;

    /**
     *用户所在的分组ID（兼容旧的用户分组接口）
     */
    @JsonProperty("groupid")
    private Integer groupId;

    /**
     *用户被打上的标签ID列表
     */
    @Column(length = 4000)
    @Convert(converter = OptionConverter.class)
    @JsonProperty("tagid_list")
    private List<Long> tagidList = new ArrayList<>();

    /**
     *返回用户关注的渠道来源，
     * ADD_SCENE_SEARCH 公众号搜索，
     * ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，
     * ADD_SCENE_PROFILE_CARD 名片分享，
     * ADD_SCENE_QR_CODE 扫描二维码，
     * ADD_SCENE_PROFILE_LINK 图文页内名称点击，
     * ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，
     * ADD_SCENE_PAID 支付后关注，
     * ADD_SCENE_OTHERS 其他
     */
    @JsonProperty("subscribe_scene")
    private String subscribeScene;

    /**
     *二维码扫码场景（开发者自定义）
     */
    @JsonProperty("qr_scene")
    private String qrScene;

    /**
     *二维码扫码场景描述（开发者自定义）
     */
    @JsonProperty("qr_scene_str")
    private String qrSceneStr;

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Long getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public List<Long> getTagidList() {
        return tagidList;
    }

    public void setTagidList(List<Long> tagidList) {
        this.tagidList = tagidList;
    }

    public String getSubscribeScene() {
        return subscribeScene;
    }

    public void setSubscribeScene(String subscribeScene) {
        this.subscribeScene = subscribeScene;
    }

    public String getQrScene() {
        return qrScene;
    }

    public void setQrScene(String qrScene) {
        this.qrScene = qrScene;
    }

    public String getQrSceneStr() {
        return qrSceneStr;
    }

    public void setQrSceneStr(String qrSceneStr) {
        this.qrSceneStr = qrSceneStr;
    }

    /**
     * 类型转换 - 可选项
     *
     * @author blackboy
     * @version 1.0
     */
    @Converter
    public static class OptionConverter extends BaseAttributeConverter<List<Long>> {
    }
}
