package cn.agree.travel.dao;

import cn.agree.travel.model.Favorite;
import cn.agree.travel.model.User;
import org.springframework.jdbc.core.JdbcTemplate;

public interface IFavoriteDao {
    Favorite findFavorite(String rid, User user);

    void addFavorite(String rid, User user, JdbcTemplate template);

    void updateRoute(String rid, JdbcTemplate template);

    int findCount(String rid);
}
