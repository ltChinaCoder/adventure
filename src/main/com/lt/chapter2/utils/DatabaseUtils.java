package com.lt.chapter2.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class DatabaseUtils {
    private static final String username;
    private static final String password;
    private static final String jdbcUrl;
    private static final String jdbcDriver;
    private static final QueryRunner queryRunner = new QueryRunner();
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL;
    private static final BasicDataSource BASIC_DATA_SOURCE;

    static {

        Properties properties = ProUtils.loadProps("jdbc.properties");
        username = properties.getProperty("jdbc.username");
        password = properties.getProperty("jdbc.password");
        jdbcUrl = properties.getProperty("jdbc.url");
        jdbcDriver = properties.getProperty("jdbc.driver");
        CONNECTION_THREAD_LOCAL = new ThreadLocal<Connection>();
        BASIC_DATA_SOURCE = new BasicDataSource();
        BASIC_DATA_SOURCE.setUsername(username);
        BASIC_DATA_SOURCE.setPassword(password);
        BASIC_DATA_SOURCE.setUrl(jdbcUrl);
        BASIC_DATA_SOURCE.setDriverClassName(jdbcDriver);
    }

    public static Connection getConnenction() {
        Connection connection = null;
        connection = CONNECTION_THREAD_LOCAL.get();
        if (connection == null) {
            try {
                connection = BASIC_DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                log.error(" getconnection ", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_THREAD_LOCAL.set(connection);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        if (connection == null) return;
        try {
            if (!connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            log.error(" closeconnection ", e);
            throw new RuntimeException(e);
        } finally {
            CONNECTION_THREAD_LOCAL.remove();
        }
    }

    public static <T> T getBean(Class<T> tClass, String sql, Object... params) {
        T t = null;
        try {
            Connection connenction = getConnenction();
            t = queryRunner.query(connenction, sql, new BeanHandler<T>(tClass, new BasicRowProcessor(new MyBeanProcessor(true))), params);
        } catch (SQLException e) {
            log.error("query beanlist error", e);
            throw new RuntimeException(e);
        }
        return t;
    }

    public static <T> T getBeanById(Class<T> tClass, long id) {
        T t = null;
        try {
            Object[] params = {id};
            Connection connenction = getConnenction();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select * from ").append(getTableName(tClass));
            stringBuilder.append(" where ").append(getIdFiled(tClass)).append(" =?");
            t = queryRunner.query(connenction, stringBuilder.toString(), new BeanHandler<T>(tClass, new BasicRowProcessor(new MyBeanProcessor(true))), params);
        } catch (SQLException e) {
            log.error("query beanlist error", e);
            throw new RuntimeException(e);
        }
        return t;
    }

    public static <T> List<T> getList(Class<T> tClass, String sql, Object... params) {
        List<T> ts = null;
        try {
            Connection connenction = getConnenction();
            ts = queryRunner.query(connenction, sql, new BeanListHandler<T>(tClass, new BasicRowProcessor(new MyBeanProcessor(true))), params);
        } catch (SQLException e) {
            log.error("query beanlist error", e);
            throw new RuntimeException(e);
        }
        return ts;
    }

    public static <T> List<T> getList(Class<T> tClass, String sql) {
        return getList(tClass, sql, null);
    }

    public static Map<String, String> getColumnToPropertyOverrides() {
        Map<String, String> stringStringMap = new HashMap<String, String>();
        stringStringMap.put("consumer_id", "consumerId");
        stringStringMap.put("consumer_name", "consumerName");
        stringStringMap.put("consumer_contact", "consumerContact");
        stringStringMap.put("consumer_telephone", "consumerTelephone");
        stringStringMap.put("consumer_email", "consumerEmail");
        stringStringMap.put("consumer_remark", "consumerRemark");
        return stringStringMap;
    }

    public static List<Map<String, Object>> mulQuery(String sql, Object... params) {
        List<Map<String, Object>> ts = null;
        try {
            Connection connenction = getConnenction();
            ts = queryRunner.query(connenction, sql, new MapListHandler(new BasicRowProcessor(new MyBeanProcessor(true))), params);
        } catch (SQLException e) {
            log.error("query beanlist error", e);
            throw new RuntimeException(e);
        }
        return ts;
    }

    public static int infectRow(String sql, Object... params) {
        int res = -1;
        try {
            Connection connenction = getConnenction();
            res = queryRunner.update(connenction, sql, params);
        } catch (SQLException e) {
            log.error("query beanlist error", e);
            throw new RuntimeException(e);
        }
        return res;
    }

    public static <T> boolean deleteEntityById(Class<T> tClass, long id) {
        String sql = "delete from " + getTableName(tClass) + " where " + getIdFiled(tClass) + " = ?";
        return infectRow(sql, new Object[]{id}) == 1;
    }

    public static <T> boolean updateEntity(Class<T> tClass, long id, Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("update ").append(getTableName(tClass)).append(" set ");
        int size = map.size();
        for (String s : map.keySet()) {
            if (size == 1)
                stringBuilder.append(getColumnNameByFileName(s)).append("=?");
            else
                stringBuilder.append(getColumnNameByFileName(s)).append("=?").append(",");
            size--;
        }
        stringBuilder.append(" where ").append(getIdFiled(tClass)).append("=?");
        Object[] objects = map.values().toArray();
        Object[] objects1 = Arrays.copyOf(objects, objects.length + 1);
        objects1[objects.length] = id;
        return infectRow(stringBuilder.toString(), objects1) == 1;
    }

    public static <T> boolean insertEntity(Class<T> tClass, Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("insert into ").append(getTableName(tClass)).append("(");
        int size = map.size();
        for (String s : map.keySet()) {
            if (size == 1)
                stringBuilder.append(getColumnNameByFileName(s));
            else
                stringBuilder.append(getColumnNameByFileName(s)).append(",");
            size--;
        }
        size = map.size();
        stringBuilder.append(") values (");
        for (; size >= 1; size--) {
            if (size == 1)
                stringBuilder.append("?");
            else
                stringBuilder.append("?").append(",");
        }
        stringBuilder.append(")");
        return infectRow(stringBuilder.toString(), map.values().toArray()) == 1;
    }

    private static <T> String getTableName(Class<T> tClass) {
        return tClass.getSimpleName().toLowerCase();
    }

    public static String getFileNameByColumnName(String columnName) {
        String[] split = columnName.split("_");
        StringBuilder sb = new StringBuilder();
        if (split.length > 0)
            sb.append(split[0]);
        for (int i = 1; i < split.length; i++) {
            sb.append(String.valueOf(split[i].charAt(0)).toUpperCase()).append(split[i].substring(1));
        }
        return sb.toString();
    }

    public static String getColumnNameByFileName(String filename) {
        char[] chars = filename.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char aChar : chars) {
            if (Character.isUpperCase(aChar))
                sb.append("_").append(Character.toLowerCase(aChar));
            else
                sb.append(aChar);
        }
        return sb.toString();
    }

    public static <T> String getIdFiled(Class<T> tClass) {
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            if (name.equals("id"))
                return name;
            else if (name.contains(tClass.getSimpleName().toLowerCase()) && name.contains("Id"))
                return getColumnNameByFileName(name);
        }
        return null;
    }
}
