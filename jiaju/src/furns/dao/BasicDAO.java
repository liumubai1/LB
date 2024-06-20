package furns.dao;


import furns.utils.JDBCUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BasicDAO<T> {
    QueryRunner queryRunner = new QueryRunner();

    //增删改
    public int update(String sql, Object... params) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            int row = queryRunner.update(connection, sql, params);
            return row;
        } catch (SQLException e) {
            throw new RuntimeException(e);//将编译异常转换成运行异常抛出，调用者可以捕获也可以不捕获
        }
    }

    //返回多行多列
    public List<T> queryMulti(String sql, Class<T> clazz, Object... params) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            List<T> list = queryRunner.query(connection, sql, new BeanListHandler<T>(clazz), params);
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //返回单行多列
    public T querySingle(String sql, Class<T> clazz, Object... params) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            T obj = queryRunner.query(connection, sql, new BeanHandler<T>(clazz), params);
            return obj;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //返回单个元素
    public Object queryScalar(String sql, Object... params) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            Object value = queryRunner.query(connection, sql, new ScalarHandler(), params);
            return value;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
