package cn.agree.travel.service.impl;

import cn.agree.travel.constant.Constant;
import cn.agree.travel.dao.IFavoriteDao;
import cn.agree.travel.dao.IRouteDao;
import cn.agree.travel.dao.impl.FavoriteDaoImpl;
import cn.agree.travel.dao.impl.RouteDaoImpl;
import cn.agree.travel.model.Favorite;
import cn.agree.travel.model.PageBean;
import cn.agree.travel.model.Route;
import cn.agree.travel.model.User;
import cn.agree.travel.service.IFavoriteService;
import cn.agree.travel.util.JDBCUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoriteServiceImpl implements IFavoriteService {

    private IFavoriteDao favoriteDao = new FavoriteDaoImpl();
    private IRouteDao routeDao = new RouteDaoImpl();

    @Override
    public Favorite isFavorite(String rid, User user) {
        Favorite favorite = favoriteDao.findFavorite(rid, user);
        return favorite;
    }

    @Override
    public boolean addFavorite(User user, String rid) {
        // 使用事务 控制添加收藏的两步SQL
        DataSource dataSource = JDBCUtil.getDataSource();
        // 创建JdbcTemplate对象
        JdbcTemplate template = new JdbcTemplate(dataSource);
        // 启动事务管理器(获取datasource操作数据库连接对象并绑定到当前线程中)
        TransactionSynchronizationManager.initSynchronization();
        // 获取连接
        Connection connection = DataSourceUtils.getConnection(dataSource);

        boolean flag = false;
        try {
            // 开启事务
            connection.setAutoCommit(false);
            // 在tab_favorite中添加一条记录
            favoriteDao.addFavorite(rid, user, template);
            // 在tab_route中把这个count+1
            favoriteDao.updateRoute(rid, template);
            // 没异常 提交
            connection.commit();
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
            // 出现了异常 滚
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            // 释放当前线程和连接对象的绑定
            TransactionSynchronizationManager.clearSynchronization();
            // 还原connection的模式
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return flag;
    }

    @Override
    public int findCount(String rid) {
        int count = 0;
        try {
            count = favoriteDao.findCount(rid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public PageBean<Favorite> findMyFavorite(User user, int curPage) throws Exception {
        // 创建PageBean
        PageBean<Favorite> pageBean = new PageBean<>();
        // 设置pagebean
        pageBean.setCurPage(curPage);
        int pageSize = Constant.ROUTE_PAGESIZE;
        pageBean.setPageSize(pageSize);
        // 获取当前用户所有的收藏记录
        Long totalSize = favoriteDao.findMyFavoriteCount(user);
        pageBean.setTotalSize(totalSize);
        // 设置多少页
        Long totalPage = (totalSize % pageSize==0) ? (totalSize/pageSize) : (totalSize/pageSize + 1);
        pageBean.setTotalPage(totalPage);

        // 调用dao 获取当前页的数据
        List<Map<String, Object>> maps = favoriteDao.findPageFavorites(user, curPage, pageSize);
        // 用一个集合放favorite
        List<Favorite> favorites = new ArrayList<>();
        // 遍历
        for (Map<String, Object> map : maps) {
            Favorite favorite = new Favorite();
            BeanUtils.populate(favorite, map); // 这一步其实只设置好了date
            // 还需要user和router
            favorite.setUser(user);

            Route route = new Route();
            BeanUtils.populate(route, map);
            favorite.setRoute(route);

            favorites.add(favorite);

        }
        pageBean.setList(favorites);
        return pageBean;

    }

    @Override
    public PageBean<Route> findFavoriteRank(int curPage, String rname, String min, String max) {
        // 创建PageBean
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setCurPage(curPage);
        int pageSize = Constant.ROUTE_PAGESIZE;
        long totalSize = routeDao.findRouteCount(rname, min, max);
        pageBean.setTotalSize(totalSize);
        Long totalPage = (totalSize % pageSize==0) ? (totalSize/pageSize) : (totalSize/pageSize + 1);
        pageBean.setTotalPage(totalPage);

        // 设置每页的数据集合
        List<Route> routes = routeDao.findFavoriteRankRoutes(curPage,pageSize,rname,min,max);
        pageBean.setList(routes);

        return pageBean;
    }
}
