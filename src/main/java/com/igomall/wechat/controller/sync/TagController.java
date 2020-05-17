package com.igomall.wechat.controller.sync;

import com.igomall.controller.admin.BaseController;
import com.igomall.wechat.entity.Tag;
import com.igomall.wechat.service.TagService;
import com.igomall.wechat.util.TagUtils;
import com.igomall.wechat.util.response.tag.ListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("syncWeChatTagController")
@RequestMapping("/sync/we_chat/tag")
public class TagController extends BaseController {

    @Autowired
    private TagService tagService;

    /**
     * 保存
     */
    @PostMapping
    public ListResponse index() {
        ListResponse listResponse = TagUtils.list();
        List<ListResponse.Tag> tags = listResponse.getTags();
        for (ListResponse.Tag tag:tags) {
            Tag tag1 = tagService.findByWeChatId(tag.getId());
            if(tag1==null){
                tag1 = new Tag();
                tag1.setIsEnabled(true);
                tag1.setCount(tag.getCount());
                tag1.setName(tag.getName());
                tag1.setWeChatId(tag.getId());
                tagService.save(tag1);
            }else{
                tag1.setIsEnabled(true);
                tag1.setCount(tag.getCount());
                tag1.setName(tag.getName());
                tag1.setWeChatId(tag.getId());
                tagService.update(tag1);
            }
        }

        return listResponse;
    }
}
