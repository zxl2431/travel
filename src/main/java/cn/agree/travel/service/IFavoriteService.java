package cn.agree.travel.service;

import cn.agree.travel.model.Favorite;
import cn.agree.travel.model.PageBean;
import cn.agree.travel.model.Route;
import cn.agree.travel.model.User;

import java.lang.reflect.InvocationTargetException;

public interface IFavoriteService {

    Favorite isFavorite(String rid, User user);

    boolean addFavorite(User user, String rid);

    int findCount(String rid);

    PageBean<Favorite> findMyFavorite(User user, int curPage) throws Exception;

    PageBean<Route> findFavoriteRank(int curPage, String rname, String min, String max);
}
