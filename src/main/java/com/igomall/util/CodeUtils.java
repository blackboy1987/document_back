package com.igomall.util;

import java.util.Random;

public final class CodeUtils {

    private static final String[] CODE_STRING= new String[]{
           "0","1","2","3","4","5","6","7","8","9",
    };

    private static final String[] CODE_STRING1= new String[]{
            "A","B","C","D","E","F","G","H","J","K","L","M","N","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9",
    };

    private static final Random random = new Random();


    private CodeUtils(){}

    public static String getNumberCode(Integer length){
        StringBuffer sb = new StringBuffer(length);
        for (int i=0;i<length;i++) {
            sb.append(random.nextInt(CODE_STRING.length));
        }
        return sb.toString();
    }
    public static String getCode(Integer length){
        StringBuffer sb = new StringBuffer(length);
        for (int i=0;i<length;i++) {
            sb.append(random.nextInt(CODE_STRING1.length));
        }
        return sb.toString();
    }

}
