package com.datealive.utils;

import java.util.UUID;

/**
 * @Description: 生成随机的UUID
 * @Author: zt
 */
public class UUIDUtil {
    /**生成16位随机的UUID*/
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
