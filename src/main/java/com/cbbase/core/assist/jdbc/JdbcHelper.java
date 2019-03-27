package com.cbbase.core.assist.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.cbbase.core.tools.StringUtil;

/**
 * 	不适用于大量并发
 * @author changbo
 * 
 */
public class JdbcHelper {

	private Connection conn = null;
	private boolean autoCommit = true;
	
	private JdbcHelper(Connection conn){
		this.conn = conn;
	}
	public static JdbcHelper getJdbcHelper(){
		return new JdbcHelper(JdbcConnection.getConnection());
	}
	public static JdbcHelper getJdbcHelper(Connection conn){
		return new JdbcHelper(conn);
	}
	public static JdbcHelper getJdbcHelper(String database){
		return new JdbcHelper(JdbcConnection.getConnection(database));
	}
	
	private void setAutoCommit(boolean autoCommit){
		try {
			this.autoCommit = autoCommit;
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void commit(){
		try {
			if(!conn.getAutoCommit()){
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void rollback(){
		try {
			if(!conn.getAutoCommit()){
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Map<String,Object>> query(String sql){
		return query(sql, array2List(null));
	}

	public List<Map<String,Object>> query(String sql, Object... params){
		return query(sql, array2List(params));
	}
	
	public List<Map<String,Object>> query(String sql, List<Object> params){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if(StringUtil.isEmpty(sql)){
			return list;
		}
		PreparedStatement ps = null;
	    try {
			ps = conn.prepareStatement(sql);
			setParameter(ps, params);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				list.add(getResultMap(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			closeStatement(ps);
			closeConnection();
		}
		
		return list;
	}
	
	public boolean executeBatch(String sql, List<List<Object>> list){
		if(StringUtil.isEmpty(sql)){
			return false;
		}
		PreparedStatement ps = null;
		try {
			setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			for(int i=0; list!=null && i<list.size(); i++){
				setParameter(ps, list.get(i));
				ps.addBatch();
			}
			ps.executeBatch();
			commit();
		} catch (SQLException e) {
			rollback();
			e.printStackTrace();
			return false;
		} finally{
			closeStatement(ps);
			closeConnection();
		}
		return true;
	}

	public boolean execute(String sql){
		return execute(sql, array2List(null));
	}

	public boolean execute(String sql, Object... params){
		return execute(sql, array2List(params));
	}

	public boolean execute(String sql, List<Object> params){
		if(StringUtil.isEmpty(sql)){
			return false;
		}
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			setParameter(ps, params);
			ps.execute();
		} catch (SQLException e) {
			rollback();
			e.printStackTrace();
			return false;
		} finally{
			closeStatement(ps);
			closeConnection();
		}
		return true;
	}

	
	public boolean call(String call){
		return call(call, null, null, null);
	}
	
	
	public boolean call(String sql, Map<Integer, Object> in){
		return call(sql, in, null, null);
	}
	
	/**
	 * {CALL demoSp(? , ?)}
	 * @param call
	 * @return
	 */
	public boolean call(String sql, Map<Integer, Object> in, Map<Integer, Integer> outType, Map<Integer, Object> outValue){
		if(StringUtil.isEmpty(sql)){
			return false;
		}
		CallableStatement cs = null;
		try {
			cs = conn.prepareCall(sql);
			if(in != null){
				for(Entry<Integer, Object> entry : in.entrySet()){
					cs.setObject(entry.getKey(), entry.getValue());
				}
			}
			if(outType != null){
				for(Entry<Integer, Integer> entry : outType.entrySet()){
					cs.registerOutParameter(entry.getKey(), entry.getValue());
				}
			}
			
			cs.execute();
			if(outValue != null && outType != null){
				for(Entry<Integer, Integer> entry : outType.entrySet()){
					outValue.put(entry.getKey(), cs.getObject(entry.getKey()));
				}
			}
		} catch (SQLException e) {
			rollback();
			e.printStackTrace();
			return false;
		} finally{
			closeStatement(cs);
			closeConnection();
		}
		return true;
	}
	
	public static List<Object> array2List(Object[] params){
		List<Object> list = new ArrayList<Object>();
		if(params == null){
			return list;
		}
		Collections.addAll(list, params);
		return list;
	}
	
	public void call(String sql, CallParameter parameter){
		call(sql, parameter.getParams(), parameter.getOutType(), parameter.getOutValue());
	}
	
	public static void setParameter(PreparedStatement ps, Object[] params) throws SQLException{
		for(int i=1; params!=null && i<=params.length; i++){
			ps.setObject(i, params[i-1]);
		}
	}
	
	public static void setParameter(PreparedStatement ps, List<Object> params) throws SQLException{
		for(int i=1; params!=null && i<=params.size(); i++){
			ps.setObject(i, params.get(i-1));
		}
	}
	
	public static Map<String, Object> getResultMap(ResultSet rs) throws SQLException{
		Map<String, Object> tempData = new LinkedHashMap<String, Object>();
		ResultSetMetaData rsm = rs.getMetaData();
		for(int j=1; j<=rsm.getColumnCount(); j++){
			Object obj = null;
			try{
				obj = rs.getObject(j);
			}catch(Exception e){
				try{
					obj = rs.getTimestamp(j);
				}catch (Exception e2) {
					obj = null;
				}
			}
			tempData.put(rsm.getColumnLabel(j).toLowerCase(), obj); 
		}
		return tempData;
	}
	
	public static void closeStatement(Statement stat){
		if(stat != null){
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closeConnection(){
		if(!autoCommit){
			commit();
		}
		JdbcConnection.closeConnection(conn);
	}

}
