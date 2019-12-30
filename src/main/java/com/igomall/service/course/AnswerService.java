package com.igomall.service.course;

import com.igomall.entity.course.Answer;
import com.igomall.service.BaseService;

public interface AnswerService extends BaseService<Answer,Long> {
    /**
     * 判断编号是否存在
     *
     * @param sn
     *            编号(忽略大小写)
     * @return 编号是否存在
     */
    boolean snExists(String sn);

}
