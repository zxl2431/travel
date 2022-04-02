package cn.agree.travel.service.impl;

import cn.agree.travel.dao.IFavoriteDao;
import cn.agree.travel.dao.impl.FavoriteDaoImpl;
import cn.agree.travel.model.Favorite;
import cn.agree.travel.model.User;
import cn.agree.travel.service.IFavoriteService;

public class FavoriteServiceImpl implements IFavoriteService {

    private IFavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public Favorite isFavorite(String rid, User user) {
        Favorite favorite = favoriteDao.findFavorite(rid, user);
        return favorite;
    }

    @Override
    public boolean addFavorite(User user, String rid) {

        return false;
    }

    @Override
    public int findCount(String rid) {
        return 0;
    }
}
