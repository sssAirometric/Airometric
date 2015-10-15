package com.report.helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.model.DBUtil;

public class MiscHelper {
	
	public static HashMap<String, List<String>> getChartColors(){
		HashMap<String, List<String>> deviceColorMap = new HashMap<String, List<String>>();
		ArrayList<String> colorsList = new ArrayList<String>();
		colorsList.add("#580DFF");
		deviceColorMap.put("device_0", colorsList);
		deviceColorMap.put("device_1", colorsList);
		deviceColorMap.put("device_2", colorsList);
		deviceColorMap.put("device_3", colorsList);
		return deviceColorMap;
	}

	static boolean checkDuplicateIdentifier(String testName,String testType,String columnName,String testIdentifier,String tableName){
		boolean isIdentifierDuplicate = false;
		Connection conn = DBUtil.openConn();
		String query = "SELECT "+columnName+" FROM "+tableName+" WHERE "+columnName+"='"+testIdentifier+"' AND TEST_NAME ='"+testName+"'";
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				isIdentifierDuplicate = true;
				break;
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//		System.out.println(isIdentifierDuplicate);
		return isIdentifierDuplicate;
	}
	
	public static void main(String[] args) {
		checkDuplicateIdentifier("g2","ftp","TEST_IDENTIFIER_TIMESTAMP","1111","stg_device_info");
	}
}
