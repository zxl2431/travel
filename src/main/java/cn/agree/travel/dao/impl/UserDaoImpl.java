package cn.agree.travel.dao.impl;

import cn.agree.travel.dao.IUserDao;
import cn.agree.travel.model.User;
import cn.agree.travel.util.JDBCUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements IUserDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());

    @Override
    public User findUserByUserName(String username) throws Exception {
        System.out.println("UserDaoImpl.findUserByUserName()");
        User user = null;
        try {
            String sql = "select * from tab_user where username=?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
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
}
