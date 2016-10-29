package {daoPkName};

import {daoPkName}.model.DataModel;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.{contextpath}.setting.handler.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.ArrayList;

public class DataAccessManager {

    public static SqlMapClient sqlMapper;

    static {
    }

    public DataAccessManager() {
        DataAccessManager.init();
    }

    public static SqlMapClient getSqlMapper() {
        if (sqlMapper == null) {
            DataAccessManager.reloadSQLMap();
        }
        return sqlMapper;
    }

    public static void setSqlMapper(SqlMapClient sqlMap) {
        sqlMapper = sqlMap;
    }

    static {
        sqlMapper = null;
        DataAccessManager.init();
    }

    public static void reloadSQLMap() {
        if (sqlMapper != null) {
            try {
                if (sqlMapper.getCurrentConnection() != null) {
                    sqlMapper.getCurrentConnection().close();
                }
            } catch (SQLException ex) {
                LoggingHandler.logError(2, "", "current connecting close.. - ", ex);
            } catch (Exception ex) {
                LoggingHandler.logError(2, "", "current connecting close.. - ", ex);
            }
        }
        sqlMapper = null;
        DataAccessManager.init();

    }

    public static void init() {
        try {
            if (sqlMapper == null) {
                LoggingHandler.logInfo(2, "", "init the sql map");
                Reader reader = Resources.getResourceAsReader("CMSSqlMapConfig.xml");
                sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
                reader.close();
                LoggingHandler.logInfo(2, "", "done init the sql map");
                LoggingHandler.logInfo(2, "", "close IS");
            }
        } catch (IOException e) {
            LoggingHandler.logError(2, "", "connecting.. - ", e);
        //throw new RuntimeException("Something bad happened while building the SqlMapClient instance." + e, e);
        } catch (Exception e) {
            LoggingHandler.logError(2, "", "connecting.. - ", e);
        //throw new RuntimeException("Something bad happened while building the SqlMapClient instance." + e, e);
        }
    }
    public static String filterStr(String str) {
        str = str.trim();
        str = str.replace("%", "");
        str = str.replace("&", "-");
        str = str.replace("*", " ");
        str = str.replace("'", " ");
        str = str.replace("�", " ");
        str = str.replace("/", " ");
        str = str.replace("\\", " ");
        return str;
    }

    public static void clearCache() {

        try {
            DataAccessManager.getSqlMapper().flushDataCache();
        //DataAccessManager.queryForObject("cacheClearQuery");
        } catch (Exception ex) {
            //DataAccessManager.reloadSQLMap();
            DataAccessManager.getSqlMapper().flushDataCache();
            LoggingHandler.logError(2, "", "first Attemp", ex);
            try {
                DataAccessManager.getSqlMapper().flushDataCache();
            //DataAccessManager.queryForObject("cacheClearQuery");
            } catch (Exception exp) {
                LoggingHandler.logError(2, "", "second Attemp", exp);
            }
        }
    }

    /**
     * This method is used to validate if the passed string is a valid date compared by the format yyyy-mm-dd hh:mm:ss
     * @param date
     * @return boolean value representing the validity of the passed string
     */
    public static boolean isDateFormatValid(String date) {

        StringBuilder newDate = new StringBuilder();

        newDate.append(date.substring(0, 4));
        newDate.append("-");
        newDate.append(date.substring(4, 6));
        newDate.append("-");
        newDate.append(date.substring(6, 8));
        newDate.append(" ");
        newDate.append(date.substring(9, 11));
        newDate.append(":");
        newDate.append(date.substring(11, 13));
        newDate.append(":");
        newDate.append(date.substring(13, 15));

        return false;
    }

    public static String reformatDate(String date) {

        StringBuilder newDate = new StringBuilder();

        newDate.append(date.substring(0, 4));
        newDate.append("-");
        newDate.append(date.substring(4, 6));
        newDate.append("-");
        newDate.append(date.substring(6, 8));
        newDate.append(" ");
        newDate.append(date.substring(9, 11));
        newDate.append(":");
        newDate.append(date.substring(11, 13));
        newDate.append(":");
        newDate.append(date.substring(13, 15));

        return newDate.toString();
    }

    public static ArrayList copyList(ArrayList list) {
        ArrayList copy = new ArrayList();
        DataModel dm;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof String) {
                    copy.add(list.get(i).toString());
                } else if (list.get(i) instanceof Integer) {
                    copy.add(list.get(i));
                } else if (!(list.get(i) instanceof DataModel)) {
                    copy.add(list.get(i));
                } else {
                    dm = (DataModel) list.get(i);
                    copy.add(dm.copy());
                }
            }
        }
        return copy;

    }

    public static List copyList(List list) {
        List copy = new ArrayList();
        DataModel dm;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof String) {
                    copy.add(list.get(i).toString());
                } else if (list.get(i) instanceof Integer) {
                    copy.add(list.get(i));
                } else if (!(list.get(i) instanceof DataModel)) {
                    copy.add(list.get(i));
                } else {
                    dm = (DataModel) list.get(i);
                    copy.add(dm.copy());
                }
            }
        }
        return copy;

    }

    public static Object copyObject(Object input) {
        Object copy = null;
        DataModel dm;
        if (input != null) {
            if (!(input instanceof DataModel)) {
                copy = input;
            } else {
                dm = (DataModel) input;
                copy = dm.copy();
            }

        }
        return copy;

    }

    public static List queryForList(String queryName) throws SQLException {
        List result = null;
        try {
            result = DataAccessManager.getSqlMapper().queryForList(queryName);
        } catch (Exception ex) {

            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(2, "", "first Attemp", ex);
            try {
                result = DataAccessManager.getSqlMapper().queryForList(queryName);
            } catch (Exception exp) {
                LoggingHandler.logError(2, "", "second Attemp", exp);
            }
        }
        return copyList(result);
    }

    public static List queryForList(String queryName, Object param) throws SQLException {
        List result = null;
        try {
            result = DataAccessManager.getSqlMapper().queryForList(queryName, param);
        } catch (Exception ex) {

            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(2, "", "first Attemp", ex);
            try {
                result = DataAccessManager.getSqlMapper().queryForList(queryName, param);
            } catch (Exception exp) {
                LoggingHandler.logError(2, "", "second Attemp", exp);
                exp.printStackTrace();
            }
        }
        return copyList(result);
    }

    public static List queryForList(String queryName, int arg1, int arg2) throws SQLException {
        List result = null;
        try {
            result = DataAccessManager.getSqlMapper().queryForList(queryName, arg1, arg2);
        } catch (Exception ex) {

            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(2, "", "first Attemp", ex);
            try {
                result = DataAccessManager.getSqlMapper().queryForList(queryName, arg1, arg2);
            } catch (Exception exp) {
                LoggingHandler.logError(2, "", "second Attemp", exp);
            }
        }
        return copyList(result);
    }

    public static List queryForList(String queryName, Object param, int arg1, int arg2) throws SQLException {
        List result = null;
        try {
            result = DataAccessManager.getSqlMapper().queryForList(queryName, param, arg1, arg2);
        } catch (Exception ex) {

            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(2, "", "first Attemp", ex);
            try {
                result = DataAccessManager.getSqlMapper().queryForList(queryName, param, arg1, arg2);
            } catch (Exception exp) {
                LoggingHandler.logError(2, "", "second Attemp", exp);
            }
        }
        return copyList(result);
    }

    public static Object queryForObject(String queryName) throws SQLException {
        Object result = null;
        try {
            result = DataAccessManager.getSqlMapper().queryForObject(queryName);
        } catch (Exception ex) {

            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(2, "", "first Attemp", ex);
            try {
                result = DataAccessManager.getSqlMapper().queryForObject(queryName);
            } catch (Exception exp) {
                LoggingHandler.logError(2, "", "second Attemp", exp);
            }
        }
        return copyObject(result);
    }

    public static Object queryForObject(String queryName, Object param) throws SQLException {
        Object result = null;
        try {
            result = DataAccessManager.getSqlMapper().queryForObject(queryName, param);
        } catch (Exception ex) {

            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(2, "", "first Attemp", ex);
            try {
                result = DataAccessManager.getSqlMapper().queryForObject(queryName, param);
            } catch (Exception exp) {
                LoggingHandler.logError(2, "", "second Attemp", exp);
            }
        }
        return copyObject(result);
    }

    public static Object queryForObject(String queryName, Object param1, Object param2) throws SQLException {
        Object result = null;
        try {
            result = DataAccessManager.getSqlMapper().queryForObject(queryName, param1, param2);
        } catch (Exception ex) {

            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(2, "", "first Attemp", ex);
            try {
                result = DataAccessManager.getSqlMapper().queryForObject(queryName, param1, param2);
            } catch (Exception exp) {
                LoggingHandler.logError(2, "", "second Attemp", exp);
            }
        }
        return copyObject(result);
    }
}
