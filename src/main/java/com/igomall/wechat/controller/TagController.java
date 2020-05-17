package com.igomall.wechat.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.wechat.entity.Tag;
import com.igomall.wechat.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController("adminWeChatTagController")
@RequestMapping("/api/we_chat/tag")
public class TagController extends BaseController {

    @Autowired
    private TagService tagService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Message save(Tag tag) {
        if(tag.getIsEnabled()==null){
            tag.setIsEnabled(false);
        }
        if (!isValid(tag,BaseEntity.Save.class)) {
            return Message.error("参数错误");
        }
        Tag parent = tagService.save1(tag);
        if(parent==null){
            return Message.error("接口调用失败");
        }
        return Message.success("操作成功");
    }

    /**
     * 编辑
     */
    @PostMapping("/edit")
    @JsonView(BaseEntity.EditView.class)
    public Tag edit(Long id) {
        return tagService.find(id);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public Message update(Tag tag) {
        if(tag.getIsEnabled()==null){
            tag.setIsEnabled(false);
        }
        if (!isValid(tag)) {
            return Message.error("参数错误");
        }

        Tag parent = tagService.update1(tag);
        if(parent==null){
            return Message.error("接口调用失败");
        }
        return Message.success("操作成功");
    }

    @PostMapping("/list")
    @JsonView(BaseEntity.ListView.class)
    public Page<Tag> list(Pageable pageable, String name, Boolean isEnabled, Date beginDate, Date endDate){
        return tagService.findPage(pageable,name,isEnabled,beginDate,endDate);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Message delete(Long[] ids) {
        for (Long id:ids) {
            Tag tag = tagService.find(id);
            if(tag !=null){
                tag.setIsEnabled(false);
                tagService.update(tag);
            }
        }
        return Message.success("操作成果");
    }


}
