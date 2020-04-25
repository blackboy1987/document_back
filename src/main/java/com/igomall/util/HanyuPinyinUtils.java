package com.igomall.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import org.apache.commons.io.FileUtils;

import java.io.File;

public final class HanyuPinyinUtils {

    private static final HanyuPinyinOutputFormat HANYU_PINYIN_OUTPUT_FORMAT = new HanyuPinyinOutputFormat();

    private HanyuPinyinUtils(){}

    static {
        HANYU_PINYIN_OUTPUT_FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        HANYU_PINYIN_OUTPUT_FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    }


    public static String pinyin(String words){
        String result = words;
        result = result.replace("（","").replace("）","");
        result = result.replace("《","").replace("》","");
        result = result.replace("【","").replace("】","");
        result = result.replace("，","").replace("。","");
        result = result.replace("：","").replace("、","");
        result = result.replace("[","").replace("]","");
        result = result.replace("(","").replace(")","");
        result = result.replace("-","").replace(" ","");
        result = result.replace("－","").replace(" ","");
        try {
            char [] wordChars = result.toCharArray();
            result="";
            for (char word:wordChars) {
                if(word>128){
                    String[] results = PinyinHelper.toHanyuPinyinStringArray(word,HANYU_PINYIN_OUTPUT_FORMAT);
                    if(results.length>0){
                        result = result+results[0];
                    }else{
                        result = result+word;
                    }
                }else{
                    result  = result+word;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            //return result;
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println(pinyin("30-程序员的思维修炼 开发认知潜能的九堂课-中文版"));
        String path = "F:\\BaiduNetdiskDownload\\新建文件夹\\web前端教程\\【05】开发工具+图书+参考手册\\图书+手册\\100本最棒前端开发图书";
        String path1 = "F:\\BaiduNetdiskDownload\\新建文件夹\\web前端教程\\【05】开发工具+图书+参考手册\\图书+手册\\files";

        File parent = new File(path);
        File[] files = parent.listFiles();

        for (File file:files) {
            File file1 = new File(path1,pinyin(file.getName()).toLowerCase());
           // System.out.println(pinyin(file.getName()).toLowerCase().replace(" ",""));
             try{
                FileUtils.copyFile(file,file1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }
}
