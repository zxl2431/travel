package cn.agree.travel.dao;

import cn.agree.travel.model.Route;

import java.util.List;

public interface IRouteDao {
    // 人气最高的4条线路
    List<Route> getPopularityRouteList();
    // 最新的
    List<Route> getNewestRouteList();
    // 主题的
    List<Route> getThemeRouteList();
}
