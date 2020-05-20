package com.igomall.wechat.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.igomall.common.Pageable;
import com.igomall.util.Date8Utils;
import com.igomall.util.EhCacheUtils;
import com.igomall.util.JsonUtils;
import com.igomall.util.WebUtils;
import com.igomall.wechat.entity.response.AccessToken;
import com.igomall.wechat.entity.response.IpListResponse;
import com.igomall.wechat.entity.response.WeChatUserResponse;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

public final class WechatUtils {

    private static final String APPID = "wx51e92bcdcb5fc4d7";

    private static final String APPSECRET = "8231f4390de7413d9d844220a09a8008";


    //private static final String APPID = "wx334733aa32708827";

    //private static final String APPSECRET = "3eb62f47d6e23698c000da747ce5cede";

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


    private WechatUtils(){}

    public static Map<String, String> parseXml(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            Element root = document.getRootElement();
            List<Element> elementList = root.elements();
            for (Element e : elementList) {
                // 首字母转小写和去掉下划线：“_”
                map.put(StringUtils.uncapitalize(e.getName()).replace("_",""), e.getText());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * 获取accesstoken
     * @return
     */
    public static AccessToken getAccessToken(){
        AccessToken accessToken = EhCacheUtils.getCacheAccessToken();
        if(accessToken!=null && accessToken.getExpiresDate().compareTo(new Date())>0){
            System.out.println("accessToken:"+accessToken.getAccessToken());
            return accessToken;
        }
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String,Object> params = new HashMap<>();
        params.put("grant_type","client_credential");
        params.put("appid",APPID);
        params.put("secret",APPSECRET);
        String result = WebUtils.get(url,params);
        accessToken = JsonUtils.toObject(result, AccessToken.class);
        if(StringUtils.isNotEmpty(accessToken.getAccessToken())){
            accessToken.setExpiresDate(Date8Utils.getNextSecond(accessToken.getExpires()-30));
            EhCacheUtils.setCacheAccessToken(accessToken);
            return accessToken;
        }
        return getAccessToken();

    }

    /**
     * 获取服务器ip
     * @return
     */
    public IpListResponse getIpList(){
        AccessToken accessToken = getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
        Map<String,Object> params = new HashMap<>();
        params.put("access_token",accessToken.getAccessToken());
        String result = WebUtils.get(url,params);
        return JsonUtils.toObject(result, new TypeReference<IpListResponse>() {
        });
    }

    /**
     * 获取用户基本信息
     * @param openid
     * @return
     */
    public static WeChatUserResponse getUserInfo(String openid){
        AccessToken accessToken = getAccessToken();
        if(accessToken==null){
            WeChatUserResponse weChatUserResponse = new WeChatUserResponse();
            weChatUserResponse.setErrCode(-1);
            weChatUserResponse.setErrMsg("accessToken is null !");
            return weChatUserResponse;
        }
        String url = "https://api.weixin.qq.com/cgi-bin/user/info";
        Map<String,Object> params = new HashMap<>();
        params.put("grant_type","client_credential");
        params.put("access_token",accessToken.getAccessToken());
        params.put("openid",openid);
        params.put("lang","zh_CN");
        String result = WebUtils.get(url,params);
        return JsonUtils.toObject(result, WeChatUserResponse.class);

    }



    /**
     * 获取评论
     * @param msgDataId
     * @param pageable
     * @return
     */
    public static String getComments(String msgDataId, Integer index, Integer type, Pageable pageable){
        String url="https://api.weixin.qq.com/cgi-bin/comment/list";
        Map<String,Object> params = new HashMap<>();
        params.put("msg_data_id",msgDataId);
        params.put("index",index);
        params.put("type",type);
        params.put("begin",(pageable.getPageNumber()-1)*pageable.getPageSize());
        params.put("count",pageable.getPageSize());
        String result = WechatUtils.postJson(url,params);
        return result;
    }



    public static String postJson(String url,Map<String,Object> parameterMap){
        Assert.hasText(url,"");
        String result = null;
        try {
            HttpPost httpPost = new HttpPost(url + (StringUtils.contains(url, "?") ? "&" : "?") + "access_token="+getAccessToken().getAccessToken());
            if (parameterMap != null && parameterMap.size()>0) {
                StringEntity params = new StringEntity(JsonUtils.toJson(parameterMap),"utf-8");//解决中文乱码问题
                params.setContentEncoding("UTF-8");
                params.setContentType("application/json");
                httpPost.setEntity(params);
            }
            CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpPost);
            try {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    result = EntityUtils.toString(httpEntity);
                    EntityUtils.consume(httpEntity);
                }
            } finally {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        System.out.println("result:"+result);
        return result;
    }

    /**
     * GET请求
     *
     * @param url
     *            URL
     * @param parameterMap
     *            请求参数
     * @return 返回结果
     */
    public static InputStream get1(String url, Map<String, Object> parameterMap) {
        Assert.hasText(url,"");
        InputStream inputStream;
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            if (parameterMap != null) {
                for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
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
                    return inputStream;
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
        return null;
    }

    public static String get(String url, Map<String, Object> parameterMap) {
        Assert.hasText(url,"");

        String result = null;
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("access_token",getAccessToken().getAccessToken()));
            if (parameterMap != null) {
                for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
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
                    result = EntityUtils.toString(httpEntity);
                    EntityUtils.consume(httpEntity);
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
        System.out.println(result);
        return result;
    }

    public static String uploadFile(String url, File file, String type){
        Assert.hasText(url,"");
        String result = null;
        AccessToken accessToken = WechatUtils.getAccessToken();
        try {
            HttpPost httpPost = new HttpPost("url");
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("media",file);
            if(StringUtils.isNotEmpty(type)){
                builder.addTextBody("type",type);
            }
            builder.addTextBody("access_token",accessToken.getAccessToken());
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpPost);
            try {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    result = EntityUtils.toString(httpEntity);
                    EntityUtils.consume(httpEntity);
                }
            } finally {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        System.out.println("result:"+result);
        return result;
    }














































    public static void main(String[] args) {
        Pageable pageable = new Pageable();
        getComments("4PCbTGwo9Nmscuv1McTgoCw0dBE2n8drTaDUixur1J0",0,0,pageable);
    }
}
