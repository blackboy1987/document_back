package com.igomall.wechat.util;

import com.igomall.util.JsonUtils;
import com.igomall.wechat.util.response.tag.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 标签管理工具
 */
public final class TagUtils {
    private TagUtils(){};

    /**
     *  创建标签
     * @param name
     *      标签名（30个字符以内）
     * @return
     */
    public static CreateResponse create(String name){
        String url="https://api.weixin.qq.com/cgi-bin/tags/create";
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        params.put("tag",map);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), CreateResponse.class);
    }

    /**
     *  获取公众号已创建的标签
     * @return
     */
    public static ListResponse list(){
        String url="https://api.weixin.qq.com/cgi-bin/tags/get";
        return JsonUtils.toObject(WechatUtils.get(url,null), ListResponse.class);
    }

    /**
     *  获取公众号已创建的标签
     * @return
     */
    public static UpdateResponse update(Long weChatId, String name){
        String url="https://api.weixin.qq.com/cgi-bin/tags/update";
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("id",weChatId);
        map.put("name",name);
        params.put("tag",map);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), UpdateResponse.class);
    }

    /**
     *  删除标签
     * @param tagId
     *      标签id
     * @return
     */
    public static DeleteResponse delete(Long tagId){
        String url="https://api.weixin.qq.com/cgi-bin/tags/delete";
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("id",tagId);
        params.put("tag",map);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), DeleteResponse.class);
    }

    /**
     *   获取标签下粉丝列表
     * @return
     */
    public static UserListResponse userList(Long tagId, String nextOpenId){
        String url="https://api.weixin.qq.com/cgi-bin/user/tag/get";
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("tagid",tagId);
        params.put("next_openid",nextOpenId);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), UserListResponse.class);
    }
}
