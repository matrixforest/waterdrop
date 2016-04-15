package com.matrixdroplet.waterdrop.jdbc;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.matrixdroplet.waterdrop.ioc.annotation.Autowired;
import com.matrixdroplet.waterdrop.ioc.annotation.Component;
import com.matrixdroplet.waterdrop.jdbc.pool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by li on 2016/4/15.
 */
@Component
public class JdbcTemplate {
    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplate.class);
    @Autowired
    private ConnectionPool connectionPool;

    public List<Map<String, Object>> select(String sql) {
        return select(sql, null);
    }

    public List<Map<String, Object>> select(String sql, Object[] args) {
        Preconditions.checkNotNull(sql);
        Connection connection = connectionPool.getConnection();
        List<Map<String, Object>> list = Lists.newArrayList();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args);
                }
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int cols_len = metaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i < cols_len; i++) {
                    String cols_name = metaData.getColumnName(i + 1);
                    Object cols_value = resultSet.getObject(cols_name);
                    if (cols_value == null) {
                        cols_value = "";
                    }
                    map.put(cols_name, cols_value);
                }
                list.add(map);
            }

        } catch (SQLException e) {
            logger.warn("数据库查询失败[{}]", e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public <T> List<T> findMoreRefResult(String sql, List<Object> params,
                                         Class<T> cls) {
        List<T> list = new ArrayList<T>();
        int index = 1;
        Connection connection = connectionPool.getConnection();
        try {

            PreparedStatement pstmt = connection.prepareStatement(sql);
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(index++, params.get(i));
                }
            }
            ResultSet resultSet = pstmt.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int cols_len = metaData.getColumnCount();
            while (resultSet.next()) {
                T resultObject = cls.newInstance();
                for (int i = 0; i < cols_len; i++) {
                    String cols_name = metaData.getColumnName(i + 1);
                    Object cols_value = resultSet.getObject(cols_name);
                    if (cols_value == null) {
                        cols_value = "";
                    }
                    Field field = cls.getDeclaredField(cols_name);
                    field.setAccessible(true);
                    field.set(resultObject, cols_value);
                }
                list.add(resultObject);
            }
        } catch (Exception e) {
            logger.warn("数据库查询失败[{}]", e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public boolean update(String sql, List<Object> params) {
        boolean flag = false;
        int result = -1;
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            int index = 1;
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(index++, params.get(i));
                }
            }
            result = pstmt.executeUpdate();
            flag = result > 0 ? true : false;
        } catch (Exception e) {
            logger.warn("数据库更新操作失败[{}]", e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return flag;
    }
}
