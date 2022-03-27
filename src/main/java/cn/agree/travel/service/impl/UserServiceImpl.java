package cn.agree.travel.service.impl;

import cn.agree.travel.dao.IUserDao;
import cn.agree.travel.dao.impl.UserDaoImpl;
import cn.agree.travel.exception.PasswordErrorException;
import cn.agree.travel.exception.UnActiveException;
import cn.agree.travel.exception.UserNameErrorException;
import cn.agree.travel.model.User;
import cn.agree.travel.service.IUserService;

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
    public void saveUser(User user) throws Exception {

    }

    @Override
    public User findUserByCode(String code) throws Exception {
        return null;
    }

    @Override
    public void updateUser(User user) throws Exception {

    }

    @Override
    public User doLogin(String username, String password) throws UserNameErrorException, PasswordErrorException, UnActiveException {
        return null;
    }
}
