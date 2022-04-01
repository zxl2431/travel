package cn.agree.travel.service.impl;

import cn.agree.travel.model.Favorite;
import cn.agree.travel.model.User;
import cn.agree.travel.service.IFavoriteService;

public class FavoriteServiceImpl implements IFavoriteService {
    @Override
    public Favorite isFavorite(String rid, User user) {
        return null;
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
