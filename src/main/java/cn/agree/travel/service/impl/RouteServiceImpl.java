package cn.agree.travel.service.impl;

import cn.agree.travel.dao.IRouteDao;
import cn.agree.travel.dao.impl.RouteDaoImpl;
import cn.agree.travel.model.Route;
import cn.agree.travel.service.IRouteService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteServiceImpl implements IRouteService {

    private IRouteDao routeDao = new RouteDaoImpl();

    @Override
    public Map<String, List<Route>> routeCareChoose() {
        //定义Map<String,List<Route>>对象用于接收结果
        Map<String,List<Route>> map = new HashMap<String,List<Route>>();
        //调用数据方法类RouteDao方法获取人气旅游前4个产品
        List<Route> popularityList = routeDao.getPopularityRouteList();
        map.put("popularity",popularityList);
        //调用数据方法类RouteDao方法获取最新旅游前4个产品
        List<Route> newestList = routeDao.getNewestRouteList();
        map.put("newest",newestList);
        //调用数据方法类RouteDao方法获取主题旅游前4个产品
        List<Route> themeList = routeDao.getThemeRouteList();
        map.put("theme",themeList);
        return map;
    }
}
