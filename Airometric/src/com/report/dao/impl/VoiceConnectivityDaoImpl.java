package com.report.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.model.DBUtil;
import com.preprocessing.reports.PreprocessingVCCallRetentionReport;
import com.report.dao.VoiceConnectivityDao;
import com.report.to.CallretentionTo;
import com.to.CallSetUpTo;
import com.to.VoiceConnectivityTO;
import com.util.DBHelper;

public class VoiceConnectivityDaoImpl extends DBHelper implements VoiceConnectivityDao  {

	Connection conn = null;
	Statement st = null;
	
	public void openConn(){
		try{
			conn= DBUtil.getConnection();
			st = conn.createStatement();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeConn(){
		try{
			st.close();
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getNeighbourInfo(List<String> neighbourInfoList) {
		String neighbourtStr = null;
		List neighboursubInfoList = new ArrayList();
		try {
			for (int j = 0; j < neighbourInfoList.size(); j++) {
				if (null != neighbourInfoList.get(j)
						&& !neighbourInfoList.get(j).toString().equals("Empty")) {
					String neighbourInfoArray[] = neighbourInfoList.get(j)
							.toString().split("\\$");
					for (int a = 0; a < neighbourInfoArray.length; a++) {
						String neighbourSubInfoArray[] = neighbourInfoArray[a]
								.split("\\^");
						if (a == 0) {
							if (neighbourtStr == null) {
								if (neighbourSubInfoArray[0].matches("-1")
										&& neighbourSubInfoArray[1]
												.matches("-1")) {
									neighbourtStr = "Cell: Empty" + ","
											+ "Lac:Empty" + "," + "PSC:"
											+ neighbourSubInfoArray[3] + ","
											+ "RSSI:"
											+ neighbourSubInfoArray[4];
								} else if (neighbourSubInfoArray[0]
										.matches("-1")) {
									neighbourtStr = "Cell: Empty" + ","
											+ "Lac:" + neighbourSubInfoArray[1]
											+ "," + "PSC:"
											+ neighbourSubInfoArray[3] + ","
											+ "RSSI:"
											+ neighbourSubInfoArray[4];
								} else if (neighbourSubInfoArray[1]
										.matches("-1")) {
									neighbourtStr = "Cell:"
											+ neighbourSubInfoArray[0] + ","
											+ "Lac:Empty" + "," + "PSC:"
											+ neighbourSubInfoArray[3] + ","
											+ "RSSI:"
											+ neighbourSubInfoArray[4];
								} else {
									neighbourtStr = "Cell:"
											+ neighbourSubInfoArray[0] + ","
											+ "Lac:" + neighbourSubInfoArray[1]
											+ "," + "PSC:"
											+ neighbourSubInfoArray[3] + ","
											+ "RSSI:"
											+ neighbourSubInfoArray[4];
								}
							} else {
								if (neighbourSubInfoArray[0].matches("-1")
										&& neighbourSubInfoArray[1]
												.matches("-1")) {
									neighbourtStr = neighbourtStr
											+ "|||Cell:Empty" + ","
											+ "Lac:Empty" + "," + "PSC:"
											+ neighbourSubInfoArray[3] + ","
											+ "RSSI:"
											+ neighbourSubInfoArray[4];
								} else if (neighbourSubInfoArray[0]
										.matches("-1")) {
									neighbourtStr = neighbourtStr
											+ "|||Cell:Empty" + "," + "Lac:"
											+ neighbourSubInfoArray[1] + ","
											+ "PSC:" + neighbourSubInfoArray[3]
											+ "," + "RSSI:"
											+ neighbourSubInfoArray[4];
								} else if (neighbourSubInfoArray[1]
										.matches("-1")) {
									neighbourtStr = neighbourtStr + "|||Cell:"
											+ neighbourSubInfoArray[0] + ","
											+ "Lac:Empty" + "," + "PSC:"
											+ neighbourSubInfoArray[3] + ","
											+ "RSSI:"
											+ neighbourSubInfoArray[4];
								} else {
									neighbourtStr = neighbourtStr + "|||Cell:"
											+ neighbourSubInfoArray[0] + ","
											+ "Lac:" + neighbourSubInfoArray[1]
											+ "," + "PSC:"
											+ neighbourSubInfoArray[3] + ","
											+ "RSSI:"
											+ neighbourSubInfoArray[4];
								}

							}
						} else if (a == (neighbourInfoArray.length - 1)) {
							if (neighbourSubInfoArray[0].matches("-1")
									&& neighbourSubInfoArray[1].matches("-1")) {
								neighbourtStr = neighbourtStr + "|||Cell:Empty"
										+ "," + "Lac:Empty" + "," + "PSC:"
										+ neighbourSubInfoArray[3] + ","
										+ "RSSI:" + neighbourSubInfoArray[4]
										+ "||";
							} else if (neighbourSubInfoArray[0].matches("-1")) {
								neighbourtStr = neighbourtStr + "|||Cell:Empty"
										+ "," + "Lac:"
										+ neighbourSubInfoArray[1] + ","
										+ "PSC:" + neighbourSubInfoArray[3]
										+ "," + "RSSI:"
										+ neighbourSubInfoArray[4] + "||";
							} else if (neighbourSubInfoArray[1].matches("-1")) {
								neighbourtStr = neighbourtStr + "|||Cell:"
										+ neighbourSubInfoArray[0] + ","
										+ "Lac:Empty" + "," + "PSC:"
										+ neighbourSubInfoArray[3] + ","
										+ "RSSI:" + neighbourSubInfoArray[4]
										+ "||";
							} else {
								neighbourtStr = neighbourtStr + "|||Cell:"
										+ neighbourSubInfoArray[0] + ","
										+ "Lac:" + neighbourSubInfoArray[1]
										+ "," + "PSC:"
										+ neighbourSubInfoArray[3] + ","
										+ "RSSI:" + neighbourSubInfoArray[4]
										+ "||";
							}

						} else {
							if (neighbourSubInfoArray[0].matches("-1")
									&& neighbourSubInfoArray[1].matches("-1")) {
								neighbourtStr = neighbourtStr + "|||Cell:Empty"
										+ "," + "Lac:Empty" + "," + "PSC:"
										+ neighbourSubInfoArray[3] + ","
										+ "RSSI:" + neighbourSubInfoArray[4];
							} else if (neighbourSubInfoArray[0].matches("-1")) {
								neighbourtStr = neighbourtStr + "|||Cell:Empty"
										+ "," + "Lac:"
										+ neighbourSubInfoArray[1] + ","
										+ "PSC:" + neighbourSubInfoArray[3]
										+ "," + "RSSI:"
										+ neighbourSubInfoArray[4];
							} else if (neighbourSubInfoArray[1].matches("-1")) {
								neighbourtStr = neighbourtStr + "|||Cell:"
										+ neighbourSubInfoArray[0] + ","
										+ "Lac:Empty" + "," + "PSC:"
										+ neighbourSubInfoArray[3] + ","
										+ "RSSI:" + neighbourSubInfoArray[4];
							} else {
								neighbourtStr = neighbourtStr + "|||Cell:"
										+ neighbourSubInfoArray[0] + ","
										+ "Lac:" + neighbourSubInfoArray[1]
										+ "," + "PSC:"
										+ neighbourSubInfoArray[3] + ","
										+ "RSSI:" + neighbourSubInfoArray[4];
							}
						}
					}
				} else {
					neighbourtStr = neighbourtStr
							+ "|||Cell:Empty,Lac:Empty,PSC:Empty,RSSI:Empty||";
				}
			}
			neighbourInfoList.clear();
			if (neighboursubInfoList.size() > 0) {
				boolean single = true;
				for (int x = 0; x < neighboursubInfoList.size(); x++) {
					if (neighbourtStr == null) {
						neighbourtStr = neighboursubInfoList.get(x).toString();
					} else {
						neighbourtStr = neighbourtStr + ","
								+ neighboursubInfoList.get(x).toString();
					}
				}
			}

			if (null != neighbourtStr) {
				neighbourtStr = neighbourtStr.substring(0, neighbourtStr
						.length() - 1);
				neighbourtStr = neighbourtStr.substring(0, neighbourtStr
						.length() - 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return neighbourtStr;
	}

	public List<VoiceConnectivityTO> getAllDeviceInfo(String testName,String market_id,String test_type) {
			List<VoiceConnectivityTO> deviceInfosList = new ArrayList<VoiceConnectivityTO>();
			Connection conn = null;
			Statement st = null;
			ResultSet rs = null;
			PreparedStatement pst = null;
			String test_name=testName+"-%";
			try {
				conn = DataConnectivityDaoImpl.conn;
				st = conn.createStatement();
				String deviceInfoQuery = "SELECT  * FROM STG_DEVICE_INFO WHERE (TEST_NAME LIKE ('"+test_name+"') OR TEST_NAME='"+testName+"') AND  MARKET_ID IN("+market_id+") " +
						"ORDER BY TIME_STAMP_FOREACH_SAMPLE";
//				System.out.println("deviceInfoQuery-----------"+deviceInfoQuery);
				rs = st.executeQuery(deviceInfoQuery);
				while (rs.next()) {
					VoiceConnectivityTO deviceInfos=new VoiceConnectivityTO();
					deviceInfos.setTestName(rs.getString("TEST_NAME"));
					deviceInfos.setNetworkType(rs.getString("NETWORK_NETWORKTYPE"));
					deviceInfos.setNetworkDataState(rs.getString("NETWORK_DATASTATE"));
					deviceInfos.setNetworkRoaming(rs.getString("NETWORK_ROAMING"));
					deviceInfos.setSignalStrength(rs.getString("SIGNALSTRENGTH_GSMSIGNALSTRENGTH"));
					deviceInfos.setSignalStrengthCDMA(rs.getString("SIGNALSTRENGTH_CDMADBM"));
					deviceInfos.setSignalStrengthEVDO(rs.getString("SIGNALSTRENGTH_EVDODBM"));
					deviceInfos.setLattitude(rs.getDouble("GEOLOCATION_LATITUDE"));
					deviceInfos.setLongitude(rs.getDouble("GEOLOCATION_LANGITUDE"));
					deviceInfos.setCellLocationCID(rs.getString("CELLLOCATION_CID"));
					deviceInfos.setCellLocationLAC(rs.getString("CELLLOCATION_LAC"));
					deviceInfos.setDevicePhoneType(rs.getString("DEVICE_PHONETYPE"));
					deviceInfos.setNetworkMCC(rs.getString("NETWORK_MCC"));
					deviceInfos.setNetworkMNC(rs.getString("NETWORK_MNC"));
					deviceInfos.setSignalStrengthSnr(rs.getString("SIGNALSTRENGTH_EVDOSNR"));
					deviceInfos.setTimeStampForEachSample(rs.getString("TIME_STAMP_FOREACH_SAMPLE"));
					deviceInfos.setNeighbourInfo(rs.getString("NEIGHBOUR_INFO"));
					deviceInfos.setSignalStrengthCDMACIO(rs.getString("SIGNALSTRENGTH_CDMACIO"));
					deviceInfos.setSignalStrengthEVDOECIO(rs.getString("SIGNALSTRENGTH_EVDOECIO"));
					deviceInfos.setSignalStrengthLTE(rs.getString("SIGNALSTRENGTH_LTESIGNALSTRENGTH"));
					deviceInfos.setSignalStrengthLTERSRP(rs.getString("SIGNALSTRENGTH_LTERSRP"));
					deviceInfos.setSignalStrengthLTERSRQ(rs.getString("SIGNALSTRENGTH_LTERSRQ"));
					deviceInfos.setSignalStrengthLTERSSNR(rs.getString("SIGNALSTRENGTH_LTERSSNR"));
					deviceInfos.setSignalStrengthLTECQI(rs.getString("SIGNALSTRENGTH_LTECQI"));
					deviceInfosList.add(deviceInfos);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} /*finally {
				try {
					rs.close();
					st.close();
					conn.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}*/
			return deviceInfosList;
		}


	
	/*public List<String> getAllDeviceInfoCallDropList(String testCaseName,String marketId,String testtype) {
		List<String> deviceInfosList = new ArrayList<String>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String deviceInfoQuery =null;
		String sql=null;
		try {
			conn = DataConnectivityDaoImpl.conn;
			st = conn.createStatement();
		    sql="SELECT TEST_NAME,TEST_TYPE,TEST_IDENTIFIER_TIMESTAMP " +
					" FROM FILE_HISTORY WHERE TEST_NAME IN('"+testCaseName+"') AND MARKET_ID IN('"+marketId+"') AND ACTIVE='1'";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String test_name=rs.getString(1);
				test_name=test_name+"\\-%";
				String test_type=rs.getString(2);
				String test_identifier_timestamp=rs.getString(3);
				deviceInfoQuery = "SELECT *FROM STG_LOG_CAT_INFO WHERE TEST_NAME LIKE '"+test_name+"' AND TEST_TYPE IN("+testtype+")" +
				" AND TEST_IDENTIFIER_TIMESTAMP='"+test_identifier_timestamp+"' AND EVENT_NAME='CALL_DROP' ORDER BY TIME_STAMP ";
				//System.out.println("deviceInfoQuery----------------"+deviceInfoQuery);
				rs = st.executeQuery(deviceInfoQuery);
				while (rs.next()) {
					deviceInfosList.add(rs.getString("TIME_STAMP"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//System.out.println("deviceInfosList--------------"+deviceInfosList);
		return deviceInfosList;
	}*/
	
	public List<String> getAllDeviceInfoCallDropList(String testCaseName,String marketId,String testtype) {
		List<String> deviceInfosList = new ArrayList<String>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String test_name=testCaseName+"\\-%";
		PreparedStatement pst = null;
		try {
			conn = DBUtil.getConnection();
			st = conn.createStatement();
			
			String deviceInfoQuery = "SELECT STG.TIME_STAMP  FROM STG_LOG_CAT_INFO STG ,(SELECT TEST_NAME,TEST_TYPE,TEST_IDENTIFIER_TIMESTAMP,MARKET_ID FROM STG_DEVICE_INFO "
				+ "WHERE TEST_NAME LIKE  ('"
				+ test_name
				+ "') AND TEST_TYPE="
				+ testtype
				+ " AND MARKET_ID='"
				+ marketId
				+ "'  GROUP BY TEST_IDENTIFIER_TIMESTAMP) ST WHERE "
				+ "ST.TEST_NAME=STG.TEST_NAME  AND ST.TEST_TYPE=STG.TEST_TYPE AND ST.TEST_IDENTIFIER_TIMESTAMP=STG.TEST_IDENTIFIER_TIMESTAMP AND  STG.EVENT_NAME='CALL_DROP'  " + "GROUP BY STG.TIME_STAMP";
//			System.out.println("deviceInfoQuery--------vq-----------"+deviceInfoQuery);
			rs = st.executeQuery(deviceInfoQuery);
			List<VoiceConnectivityTO> vcToList = new PreprocessingVCCallRetentionReport().getPreVoiceDataForLogcat(testCaseName);
			for(VoiceConnectivityTO vto : vcToList){
				deviceInfosList.add(vto.getTime_stamp());
			}
			/*while (rs.next()) {
				deviceInfosList.add(rs.getString("TIME_STAMP"));
			}*/
			if(deviceInfosList.size()==0){
//				deviceInfosList = getDropFromCallEvents(testCaseName);
				List<List<CallSetUpTo>> allCycles = getAllInLogCatInfoEvents(testCaseName,
						marketId,conn);
				deviceInfosList = getCallDropsMissedCalls(allCycles);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
//			System.out.println("deviceInfosList----"+deviceInfosList);
		
		}return deviceInfosList;
	}

	public static List<List<CallSetUpTo>> getAllInLogCatInfoEvents(String test_name,
			String marketId,Connection conn) {
		List<List<CallSetUpTo>> eventCycleList = new ArrayList<List<CallSetUpTo>>();
		List<CallSetUpTo> individulaEventList = new ArrayList<CallSetUpTo>();
		String query = "";
		ResultSet rs = null;
		
		try {
			query = "SELECT Call_Timestamp,STR_TO_DATE(Call_Timestamp,'%m/%d/%Y %H:%i:%s') AS formattedTimeStamp ,CALL_CONTROL_EVENT FROM STG_CALLEVENT_RESULTS WHERE TEST_NAME LIKE '%"
					+ test_name
					+ "%'  AND MARKET_ID='"
					+ marketId
					+ "'";
//			System.out.println(query);
			Statement st = conn.createStatement();
			rs = st.executeQuery(query);
			st.setFetchDirection(ResultSet.FETCH_REVERSE);
			boolean isPrevEvent_Placing = false;
			rs.afterLast();
			int count = 1417;
			while (rs.previous()) {
				CallSetUpTo cst = new CallSetUpTo();
				String calleventTimeStamp =  rs.getString("Call_Timestamp");
				String timeStamp = rs.getString("formattedTimeStamp");
				String eventName = rs.getString("CALL_CONTROL_EVENT").trim();
//				System.out.println(count+"--eventName------"+eventName);
				count--;
				if (isPrevEvent_Placing
						&& eventName.equalsIgnoreCase("Placing Call")) {
					individulaEventList = new ArrayList<CallSetUpTo>();
				}
				if (!isPrevEvent_Placing
						&& eventName.equalsIgnoreCase("Placing Call")) {
					isPrevEvent_Placing = true;
				}
				cst.setEventName(eventName);
				cst.setTimeStamp(timeStamp);
				individulaEventList.add(cst);
				if (eventName.equalsIgnoreCase("Call Dropped")||eventName.equalsIgnoreCase("Missed Call")) {
					eventCycleList.add(individulaEventList);
					isPrevEvent_Placing = false;
					individulaEventList = new ArrayList<CallSetUpTo>();
				}
				cst.setCalleventsTimeStamp(calleventTimeStamp);
				cst.setEventName(eventName);
				cst.setTimeStamp(timeStamp);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("eventCycleList------------" + eventCycleList);
		return eventCycleList;
	}
	
	public static List<String> getCallDropsMissedCalls(List<List<CallSetUpTo>> allCyclesEvents) {
		/**
		 * For drop calls, there should be two consective call connect and call
		 * disconnect in a single cycle, Rest others are missed call.Thats what
		 * we are doing below
		 **/
		List<String> timeStampList = new ArrayList<String>();
		int missedCalls = 0;
		int numberOfCallDrops = 0;
		for(List<CallSetUpTo> singleCycle : allCyclesEvents){
			boolean isConnect = false;
			boolean isDisconnect = false;
			String prevEvent = "";
			String eventName = "";
			String timestamp = "";
			for(CallSetUpTo callSetUpTo : singleCycle){
				 eventName = callSetUpTo.getEventName();
				 timestamp = callSetUpTo.getTimeStamp();
//				 System.out.println("timestamp--------"+callSetUpTo.getCalleventsTimeStamp());
				if(prevEvent.equalsIgnoreCase("Connected")&&eventName.equalsIgnoreCase("Connected")){
					isConnect = true;
				}
				if(isConnect&&prevEvent.equalsIgnoreCase("Disconnected")&&eventName.equalsIgnoreCase("Disconnected")){
					isDisconnect = true;
				}
				prevEvent = eventName;
			}
			if(isConnect&&isDisconnect){
				numberOfCallDrops++;
				timeStampList.add(timestamp);
			}
		}
//		System.out.println(numberOfCallDrops+"-----------timeStampList()------"+timeStampList.size());
		
		missedCalls = allCyclesEvents.size()-numberOfCallDrops;
		return timeStampList;
	}
	
	public static List<String> getDropFromCallEvents(String testName){
		List<String> dropcallList = new ArrayList<String>();
		List<String> deviceInfoList = new ArrayList<String>();
		List<String> matchinList = new ArrayList<String>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String getCallEventsDropCall = "SELECT str_to_date(VQuad_Timestamp,'%m/%d/%Y %H:%i:%s') AS CALLEVENTTIME FROM stg_callevent_results WHERE TEST_NAME LIKE '%"+testName+"%'";
		String deviceInfoCall = "SELECT TIME_STAMP_FOREACH_SAMPLE FROM stg_device_info WHERE TEST_NAME LIKE '%"+testName+"%'";
		try{
			conn = DBUtil.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(getCallEventsDropCall);
//			System.out.println(getCallEventsDropCall);
			while(rs.next()){
				String timeStamp = rs.getString("CALLEVENTTIME");
				dropcallList.add(timeStamp);
			}
			rs = st.executeQuery(deviceInfoCall);
			while(rs.next()){
				String timeStamp = rs.getString("TIME_STAMP_FOREACH_SAMPLE");
				deviceInfoList.add(timeStamp);
			}
//			System.out.println("dropcallList--------"+dropcallList.size());
//			System.out.println("deviceInfoList--------"+deviceInfoList.size());
			for(int i=0;i<dropcallList.size();i++){
				String dropCallTime = dropcallList.get(i);
				for(int j=0;j<deviceInfoList.size();j++){
					String deviceTime = deviceInfoList.get(j);
					if(matchCallTimestamp(dropCallTime,deviceTime)){
						matchinList.add(deviceTime);
					}
				}
			}
//			System.out.println("after-++--");
			//System.out.println("matchinList----------"+matchinList);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return matchinList;
	}
	public List<VoiceConnectivityTO> getAllMultiplDeviceInfo(String testCaseName,String marketId) {
		List<VoiceConnectivityTO> deviceInfosList = new ArrayList<VoiceConnectivityTO>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String test_name=testCaseName+"\\-%";
		try {
			conn = DataConnectivityDaoImpl.conn;
			st = conn.createStatement();
			String deviceInfoQuery = "SELECT  * FROM STG_DEVICE_INFO WHERE TEST_NAME  IN(SELECT DISTINCT TEST_NAME FROM STG_DEVICE_INFO " +
					" WHERE TEST_NAME LIKE  '"+test_name+"' AND MARKET_ID= '"+marketId+"' )ORDER BY TIME_STAMP_FOREACH_SAMPLE ";
			rs = st.executeQuery(deviceInfoQuery);
			while (rs.next()){
				VoiceConnectivityTO deviceInfos = new VoiceConnectivityTO();
				deviceInfos.setTestName(rs.getString("TEST_NAME"));
				deviceInfos.setNetworkType(rs.getString("NETWORK_NETWORKTYPE"));
				deviceInfos.setNetworkDataState(rs.getString("NETWORK_DATASTATE"));
				deviceInfos.setNetworkRoaming(rs.getString("NETWORK_ROAMING"));
				deviceInfos.setSignalStrength(rs.getString("SIGNALSTRENGTH_GSMSIGNALSTRENGTH"));
				deviceInfos.setLattitude(rs.getDouble("GEOLOCATION_LATITUDE"));
				deviceInfos.setLongitude(rs.getDouble("GEOLOCATION_LANGITUDE"));
				deviceInfos.setCellLocationCID(rs.getString("CELLLOCATION_CID"));
				deviceInfos.setCellLocationLAC(rs.getString("CELLLOCATION_LAC"));
				deviceInfos.setDevicePhoneType(rs.getString("DEVICE_PHONETYPE"));
				deviceInfos.setNetworkMCC(rs.getString("NETWORK_MCC"));
				deviceInfos.setNetworkMNC(rs.getString("NETWORK_MNC"));
				deviceInfos.setTimeStampForEachSample(rs.getString("TIME_STAMP_FOREACH_SAMPLE"));
				deviceInfos.setNeighbourInfo(rs.getString("NEIGHBOUR_INFO"));
				deviceInfos.setSignalStrengthLTE(rs.getString("SIGNALSTRENGTH_LTESIGNALSTRENGTH"));
				deviceInfos.setSignalStrengthLTERSRP(rs.getString("SIGNALSTRENGTH_LTERSRP"));
				deviceInfos.setSignalStrengthLTERSRQ(rs.getString("SIGNALSTRENGTH_LTERSRQ"));
				deviceInfos.setSignalStrengthLTERSSNR(rs.getString("SIGNALSTRENGTH_LTERSSNR"));
				deviceInfos.setSignalStrengthLTECQI(rs.getString("SIGNALSTRENGTH_LTECQI"));
				deviceInfosList.add(deviceInfos);
				}
		} catch (Exception e) {
			e.printStackTrace();
		} /*finally {
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		return deviceInfosList;
	}

	public List<String> getAllMultipleDeviceInfoCallDropList(String testCaseName,String marketId) {
		List<String> deviceInfosList = new ArrayList<String>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String test_name=testCaseName+"\\-%";
		PreparedStatement pst = null;
		String sql=null;
		String deviceInfoQuery=null;
		try {
			conn = DataConnectivityDaoImpl.conn;
			st = conn.createStatement();
			 sql="SELECT TEST_NAME,TEST_TYPE,TEST_IDENTIFIER_TIMESTAMP " +
				" FROM FILE_HISTORY WHERE TEST_NAME='"+test_name+"' AND MARKET_ID='"+marketId+"' AND ACTIVE='1'";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String test_Name=rs.getString(1);
				String test_type=rs.getString(2);
				String test_identifier_timestamp=rs.getString(3);
				deviceInfoQuery = "SELECT *FROM STG_LOG_CAT_INFO WHERE TEST_NAME IN(SELECT DISTINCT TEST_NAME FROM STG_LOG_CAT_INFO " +
				" WHERE TEST_NAME LIKE  '"+test_Name+"' AND TEST_TYPE = '"+test_type+"' AND TEST_IDENTIFIER_TIMESTAMP= '"+test_identifier_timestamp+"' " +
						")and EVENT_NAME='CALL_DROP' ORDER BY TIME_STAMP ";
				rs = st.executeQuery(deviceInfoQuery);
				while (rs.next()) {
					deviceInfosList.add(rs.getString("TIME_STAMP"));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} /*finally {
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		return deviceInfosList;
	}
	
	public String getMarketName(String marketId) {
		String marketName = "";
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String deviceId = "";
		try {
			conn = DataConnectivityDaoImpl.conn;
			st = conn.createStatement();
			String query = "SELECT MARKET_NAME FROM MARKET WHERE MARKET_ID='"+ marketId + "'";
			rs = st.executeQuery(query);
			while (rs.next()) {
				marketName = rs.getString("MARKET_NAME");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*finally{
			try{
				conn.close();
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}*/
		return marketName;

	}
	
	public String getTimeDiff(Date dateOne, Date dateTwo) {
        String diff = "";
        long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
        diff = String.format("%d hour(s) %d min(s)", TimeUnit.MILLISECONDS.toHours(timeDiff),
                TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
        return diff;
   }

	public static List<String> getTestTimeStampInDeviceInfo(String test_name,String test_type,String marketId,String network){
		List<String> timeStamp = new ArrayList<String>();
		String query = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = DataConnectivityDaoImpl.conn;
			st = conn.createStatement();
			query="SELECT DISTINCT ST.TIME_STAMP_FOREACH_SAMPLE FROM STG_DEVICE_INFO ST " +
				  "WHERE ST.TEST_NAME LIKE '"+(test_name)+"'  AND ST.TEST_TYPE='"+test_type+"' " +
				  " AND ST.MARKET_ID IN("+marketId+") AND ST.NETWORK_TYPE IN('"+network+"')";
			rs = st.executeQuery(query);
			while (rs.next()) {
				timeStamp.add(rs.getString(1));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*finally{
			try{
				conn.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		return timeStamp;
	}
	
	public static List<String> getTestTimeStampLogCatInfo(String test_name,String test_type,String marketId,String eventname){
		List<String> timeStamp = new ArrayList<String>();
		String query = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = DataConnectivityDaoImpl.conn;
			st = conn.createStatement();
			query="SELECT STG.TIME_STAMP  FROM STG_LOG_CAT_INFO STG ,(SELECT TEST_NAME,TEST_TYPE,TEST_IDENTIFIER_TIMESTAMP,MARKET_ID FROM STG_DEVICE_INFO " +
			  "WHERE TEST_NAME LIKE  ('"+test_name+"') AND TEST_TYPE='"+test_type+"' AND MARKET_ID='"+marketId+"'  GROUP BY TEST_IDENTIFIER_TIMESTAMP) ST WHERE " +
			  "ST.TEST_NAME=STG.TEST_NAME  AND ST.TEST_TYPE=STG.TEST_TYPE AND ST.TEST_IDENTIFIER_TIMESTAMP=STG.TEST_IDENTIFIER_TIMESTAMP AND  STG.EVENT_NAME='"+eventname+"'  " +
			  "GROUP BY STG.TIME_STAMP";
			rs = st.executeQuery(query);
			while (rs.next()) {
					timeStamp.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*finally{
			try{
				conn.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		return timeStamp;
	}
	

	public static List<String> getTestTimeStampInLogCatInfo(String test_name,String test_type,String marketId,String eventname){
		List<String> timeStamp = new  ArrayList<String>();
		String query = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			st = conn.createStatement();
			query="SELECT STG.TIME_STAMP  FROM STG_LOG_CAT_INFO STG ,(SELECT TEST_NAME,TEST_TYPE,TEST_IDENTIFIER_TIMESTAMP,MARKET_ID FROM STG_DEVICE_INFO " +
				  "WHERE TEST_NAME LIKE  ('"+test_name+"') AND TEST_TYPE='"+test_type+"' AND MARKET_ID='"+marketId+"'  GROUP BY TEST_IDENTIFIER_TIMESTAMP) ST WHERE " +
				  "ST.TEST_NAME=STG.TEST_NAME  AND ST.TEST_TYPE=STG.TEST_TYPE AND ST.TEST_IDENTIFIER_TIMESTAMP=STG.TEST_IDENTIFIER_TIMESTAMP AND  STG.EVENT_NAME='"+eventname+"'  " +
				  "GROUP BY STG.TIME_STAMP";
			//System.out.println(query);
			rs = st.executeQuery(query);
			while (rs.next()) {
				if(rs.getString(1)!=null){
			     	timeStamp.add(rs.getString(1));			
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*finally{
			try{
				conn.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		return timeStamp;
	}
	
	public static List<CallSetUpTo> getAllInLogCatInfo(String test_name,String test_type,String marketId){
		List<CallSetUpTo> callsetUpList = new  ArrayList<CallSetUpTo>();
		String query = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			st = conn.createStatement();
			query="SELECT  STG.TEST_IDENTIFIER_TIMESTAMP,STG.TIME_STAMP,STG.EVENT_NAME  FROM STG_LOG_CAT_INFO STG ,(SELECT TEST_NAME,TEST_TYPE,TEST_IDENTIFIER_TIMESTAMP,MARKET_ID FROM STG_DEVICE_INFO " +
				  "WHERE TEST_NAME LIKE  ('"+test_name+"-%') AND TEST_TYPE='"+test_type+"' AND MARKET_ID='"+marketId+"'  GROUP BY TEST_IDENTIFIER_TIMESTAMP) ST WHERE " +
				  "ST.TEST_NAME=STG.TEST_NAME  AND ST.TEST_TYPE=STG.TEST_TYPE AND ST.TEST_IDENTIFIER_TIMESTAMP=STG.TEST_IDENTIFIER_TIMESTAMP   " +
				  "GROUP BY STG.TIME_STAMP";
			//System.out.println(query);
			rs = st.executeQuery(query);
			while (rs.next()) {
				CallSetUpTo cst = new CallSetUpTo();
				String timeStamp = rs.getString("TIME_STAMP");
				String timeStampIdentifier = rs.getString("TEST_IDENTIFIER_TIMESTAMP");
				String eventName = rs.getString("EVENT_NAME");
				if(null!=timeStamp){
					cst.setEventName(eventName);
					cst.setTimeStamp(timeStamp);
					cst.setTimeStampIdentifier(timeStampIdentifier);
					callsetUpList.add(cst);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*finally{
			try{
				conn.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		return callsetUpList;
	}
	
	public static String getTestTimeStampIn4GLogCatInfo(String test_name,String test_type,String marketId,String network){
		String timeStamp = "";
		String query = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = DataConnectivityDaoImpl.conn;
			st = conn.createStatement();
			
			query="SELECT STG.TIME_STAMP_FOREACH_SAMPLE FROM STG_DEVICE_INFO STG"+
			" WHERE STG.TEST_NAME LIKE ('"+test_name+"') AND STG.TEST_TYPE='"+test_type+"' AND STG.NETWORK_NETWORKTYPE IN ("+network+") " +
			" AND STG.MARKET_ID='"+marketId+"' GROUP BY STG.TIME_STAMP_FOREACH_SAMPLE,STG.NETWORK_NETWORKTYPE";
			//System.out.println("query4g---------------"+query);
			rs = st.executeQuery(query);
			while (rs.next()) {
				if(timeStamp == ""){
					timeStamp=rs.getString(1);
				}else{
					timeStamp=timeStamp+","+rs.getString(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*finally{
			try{
				conn.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		return timeStamp;
	}
    
    
    
	public static int diffTimeStamp(String starttime,String EndTime){
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		  Date d1 = null;
		  Date d2 = null;
		  int seconds=0;
		  try {
			   d1 = format.parse(starttime);
			   d2 = format.parse(EndTime); 
			   long diff = d1.getTime() - d2.getTime();	
			   long diffMilliSeconds = diff / 1000 % 60 % 60;
			   long diffSeconds = diff / 1000 % 60;
			   long diffMinutes = diff / (60 * 1000) % 60;
			   long diffHours = diff / (60 * 60 * 1000) % 24;
			   long diffDays = diff / (24 * 60 * 60 * 1000);
			   seconds= Math.abs((int) diffSeconds+(int)(60*diffMinutes));
		  }catch (Exception e) {
		   e.printStackTrace();
		  }
		 return seconds;
     }
	
	public static int addTimeStamp(String starttime,String EndTime){
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		  Date d1 = null;
		  Date d2 = null;
		  int seconds=0;
		  try {
			   d1 = format.parse(starttime);
			   d2 = format.parse(EndTime); 
			   long diff = d1.getTime() + d2.getTime();	
			   long diffMilliSeconds = diff / 1000 % 60 % 60;
			   long diffSeconds = diff / 1000 % 60;
			   long diffMinutes = diff / (60 * 1000) % 60;
			   long diffHours = diff / (60 * 60 * 1000) % 24;
			   long diffDays = diff / (24 * 60 * 60 * 1000);
			   seconds= Math.abs((int) diffMilliSeconds);
		  }catch (Exception e) {
		   e.printStackTrace();
		  }
		
		 return seconds;
   }

	
/*	public HashMap<String, List<Integer>> getCallSetupTime(List<CallSetUpTo> callSetetailsList) {
		HashMap<String, List<Integer>>callSetupList=new HashMap<String, List<Integer>>();
		List<Integer> callSetUpMO=new ArrayList<Integer>();
		try {
				//System.out.println("callSetetailsList------++------"+callSetetailsList.size());
				int callsetupiListSize = callSetetailsList.size();
                    for(int j=0;j<callsetupiListSize;j++){
//			    		if(diffTimeStamp(callSetetailsList.get(j).getTimeStamp(),callSetetailsList.get(j+1).getTimeStamp())>0){
                    	//System.out.println("callSetetailsList.get(j).getTimeStamp()----------"+callSetetailsList.get(j).getTimeStamp());
                    	//System.out.println("callSetetailsList.get(j+1).getTimeStamp()----------"+callSetetailsList.get(j+1).getTimeStamp());
			    			callSetUpMO.add(diffTimeStamp(callSetetailsList.get(j+1).getTimeStamp(),callSetetailsList.get(j).getTimeStamp()));		    			
//			    		}
			    		j++;
                    }
					callSetupList.put("mo", callSetUpMO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return callSetupList;
	}*/
	
	public HashMap<String, List<Integer>> getCallSetupTime(String testName,String marketId) {
		HashMap<String, List<Integer>>callSetupList=new HashMap<String, List<Integer>>();
		List<Integer> callSetUpMO=new ArrayList<Integer>();
		String query  = "SELECT * FROM pre_calc_voiceconnectivity WHERE TEST_NAME = '"+testName+"' AND MARKET_ID = '"+marketId+"' AND CHART_TYPE = 'callsetup' AND NETWORK_TYPE='ALL'";
		try{
			openConn();
//			System.out.println("query----++------"+query);
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				String values = rs.getString("VALUES");
				if(values!=null && values.length()<1)
					values="0";
				
				String[] valArr = values.split(",");
				for(int i=0;i<valArr.length;i++){
					callSetUpMO.add(new Integer(valArr[i].trim()));
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//		System.out.println("callSetUpMO------------"+callSetUpMO);
		callSetupList.put("mo", callSetUpMO);
		closeConn();
		//System.out.println("callSetupList---------"+callSetupList);
		return callSetupList;
	}
	

	public HashMap<String, List<Float>> getCallRetentionTime(String testName,String marketId) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		CallretentionTo cto = new CallretentionTo();
		HashMap<String, List<Float>>callRetenctionList=new HashMap<String, List<Float>>();
		List<String> New_Outgoing_Call_Time_Stamp=new ArrayList<String>();
		List<String> callDropTime_Stamp=new ArrayList<String>();;
		String test_name=null;
		String test_type=null;
		List<Float> callRetentionMO=new ArrayList<Float>();
		double callDropRate=0;
		try {
			conn = DataConnectivityDaoImpl.conn;
			st = conn.createStatement();
				String query = "SELECT * FROM pre_cal_callretention WHERE TEST_NAME ='"+testName+"' AND MARKET_ID='"+marketId+"' AND NETWORK_TYPE='ALL'";
				rs = st.executeQuery(query);
//				System.out.println("query---call retention------"+query);
			while (rs.next()) {
				
				String parameterType = rs.getString("PARAMETER");
				float pramaterValue = rs.getFloat("PARAMETER_VALUE");
				if(parameterType.equalsIgnoreCase("totalcalls")){
					cto.setTotalCalls(pramaterValue);
				}else 
					if(parameterType.equalsIgnoreCase("dropcalls")){
						cto.setDropCalls(pramaterValue);
					}
					else if(parameterType.equalsIgnoreCase("dcr")){
						cto.setDcr(pramaterValue);
					}
					else if(parameterType.equalsIgnoreCase("missed calls")){
						cto.setMissedCalls(pramaterValue);
					}
					else if(parameterType.equalsIgnoreCase("access failure")){
						cto.setAccess_failure(pramaterValue);
					}
			}	
			callRetentionMO.add(cto.getTotalCalls()/10);
			callRetentionMO.add(cto.getDropCalls());
			callRetentionMO.add(cto.getDcr());
			callRetentionMO.add(cto.getAccess_failure());
			callRetentionMO.add(cto.getMissedCalls());
			callRetenctionList.put("mo", callRetentionMO); 
//			System.out.println("callRetenctionList-----------"+callRetenctionList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*finally{
			try{
				closeConn();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		return callRetenctionList;
	}

	
	public HashMap<String, List<Integer>> getCallTearDownTime(String testName,String marketId) {
		HashMap<String, List<Integer>>callTearDownList=new HashMap<String, List<Integer>>();
		List<Integer> callTearDownMO=new ArrayList<Integer>();
		String query  = "SELECT * FROM pre_calc_voiceconnectivity WHERE TEST_NAME = '"+testName+"' AND MARKET_ID = '"+marketId+"' AND CHART_TYPE = 'callteardown'  AND NETWORK_TYPE='ALL'";
		try{
			openConn();
			//System.out.println("query----++------"+query);
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				String values = rs.getString("VALUES");
				String[] valArr = values.split(",");
				for(int i=0;i<valArr.length;i++){
					callTearDownMO.add(new Integer(valArr[i].trim()));
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		callTearDownList.put("mo", callTearDownMO);
		closeConn();
		return callTearDownList;
	}
	

	public List<Integer> getIdleTimeOutOfCoverage(String deviceName,String marketId,String testName,String testType) {
		 List<Integer>idleTimeList=new ArrayList<Integer>();
		String query  = "SELECT * FROM pre_calc_voiceconnectivity WHERE TEST_NAME = '"+testName+"' AND MARKET_ID = '"+marketId+"' AND CHART_TYPE = 'idleTime' AND NETWORK_TYPE='ALL'";
		try{
			openConn();
			//System.out.println("query----++------"+query);
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				String values = rs.getString("VALUES");
				String[] valArr = values.split(",");
				for(int i=0;i<valArr.length;i++){
					idleTimeList.add(new Integer(valArr[i].trim()));
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		closeConn();
		return idleTimeList;
	}
	public List<String> getIdleTime(String deviceName,String marketId,String testName,String testType) {
		List<String> idleTimeList = new ArrayList<String>();
		boolean foundValue = false;
		int prevStrength = 0;
		int currentStrength = 0;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try{
			conn =  DBUtil.getConnection();
			st = conn.createStatement();
			String getIdleTimeQuery = " select (SIGNALSTRENGTH_GSMSIGNALSTRENGTH),TIME_STAMP_FOREACH_SAMPLE " +
					" FROM stg_device_info  "
					+ "WHERE DEVICE_MODEL ='"+deviceName+"' AND MARKET_ID = '"+marketId+"' and " +
							"test_name LIKE '"+testName+"\\-%' AND TEST_TYPE = '"+testType+"' " +
							" ORDER BY TIME_STAMP_FOREACH_SAMPLE" ;
			//System.out.println("getIdleTimestamp-------"+getIdleTimeQuery);
			rs = st.executeQuery(getIdleTimeQuery);
			while(rs.next()){
				currentStrength = rs.getInt("SIGNALSTRENGTH_GSMSIGNALSTRENGTH");
				String idleTime = rs.getString("TIME_STAMP_FOREACH_SAMPLE");
			
				if(!foundValue && currentStrength<6){
					idleTimeList.add(idleTime);
				}
				if(currentStrength<6){
					foundValue = true;
				}
				else{
					foundValue = false;
				}
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return idleTimeList;
	}
	
	public HashMap<String, List<Float>> getCallRetentionTime4G(String testName,String marketId) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		CallretentionTo cto = new CallretentionTo();
		HashMap<String, List<Float>>callRetenctionList=new HashMap<String, List<Float>>();
		List<String> New_Outgoing_Call_Time_Stamp=new ArrayList<String>();
		List<String> callDropTime_Stamp=new ArrayList<String>();;
		String test_name=null;
		String test_type=null;
		List<Float> callRetentionMO=new ArrayList<Float>();
		double callDropRate=0;
		try {
			conn = DataConnectivityDaoImpl.conn;
			st = conn.createStatement();
				String query = "SELECT * FROM pre_cal_callretention WHERE TEST_NAME ='"+testName+"' AND MARKET_ID='"+marketId+"' AND NETWORK_TYPE='LTE'";
				rs = st.executeQuery(query);
				//System.out.println("query---call retention------"+query);
			while (rs.next()) {
				
				String parameterType = rs.getString("PARAMETER");
				int pramaterValue = rs.getInt("PARAMETER_VALUE");
				if(parameterType.equalsIgnoreCase("NEW OUTGOING CALL")){
					cto.setTotalCalls(pramaterValue);
				}else 
					if(parameterType.equalsIgnoreCase("dropcalls")){
						cto.setDropCalls(pramaterValue);
					}
					else if(parameterType.equalsIgnoreCase("dcr")){
						cto.setDcr(pramaterValue);
					}
			}			
			callRetentionMO.add(cto.getTotalCalls());
			callRetentionMO.add(cto.getDropCalls());
			callRetentionMO.add(cto.getDcr());
			callRetenctionList.put("mo", callRetentionMO); 
			//System.out.println("callRetenctionList-----------"+callRetenctionList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return callRetenctionList;
	}
	
	public HashMap<String, List<Integer>> getCallSetupTime4G(String testName,String marketId) {
		HashMap<String, List<Integer>>callSetupList=new HashMap<String, List<Integer>>();
		List<Integer> callSetUpMO=new ArrayList<Integer>();
		String query  = "SELECT * FROM pre_calc_voiceconnectivity WHERE TEST_NAME = '"+testName+"' AND MARKET_ID = '"+marketId+"' AND CHART_TYPE = 'callsetup' AND NETWORK_TYPE='LTE'";
		try{
			openConn();
			//System.out.println("query----++------"+query);
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				String values = rs.getString("VALUES");
				String[] valArr = values.split(",");
				for(int i=0;i<valArr.length;i++){
					callSetUpMO.add(new Integer(valArr[i].trim()));
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//System.out.println("callSetUpMO------------"+callSetUpMO);
		callSetupList.put("mo", callSetUpMO);
		closeConn();
		//System.out.println("callSetupList---------"+callSetupList);
		return callSetupList;
	}
	public static Map<String, List<CallSetUpTo>> getcallSetupChartDetails(String testName,String testType,String marketId) {
//		//System.out.println("Seattle---------"+new VoiceConnectivityDaoImpl().getCallTearDownTime("22","SGH-M919","SGH-M919_mo_Seattle East_2014-01-08 14:47:48.067_355537050050378"));
		VoiceConnectivityDaoImpl vcd = new VoiceConnectivityDaoImpl();
		List<CallSetUpTo> callList = vcd.getAllInLogCatInfo(testName,testType,marketId);
		List<CallSetUpTo> callFormattedList = new ArrayList<CallSetUpTo>();
		List<CallSetUpTo> callTearDownList = new ArrayList<CallSetUpTo>();
		Map<String, List<CallSetUpTo>> voiceConnectivityMap = new HashMap<String, List<CallSetUpTo>>();
		int listSize = callList.size();
		//System.out.println("listSize--"+listSize);
		for(int i=0;i<listSize;i++){
			CallSetUpTo cst = callList.get(i);
			String eventName = cst.getEventName();
			String nextEvent = "";
			if((i+1<listSize)&&null!=callList.get(i+1)){
				 nextEvent = callList.get(i+1).getEventName();
			}
			//HERE WE ARE GETTING DETAILS FOR CALLSETUPDOWN,
			//THE LOGIC IS: WE PICK THE EVENTS WHICH IS NEW_OUTGOING_CALL AND AGAIN NEW_OUTGOING_CALL DOESN REPEAST UNTIL  OFFHOOK IS  ENCOUNTERED
			if(eventName.equalsIgnoreCase("NEW_OUTGOING_CALL")&&!
					(nextEvent.equalsIgnoreCase("NEW_OUTGOING_CALL"))&&
					nextEvent.equalsIgnoreCase("OFFHOOK")){
				callFormattedList.add( callList.get(i));
				callFormattedList.add( callList.get(i+1));
				
			}
			//HERE WE ARE GETTING DETAILS FOR CALLTEARDOWN,
			//THE LOGIC IS: WE PICK THE EVENTS WHICH IS CALL_END AND AGAIN CALL_END DOESN REPEAST UNTIL  CALL_DISCONNECT IS  ENCOUNTERED
			if(eventName.equalsIgnoreCase("CALL_DISCONNECT")&&!
					(nextEvent.equalsIgnoreCase("CALL_DISCONNECT"))&&
					nextEvent.equalsIgnoreCase("CALL_END")){
				callTearDownList.add( callList.get(i));
				callTearDownList.add( callList.get(i+1));
				
			}
		}
		
		
		voiceConnectivityMap.put("callSetupDetails", callFormattedList);
		voiceConnectivityMap.put("callTearDownDetails", callTearDownList);
		return voiceConnectivityMap;
	}
	
	/*Duplicate method*/
    public boolean matchTimestamp_Duplicate(String starttime,String Endtime){
    	boolean status=false;
    	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		  Date d1 = null;
		  Date d2 = null;
		  int seconds=0;
		  try {
			   d1 = format.parse(starttime);
			   d2 = format.parse(Endtime); 
			   long diff = d1.getTime() - d2.getTime();	
			   long diffMilliSeconds = diff / 1000 % 60 % 60;
			   long diffSeconds = diff / 1000 % 60;
			   long diffMinutes = diff / (60 * 1000) % 60;
			   long diffHours = diff / (60 * 60 * 1000) % 24;
			   long diffDays = diff / (24 * 60 * 60 * 1000);
			   seconds= Math.abs((int) diffSeconds+(int)(60*diffMinutes));
		  }catch (Exception e) {
		   e.printStackTrace();
		  }
		  if(seconds>=1 && seconds<=10){
			  status=true;
		  }
    	return status;
    }
    
    public static boolean matchCallTimestamp(String starttime,String Endtime){
    	boolean status=false;
    	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		  Date d1 = null;
		  Date d2 = null;
		  int seconds=0;
		  try {
//			   d1 = format.parse(starttime);
//			   d2 = format.parse(Endtime); 
//			   long diff = d1.getTime() - d2.getTime();	
//			   long diffMinutes = diff / (60 * 1000) % 60;
//			   long diffSeconds = diff / 1000 % 60;
//			   seconds= Math.abs((int) diffSeconds+(int)(60*diffMinutes));
			  d1 = format.parse(starttime);
				d2 = format.parse(Endtime);
				long diff = (d1.getTime() - d2.getTime()) / 1000;
				seconds = (int) diff;
				String temp = String.valueOf(seconds).replaceAll("-", "");
				seconds = Integer.parseInt(temp);
		  }catch (Exception e) {
		   e.printStackTrace();
		  }
		  if(seconds>=1 && seconds<=10){
			  status=true;
		  }
    	return status;
    }
    
    public void insertIntoPreCalTable(String marketId,String testName,String chartType,String voiceConnectivityValue){
    	String insertQuery = "INSERT INTO pre_calc_voiceconnectivity VALUES ('"+marketId+"', '"+testName+"', '"+chartType+"', '"+voiceConnectivityValue+"')";
    	try{
    		st.executeUpdate(insertQuery);
    	}
    	catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    public static void main(String[] args) {
//    	//System.out.println("voiceConnectivityMap-------"+new VoiceConnectivityDaoImpl().getIdleTimeOutOfCoverage( "SGH-M919","33", "g2ref", "mo"));
    	
    	
		System.out.println("callSetupLineChartMoValueList2------------"+new VoiceConnectivityDaoImpl().getAllDeviceInfoCallDropList("D3MNM0710","35","'mo'"));
		
	}
}

