package cn.agree.travel.dao.impl;

import cn.agree.travel.dao.IFavoriteDao;
import cn.agree.travel.model.Favorite;
import cn.agree.travel.model.User;
import cn.agree.travel.util.DateUtil;
import cn.agree.travel.util.JDBCUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

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

    @Override
    public Long findMyFavoriteCount(User user) {
        String sql = "select count(*) from tab_favorite where uid=?";
        Long count = template.queryForObject(sql, Long.class, user.getUid());
        return count;
    }

    @Override
    public List<Map<String, Object>> findPageFavorites(User user, int curPage, int pageSize) {
        String sql = "select * from tab_favorite f,tab_route r where f.rid=r.rid and uid=? limit ?,?";
        List<Map<String, Object>> maps = template.queryForList(sql, user.getUid(), (curPage - 1) * pageSize, pageSize);
        return maps;
    }
}
