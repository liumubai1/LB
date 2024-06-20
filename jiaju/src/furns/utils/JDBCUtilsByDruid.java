package furns.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtilsByDruid {
    private static DataSource dataSource;

    //定义属性ThreadLocal,这里存放一个Connection
    private static ThreadLocal<Connection> threadLocalConn = new ThreadLocal<>();

    static {
        Properties properties = new Properties();
        try {
            InputStream inputStream = JDBCUtilsByDruid.class.getClassLoader().getResourceAsStream("druid.properties");
            properties.load(inputStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection() {
        Connection connection = threadLocalConn.get();
        if (connection == null) {
            try {
                connection = dataSource.getConnection();
                connection.setAutoCommit(false); //将连接设置为手动提交
            } catch (SQLException e) {
                e.printStackTrace();
            }
            threadLocalConn.set(connection);
        }
        return connection;
    }

    //提交事务
    public static void commit() {
        Connection connection = threadLocalConn.get();
        if (connection != null) {
            try {
                connection.commit(); //提交事务
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close(); //关闭连接
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        threadLocalConn.remove();
    }

    //回滚(撤销和connection管理的操作：增删改)
    public static void rollback() {
        Connection connection = threadLocalConn.get();
        if (connection != null) {
            try {
                connection.rollback(); //回滚
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close(); //关闭连接
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        threadLocalConn.remove();
    }


    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {

                connection.close();
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
