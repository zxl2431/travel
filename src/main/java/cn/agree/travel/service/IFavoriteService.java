package cn.agree.travel.service;

import cn.agree.travel.model.Favorite;
import cn.agree.travel.model.User;

public interface IFavoriteService {

    Favorite isFavorite(String rid, User user);

    boolean addFavorite(User user, String rid);

    int findCount(String rid);
}
