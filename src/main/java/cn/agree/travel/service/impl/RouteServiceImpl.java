package cn.agree.travel.service.impl;

import cn.agree.travel.constant.Constant;
import cn.agree.travel.dao.IRouteDao;
import cn.agree.travel.dao.impl.RouteDaoImpl;
import cn.agree.travel.model.PageBean;
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

    @Override
    public PageBean<Route> findPageBean(String cid, int curPage, String keyword) {
        // 创建PageBean
        PageBean<Route> pageBean = new PageBean<Route>();
        // 设置当前页
        pageBean.setCurPage(curPage);
        // 每页条数自己设定
        int pageSize = Constant.ROUTE_PAGESIZE;
        pageBean.setPageSize(pageSize);
        // 设置总条数
        Long totalSize = routeDao.getCountByCid(cid, keyword);
        pageBean.setTotalSize(totalSize);

        // 设置多少页
        Long totalPage = (totalSize % pageSize==0) ? (totalSize/pageSize) : (totalSize/pageSize + 1);
        pageBean.setTotalPage(totalPage);

        // 设置每一页的数据
        List<Route> routes = routeDao.findPageRoutes(curPage, cid, pageSize, keyword);

        pageBean.setList(routes);

        return pageBean;
    }
}
