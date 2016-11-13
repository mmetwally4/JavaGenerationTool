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
		return DataAccessManager.delete("{tblNamelow}_custom", whereClause);
	}
	
	public static Integer insertSelective(String insertStatment, {className} paramter) {
		if(insertStatment == null || insertStatment.isEmpty()){
			insertStatment = "{tblNamelow}_insertSelective";
		}
		return DataAccessManager.insert(insertStatment, paramter);
	}

	
	public static Integer updateSelective(String updateStatment, {className} paramter) {
		if(updateStatment == null || updateStatment.isEmpty()){
			updateStatment = "{tblNamelow}_updateByPrimaryKeySelective";
		}
		return DataAccessManager.update(updateStatment, paramter);
	}

    
    public static int updateByPrimaryKeySelective({className} record) {        
		return DataAccessManager.update("{tblNamelow}_updateByPrimaryKeySelective", record);
    }
}
