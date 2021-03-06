package cn.agree.travel.service;

import cn.agree.travel.model.PageBean;
import cn.agree.travel.model.Route;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface IRouteService {

    Map<String, List<Route>> routeCareChoose();

    PageBean<Route> findPageBean(String cid, int curPage, String keyword);

    Route getRouteByRid(String rid) throws Exception;
}
