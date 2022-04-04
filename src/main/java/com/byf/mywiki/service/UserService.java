package com.byf.mywiki.service;

import com.byf.mywiki.domain.User;
import com.byf.mywiki.domain.UserExample;
import com.byf.mywiki.exception.BusinessException;
import com.byf.mywiki.exception.BusinessExceptionCode;
import com.byf.mywiki.mapper.UserMapper;
import com.byf.mywiki.req.UserLoginReq;
import com.byf.mywiki.req.UserQueryReq;
import com.byf.mywiki.req.UserResetPasswordReq;
import com.byf.mywiki.req.UserSaveReq;
import com.byf.mywiki.resp.UserLoginResp;
import com.byf.mywiki.resp.UserQueryResp;
import com.byf.mywiki.resp.PageResp;
import com.byf.mywiki.util.CopyUtil;
import com.byf.mywiki.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);


    @Resource
    private SnowFlake snowFlake;

    @Autowired
    private UserMapper userMapper;
//    public List<User>  list() {
//        return userMapper.selectByExample(null);
//    }
//    public List<User>  list(String name) {
//        UserExample userExample = new UserExample();
//        UserExample.Criteria criteria = userExample.createCriteria();
//        criteria.andNameLike("%" + name + "%");
//
//        return userMapper.selectByExample(userExample);
//    }
//
//    public List<User>  list(UserReq req) {
//        UserExample userExample = new UserExample();
//        UserExample.Criteria criteria = userExample.createCriteria();
//        criteria.andNameLike("%" + req.getName() + "%");
//
//        return userMapper.selectByExample(userExample);
//    }

//    public List<UserResp>  list(UserReq req) {
//
//        UserExample userExample = new UserExample();
//        UserExample.Criteria criteria = userExample.createCriteria();
//        // 动态sql 常用写法
//        //
//        if(!ObjectUtils.isEmpty(req.getName())){
//            criteria.andNameLike("%" + req.getName() + "%");
//        }
//        PageHelper.startPage(1,3);
//        List<User> users = userMapper.selectByExample(userExample);
////        List<UserResp> respList = new ArrayList<>();
////        for(User user:users) {
//////            UserResp userResp = new UserResp();
//////            BeanUtils.copyProperties(user, userResp);
////            // 将user对象按对应属性复制到userresponse对象上
////            UserResp userResp = CopyUtil.copy(user, UserResp.class);
////            respList.add(userResp);
////        }
//        List<UserResp> respList = CopyUtil.copyList(users, UserResp.class);
//        return respList;
//    }



    public PageResp<UserQueryResp> list(UserQueryReq req) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getLoginName())) {
            criteria.andLoginNameEqualTo(req.getLoginName());
        }
//        if (!ObjectUtils.isEmpty(req.getCategoryId2())) {
//            criteria.andCategory2IdEqualTo(req.getCategoryId2());
//        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        // List<UserResp> respList = new ArrayList<>();
        // for (User user : userList) {
        //     // UserResp userResp = new UserResp();
        //     // BeanUtils.copyProperties(user, userResp);
        //     // 对象复制
        //     UserResp userResp = CopyUtil.copy(user, UserResp.class);
        //
        //     respList.add(userResp);
        // }

        // 列表复制
        List<UserQueryResp> list = CopyUtil.copyList(userList, UserQueryResp.class);

        PageResp<UserQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    public void save(UserSaveReq req) {
        User user = CopyUtil.copy(req, User.class);
        if (ObjectUtils.isEmpty(req.getId())) {
//            // 新增
////            LOG.info("11111111");
//            user.setId(snowFlake.nextId());
//            userMapper.insert(user);
            User userDB = selectByLoginName(req.getLoginName());
            if (ObjectUtils.isEmpty(userDB)) {
                // 新增
                user.setId(snowFlake.nextId());
                userMapper.insert(user);
            } else {
                // 用户名已存在
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        } else {
            // 更新
            user.setLoginName(null);
            user.setPassword(null);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public User selectByLoginName(String LoginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(LoginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }

    /**
     * 修改密码
     */
    public void resetPassword(UserResetPasswordReq req) {
        User user = CopyUtil.copy(req, User.class);
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 登录
     */
    public UserLoginResp login(UserLoginReq req) {
        User userDb = selectByLoginName(req.getLoginName());
        if (ObjectUtils.isEmpty(userDb)) {
            // 用户名不存在
            LOG.info("用户名不存在, {}", req.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (userDb.getPassword().equals(req.getPassword())) {
                // 登录成功
                UserLoginResp userLoginResp = CopyUtil.copy(userDb, UserLoginResp.class);
                return userLoginResp;
            } else {
                // 密码不对
                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", req.getPassword(), userDb.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }
}
