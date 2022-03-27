package cn.agree.travel.service;

import cn.agree.travel.exception.PasswordErrorException;
import cn.agree.travel.exception.UnActiveException;
import cn.agree.travel.exception.UserNameErrorException;
import cn.agree.travel.model.User;

public interface IUserService {
    User findUserByUserName(String username) throws Exception;
    void saveUser(User user) throws Exception;

    User findUserByCode(String code) throws Exception;

    void updateUser(User user) throws Exception;

    User doLogin(String username, String password) throws UserNameErrorException, PasswordErrorException, UnActiveException;
}
