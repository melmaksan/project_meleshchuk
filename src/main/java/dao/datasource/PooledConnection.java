package dao.datasource;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class PooledConnection {

    private static final String DB_BUNDLE = "mysqldb";
    private static final String DB_URL = "url";
    private static final String DB_DRIVER = "driver";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "password";

    private static DataSource instance;

    public static synchronized DataSource getInstance() {
        if(instance == null) {
            instance = initDataSource();
        }
        return instance;
    }

    private PooledConnection() {
    }

    private static DataSource initDataSource() {
        ResourceBundle bundle = ResourceBundle.getBundle(DB_BUNDLE);
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(bundle.getString(DB_DRIVER));
        dataSource.setUrl(bundle.getString(DB_URL));
        dataSource.setUsername(bundle.getString(DB_USER));
        dataSource.setPassword(bundle.getString(DB_PASSWORD));
        return dataSource;
    }

}
