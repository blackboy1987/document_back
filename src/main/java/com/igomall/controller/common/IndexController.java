package com.igomall.controller.common;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.Message;
import com.igomall.common.Order;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.course.CourseTag;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Folder;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.member.LessonReadRecord;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.PointLog;
import com.igomall.security.CurrentUser;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.CourseTagService;
import com.igomall.service.course.FolderService;
import com.igomall.service.course.LessonService;
import com.igomall.service.member.LessonReadRecordService;
import com.igomall.service.member.MemberService;
import com.igomall.util.*;
import com.igomall.util.bilibili.BilibiliUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController("commonIndexController")
@RequestMapping("/api")
public class IndexController extends BaseController{

    @Autowired
    private CourseService courseService;
    @Autowired
    private FolderService folderService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private LessonReadRecordService lessonReadRecordService;
    @Autowired
    private MemberService memberService;

    @Resource
    private CourseTagService courseTagService;

    @GetMapping("/init123")
    public String init123(String[] bids){
        // BV1Cb411j7RA,BV16V411d76S,BV1zE41197bw,BV1mT4y137VP,BV17p4y197VC,BV1zt4y1172p,BV1YA411b7in,BV1yE411Z7AP,BV1Rx411876f,BV1fV411R7aK,BV1Yz411B7Lm,BV12K41157zb,BV1eA41147vG,BV1YJ411W7YA,BV1vE411D7KE,BV1oW411u75R,BV11t411S7iG,BV1G541147JY,BV1Zk4y1r756,BV1di4y1x75T,BV1Ct4y1274w,BV1HE411K7Pz,BV1Eg4y187XT,BV1hz411b7Ha,BV1Ye411p7xB,BV13C4y1W7Tf,BV1tz411q7c2,BV1VJ411X7xX,BV1yE411x7TY,BV1CJ411377B,BV1wZ4y1x7nr,BV1ri4y1x71A,BV1xE411d7hY,BV1y7411y7am,BV18K4y1b7DA,BV1b4411q7DX,BV1uT4y137vq,BV1aC4y1W7Rs,BV1Nt4y1m7qL,BV1Cz411B7qd,BV1Fp4y19729,BV1cJ411i7B1,BV1nJ411D71E
        for (String bid:bids) {
            Course course = BilibiliUtils.getCourseInfo(bid);
            Set<CourseTag> courseTags = course.getCourseTags();
            Set<CourseTag> courseTags1 = new HashSet<>();
            for (CourseTag courseTag:courseTags) {
                CourseTag courseTag1 = courseTagService.findByName(courseTag.getName());
                if(courseTag1==null){
                    courseTag1 = courseTagService.save(courseTag);
                }
                courseTags1.add(courseTag1);
            }
            course.setCourseTags(courseTags1);
            courseService.save(course);
        }



        /*List<Course> courses = courseService.findAll();
        for (Course course: courses) {
            Set<Lesson> lessons = course.getLessons();
            Lesson lesson = lessons.iterator().next();

            Lesson.PlayUrl playUrl = lesson.getPlayUrls().get(0);
            // https://www.bilibili.com/video/BV1Rx411876f?p=6

            String result = playUrl.getUrl().replace("https://www.bilibili.com/video/","");
            result = result.split("\\?")[0];
            System.out.println(result);
            course.setProps(new HashMap<>());
            course.getProps().put("bid",result);
            courseService.update(course);

        }*/




        return "ok";
    }



    @PostMapping("/course")
    @JsonView(BaseEntity.JsonApiView.class)
    public List<Course> course(@CurrentUser Member member){
        List<Order> orders = new ArrayList<>();
        orders.add(Order.asc("order"));
        return courseService.findList(null,null,orders);
    }

    @PostMapping("/folder")
    @JsonView(BaseEntity.JsonApiView.class)
    public List<Folder> folder(Long courseId, @CurrentUser Member member){
        List<Order> orders = new ArrayList<>();
        orders.add(Order.asc("order"));
        List<Folder> folders = folderService.findList(courseService.find(courseId),null,null,orders);
        if(folders.isEmpty()){
            Folder folder = new Folder();
            folder.setName(null);
            folder.setId(null);
            folder.setLessons(new HashSet<>(lessonService.findList(courseService.find(courseId),null,null,null,null)));
            folders.add(folder);
        }
        return folders;
    }

    @PostMapping("/lesson")
    @JsonView(BaseEntity.JsonApiView.class)
    public List<Lesson> lesson(Long courseId,Long folderId, @CurrentUser Member member){
        List<Order> orders = new ArrayList<>();
        orders.add(Order.asc("order"));
        List<Lesson> lessons = lessonService.findList(courseService.find(courseId),folderService.find(folderId),null,null,orders);
        return lessons;
    }

    @PostMapping("/play_url")
    @JsonView(BaseEntity.JsonApiView.class)
    public List<Lesson.PlayUrl> playUrl(Long lessonId, @CurrentUser Member member){
        Lesson lesson = lessonService.find(lessonId);
        if(lesson!=null){
            if(lesson.getDocument()){
                Lesson.PlayUrl playUrl = new Lesson.PlayUrl();
                playUrl.setName("资料下载");
                playUrl.setUrl("资料下载");
                lesson.getPlayUrls().add(playUrl);
            }
            return lesson.getPlayUrls();
        }
        return Collections.emptyList();
    }

    @PostMapping("/lesson_record")
    public Message lessonRecord(Long lessonId,String playUrlName,String playUrlUrl, @CurrentUser Member member){
        /*if(member==null){
            return Message.error("请先登录");
        }*/

        // 加积分操作
        // 当天，同一个课程，同一个链接只加一次
        if(member!=null){
            boolean exist = lessonReadRecordService.existToday(lessonId,member.getId(),playUrlName,playUrlUrl);
            if(!exist){
                LessonReadRecord lessonReadRecord = lessonReadRecordService.save(lessonId,member.getId(),playUrlName,playUrlUrl);
                if(lessonReadRecord!=null){
                    memberService.addPoint(member,10, PointLog.Type.reward,"看视频");
                }
            }
        }
        return Message.success("请求成功");
    }

    @PostMapping("/download")
    public Message download(Long lessonId,String playUrlName,String playUrlUrl, @CurrentUser Member member){
        if(member==null){
            return Message.error("请先登录");
        }
        Lesson lesson = lessonService.find(lessonId);
        if(lesson!=null){
            if(StringUtils.isNotEmpty(lesson.getDocumentUrl())){
                memberService.addPoint(member,-50, PointLog.Type.download,"下载资源");
                return Message.success(lesson.getDocumentUrl());
            }else{
                return Message.error("暂未提供文档下载服务");
            }
        }
        return Message.error("课程不存在！！！");
    }

    @PostMapping("/messagecallback")
    public void messagecallback(@RequestBody String body, HttpServletRequest request, HttpServletResponse response) throws Exception{
        MessageResponse messageResponse = JsonUtils.toObject(body,MessageResponse.class);
        MessageResult messageResult = JsonUtils.toObject(messageResponse.getMessage(),MessageResult.class);
        System.out.println(messageResult.getJobId());
        response.setStatus(HttpStatus.SC_NO_CONTENT);
    }













}
