package {daoPkName}.base;

import {daoPkName}.DataAccessManager;
import {daoPkName}.model.*;
import {daoPkName}.main.MetaData;
import org.apache.ibatis.session.SqlSession;
import {daoPkName}.logger.handler.LoggingHandler;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class {className}DAOBase {

    public {className}DAOBase() {
    }

    public static List<{className}> selectAll() {
        try {
            return DataAccessManager.queryForList("{tblNamelow}_selectAll");
        } catch (Exception ex) {
            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(MetaData.LOGGER_NAME,"first attemp ", ex);
            try {
                return DataAccessManager.queryForList("{tblNamelow}_selectAll");
            } catch (Exception exp) {
                LoggingHandler.logError(MetaData.LOGGER_NAME,"second attemp ", exp);
                return null;
            }
        }
    }


    public static List<{className}> search{className}(String whereClause) {
        try {
            return DataAccessManager.queryForList("{tblNamelow}_search",whereClause);
        } catch (Exception ex) {
            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(MetaData.LOGGER_NAME,"first attemp ", ex);
            try {
                return DataAccessManager.queryForList("{tblNamelow}_search",whereClause);
            } catch (Exception exp) {
                LoggingHandler.logError(MetaData.LOGGER_NAME,"second attemp ", exp);
                return null;
            }
        }
    }

	 public static Integer selectCount(){
        try {
			return (Integer) DataAccessManager.queryForObject("{tblNamelow}_count");
        } catch (Exception ex) {
            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(MetaData.LOGGER_NAME,"first attemp ", ex);
            try {
				return (Integer) DataAccessManager.queryForObject("{tblNamelow}_count");
            } catch (Exception exp) {
                LoggingHandler.logError(MetaData.LOGGER_NAME,"second attemp ", exp);
                return 0;
            }
        }
    }

    public static Integer selectMaxID(){
        try {
	        return (Integer) DataAccessManager.queryForObject("{tblNamelow}_max");
        } catch (Exception ex) {
            DataAccessManager.reloadSQLMap();
            LoggingHandler.logError(MetaData.LOGGER_NAME,"first attemp ", ex);
            try {
		        return (Integer) DataAccessManager.queryForObject("{tblNamelow}_max");
            } catch (Exception exp) {
                LoggingHandler.logError(MetaData.LOGGER_NAME,"second attemp ", exp);
                return 0;
            }
        }
    }
    {selectByProps} 
    {deleteByProps}
	
	public static int deleteCustom(String whereClause)  { 
		int rows = 0;
		SqlSession sqlSession = DataAccessManager.getSessionFactory()
				.openSession();
		try {
			rows = sqlSession.delete("{tblNamelow}_custom", whereClause);
		} catch (Exception ex) {
		    DataAccessManager.reloadSQLMap();
		    LoggingHandler.logError(MetaData.LOGGER_NAME,"first attemp ", ex);
		    try {
				rows = sqlSession.delete("{tblNamelow}_custom", whereClause);
		    } catch (Exception exp) {
			LoggingHandler.logError(MetaData.LOGGER_NAME,"second attemp ", exp);
			
		    }
		}
		return rows;
	   }
	
	public static Integer insertSelective(String insertStatment, {className} paramter) {
		SqlSession sqlSession = DataAccessManager.getSessionFactory()
				.openSession();
				Integer rows = 0;
				if(insertStatment == null || insertStatment.isEmpty()){
					insertStatment = "{tblNamelow}_insertSelective";
				}
		try {
			String transId = "";

			if (paramter != null) {
				if (sqlSession != null) {
					LoggingHandler.logInfo(MetaData.LOGGER_NAME,
							"logging in DB ", insertStatment);
					
					rows = sqlSession.insert(insertStatment, paramter);
					sqlSession.commit();
					LoggingHandler.logInfo(MetaData.LOGGER_NAME,
							"inserted ", String.valueOf(rows));
				}
			} else {
				LoggingHandler.logInfo(MetaData.LOGGER_NAME,
						"logging in DB failed empty object ",
						paramter.toString());
			}

		} catch (Exception ex) {
			LoggingHandler.logError(MetaData.LOGGER_NAME, "", ex);
		} finally {
			sqlSession.close();
		}
		return rows;
	}

	
	public static Integer updateSelective(String updateStatment, {className} paramter) {
		SqlSession sqlSession = DataAccessManager.getSessionFactory()
				.openSession();
				Integer rows = 0;
				if(updateStatment == null || updateStatment.isEmpty()){
					updateStatment = "{tblNamelow}_insertSelective";
				}
		try {			

			if (paramter != null) {
				if (sqlSession != null) {
					LoggingHandler.logInfo(MetaData.LOGGER_NAME,
							"logging in DB ", updateStatment);
					
					rows = sqlSession.update(updateStatment, paramter);
					sqlSession.commit();
					LoggingHandler.logInfo(MetaData.LOGGER_NAME,
							"inserted ", String.valueOf(rows));
				}
			} else {
				LoggingHandler.logInfo(MetaData.LOGGER_NAME,
						"logging in DB failed empty object ",
						paramter.toString());
			}

		} catch (Exception ex) {
			LoggingHandler.logError(MetaData.LOGGER_NAME, "", ex);
		} finally {
			sqlSession.close();
		}
		return rows;
	}

    
    public static int updateByPrimaryKeySelective({className} record) {
        int rows = 0;
		SqlSession sqlSession = DataAccessManager.getSessionFactory()
				.openSession();
        try {
            rows = sqlSession.update("{tblNamelow}_updateByPrimaryKeySelective", record);
        } catch (Exception ex) {
            DataAccessManager.reloadSQLMap();
			sqlSession = DataAccessManager.getSessionFactory()
				.openSession();
            LoggingHandler.logError(MetaData.LOGGER_NAME,"first attemp ", ex);
            try {
                rows = sqlSession.update("{tblNamelow}_updateByPrimaryKeySelective", record);
            } catch (Exception exp) {
                LoggingHandler.logError(MetaData.LOGGER_NAME,"second attemp ", exp);
            }
        }finally {
			sqlSession.close();
		}
        return rows;
    }
}
