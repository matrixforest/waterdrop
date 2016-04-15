package com.matrixdroplet.waterdrop.jdbc.pool;

import com.matrixdroplet.waterdrop.ioc.annotation.Component;
import com.matrixdroplet.waterdrop.ioc.config.Config;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by li on 2016/4/15.
 */
@Component
public class ConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private DataSource dataSource;

    public void init(Config config) {
        Properties properties = new Properties();
        Map<String, String> databaseMap = config.getDatabase();
        properties.put("driverClassName", databaseMap.get("driver"));
        properties.put("url", databaseMap.get("url"));
        properties.put("username", databaseMap.get("username"));
        properties.put("password", databaseMap.get("password"));
        try {
            dataSource = BasicDataSourceFactory.createDataSource(properties);
            Connection connection = dataSource.getConnection();
            if (connection != null) {
                logger.info("数据库连接池初始化成功");
                connection.close();
            }else {
                throw new Exception();
            }
        } catch (Exception e) {
            logger.warn("数据库连接池初始化失败[{}]", e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.warn("获取数据库连接失败[{}]",e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}
