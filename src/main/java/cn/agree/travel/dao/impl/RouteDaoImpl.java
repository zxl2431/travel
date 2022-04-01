package cn.agree.travel.dao.impl;

import cn.agree.travel.dao.IRouteDao;
import cn.agree.travel.model.Route;
import cn.agree.travel.model.RouteImg;
import cn.agree.travel.util.JDBCUtil;
import cn.agree.travel.util.StringUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public long getCountByCid(String cid, String keyword) {
        //如果cid和keyword都不为空，我们的SQL语句该怎么写
        /*String sql = "select count(*) from tab_route where cid=? and rname like ?";*/
        //如果cid不为空，但是keyword为空，我们怎么写SQL语句
        /*String sql = "select count(*) from tab_route where cid=?";*/
        //如果keyword不为空，但是cid为空，我们怎么写SQL语句
        /*String sql = "select count(*) from tab_route where rname LIKE ?";*/
        //如果都为空，我们怎么写SQL语句
        /*String sql = "select count(*) from tab_route";*/

        String sql = "select count(*) from tab_route where 1=1";
        // 声明一个集合,用于存放问号处的参数
        List<Object> params = new ArrayList<>();
        if (!StringUtil.isEmpty(cid)) {
            sql += " and cid = ?";
            params.add(cid);
        }

        if (!StringUtil.isEmpty(keyword)) {
            sql += " and rname like ?";
            params.add("%"+keyword+"%");
        }

        Long totalSize = jdbcTemplate.queryForObject(sql, Long.class, params.toArray());
        return totalSize;
    }

    @Override
    public List<Route> findPageRoutes(int curPage, String cid, int pageSize, String keyword) {
        //此时，判断cid和keyword是否为空
        String sql = "select * from tab_route where 1=1";
        List<Object> params = new ArrayList<>();
        //判断cid不为空
        if (!StringUtil.isEmpty(cid)) {
            sql += " and cid=?";
            params.add(cid);
        }
        //判断keyword不为空
        if (!StringUtil.isEmpty(keyword)) {
            sql += " and rname like ?";
            params.add("%"+keyword+"%");
        }
        //还得拼接，分页的语句"limit"
        sql += " limit ?,?";
        params.add((curPage-1)*pageSize);
        params.add(pageSize);
        List<Route> routes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
        return routes;
    }

    @Override
    public Map<String, Object> getRouteByRid(String rid) {
        String sql = "select * from tab_route r,tab_category c,tab_seller s where r.cid=c.cid and r.sid=s.sid and r.rflag=? and r.rid=?";
        Map<String, Object> map = null;

        try {
            map = jdbcTemplate.queryForMap(sql, "1", rid);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<RouteImg> getRouteImgsByRid(String rid) {
        String sql = "select * from tab_route_img where rid=?";

        List<RouteImg> routeImgs = null;
        try {
            routeImgs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return routeImgs;
    }


}
