package com.igomall.job;

import com.igomall.entity.Sn;
import com.igomall.entity.course.*;
import com.igomall.entity.member.Member;
import com.igomall.service.SnService;
import com.igomall.service.course.CourseCategoryService;
import com.igomall.service.course.CourseCommentService;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.LessonService;
import com.igomall.service.member.MemberRankService;
import com.igomall.service.member.MemberService;
import com.igomall.service.teacher.TeacherService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

@Component
@EnableScheduling
public class Demo {

    public static List<String> imageUrls = new ArrayList<>();
    public static List<String> avatarUrls = new ArrayList<>();
    public static List<String> comments = new ArrayList<>();
    public static List<Member> members = new ArrayList<>();
    public static List<Lesson> lessons = new ArrayList<>();
    public static List<String> videoUrls = new ArrayList<>();

    @Autowired
    private CourseCategoryService courseCategoryService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SnService snService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRankService memberRankService;
    @Autowired
    private CourseCommentService courseCommentService;

    static {
        try {
            // getImageUrls();
        }catch (Exception e){
            e.printStackTrace();
        }
        avatarUrls.add("https://10.url.cn/eth/ajNVdqHZLLCp4ESLAqxJlLmybyjtIJMBKPjUwkibUfVLiaricZdkVsU8pGsTssK9szibgNVH1ibdpesc/130");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=FKgv21T76hxpRPkiaT4BibwA&s=140&t=1576496831");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=s4WGRFu9cO1bGooxYQW3YA&s=140&t=1576197898");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=NsVXONYnoQVWjkXO4vah1Q&s=140&t=1556989496");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=IpaZtaESJMC7qxeOZfsUDQ&s=140&t=1554262621");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=j7R7aicQxDVfH9BwibQQVHzQ&s=140&t=1576588999");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=fb38LlWBG6sFZSIYzO2zBA&s=140&t=1552981791");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=rPudiaUzvYnD6n9XvyYVqNg&s=140&t=1563884020");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=e2GKrtbMmyXVwwATmoMM2Q&s=140&t=1559116473");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=QlY8L3BeKwhCUySaXLcBjg&s=140&t=1559878870");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=yZS33OlxKNRIzMPpkoicibVQ&s=140&t=1557499476");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=QQaEqIYdUKNnWIibNLZHmOA&s=140&t=1559394052");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=LK5eNqibx3r6E0KrMic0AHcw&s=140&t=1559116973");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=qXbSLYVbMxJVCV1bcVomjQ&s=140&t=1556601812");
        avatarUrls.add("https://wx.qlogo.cn/mmopen/vi_32/eICicwcoFyDoibicfibDvnKWKibU3RKRjtl7KaA36RavIT3yxVdKNPjox55wPD06wjzFnPJe9a4WTEpTs5AFib0zpItA/132");
        avatarUrls.add("https://wx.qlogo.cn/mmopen/vi_32/R4DicBy8rqekVokfReoQjmfgZcxBxpmrx2NicHSWfREaI1bvOzsW5iarsK4XzWicvUIzOC4FME7ziaibVNbd5hn33jnw/132");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=0HLlDhp9dUX3IFrXW7mlBA&s=140&t=1556368889");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=6HongicMGXXUhOuu7ftQXibg&s=140&t=1556002201");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=3a9syZSRzhLSIMA7Po1NVg&s=100&t=570");
        avatarUrls.add("http://thirdwx.qlogo.cn/mmopen/vi_32/W6y4vEtnIePMwb0U2IsPODJ2h9gOvRyDFq9UBsZ1DIY21ibaibMy48uTClCZhyXFLEicSGicBKicRUjW5lKTzT2KzKw/132");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=cuf1FnyicJ1gv3oF2jhlP1Q&s=140&t=1551586128");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=WkqtTmmN3xvEJcVOtSf6fQ&s=140&t=1557471912");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=ibqKh43KldmibAXzibvePYuaw&s=140&t=1574318170");
        avatarUrls.add("http://thirdwx.qlogo.cn/mmopen/vi_32/PVicNqQMWvC6icb2dlhFzfGTSJ2NZuG5XwTtLiaWnEDT5surPTeJzm2wrJsvpe0FwNtA7n7J2XiaRIKsq0BhzWrn9w/132");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=oC4LIzwKFwbQGSJpFyCnDg&s=140&t=1574831832");
        avatarUrls.add("https://thirdqq.qlogo.cn/g?b=sdk&k=ogyLKZKic38OSSae0IVjMNw&s=140&t=1555863084");

        comments.add("就喜欢这种讲的很详细的课程.在课程中能学到非常多底层的东西,老师喜欢带着源码分析,非常棒.练习也不少,新来的小伙伴.看基础课程不要太快哦,稳一点,练习做完再点开下一集,收货满满滴.");
        comments.add("对想进一步学习的人来说，有很好的吸引.了解的更多的是思想。后续课程应该还有很多。");
        comments.add("老师讲得超级好，是我目前听过的所有网课中最好的");
        comments.add("详尽,全面,准确,是相当不错的公开课");
        comments.add("很庆幸自己选择的马士兵老师的课，老师们讲课很条理很清晰，重要的是能讲解学习的思路。自己的工作时间虽然不短了，但是还是欠缺了不少东西，最近找工作一直碰壁，很受打击，但是不管如何都要自己埋头苦学，加油！！！");
        comments.add("我目前只学习了马老师讲的java基础知识，已经进行到了多线程，课程内容几乎都从程序的运行机制，以及底层的实现原理深入浅出的描述如何学习相应的知识点，真的受益匪浅，我会在这条路上坚定不移的走下去。但愿学有所成。");
        comments.add("马老师讲的是真的好，职业规划讲的对我起了不小帮助");
        comments.add("老师讲的非常细、容易明白，学到了一些自己平时没有注意到的地方，蟹蟹！");
        comments.add("目前听到的最靠谱的公开课，知识点覆盖很全面，教学深入浅出，很容易理解，把复杂的问题抽丝剥茧，足见老师扎实的技术功底，感谢。");
        comments.add("用尽一切时间打广告！这个培训班很有意思，绝对是吹牛出身的！！每天推送好几次，题目订的挺好的，结果打开永远都是广告！！！");
        comments.add("老师讲的太棒了，声音洪亮，思路清晰，对待问题细心认真！");
        comments.add("张老师的课很给力。都是实战经验传授。超五星");

        videoUrls.add("http://demo.musiceol.com/426-1/demo/426-1-57.mp4");
        videoUrls.add("http://demo.musiceol.com/376-1/demo/376-1-2.mp4");
        videoUrls.add("http://demo.musiceol.com/199-1/demo/199-1-1.mp4");
        videoUrls.add("http://demo.musiceol.com/681-1/demo/681-1-1.mp4");
        videoUrls.add("http://demo.musiceol.com/683-1/demo/683-1-1.mp4");
        videoUrls.add("http://demo.musiceol.com/547-1/demo/547-1-30.mp4");
        videoUrls.add("http://demo.musiceol.com/414-1/demo/414-1-1.mp4");
        videoUrls.add("http://demo.musiceol.com/390-1/demo/390-1-25.mp4");
        videoUrls.add("http://demo.musiceol.com/100-2/demo/100-2-2.mp4");
        videoUrls.add("http://demo.musiceol.com/667-1/demo/667-1-1.mp4");
        videoUrls.add("http://demo.musiceol.com/427-1/demo/427-1-3.mp4");
        videoUrls.add("http://demo.musiceol.com/511-1/demo/511-1-13.mp4");
        videoUrls.add("http://demo.musiceol.com/500-1/demo/500-1-1.mp4");
        videoUrls.add("http://demo.musiceol.com/445-1/demo/445-1-29.mp4");
        videoUrls.add("http://demo.musiceol.com/37-1/demo/37-1-2.mp4");
        videoUrls.add("http://demo.musiceol.com/570-1/demo/570-1-1.mp4");
        videoUrls.add("http://demo.musiceol.com/167-1/demo/167-1-1.mp4");
        videoUrls.add("http://demo.musiceol.com/682-1/demo/682-1-3.mp4");
    }



    /**
     * 每隔1钟创建一个视频
     */
    // @Scheduled(fixedRate =1000*60*2)
    public void task() throws Exception{
        List<CourseCategory> courseCategories =  courseCategoryService.findAll();
        Integer index = 0;
        for (CourseCategory courseCategory:courseCategories) {
            if(courseCategory.getGrade()==2){// 根菜单
                Course course = new Course();
                course.setCourseCategory(courseCategory);
                course.setSn(snService.generate(Sn.Type.course));
                course.setTeacher(teacherService.find(1L));
                course.setChapters(new HashSet<>());
                course.setLessons(new HashSet<>());
                course.setOrder(index++);
                course.setDescription(courseCategory.getName()+"_第"+course.getOrder()+"套课程描述");
                course.setImage(imageUrls.get(new Random().nextInt(imageUrls.size())));
                course.setMemo(courseCategory.getName()+"_第"+course.getOrder()+"套课程简介");
                course.setTitle(courseCategory.getName()+"_第"+course.getOrder()+"套课程标题");
                createChapter(course);
                courseService.save(course);
                Thread.sleep(1000);
            }
        }
    }

    public void createChapter(Course course){
        Integer count = new Random().nextInt(10);
        if(count>0){
            for (Integer i=1;i<count;i++) {
                Chapter chapter = new Chapter();
                chapter.setSn(snService.generate(Sn.Type.chapter));
                chapter.setCourse(course);
                chapter.setTeacher(course.getTeacher());
                chapter.setLessons(new HashSet<>());
                chapter.setTitle("第"+i+"章节标题");
                chapter.setOrder(i);
                createLesson(chapter,course);
                course.getChapters().add(chapter);
            }
        }
    }

    public void createLesson(Chapter chapter, Course course){
        Integer count = new Random().nextInt(10);
        if(count>0){
            for (Integer i=1;i<count;i++) {
                Lesson lesson = new Lesson();
                lesson.setSn(snService.generate(Sn.Type.chapter));
                lesson.setTeacher(course.getTeacher());
                lesson.setChapter(chapter);
                lesson.setCourse(course);
                lesson.setOrder(i);
                lesson.setTitle(chapter.getTitle()+"  第"+i+" 节视频");
                lesson.setVideoImage(imageUrls.get(new Random().nextInt(imageUrls.size())));
                lesson.setVideoUrl(chapter.getTitle()+"  第"+i+" 节视频地址");
                chapter.getLessons().add(lesson);
                course.getLessons().add(lesson);
            }
        }
    }

    private static void getImageUrls() throws Exception{
        for (int i=1;i<=101;i++){
            Document document = Jsoup.parse(new URL("https://www.jikexueyuan.com/course/?pageNum="+i),2000);
            Element element = document.getElementById("changeid");
            Elements elements = element.getElementsByTag("img");
            Iterator<Element> iterator = elements.iterator();
            while (iterator.hasNext()){
                Element elements1 = iterator.next();
                imageUrls.add(elements1.attr("src"));
            }
        }
    }

    /**
     * 创建评论
     * @throws Exception
     */
   // @Scheduled(fixedRate =1)
    public void task1(){
        if(members.isEmpty()){
            members = memberService.findAll();
        }
        if(lessons.isEmpty()){
            lessons = lessonService.findAll();
        }
        Lesson lesson = lessons.get(new Random().nextInt(lessons.size()));
        if(lesson!=null){
            Member member = members.get(new Random().nextInt(comments.size()));
            if(member==null){
                return;
            }

            CourseComment courseComment = new CourseComment();
            courseComment.setLesson(lesson);
            courseComment.setCourse(lesson.getCourse());
            courseComment.setForReview(null);
            courseComment.setIsShow(true);
            courseComment.setScore(new Random().nextInt(6));
            if(courseComment.getScore()==0){
                courseComment.setScore(1);
            }
            courseComment.setContent(comments.get(new Random().nextInt(comments.size())));
            courseComment.setIp("127.0.0.1");
            courseComment.setMember(member);
            courseComment.setReplyReviews(new HashSet<>());
            courseCommentService.save(courseComment);
            System.out.println("ok");
        }
    }

    /**
     * 创建评论
     * @throws Exception
     */
    // @Scheduled(fixedRate =100)
    public void createMember(){
        Member member = new Member();
        member.setAmount(BigDecimal.ZERO);
        member.setBalance(BigDecimal.ZERO);
        member.setEmail("yunxiaocha_"+(System.currentTimeMillis()%999)+"@qq.com");
        member.setPassword("123456");
        member.setPoint(0L);
        member.setUsername("yunxiaocha_"+(System.currentTimeMillis()%999));
        member.setMemberRank(memberRankService.findDefault());
        member.setAvatar(avatarUrls.get(new Random().nextInt(avatarUrls.size())));
        member.setIsEnabled(true);
        member.setIsLocked(false);
        memberService.save(member);
    }



    // @Scheduled(fixedRate =2000)
    public void createLesson2(){
        List<Lesson> lessons = lessonService.findAll();
        Integer i = 0;
        for (Lesson lesson:lessons) {
            lesson.setVideoUrl(videoUrls.get((i++)%videoUrls.size()));
            System.out.println(lesson.getId()+":"+lesson.getVideoUrl());
            lessonService.update(lesson);
        }

        System.out.println("==============");
    }


   // @Scheduled(fixedRate =1000)
    public void pingjiaCourse(){
        List<Course> courses = courseService.findAll();
        for (Course course:courses) {
            //course.setScore(new Random().nextFloat());
           // course.setHits(new Random().nextLong());
            if(course.getId()%9==0){
                course.setPrice(new BigDecimal(new Random().nextFloat()*100));
            }else{
                course.setPrice(BigDecimal.ZERO);
            }

            courseService.update(course);
        }

        System.out.println("==============");
    }


}
