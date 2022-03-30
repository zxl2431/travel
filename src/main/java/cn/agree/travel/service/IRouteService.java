package cn.agree.travel.service;

import cn.agree.travel.model.Route;

import java.util.List;
import java.util.Map;

public interface IRouteService {

    Map<String, List<Route>> routeCareChoose();
}
