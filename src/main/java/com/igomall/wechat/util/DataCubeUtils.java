package com.igomall.wechat.util;

import com.igomall.util.Date8Utils;
import com.igomall.util.JsonUtils;
import com.igomall.wechat.util.response.datacube.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据统计工具类
 */
public final class DataCubeUtils {

    private DataCubeUtils(){}

    /**
     * 获取用户增减数据
     * @param beginDate
     *      获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *      获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUserSummaryResponse userSummary(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getusersummary";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeUserSummaryResponse.class);
    }

    /**
     * 获取累计用户数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUserSummaryResponse userCumulate(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getusercumulate";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeUserSummaryResponse.class);
    }

    /**
     *获取图文群发每日数据
     * @param date
     *   获取数据的日期
     */
    public static DataCubeArticleSummaryResponse getArticleSummary(Date date){
        String url="https://api.weixin.qq.com/datacube/getarticlesummary";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(date,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(date,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeArticleSummaryResponse.class);
    }

    /**
     *获取图文群发总数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeArticleTotalResponse getarticletotal(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getarticletotal";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeArticleTotalResponse.class);
    }

    /**
     *获取图文统计数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUserReadResponse getuserread(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getuserread";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeUserReadResponse.class);
    }

    /**
     *获取图文统计分时数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUserReadHourResponse getuserreadhour(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getuserreadhour";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeUserReadHourResponse.class);
    }

    /**
     *获取图文分享转发数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUserShareResponse getusershare(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getusershare";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params), DataCubeUserShareResponse.class);
    }

    /**
     *获取图文分享转发分时数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUserShareHourResponse getusersharehour(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getusersharehour";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeUserShareHourResponse.class);
    }




























    /**
     *获取消息发送概况数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUpstreamMsgResponse getupstreammsg(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getupstreammsg";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeUpstreamMsgResponse.class);
    }
    /**
     *获取消息分送分时数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUpstreamMsgHourResponse getupstreammsghour(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getupstreammsghour";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeUpstreamMsgHourResponse.class);
    }
    /**
     *获取消息发送周数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUpstreamMsgWeekResponse getupstreammsgweek(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getupstreammsgweek";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeUpstreamMsgWeekResponse.class);
    }
    /**
     *获取消息发送月数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUpstreamMsgMonthResponse getupstreammsgmonth(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getupstreammsgmonth";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeUpstreamMsgMonthResponse.class);
    }
    /**
     *获取消息发送分布数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUpstreamMsgDistResponse getupstreammsgdist(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getupstreammsgdist";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeUpstreamMsgDistResponse.class);
    }
    /**
     *获取消息发送分布周数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUpstreamMsgDistWeekResponse getupstreammsgdistweek(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getupstreammsgdistweek";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeUpstreamMsgDistWeekResponse.class);
    }
    /**
     *获取消息发送分布月数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeUpstreamMsgDistMonthResponse getupstreammsgdistmonth(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getupstreammsgdistmonth";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeUpstreamMsgDistMonthResponse.class);
    }

    /**
     *获取公众号广告汇总数据
     * @param slotId
     *      广告位枚举值, 广告位实际上映射的数字
     *      4090752905805508 : 视频后贴广告位
     *      72058780271891663 : 文章底部广告位
     *      9020229299926746 : 文章中部广告位
     *      20160808 : 互选广告位
     * @param page
     *      数据返回页数
     * @param pageSize
     *      每页返回数据条数
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     *
     */
    public static DataCubePublisherAdposGeneralResponse publisher_adpos_general(Integer slotId,Integer page,Integer pageSize,Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/publisher/stat";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));
        params.put("page", page);
        params.put("page_size", pageSize);
        params.put("action","publisher_adpos_general");
        params.put("slot_id",slotId);

        return JsonUtils.toObject(WechatUtils.get(url,params),DataCubePublisherAdposGeneralResponse.class);
    }

    /**
     *获取公众号返佣商品数据
     * @param slotId
     *      广告位枚举值, 广告位实际上映射的数字
     *      4090752905805508 : 视频后贴广告位
     *      72058780271891663 : 文章底部广告位
     *      9020229299926746 : 文章中部广告位
     *      20160808 : 互选广告位
     * @param page
     *      数据返回页数
     * @param pageSize
     *      每页返回数据条数
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     *
     */
    public static DataCubePublisherCpsGeneralResponse publisher_cps_general(Long slotId,Integer page,Integer pageSize,Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/publisher/stat";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));
        params.put("page", page);
        params.put("page_size", pageSize);
        params.put("action","publisher_adpos_general");
        params.put("slot_id",slotId);

        return JsonUtils.toObject(WechatUtils.get(url,params),DataCubePublisherCpsGeneralResponse.class);
    }


    /**
     *获取接口分析数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeInterfaceSummaryResponse getinterfacesummary(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getinterfacesummary";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeInterfaceSummaryResponse.class);
    }
    /**
     *获取接口分析分时数据
     * @param beginDate
     *  获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”（比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
     * @param endDate
     *   获取数据的结束日期，end_date允许设置的最大值为昨日
     */
    public static DataCubeInterfaceSummaryHourResponse getinterfacesummaryhour(Date beginDate, Date endDate){
        String url="https://api.weixin.qq.com/datacube/getinterfacesummaryhour";
        Map<String,Object> params = new HashMap<>();
        params.put("begin_date", Date8Utils.formatDateToString(beginDate,"yyyy-MM-dd"));
        params.put("end_date", Date8Utils.formatDateToString(endDate,"yyyy-MM-dd"));

        return JsonUtils.toObject(WechatUtils.postJson(url,params),DataCubeInterfaceSummaryHourResponse.class);
    }
}
