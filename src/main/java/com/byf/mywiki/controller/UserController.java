package com.byf.mywiki.controller;


import com.alibaba.fastjson.JSONObject;
import com.byf.mywiki.req.UserLoginReq;
import com.byf.mywiki.req.UserQueryReq;
import com.byf.mywiki.req.UserResetPasswordReq;
import com.byf.mywiki.req.UserSaveReq;
import com.byf.mywiki.resp.CommonResp;
import com.byf.mywiki.resp.UserLoginResp;
import com.byf.mywiki.resp.UserQueryResp;
import com.byf.mywiki.resp.PageResp;
import com.byf.mywiki.service.UserService;
import com.byf.mywiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {
//    @Resource
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private RedisTemplate redisTemplate;
//    @GetMapping("/list")
//    public List<User> list() {
//        return userService.list();
//    }

//    无参查询
//    @GetMapping("/list")
//    public CommonResp list() {
//        CommonResp<List<User>> resp = new CommonResp<>();
//        List<User> list = userService.list();
//        resp.setContent(list);
//        return resp;
//    }

//        @GetMapping("/list")
//    public CommonResp list(String name) {
//        CommonResp<List<User>> resp = new CommonResp<>();
//        List<User> list = userService.list(name);
//        resp.setContent(list);
//        return resp;
//    }
//
//    @GetMapping("/list")
//    public CommonResp list(UserReq req) {
//        CommonResp<List<User>> resp = new CommonResp<>();
//        List<User> list = userService.list(req);
//        resp.setContent(list);
//        return resp;
//    }

//    @GetMapping("/list")
//    public CommonResp list(UserReq req) {
//        CommonResp<List<UserResp>> resp = new CommonResp<>();
//        List<UserResp> list = userService.list(req);
//        resp.setContent(list);
//        return resp;
//    }

    @GetMapping("/list")
    public CommonResp list(@Valid UserQueryReq req) {
        CommonResp<PageResp<UserQueryResp>> resp = new CommonResp<>();
        PageResp<UserQueryResp> list = userService.list(req);
        resp.setContent(list);
        return resp;
    }




    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody UserSaveReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        userService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        userService.delete(id);
        return resp;
    }

    @PostMapping("/reset-password")
    public CommonResp resetPassword(@Valid @RequestBody UserResetPasswordReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        userService.resetPassword(req);
        return resp;
    }

    @PostMapping("/login")
    public CommonResp login(@Valid @RequestBody UserLoginReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<UserLoginResp> resp = new CommonResp<>();
        UserLoginResp userLoginResp = userService.login(req);

        Long token = snowFlake.nextId();
        LOG.info("生成单点登录token：{}，并放入redis中", token);
        userLoginResp.setToken(token.toString());
        redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(userLoginResp), 3600 * 24, TimeUnit.SECONDS);

        resp.setContent(userLoginResp);
        return resp;
    }

    @GetMapping("/logout/{token}")
    public CommonResp logout(@PathVariable String token) {
        CommonResp resp = new CommonResp<>();
        redisTemplate.delete(token);
        LOG.info("从redis中删除token: {}", token);
        return resp;
    }

}
