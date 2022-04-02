package cn.agree.travel.dao.impl;

import cn.agree.travel.dao.IFavoriteDao;
import cn.agree.travel.model.Favorite;
import cn.agree.travel.model.User;
import cn.agree.travel.util.DateUtil;
import cn.agree.travel.util.JDBCUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class FavoriteDaoImpl implements IFavoriteDao {

    JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());

    @Override
    public Favorite findFavorite(String rid, User user) {
        String sql = "select * from tab_favorite where rid=? and uid=?";
        Favorite favorite = null;
        try {
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, user.getUid());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return favorite;
    }

    @Override
    public void addFavorite(String rid, User user, JdbcTemplate template) {
        String sql = "insert into tab_favorite values (?,?,?)";
        template.update(sql,rid, DateUtil.getCurrentDate(),user.getUid());
    }

    @Override
    public void updateRoute(String rid, JdbcTemplate template) {
        String sql = "update tab_route set count=count+1 where rid=?";
        template.update(sql,rid);
    }

    @Override
    public int findCount(String rid) {
        String sql = "select count from tab_route where rid=?";
        Integer count = template.queryForObject(sql, Integer.class, rid);
        return count;
    }
}
