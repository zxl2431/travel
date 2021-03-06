package cn.agree.travel.dao.impl;

import cn.agree.travel.constant.Constant;
import cn.agree.travel.dao.IUserDao;
import cn.agree.travel.model.User;
import cn.agree.travel.util.JDBCUtil;
import org.springframework.dao.DataAccessException;
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
    public boolean saveUser(User user) throws Exception {
        String sql = "insert into tab_user values (?,?,?,?,?,?,?,?,?,?)";
        boolean flag = false;
        try {
            int i = template.update(sql, user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(),
                    user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
            if (i == 1){
                flag = true;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public int findUserByCode(String code) throws Exception {
        String sql = "update tab_user set code=?,status=? where code=?";
        return template.update(sql,0, Constant.ACTIVED,code);
    }

    @Override
    public void updateUser(User user) throws Exception {

    }
}
