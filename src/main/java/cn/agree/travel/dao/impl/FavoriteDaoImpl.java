package cn.agree.travel.dao.impl;

import cn.agree.travel.dao.IFavoriteDao;
import cn.agree.travel.model.Favorite;
import cn.agree.travel.model.User;
import cn.agree.travel.util.JDBCUtil;
import org.springframework.jdbc.core.JdbcTemplate;

public class FavoriteDaoImpl implements IFavoriteDao {

    JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());

    @Override
    public Favorite findFavorite(String rid, User user) {
        return null;
    }

    @Override
    public void addFavorite(String rid, User user, JdbcTemplate template) {

    }

    @Override
    public void updateRoute(String rid, JdbcTemplate template) {

    }

    @Override
    public int findCount(String rid) {
        return 0;
    }
}
