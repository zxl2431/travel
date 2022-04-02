package cn.agree.travel.service.impl;

import cn.agree.travel.dao.IFavoriteDao;
import cn.agree.travel.dao.impl.FavoriteDaoImpl;
import cn.agree.travel.model.Favorite;
import cn.agree.travel.model.User;
import cn.agree.travel.service.IFavoriteService;
import cn.agree.travel.util.JDBCUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class FavoriteServiceImpl implements IFavoriteService {

    private IFavoriteDao favoriteDao = new FavoriteDaoImpl();

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
}
