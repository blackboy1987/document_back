package com.igomall;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Demo {

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\black\\11";
        File[] files = new File(path).listFiles();

        for (File file:files) {
            String fileName = file.getAbsolutePath();
            FileUtils.copyFile(file,new File(fileName.replace(" _ 美女直播见鬼的故事 ","")));
            FileUtils.deleteQuietly(file);
        }
    }
}
