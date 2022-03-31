package cn.agree.travel.dao.impl;

import cn.agree.travel.dao.IRouteDao;
import cn.agree.travel.model.Route;
import cn.agree.travel.util.JDBCUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteDaoImpl implements IRouteDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtil.getDataSource());

    @Override
    public List<Route> getPopularityRouteList() {
        //sql语句，rflag='1'筛选上架旅游线路，人气按照收藏数量降序
        String sql="SELECT * FROM tab_route WHERE rflag='1' ORDER BY COUNT DESC LIMIT 0,4";
        List<Route> routeList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class));
        return routeList;
    }

    @Override
    public List<Route> getNewestRouteList() {
        //sql语句，最新旅游线路根据上架时间降序获取前4条数据
        String sql="SELECT * FROM tab_route WHERE rflag='1' ORDER BY rdate DESC LIMIT 0,4";
        List<Route> routeList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class));
        return routeList;
    }

    @Override
    public List<Route> getThemeRouteList() {
        //sql语句，isThemeTour='1'为主题旅游线路
        String sql="SELECT * FROM tab_route WHERE rflag='1' AND isThemeTour='1' ORDER BY rdate DESC LIMIT 0,4";
        List<Route> routeList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class));
        return routeList;
    }

    @Override
    public long getCountByCid(String cid) {
        String sql = "select count(*) from tab_route where cid=? and rflag=?";
        Long totalSize = jdbcTemplate.queryForObject(sql, Long.class, cid, "1");
        return totalSize;
    }

    @Override
    public List<Route> findPageRoutes(int curPage, String cid, int pageSize) {
        String sql = "select * from tab_route where cid=? and rflag=? limit ?,?";
        List<Route> routeList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), cid, "1", (curPage - 1) * pageSize, pageSize);
        return routeList;
    }


}
