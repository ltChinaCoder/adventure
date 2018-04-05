package com.lt.chapter2.utils;

import org.apache.commons.dbutils.BeanProcessor;

import java.beans.PropertyDescriptor;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
public class MyBeanProcessor extends BeanProcessor {
    private boolean openAutoCamel;
    private Map<String, String> columnToPropertyOverrides;

    public MyBeanProcessor(boolean openAutoCamel) {
        this.openAutoCamel = openAutoCamel;
        if (!openAutoCamel)
            columnToPropertyOverrides = new HashMap<String, String>();
    }

    public MyBeanProcessor(Map<String, String> columnToPropertyOverrides) {
        columnToPropertyOverrides = columnToPropertyOverrides;
    }

    @Override
    protected int[] mapColumnsToProperties(ResultSetMetaData rsmd, PropertyDescriptor[] props) throws SQLException {
        int cols = rsmd.getColumnCount();
        int[] columnToProperty = new int[cols + 1];
        Arrays.fill(columnToProperty, -1);

        for (int col = 1; col <= cols; ++col) {
            String columnName = rsmd.getColumnLabel(col);
            if (null == columnName || 0 == columnName.length()) {
                columnName = rsmd.getColumnName(col);
            }
            String propertyName = null;
            if (openAutoCamel) {

                propertyName = DatabaseUtils.getFileNameByColumnName(columnName);
            } else {
                propertyName = columnToPropertyOverrides.get(columnName);
                if (propertyName == null) {
                    propertyName = columnName;
                }
            }

            for (int i = 0; i < props.length; ++i) {
                if (propertyName.equalsIgnoreCase(props[i].getName())) {
                    columnToProperty[col] = i;
                    break;
                }
            }
        }

        return columnToProperty;
    }
}
