package com.igomall.controller.member;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.controller.common.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.Resource;
import com.igomall.entity.member.Member;
import com.igomall.entity.wechat.BookItem;
import com.igomall.security.CurrentUser;
import com.igomall.service.wechat.BookCategoryService;
import com.igomall.service.wechat.BookItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("commonBookController")
@RequestMapping("/member/api/book")
public class BookController extends BaseController {

    @Autowired
    private BookCategoryService bookCategoryService;
    @Autowired
    private BookItemService bookItemService;

    @PostMapping
    public List<Map<String,Object>> book(Long parentId,@CurrentUser Member member){
        if(parentId==null){
            return jdbcTemplate.queryForList("select id,name from edu_book_category where parent_id is null order by orders asc");
        }
        List<Map<String, Object>> bookCategories = jdbcTemplate.queryForList("select id, name from edu_book_category where parent_id=? order by orders asc",parentId);
        for (Map<String,Object> bookCategory:bookCategories) {
            bookCategory.put("bookItems",jdbcTemplate.queryForList(BookItem.QUERY_LIST,bookCategory.get("id")));
        }


        return bookCategories;
    }

    @PostMapping("/item")
    @JsonView(BaseEntity.JsonApiView.class)
    public List<BookItem> item(@CurrentUser Member member, Long bookCategoryId){
        return bookItemService.findList(bookCategoryService.find(bookCategoryId),true,null,null,null);
    }

    @PostMapping("/download")
    public List<String> download(Long id){
        BookItem bookItem = bookItemService.find(id);
        List<String> result = new ArrayList<>();
        if(bookItem!=null&& StringUtils.isNoneEmpty(bookItem.getDownloadUrl())){
            result.add(bookItem.getDownloadUrl());
        }
        return result;
    }

    @PostMapping("/download_hits")
    public Map<String,Object> downloadHits(Long id){
        Map<String, Object> data = new HashMap<>();
        data.put("downloadHits", bookItemService.viewHits(id));
        return data;
    }
}
