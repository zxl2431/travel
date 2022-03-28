package cn.agree.travel.service.impl;

import cn.agree.travel.dao.IUserDao;
import cn.agree.travel.dao.impl.UserDaoImpl;
import cn.agree.travel.exception.PasswordErrorException;
import cn.agree.travel.exception.UnActiveException;
import cn.agree.travel.exception.UserNameErrorException;
import cn.agree.travel.model.User;
import cn.agree.travel.service.IUserService;
import cn.agree.travel.util.MailUtil;

public class UserServiceImpl implements IUserService {

    private IUserDao dao = new UserDaoImpl();

    @Override
    public User findUserByUserName(String username) throws Exception {
        System.out.println("UserServiceImpl.findUserByUserName");
        User user = null;
        user = dao.findUserByUserName(username);
        return user;
    }

    @Override
    public boolean saveUser(User user) throws Exception {
        // 调用dao
        boolean flag = dao.saveUser(user);
        if (flag) {
            //如果注册成功，则发送邮件到客户端
            MailUtil.sendMail(user.getEmail(),"<a href='http://localhost:8080/travel/user?action=active&code="+user.getCode()+"'>点击激活<a>");
        }

        return flag;
    }

    @Override
    public boolean findUserByCode(String code) throws Exception {
        // 调用dao层根据code更新用户信息
        boolean flag = false;
        int i = dao.findUserByCode(code);
        if (i == 1) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void updateUser(User user) throws Exception {

    }

    @Override
    public User doLogin(String username, String password) throws UserNameErrorException, PasswordErrorException, UnActiveException {
        return null;
    }
}
