package com.igomall.wechat.util;

import com.igomall.common.Pageable;
import com.igomall.wechat.entity.material.Article;
import com.igomall.wechat.entity.material.NewsMaterialResponse;
import com.igomall.wechat.entity.response.AccessToken;
import com.igomall.util.JsonUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 素材工具类
 */
public final class MaterialUtils {
    /**
     * PoolingHttpClientConnectionManager
     */
    private static final PoolingHttpClientConnectionManager HTTP_CLIENT_CONNECTION_MANAGER;

    /**
     * CloseableHttpClient
     */
    private static final CloseableHttpClient HTTP_CLIENT;

    static {
        HTTP_CLIENT_CONNECTION_MANAGER = new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build());
        HTTP_CLIENT_CONNECTION_MANAGER.setDefaultMaxPerRoute(100);
        HTTP_CLIENT_CONNECTION_MANAGER.setMaxTotal(200);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(60000).setConnectTimeout(60000).setSocketTimeout(60000).build();
        HTTP_CLIENT = HttpClientBuilder.create().setConnectionManager(HTTP_CLIENT_CONNECTION_MANAGER).setDefaultRequestConfig(requestConfig).build();
    }

    private MaterialUtils(){}

    /**
     * 上传临时素材
     * @param file
     *  文件
     * @param type
     *  媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
     * @return
     */
    /**
     * {
     *  "type":"image",
     *  "media_id":"cZswiJGARalhVaymkTmvyXeQt6v7Ux7KM9mq-LYLBeH8xL9OMvD2w6ZJu_SX4Ykr",
     *  "created_at":1586446258,
     *  "item":[]
     * }
     *
     */
    public static String upload(File file, String type) {
        String url="https://api.weixin.qq.com/cgi-bin/media/upload";
        String result = WechatUtils.uploadFile(url,file,type);
        System.out.println("result:"+result);
        return result;
    }

    /**
     * 获取临时素材：非视频。
     *
     * @param mediaId
     *  素材id
     * @return
     */
    public static void get(String mediaId,String path) {
        AccessToken accessToken = WechatUtils.getAccessToken();
        Map<String,Object> params = new HashMap<>();
        params.put("media_id",mediaId);
        params.put("access_token",accessToken.getAccessToken());
        InputStream inputStream;
        String url = "https://api.weixin.qq.com/cgi-bin/media/get";
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String name = entry.getKey();
                    String value = ConvertUtils.convert(entry.getValue());
                    if (StringUtils.isNotEmpty(name)) {
                        nameValuePairs.add(new BasicNameValuePair(name, value));
                    }
                }
            }
            HttpGet httpGet = new HttpGet(url + (StringUtils.contains(url, "?") ? "&" : "?") + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, "UTF-8")));
            CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpGet);
            try {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    inputStream = httpEntity.getContent();
                    File file = new File(path);
                    if(!file.getParentFile().exists()){
                        file.getParentFile().mkdirs();
                    }
                    try {
                        FileUtils.copyInputStreamToFile(inputStream,file);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    System.out.println(file.getAbsolutePath());
                }
            } finally {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 获取临时素材:视频。返回的数据如下：
     *
     * @param mediaId
     *  素材id
     * @return
     */
    public static void getVideo(String mediaId,String path) {
        AccessToken accessToken = WechatUtils.getAccessToken();
        Map<String,Object> params = new HashMap<>();
        params.put("media_id",mediaId);
        params.put("access_token",accessToken.getAccessToken());
        InputStream inputStream;
        String url = "https://api.weixin.qq.com/cgi-bin/media/get";
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String name = entry.getKey();
                    String value = ConvertUtils.convert(entry.getValue());
                    if (StringUtils.isNotEmpty(name)) {
                        nameValuePairs.add(new BasicNameValuePair(name, value));
                    }
                }
            }
            HttpGet httpGet = new HttpGet(url + (StringUtils.contains(url, "?") ? "&" : "?") + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, "UTF-8")));
            CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpGet);
            try {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    inputStream = httpEntity.getContent();
                    File file = new File(path);
                    if(!file.getParentFile().exists()){
                        file.getParentFile().mkdirs();
                    }
                    try {
                        FileUtils.copyInputStreamToFile(inputStream,file);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    System.out.println(file.getAbsolutePath());
                }
            } finally {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    /**
     * 新增永久图文素材
     */
    public static String addNews(List<Article> articles){
        AccessToken accessToken = WechatUtils.getAccessToken();
        Map<String,Object> map = new HashMap<>();
        map.put("articles",articles);
        String url="https://api.weixin.qq.com/cgi-bin/material/add_news";
        String result = WechatUtils.postJson(url,map);
        System.out.println("result:"+result);
        return result;
    }

    /**
     * 新增永久图片，音频，视频素材
     */
    public static String addMaterial(File file,String type){
        String url="https://api.weixin.qq.com/cgi-bin/material/add_material";
        String result = WechatUtils.uploadFile(url,file,type);
        System.out.println("result:"+result);
        return result;
    }


    /**
     * 上传图文消息内的图片获取URL
     * @param file
     * @return
     */
    // result:{"url":"http:\/\/mmbiz.qpic.cn\/mmbiz_jpg\/JEjICianj2jGBH0H1cmDPJ3H8olh7SKqtnUwAGP6AuciaEWZ7zuVb6yQqxZl2qlHP6ibplNKe2FZzGZ43UZRjqYXQ\/0"}
    public static String uploadimg(File file){
        String result = null;
        String url="https://api.weixin.qq.com/cgi-bin/media/uploadimg";
        result = WechatUtils.uploadFile(url,file,null);
        System.out.println("result:"+result);
        return result;
    }

    /**
     * 获取永久素材
     * @param mediaId
     * @return
     */
    // result:{"news_item":[{"title":"?????????��??","author":"??��?��????��??","digest":"��??????????????��????????http:\/\/mmbiz.qpic.cn\/mmbiz_jpg\/JEjICianj2jGBH0","content":"<p>��??????????????��????????<\/p><p>http:\/\/mmbiz.qpic.cn\/mmbiz_jpg\/JEjICianj2jGBH0H1cmDPJ3H8olh7SKqtnUwAGP6AuciaEWZ7zuVb6yQqxZl2qlHP6ibplNKe2FZzGZ43UZRjqYXQ\/0<\/p>","content_source_url":"","thumb_media_id":"be-XCwSBZ2ezRRUs15-XT_kmjGwRpQwO-k4ZDO70jlE","show_cover_pic":1,"url":"http:\/\/mp.weixin.qq.com\/s?__biz=MzIzMTM2Njg3MA==&mid=100000003&idx=1&sn=3e2acd5ab6ce2b476b10e06d428683db&chksm=68a408ba5fd381ac3a5343159f9c176eb09e8d69366b6b4b57eb7e3665fce319e148627fbbf4#rd","thumb_url":"http:\/\/mmbiz.qpic.cn\/mmbiz_jpg\/JEjICianj2jGBH0H1cmDPJ3H8olh7SKqtnUwAGP6AuciaEWZ7zuVb6yQqxZl2qlHP6ibplNKe2FZzGZ43UZRjqYXQ\/0?wx_fmt=jpeg","need_open_comment":1,"only_fans_can_comment":0}],"create_time":1586757610,"update_time":1586757610}
    public static String getMaterial(String mediaId){
        Map<String,Object> map = new HashMap<>();
        map.put("media_id",mediaId);
        AccessToken accessToken = WechatUtils.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_material";
        return WechatUtils.postJson(url,map);
    }

    /**
     * 删除不再需要的永久素材
     * @param mediaId
     * @return
     */
    public static String delMaterial(String mediaId){
        Map<String,Object> map = new HashMap<>();
        map.put("media_id",mediaId);
        String url = "https://api.weixin.qq.com/cgi-bin/material/del_material";
        return WechatUtils.postJson(url,map);
    }

    /**
     * 修改永久图文素材
     * @return
     */
    public static String updateNews(String mediaId,Article article,Integer index){
        String url=" https://api.weixin.qq.com/cgi-bin/material/update_news";
        Map<String,Object> map = new HashMap<>();
        map.put("media_id",mediaId);
        map.put("index",index);
        map.put("articles",article);
        return WechatUtils.postJson(url,map);
    }

    /**
     * 1.永久素材的总数，也会计算公众平台官网素材管理中的素材
     * 2.图片和图文消息素材（包括单图文和多图文）的总数上限为5000，其他素材的总数上限为1000
     * 3.调用该接口需https协议
     * @return
     */
    // {"voice_count":0,"video_count":0,"image_count":1,"news_count":1}
    public static String getMaterialCount(){
        String url="https://api.weixin.qq.com/cgi-bin/material/get_materialcount";
        return WechatUtils.get(url,null);
    }

    /**
     * 获取素材，分页展示
     * @param type
     *  素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
     * @param pageable
     * @return
     */
    public static NewsMaterialResponse getMaterial(String type, Pageable pageable){
        String url="https://api.weixin.qq.com/cgi-bin/material/batchget_material";
        Map<String,Object> params = new HashMap<>();
        params.put("type",type);
        params.put("offset",(pageable.getPageNumber()-1)*pageable.getPageSize());
        params.put("count",pageable.getPageSize());
        String result = WechatUtils.postJson(url,params);
        return JsonUtils.toObject(result,NewsMaterialResponse.class);
    }















    public static void main(String[] args) {
        String path = "C:\\Users\\black\\Desktop\\1\\1.JPG";
        // result:{"media_id":"be-XCwSBZ2ezRRUs15-XT_kmjGwRpQwO-k4ZDO70jlE","url":"http:\/\/mmbiz.qpic.cn\/mmbiz_jpg\/JEjICianj2jGBH0H1cmDPJ3H8olh7SKqtnUwAGP6AuciaEWZ7zuVb6yQqxZl2qlHP6ibplNKe2FZzGZ43UZRjqYXQ\/0?wx_fmt=jpeg","item":[]}
       // addMaterial(new File(path),"image");
        // get("cZswiJGARalhVaymkTmvyXeQt6v7Ux7KM9mq-LYLBeH8xL9OMvD2w6ZJu_SX4Ykr");
        // uploadimg(new File(path));
        List<Article> articles = new ArrayList<>();
        for (int i=0;i<1;i++){
            Article article = new Article();
            article.setAuthor("爱尚学院");
            article.setContent("<p>这是我的测试图片</p><p>http://mmbiz.qpic.cn/mmbiz_jpg/JEjICianj2jGBH0H1cmDPJ3H8olh7SKqtnUwAGP6AuciaEWZ7zuVb6yQqxZl2qlHP6ibplNKe2FZzGZ43UZRjqYXQ/0</p>");
            article.setNeedOpenComment(1);
            article.setOnlyFansCanComment(0);
            article.setThumbMediaId("be-XCwSBZ2ezRRUs15-XT_kmjGwRpQwO-k4ZDO70jlE");
            article.setShowCoverPic(1);
            article.setTitle("我是标题");
            articles.add(article);
        }
        // result:{"media_id":"be-XCwSBZ2ezRRUs15-XT2KHQ5Z7jXYIzGIWP-xoKQY","item":[]}
        // result:{"media_id":"be-XCwSBZ2ezRRUs15-XT9jcug-OQD9E45FMkSyx5_U","item":[]}
      // addNews(articles);
       // getMaterial("be-XCwSBZ2ezRRUs15-XT9jcug-OQD9E45FMkSyx5_U");
        // {"errcode":0,"errmsg":"ok"}
       // System.out.println(delMaterial("be-XCwSBZ2ezRRUs15-XT9jcug-OQD9E45FMkSyx5_U"));
// {"voice_count":0,"video_count":0,"image_count":1,"news_count":1}
       // System.out.println(getMaterialCount());
        NewsMaterialResponse newsMaterialResponse = getMaterial("news",new Pageable());
        System.out.println(newsMaterialResponse);
    }
}
