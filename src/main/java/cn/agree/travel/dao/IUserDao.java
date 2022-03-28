package cn.agree.travel.dao;

import cn.agree.travel.model.User;

public interface IUserDao {

    User findUserByUserName(String username) throws Exception;

    boolean saveUser(User user) throws Exception;

    int findUserByCode(String code) throws Exception;

    void updateUser(User user) throws Exception;

}
