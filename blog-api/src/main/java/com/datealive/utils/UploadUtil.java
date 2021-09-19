package com.datealive.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Description: 文件上传帮助类
 * @Author: zt
 */
@Component
public class UploadUtil {

    @Value("${imgRealUrl}")
    private String imgRealUrl;

    public String Upload(MultipartFile filename, String realPath) throws IOException {
        String dataDirpath = "";
        //imgPath代表磁盘路径
        if(imgRealUrl.lastIndexOf("/") != -1 ) {
            dataDirpath = imgRealUrl + "/" +realPath;
        }else {
            dataDirpath = imgRealUrl + realPath;
        }
        File dataDir = new File(dataDirpath);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        //=========>保存图片
        //获取文件的原始名称
        String oldFilename = filename.getOriginalFilename();
        //获取文件的后缀名称
        String extname = "." + FilenameUtils.getExtension(oldFilename);
        //生成新的文件名
        String newFilename = DateUtil.getFormat() + UUIDUtil.getUUID() + extname;
        //处理文件信息
        filename.transferTo(new File(dataDir,newFilename));
        return newFilename;
    }

}
