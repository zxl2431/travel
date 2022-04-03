package cn.agree.travel.dao;

import cn.agree.travel.model.Route;
import cn.agree.travel.model.RouteImg;

import java.util.List;
import java.util.Map;

public interface IRouteDao {
    // 人气最高的4条线路
    List<Route> getPopularityRouteList();
    // 最新的
    List<Route> getNewestRouteList();
    // 主题的
    List<Route> getThemeRouteList();

    // 某类线路的总条数
    long getCountByCid(String cid, String keyword);
    // 分页查询某类线路的明细
    List<Route> findPageRoutes(int curPage, String cid, int pageSize, String keyword);

    Map<String,Object> getRouteByRid(String rid);

    List<RouteImg> getRouteImgsByRid(String rid);

    long findRouteCount(String rname, String min, String max);

    List<Route> findFavoriteRankRoutes(int curPage, int pageSize, String rname, String min, String max);
}
