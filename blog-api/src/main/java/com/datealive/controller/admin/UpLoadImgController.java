package com.datealive.controller.admin;

import cn.hutool.json.JSONUtil;
import com.datealive.annotation.LogAnno;
import com.datealive.common.Result;
import com.datealive.common.ResultCode;
import com.datealive.common.StaticFinalCode;
import com.datealive.service.vo.GiteeImageVo;
import com.datealive.utils.RedisUtil;
import com.datealive.utils.UploadUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: UpLoadImgController
 * @Description: TODO
 * @author: zt
 * @date: 2021/4/11  10:31
 */
@RestController
@RequestMapping("/admin")
@Transactional(rollbackFor = Exception.class)
public class UpLoadImgController {

    @Autowired
    RedisUtil redisUtil;

    @Value("${imgServerUrl}")
    private String imgServerUrl;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UploadUtil uploadUtil;

    /**
     * 上传图片
     *
     * @param file
     * @return
     * @throws Exception
     */
    @RequiresPermissions(value = {"admin"}, logical = Logical.OR)
    @LogAnno(operateType = "POST", description = "上传图片", classification = 1)
    @RequiresAuthentication
    @PostMapping("/uploadImgtoGitee")
    @ResponseBody
    public Result uploadImg(@RequestParam("file") MultipartFile file) throws Exception {
        String originaFileName = file.getOriginalFilename();
        //上传图片不存在时
        if (originaFileName == null) {
            return Result.error(ResultCode.Not_Found, "图片不存在");
        }
        String realPath = "/zhou/";
        String newFilename = uploadUtil.Upload(file, realPath);
        //上传成功后需要更新redis
        redisUtil.hset(StaticFinalCode.GITEE_IMAGE_PRO_FIX, originaFileName, imgServerUrl + newFilename);
        return Result.success("上传图片成功", JSONUtil.toJsonStr(newFilename));
    }

    /**
     * 删除图片
     *
     * @param imgPath
     * @return
     * @throws Exception
     */
    @RequiresPermissions(value = {"admin"}, logical = Logical.OR)
    @LogAnno(operateType = "DELETE", description = "删除Gitee图床的图片", classification = 1)
    @RequiresAuthentication
    @DeleteMapping("/delImgFromGitee")
    @ResponseBody
    public Result delImg(@RequestParam(value = "imgPath") String imgPath) throws Exception {
        if (redisUtil.hasKey(StaticFinalCode.GITEE_IMAGE_PRO_FIX)){
            redisUtil.hdel(StaticFinalCode.GITEE_IMAGE_PRO_FIX,imgPath);
        }
        return Result.success("删除成功");
    }


    @RequiresAuthentication
    @GetMapping("/allImageByGiteeFromGitee")
    public Result getAllImageByGitee() {

        if (redisUtil.hasKey(StaticFinalCode.GITEE_IMAGE_PRO_FIX)) {
            Map<Object, Object> hmget = redisUtil.hmget(StaticFinalCode.GITEE_IMAGE_PRO_FIX);
            List<GiteeImageVo> collect = hmget.entrySet().stream().map(item -> {
                GiteeImageVo imageVo = new GiteeImageVo();
                imageVo.setSha(item.getKey().toString());
                imageVo.setDownload_url(item.getValue().toString());
                return imageVo;
            }).collect(Collectors.toList());
            return Result.success("获取成功", collect);
        } else {
            return Result.success("数据为空", null);
        }

    }

    /**
     * 集合分页
     *
     * @param resourceList 要分页的集合
     * @param pageIndex    页码
     * @param pageSize     每页条数
     * @return 分页后的集合
     */
    private static List<GiteeImageVo> getPageByList(List<GiteeImageVo> resourceList, int pageIndex, int pageSize, int totalPage) {

        List<GiteeImageVo> pageList = new ArrayList<>();
        if (pageIndex < 1) {
            pageIndex = 1;
        }

        if (pageIndex > totalPage) {
            pageIndex = totalPage;
        }


        int size = resourceList.size();
        int pageCount = size / pageSize;
        int fromIndex = (pageIndex - 1) * pageSize;

        int toIndex = fromIndex + pageSize;
        if (toIndex >= size) {
            toIndex = size;
        }
        if (pageIndex > pageCount + 1) {
            fromIndex = 0;
            toIndex = 0;
        }

        pageList = resourceList.subList(fromIndex, toIndex);
        return pageList;
    }


}
