package com.igomall.wechat.util;

import com.igomall.util.JsonUtils;
import com.igomall.wechat.util.response.comment.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 评论工具类
 */
public final class CommentUtils {

    private CommentUtils(){}

    /**
     * 打开已群发文章评论（新增接口）
     * @param msgDataId
     *      群发返回的msg_data_id
     * @param index
     *      多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     */
    public static CommentOpenResponse open(Long msgDataId, Integer index){
        String url ="https://api.weixin.qq.com/cgi-bin/comment/open";
        Map<String,Object> params = new HashMap<>();
        params.put("msg_data_id",msgDataId);
        if(index!=null){
            params.put("index",index);
        }
        return JsonUtils.toObject(WechatUtils.postJson(url,params), CommentOpenResponse.class);
    }

    /**
     * 关闭已群发文章评论（新增接口）
     * @param msgDataId
     *      群发返回的msg_data_id
     * @param index
     *      多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @return
     */
    public static CommentCloseResponse close(Long msgDataId, Integer index){
        String url ="https://api.weixin.qq.com/cgi-bin/comment/close";
        Map<String,Object> params = new HashMap<>();
        params.put("msg_data_id",msgDataId);
        if(index!=null){
            params.put("index",index);
        }
        return JsonUtils.toObject(WechatUtils.postJson(url,params), CommentCloseResponse.class);
    }

    /**
     * 查看指定文章的评论数据（新增接口）
     * @param msgDataId
     *      群发返回的msg_data_id
     * @param index
     *      多图文时，用来指定第几篇图文，从0开始，不带默认返回该msg_data_id的第一篇图文
     * @param begin
     *      起始位置
     * @param count
     *      获取数目（>=50会被拒绝）
     * @param type
     *      type=0 普通评论&精选评论
     *      type=1 普通评论
     *      type=2 精选评论
     */
    public static CommentListResponse list(Long msgDataId, Integer index, Integer begin, Integer count, Integer type){
        String url ="https://api.weixin.qq.com/cgi-bin/comment/list";
        Map<String,Object> params = new HashMap<>();
        params.put("msg_data_id",msgDataId);
        if(index!=null){
            params.put("index",index);
        }
        params.put("begin",begin);
        params.put("count",count);
        params.put("type",type);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), CommentListResponse.class);
    }

    /**
     * 将评论标记精选（新增接口）
     * @param msgDataId
     *      群发返回的msg_data_id
     * @param index
     *      多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @param userCommentId
     *      用户评论id
     */
    public static CommentMarkelectResponse markelect(Long msgDataId, Integer index, Long userCommentId){
        String url ="https://api.weixin.qq.com/cgi-bin/comment/markelect";
        Map<String,Object> params = new HashMap<>();
        params.put("msg_data_id",msgDataId);
        if(index!=null){
            params.put("index",index);
        }
        params.put("user_comment_id",userCommentId);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), CommentMarkelectResponse.class);
    }

    /**
     *将评论取消精选
     * @param msgDataId
     *      群发返回的msg_data_id
     * @param index
     *      多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @param userCommentId
     *      用户评论id
     */
    public static CommentUnMarkelectResponse unmarkelect(Long msgDataId, Integer index, Long userCommentId){
        String url ="https://api.weixin.qq.com/cgi-bin/comment/unmarkelect";
        Map<String,Object> params = new HashMap<>();
        params.put("msg_data_id",msgDataId);
        if(index!=null){
            params.put("index",index);
        }
        params.put("user_comment_id",userCommentId);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), CommentUnMarkelectResponse.class);
    }

    /**
     *删除评论（新增接口）
     * @param msgDataId
     *      群发返回的msg_data_id
     * @param index
     *      多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @param userCommentId
     *      用户评论id
     */
    public static CommentDeleteResponse delete(Long msgDataId, Integer index, Long userCommentId){
        String url ="https://api.weixin.qq.com/cgi-bin/comment/delete";
        Map<String,Object> params = new HashMap<>();
        params.put("msg_data_id",msgDataId);
        if(index!=null){
            params.put("index",index);
        }
        params.put("user_comment_id",userCommentId);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), CommentDeleteResponse.class);
    }

    /**
     * 回复评论（新增接口）
     * @param msgDataId
     *      群发返回的msg_data_id
     * @param index
     *      多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @param userCommentId
     *      评论id
     * @param content
     *      回复内容
     * @return
     */
    public static CommentReplayResponse replyAdd(Long msgDataId, Integer index, Long userCommentId, String content){
        String url ="https://api.weixin.qq.com/cgi-bin/comment/reply/add";
        Map<String,Object> params = new HashMap<>();
        params.put("msg_data_id",msgDataId);
        if(index!=null){
            params.put("index",index);
        }
        params.put("user_comment_id",userCommentId);
        params.put("content",content);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), CommentReplayResponse.class);
    }

    /**
     * 删除回复（新增接口）
     * @param msgDataId
     *      群发返回的msg_data_id
     * @param index
     *      多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @param userCommentId
     *      评论id
     * @return
     */
    public static CommentReplyDeleteResponse replyDelete(Long msgDataId, Integer index, Long userCommentId){
        String url ="https://api.weixin.qq.com/cgi-bin/comment/reply/delete";
        Map<String,Object> params = new HashMap<>();
        params.put("msg_data_id",msgDataId);
        if(index!=null){
            params.put("index",index);
        }
        params.put("user_comment_id",userCommentId);
        return JsonUtils.toObject(WechatUtils.postJson(url,params), CommentReplyDeleteResponse.class);
    }


    public static void main(String[] args) {
        CommentOpenResponse commentOpenResponse = open(1234L,null);
        System.out.println(commentOpenResponse);
    }
}
