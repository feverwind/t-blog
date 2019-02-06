package com.xsh.blog.service.impl;

import com.xsh.blog.dao.UserVoMapper;
import com.xsh.blog.exception.BusinessException;
import com.xsh.blog.model.Vo.UserVo;
import com.xsh.blog.model.Vo.UserVoExample;
import com.xsh.blog.service.IUserService;
import com.xsh.blog.utils.TaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by BlueT on 2017/3/3.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserVoMapper userDao;

    @Override
    @Transactional
    public Integer insertUser(UserVo userVo) {

        if (StringUtils.isBlank(userVo.getUsername()) || StringUtils.isNotBlank(userVo.getEmail())){
            throw new BusinessException("用户名和密码不能为空");
        }

        //  用户密码加密
        String encodePwd = TaleUtils.MD5encode(userVo.getUsername() + userVo.getPassword());
        userVo.setPassword(encodePwd);
        userDao.insertSelective(userVo);
        return userVo.getUid();
    }

    @Override
    public UserVo queryUserById(Integer uid) {
        UserVo userVo = null;
        if (uid != null) {
            userVo = userDao.selectByPrimaryKey(uid);
        }
        return userVo;
    }

    @Override
    public UserVo login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new BusinessException("用户名和密码不能为空");
        }

        /* 先检查用户是否存在 */
        UserVoExample example = new UserVoExample();
        UserVoExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        long count = userDao.countByExample(example);
        if (count < 1) {
            throw new BusinessException("不存在该用户");
        }

        /* 检查密码是否正确 */
        String pwd = TaleUtils.MD5encode(username + password);
        criteria.andPasswordEqualTo(pwd);
        List<UserVo> userVos = userDao.selectByExample(example);
        if (userVos.size() != 1) {
            throw new BusinessException("用户名或密码错误");
        }
        return userVos.get(0);
    }

    @Override
    @Transactional
    public void updateByUid(UserVo userVo) {
        if (null == userVo || null == userVo.getUid()) {
            throw new BusinessException("userVo is null");
        }

        int i = userDao.updateByPrimaryKeySelective(userVo);
        if (i != 1) {
            throw new BusinessException("update user by uid and retrun is not one");
        }
    }
}
