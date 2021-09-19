package com.datealive.controller;

import com.datealive.service.CommentService;
import com.datealive.service.UserService;
import com.datealive.utils.UserAgentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: UserController
 * @Description: TODO
 * @author: zt
 * @date: 2021/1/31  12:59
 */
@RestController
public class TestController {

    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserAgentUtils agentUtils;


//    @RequiresAuthentication
//    @RequestMapping("/test")
//    public Result test(){
//
//        String testname="admin";
//        User user = userService.getUserByName(testname);
//        return Result.success("SUCCESS",user);
//    }
//
//    @PostMapping("/user")
//    public Result user(@Validated @RequestBody User user){
//        return Result.success("SUCCESS", user);
//    }
//
//    @GetMapping("/qqtest/{qq}")
//    public Result getQQavatar(@PathVariable("qq")String qq) throws UnsupportedEncodingException {

//        String substring = qq.substring(0, qq.indexOf('@'));
//        System.out.println(substring);
//        String qqAvatarURL = QQInfoUtils.getQQAvatarURL(qq);
//        String qqNickname = QQInfoUtils.getQQNickname(qq);
//        Map<String,String> map = new HashMap<>();
//        map.put("qqAvatarURL",qqAvatarURL);
//        map.put("qqNickname",qqNickname);
//        return Result.success("OK",map);
//
//    }
//    @GetMapping("/ipadress/{ip}")
//    public Result ipadress(@PathVariable("ip") String ip){
//        String cityInfo = UserUtils.getCityInfo(ip);
//        return Result.success("OK",cityInfo);
//    }
//
//    @LogAnno(operateType="error",description = "ERROR",classification = 0)
//    @GetMapping("/testError")
//    public Result testError() throws Exception{
//        throw new Exception(".");
//    }


}
