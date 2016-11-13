package {daoPkName};

import {daoPkName}.model.DataModel;
import {daoPkName}.main.MetaData;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import {daoPkName}.logging.handler.LoggingHandler;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.sql.SQLException;

import org.apache.camel.CamelContext;
import org.apache.camel.component.mybatis.MyBatisComponent;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class DataAccessManager {

    	static SqlSession sqlSession = null;
	static SqlSessionFactory sessionFactory = null;
	private static CamelContext camelContext;
	static MyBatisComponent component = null;
static String url;
	static String userName;
	static String password;
public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		DataAccessManager.url = url;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		DataAccessManager.userName = userName;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		DataAccessManager.password = password;
	}
  
public DataAccessManager(CamelContext camelContext) {
		this.camelContext = camelContext;
		initCamel();

	}
public DataAccessManager(CamelContext camelContext, String url, String userName,
			String password) {
		this.camelContext = camelContext;		
		this.url = url;
		this.userName = userName;
		this.password = password;
		initCamel();

	}

    public static SqlSessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            DataAccessManager.reloadSQLMap();
        }
        return sessionFactory;
    }

public static SqlSession getSqlSession() {
		return sqlSession;
	}

	public static void setSqlSession(SqlSession sqlSession) {
		DataAccessManager.sqlSession = sqlSession;
	}

	public static void setSessionFactory(SqlSessionFactory sessionFactory) {
		DataAccessManager.sessionFactory = sessionFactory;
	}

      public static void reloadSQLMap() {
        if (sqlMapper != null) {
            try {
                if (sqlMapper.getCurrentConnection() != null) {
                    sqlMapper.getCurrentConnection().close();
                }
            } catch (SQLException ex) {
                LoggingHandler.logError(MetaData.LOGGER_NAME, "current connecting close.. - ", ex);
            } catch (Exception ex) {
                LoggingHandler.logError(MetaData.LOGGER_NAME, "current connecting close.. - ", ex);
            }
        }
        sqlMapper = null;
        DataAccessManager.init();

    }
public static void initCamel() {
		try {
			if (sqlSession == null) {

				CamelMyBatisComponent component = camelContext.getComponent(
						"mybatis", CamelMyBatisComponent.class);

				LoggingHandler.logDebug(MetaData.LOGGER_NAME,
						"component = ", component.getConfigurationUri());
				sessionFactory = component.getSqlSessionFactory();
				sqlSession = sessionFactory.openSession();
			}
		} catch (Exception ex) {
			LoggingHandler.logError(MetaData.LOGGER_NAME, "", ex);
			// ex.printStackTrace();
		}
	}
	/*
    public static void init() {
        try {
            if (sqlMapper == null) {
                LoggingHandler.logInfo(MetaData.LOGGER_NAME, "init the sql map");
                Reader reader = Resources.getResourceAsReader("CMSSqlMapConfig.xml");
                sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
                reader.close();
                LoggingHandler.logInfo(MetaData.LOGGER_NAME, "done init the sql map");
                LoggingHandler.logInfo(MetaData.LOGGER_NAME, "close IS");
            }
        } catch (IOException e) {
            LoggingHandler.logError(MetaData.LOGGER_NAME, "connecting.. - ", e);        
        } catch (Exception e) {
            LoggingHandler.logError(MetaData.LOGGER_NAME, "connecting.. - ", e);
       
        }
    }*/
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
            sqlSession.clearCache();
        //DataAccessManager.selectOne("cacheClearQuery");
        } catch (Exception ex) {
            //DataAccessManager.reloadSQLMap();
            sqlSession.clearCache();
            LoggingHandler.logError(MetaData.LOGGER_NAME, "first Attemp", ex);
            try {
                sqlSession.clearCache();
            //DataAccessManager.selectOne("cacheClearQuery");
            } catch (Exception exp) {
                LoggingHandler.logError(MetaData.LOGGER_NAME, "second Attemp", exp);
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
		SqlSession sqlSession = DataAccessManager.getSessionFactory()
				.openSession();
        try {
            result = sqlSession.selectList(queryName);
        } catch (Exception ex) {

            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(MetaData.LOGGER_NAME, "first Attemp", ex);
            try {
                result = sqlSession.selectList(queryName);
            } catch (Exception exp) {
                LoggingHandler.logError(MetaData.LOGGER_NAME, "second Attemp", exp);
            }
        } finally {
			sqlSession.close();
		}
        return copyList(result);
    }

    public static List queryForList(String queryName, Object param) throws SQLException {
        List result = null;
		SqlSession sqlSession = DataAccessManager.getSessionFactory()
				.openSession();
        try {
            result = sqlSession.selectList(queryName, param);
        } catch (Exception ex) {

            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(MetaData.LOGGER_NAME, "first Attemp", ex);
            try {
                result = sqlSession.selectList(queryName, param);
            } catch (Exception exp) {
                LoggingHandler.logError(MetaData.LOGGER_NAME, "second Attemp", exp);
                exp.printStackTrace();
            }
        } finally {
			sqlSession.close();
		}
        return copyList(result);
    }

    public static List queryForList(String queryName, int arg1, int arg2) throws SQLException {
        List result = null;
		SqlSession sqlSession = DataAccessManager.getSessionFactory()
				.openSession();
        try {
            result = sqlSession.selectList(queryName, arg1, arg2);
        } catch (Exception ex) {

            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(MetaData.LOGGER_NAME, "first Attemp", ex);
            try {
                result = sqlSession.selectList(queryName, arg1, arg2);
            } catch (Exception exp) {
                LoggingHandler.logError(MetaData.LOGGER_NAME, "second Attemp", exp);
            }
        } finally {
			sqlSession.close();
		}
        return copyList(result);
    }

    public static List queryForList(String queryName, Object param, int arg1, int arg2) throws SQLException {
        List result = null;
		SqlSession sqlSession = DataAccessManager.getSessionFactory()
				.openSession();
        try {
            result = sqlSession.selectList(queryName, param, arg1, arg2);
        } catch (Exception ex) {

            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(MetaData.LOGGER_NAME, "first Attemp", ex);
            try {
                result = sqlSession.selectList(queryName, param, arg1, arg2);
            } catch (Exception exp) {
                LoggingHandler.logError(MetaData.LOGGER_NAME, "second Attemp", exp);
            }
        } finally {
			sqlSession.close();
		}
        return copyList(result);
    }

    public static Object queryForObject(String queryName) throws SQLException {
        Object result = null;
		SqlSession sqlSession = DataAccessManager.getSessionFactory()
				.openSession();
        try {
            result = sqlSession.selectOne(queryName);
        } catch (Exception ex) {

            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(MetaData.LOGGER_NAME, "first Attemp", ex);
            try {
                result = sqlSession.selectOne(queryName);
            } catch (Exception exp) {
                LoggingHandler.logError(MetaData.LOGGER_NAME, "second Attemp", exp);
            }
        } finally {
			sqlSession.close();
		}
        return copyObject(result);
    }

    public static Object queryForObject(String queryName, Object param) throws SQLException {
        Object result = null;
		SqlSession sqlSession = DataAccessManager.getSessionFactory()
				.openSession();
        try {
            result = sqlSession.selectOne(queryName, param);
        } catch (Exception ex) {

            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(MetaData.LOGGER_NAME, "first Attemp", ex);
            try {
                sqlSession.selectOne(queryName, param);
            } catch (Exception exp) {
                LoggingHandler.logError(MetaData.LOGGER_NAME, "second Attemp", exp);
            }
        } finally {
			sqlSession.close();
		}
        return copyObject(result);
    }

    public static Object queryForObject(String queryName, Object param1, Object param2) throws SQLException {
        Object result = null;
		SqlSession sqlSession = DataAccessManager.getSessionFactory()
				.openSession();
        try {
            result = sqlSession.selectOne(queryName, param1, param2);
        } catch (Exception ex) {

            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(MetaData.LOGGER_NAME, "first Attemp", ex);
            try {
                result =sqlSession.selectOne(queryName, param1, param2);
            } catch (Exception exp) {
                LoggingHandler.logError(MetaData.LOGGER_NAME, "second Attemp", exp);
            }
        } finally {
			sqlSession.close();
		}
        return copyObject(result);
    }
	

	public static int delete(String deleteStatment, Object param) {
		int rows = 0;
		SqlSession sqlSession = DataAccessManager.getSessionFactory()
				.openSession();
		try {
			if (param != null) {
				rows = sqlSession.delete(deleteStatment, param);
			} else {
				rows = sqlSession.delete(deleteStatment);
			}
			sqlSession.commit();
		} catch (Exception ex) {
			// DataAccessManager.reloadSQLMap();
			LoggingHandler.logError(MetaData.LOGGER_NAME, "first attemp ", ex);
			try {
				if (param != null) {
					rows = sqlSession.delete(deleteStatment, param);
				} else {
					rows = sqlSession.delete(deleteStatment);
				}
				sqlSession.commit();
			} catch (Exception exp) {
				LoggingHandler.logError(MetaData.LOGGER_NAME, "second attemp ",
						exp);

			}
		}
		return rows;
	}

	public static Integer insert(String insertStatment, Object paramter) {
		SqlSession sqlSession = DataAccessManager.getSessionFactory()
				.openSession();
		Integer rows = 0;
		if (insertStatment == null || insertStatment.isEmpty()) {
			insertStatment = "company_insertSelective";
		}
		try {
			String transId = "";

			if (paramter != null) {
				if (sqlSession != null) {
					LoggingHandler.logInfo(MetaData.LOGGER_NAME,
							"logging in DB ", insertStatment);

					rows = sqlSession.insert(insertStatment, paramter);
					sqlSession.commit();
					LoggingHandler.logInfo(MetaData.LOGGER_NAME, "inserted ",
							String.valueOf(rows));
				}
			} else {
				LoggingHandler.logInfo(MetaData.LOGGER_NAME,
						"logging in DB failed empty object ");
			}

		} catch (Exception ex) {
			LoggingHandler.logError(MetaData.LOGGER_NAME, "", ex);
		} finally {
			sqlSession.close();
		}
		return rows;
	}

	public static Integer update(String updateStatment, Object paramter) {
		SqlSession sqlSession = DataAccessManager.getSessionFactory()
				.openSession();
		Integer rows = 0;
		try {

			if (paramter != null) {
				if (sqlSession != null) {
					LoggingHandler.logInfo(MetaData.LOGGER_NAME,
							"logging in DB ", updateStatment);

					rows = sqlSession.update(updateStatment, paramter);
					sqlSession.commit();
					LoggingHandler.logInfo(MetaData.LOGGER_NAME, "inserted ",
							String.valueOf(rows));
				}
			} else {
				LoggingHandler.logInfo(MetaData.LOGGER_NAME,
						"logging in DB failed empty object ");
			}

		} catch (Exception ex) {
			LoggingHandler.logError(MetaData.LOGGER_NAME, "", ex);
		} finally {
			sqlSession.close();
		}
		return rows;
	}

	
	public void close() {
		if (this.sqlSession != null) {
			try {
				this.sqlSession.close();
			} catch (Exception ex) {

			}
		}
	}
}
