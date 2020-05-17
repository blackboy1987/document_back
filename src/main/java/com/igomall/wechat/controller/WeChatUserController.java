package com.igomall.wechat.controller;

import com.igomall.common.Message;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.wechat.entity.WeChatUser;
import com.igomall.wechat.service.WechatUserService;
import com.igomall.wechat.util.UserUtils;
import com.igomall.wechat.util.response.user.UserGetResponse;
import com.igomall.wechat.util.response.user.UserInfoResponse;
import com.igomall.wechat.util.response.user.UserInfoUpdateRemarkResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController("adminWeChatUserController")
@RequestMapping("/api/we_chat/user")
public class WeChatUserController extends BaseController {

    @Autowired
    private WechatUserService wechatUserService;

    @PostMapping("/list")
    public Page<WeChatUser> list(Pageable pageable,String nickName, Integer status, Date beginDate,Date endDate){
        if(StringUtils.isEmpty(pageable.getOrderProperty()) || pageable.getOrderDirection()==null){
            pageable.setOrderDirection(Order.Direction.desc);
            pageable.setOrderProperty("subscribeTime");
        }
        return wechatUserService.findPage(pageable,nickName,status,beginDate,endDate);
    }

    @PostMapping("/remark")
    public Message remark(String openId, String remark){
        if(StringUtils.isEmpty(openId)){
            return Message.error("请选择需要设置的用户");
        }
        if(StringUtils.isEmpty(remark)){
            return Message.error("请输入备注信息");
        }
        UserInfoUpdateRemarkResponse userInfoUpdateRemarkResponse = UserUtils.userInfoUpdateRemark(openId,remark);
        if(userInfoUpdateRemarkResponse.getErrCode()==0){
            wechatUserService.remark(openId,remark);
            return Message.success("操作成功");
        }
        return Message.error(userInfoUpdateRemarkResponse.getErrMsg());
    }




    /**
     * 获取用户列表
     * @return
     */
    @PostMapping("/userGet")
    public UserGetResponse userGet(){
        UserGetResponse userGetResponse = UserUtils.userGet(null);
        List<String> openIds = userGetResponse.getData().getOpenIds();
        for (String openId:openIds) {
            UserInfoResponse userInfoResponse = UserUtils.userInfo(openId);
            WeChatUser weChatUser = new WeChatUser();
            WeChatUser weChatUser1 = wechatUserService.findByOpenId(openId);
            if(weChatUser1==null){
                BeanUtils.copyProperties(userInfoResponse,weChatUser,"subscribeTime");
                weChatUser.setSubscribeTime(new Date(userInfoResponse.getSubscribeTime()*1000));
                weChatUser.setUpdateTime(new Date());
                wechatUserService.save(weChatUser);
            }else{
                if(userInfoResponse.getSubscribe()==0){
                    // 未关注。只更新关注状态
                    weChatUser1.setStatus(2);
                }else{
                    BeanUtils.copyProperties(userInfoResponse,weChatUser1,"subscribeTime","openId","name","address","mobile","weChatId");
                    weChatUser1.setSubscribeTime(new Date(userInfoResponse.getSubscribeTime()*1000));
                    weChatUser1.setStatus(1);
                }
                weChatUser1.setUpdateTime(new Date());
                wechatUserService.update(weChatUser1);


            }
            System.out.println(weChatUser);
        }

        return userGetResponse;

    }

    /**
     * 获取用户列表
     * @return
     */
    @PostMapping("/userInfo")
    public UserInfoResponse userInfo(String openId){
        UserInfoResponse userInfoResponse = UserUtils.userInfo(openId);
        return userInfoResponse;

    }

    /**
     * 获取用户列表
     * @return
     */
    @PostMapping("/info")
    public UserInfoUpdateRemarkResponse info(String openId){
        UserInfoUpdateRemarkResponse userInfoResponse = UserUtils.userInfoUpdateRemark(openId,"aaa");
        return userInfoResponse;

    }
}
