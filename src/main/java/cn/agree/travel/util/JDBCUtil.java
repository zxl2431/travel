package cn.agree.travel.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class JDBCUtil {
    private static DataSource ds;
    static {
        Properties properties = new Properties();
        //将配置文件转换成字节输入流
        ClassLoader classLoader = JDBCUtil.class.getClassLoader();
        InputStream in = classLoader.getResourceAsStream("druid.properties");
        try {
            properties.load(in);
            //1.创建一个Druid连接池
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static DataSource getDataSource(){
        return ds;
    }
    /**
     * 获取连接对象的方法
     * @return
     */
    public static Connection getConnection() throws Exception {
        Connection conn = ds.getConnection();
        return conn;
    }

    /**
     * 关闭资源
     * @param conn
     * @param stm
     * @param rst
     * @throws SQLException
     */
    public static void close(Connection conn, Statement stm, ResultSet rst) throws SQLException {
        //为了避免空指针异常，那么我们在使用对象之前，先要判断对象是否为null
        if (rst != null){
            rst.close();
        }

        if (stm != null) {
            stm.close();
        }

        if (conn != null) {
            conn.close();
        }
    }
}
