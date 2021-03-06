package com.preprocessing.reports;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.helper.Misc;
import com.model.DBUtil;
import com.to.DeviceInfoTO;
import com.to.VoiceConnectivityTO;

public class PreprocessingVCCallRetentionReport extends PreprocessingVCReport {

	private static List<VoiceConnectivityTO> getPreVoiceData(String testNames){
		List<VoiceConnectivityTO> voiceDataList = new ArrayList<VoiceConnectivityTO>();
		String query = "SELECT PC.*,M.MARKET_NAME FROM pre_cal_callretention_1 PC,MARKET M WHERE PC.TEST_NAME IN ('"+testNames+"') AND M.MARKET_ID = PC.MARKET_ID";
//		System.out.println("query--------"+query);
		List<DeviceInfoTO> deviceInfoList = null;
		Connection conn = DBUtil.getConnection();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				VoiceConnectivityTO voiceConnectivityTo = new VoiceConnectivityTO();
				String marketName = rs.getString("MARKET_NAME");
				String timeStamp = rs.getString("PARAMETER_VALUE");
				String test_name = rs.getString("TEST_NAME");
				String call_Control_Event = rs.getString("PARAMETER");
				
				
				
				String imei = rs.getString("DEVICE_IMEI");
				String phonenumber = rs.getString("DEVICE_PHONENUMBER");
				String phonetype = rs.getString("DEVICE_PHONETYPE");
				String devicemodel = rs.getString("DEVICE_MODEL");
				String deviceversion = rs.getString("DEVICE_VERSION");
				String cellcid = rs.getString("CELLLOCATION_CID");
				String cellLac = rs.getString("CELLLOCATION_LAC");
				String dataState = rs.getString("DATA_STATE");
				String networkType = rs.getString("NETWORK_TYPE");
				String signalStrength = rs.getString("SIGNAL_STRENGTH");
				int lattitude = rs.getInt("GEOLOCATION_LATITUDE");
				int longitude = rs.getInt("GEOLOCATION_LANGITUDE");
				voiceConnectivityTo.setMarketName(marketName);
				voiceConnectivityTo.setTestName(test_name);
				voiceConnectivityTo.setTime_stamp(timeStamp);
				voiceConnectivityTo.setCall_Control_Event(call_Control_Event);
				
				voiceConnectivityTo.setImei(imei);
				voiceConnectivityTo.setPhoneNumber(phonenumber);
				voiceConnectivityTo.setDevicePhoneType(phonetype);
				voiceConnectivityTo.setDevicemodel(devicemodel);
				voiceConnectivityTo.setDeviceversion(deviceversion);
				voiceConnectivityTo.setCellLocationCID(cellcid);
				voiceConnectivityTo.setCellLocationLAC(cellLac);
				voiceConnectivityTo.setDataState(dataState);
				voiceConnectivityTo.setNetworkType(networkType);
				voiceConnectivityTo.setSignalStrength(signalStrength);
				voiceConnectivityTo.setLattitude(lattitude);
				voiceConnectivityTo.setLongitude(longitude);
				voiceDataList.add(voiceConnectivityTo);
			}
			deviceInfoList = getDeviceInfo("'"+testNames+"'","mo",st);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return voiceDataList;
	}

	public static List<VoiceConnectivityTO> getPreVoiceDataForLogcat(String testNames){
		List<VoiceConnectivityTO> dropCallList = new ArrayList<VoiceConnectivityTO>();
		testNames = testNames.replaceAll("'", "");
//		System.out.println("testNames----"+testNames);
		String[] testNamesArr = testNames.split(",");
		StringBuilder allCycletestNames = new StringBuilder();
		String testNamesQuery = "SELECT DISTINCT TEST_NAME FROM stg_log_cat_info WHERE TEST_NAME LIKE ";
		Connection conn = DBUtil.getConnection();
		List<DeviceInfoTO> deviceInfoList = null;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = null;
			for(int i=0;i<testNamesArr.length;i++){
//				System.out.println("testNamesArr[i]--------"+testNamesArr[i]);
//				System.out.println(testNamesQuery+"'"+testNamesArr[i]+"%'");
				 rs = st.executeQuery(testNamesQuery+"'"+testNamesArr[i]+"%'");
				 while(rs.next()){
					 allCycletestNames.append("'"+rs.getString("TEST_NAME")+"',");
				 }
			}
			allCycletestNames.setLength(allCycletestNames.length()-1);
			String query = "SELECT * FROM STG_LOG_CAT_INFO WHERE TEST_NAME IN ("+allCycletestNames+") AND EVENT_NAME IN('CALL_DROP') order by test_identifier_timestamp" ;
//			System.out.println(query);
			rs = st.executeQuery(query);
			
			while(rs.next()){
				VoiceConnectivityTO voiceConnectivityTo = new VoiceConnectivityTO();
				String marketName = "";
				String timeStamp = rs.getString("Time_Stamp");
				String test_name = rs.getString("TEST_NAME");
				String vQuadLatLong = "";
				String call_Control_Event = rs.getString("EVENT_NAME");
				String timeStampIdentifier = rs.getString("TEST_IDENTIFIER_TIMESTAMP");
				String cc_condition = "";
//				System.out.println(marketName);
				voiceConnectivityTo.setMarketName(marketName);
				voiceConnectivityTo.setTimeStampIdentifier(timeStampIdentifier);
				voiceConnectivityTo.setTestName(test_name);
				voiceConnectivityTo.setTime_stamp(timeStamp);
				voiceConnectivityTo.setvQuadLatLong(vQuadLatLong);
				voiceConnectivityTo.setCall_Control_Event(call_Control_Event);
				voiceConnectivityTo.setCc_condition(cc_condition);
				dropCallList.add(voiceConnectivityTo);
			}
			deviceInfoList = getDeviceInfo(allCycletestNames.toString(),"mo",st);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getMatchingVoiceConnectivityTo(deviceInfoList,dropCallList,false);
	}
	private static List<DeviceInfoTO> getDeviceInfo(String allCycletestNames,String test_Type,Statement st){
		List<DeviceInfoTO> deviceInfoList = new ArrayList<DeviceInfoTO>();
		String query = "SELECT M.MARKET_ID,M.MARKET_NAME,DI.TIME_STAMP_FOREACH_SAMPLE,DI.TEST_IDENTIFIER_TIMESTAMP,DI.NETWORK_TYPE," +
				"DI.GEOLOCATION_LATITUDE,DI.GEOLOCATION_LANGITUDE,DI.SIGNALSTRENGTH_LTESIGNALSTRENGTH,DI.SIGNALSTRENGTH_GSMSIGNALSTRENGTH,DI.SIGNALSTRENGTH_LTERSRP FROM " +
				"STG_DEVICE_INFO DI , MARKET M WHERE DI.TEST_NAME IN ("+allCycletestNames+") AND DI.MARKET_ID = M.MARKET_ID " +
						"AND (SIGNALSTRENGTH_LTESIGNALSTRENGTH !='Empty' OR SIGNALSTRENGTH_GSMSIGNALSTRENGTH !='Empty') ORDER BY TIME_STAMP_FOREACH_SAMPLE";
		try{
//			System.out.println(query);
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				String networkType = rs.getString("NETWORK_TYPE");
				int rsrp = 0;
				if(rs.getString("SIGNALSTRENGTH_LTERSRP").equalsIgnoreCase("Empty")){
					rsrp = rs.getInt("SIGNALSTRENGTH_LTERSRP");
				}
				if(!(networkType.contains("LTE"))||((networkType.contains("LTE"))&&rsrp<1000)){
				DeviceInfoTO dto = new DeviceInfoTO();
				String timeStampIdentifier = rs.getString("TEST_IDENTIFIER_TIMESTAMP");
				double lattitude = rs.getDouble("GEOLOCATION_LATITUDE");
				double longitude = rs.getDouble("GEOLOCATION_LANGITUDE");
				String market = rs.getString("MARKET_NAME");
				String marketId = rs.getString("MARKET_ID");
				float signalStrength = 0;
				if (networkType.equalsIgnoreCase("LTE")
						|| networkType.equalsIgnoreCase("LTE (4G)")) {
					signalStrength = 
							 (new Float(
									new Float(
											rs.getString("SIGNALSTRENGTH_LTERSRP"))));
					networkType = "LTE";
				} else {

					signalStrength =-113+2*(new Float(
									new Float(
											rs.getString("SIGNALSTRENGTH_GSMSIGNALSTRENGTH"))));
				}
				
				String timeStampForEachSample = rs.getString("TIME_STAMP_FOREACH_SAMPLE");
				dto.setTimeStampForEachSample(timeStampForEachSample);
				dto.setSignalStrength(new Float(signalStrength).toString());
				dto.setNetworkType(networkType);
				dto.setTimeStampIdentifier(timeStampIdentifier);
				dto.setLattitude(lattitude);
				dto.setLongitude(longitude);
				dto.setMarket(market);
				dto.setMarketId(marketId);
				deviceInfoList.add(dto);
			}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return deviceInfoList;
	}
	
	private static List<VoiceConnectivityTO> getMatchingVoiceConnectivityTo(List<DeviceInfoTO> deviceInfoList,List<VoiceConnectivityTO> dropCallList,boolean isCallevents){
		int found = 0;
		List<VoiceConnectivityTO> voiceConnectivityTOList = new ArrayList<VoiceConnectivityTO>();
		for(int i=0;i< dropCallList.size();i++){
			VoiceConnectivityTO vcTo = dropCallList.get(i);
			for(DeviceInfoTO dto : deviceInfoList){
				
				if(((isCallevents||vcTo.getTimeStampIdentifier().equalsIgnoreCase(dto.getTimeStampIdentifier()))&&
						Misc.matchCallTimestamp(vcTo.getTime_stamp(), dto.getTimeStampForEachSample()))){
					vcTo.setNetworkType(dto.getNetworkType());
					vcTo.setSignalStrength(dto.getSignalStrength());
					vcTo.setLattitude(dto.getLattitude());
					vcTo.setLongitude(dto.getLongitude());
					vcTo.setvQuadLatLong(dto.getLattitude()+""+dto.getLongitude());
					vcTo.setMarketName(dto.getMarket());
					vcTo.setTime_stamp(dto.getTimeStampForEachSample());
					voiceConnectivityTOList.add(vcTo);
					found++;
					break;
				}
			}
		}
		return voiceConnectivityTOList;
	}
	 static void renderReport(
			List<VoiceConnectivityTO> voiceDataList) {
		for(VoiceConnectivityTO voiceConnectivityTO : voiceDataList ){
			int colIndex = 1;
			writeToCell(voiceConnectivityTO.getMarketName(),rowIndex,colIndex,cellFormat1);
			colIndex++;
			writeToCell(voiceConnectivityTO.getTestName(),rowIndex,colIndex,cellFormat1);
			colIndex++;
			writeToCell(voiceConnectivityTO.getTime_stamp().toString(),rowIndex,colIndex,cellFormat1);
			colIndex++;
			writeToCell(voiceConnectivityTO.getCall_Control_Event(),rowIndex,colIndex,cellFormat1);
			colIndex++;
			
			
			
			writeToCell(voiceConnectivityTO.getImei(),rowIndex,colIndex,cellFormat1);
			colIndex++;
			writeToCell(voiceConnectivityTO.getPhoneNumber(),rowIndex,colIndex,cellFormat1);
			colIndex++;
			writeToCell(voiceConnectivityTO.getDevicePhoneType(),rowIndex,colIndex,cellFormat1);
			colIndex++;
			
			writeToCell(voiceConnectivityTO.getDevicemodel(),rowIndex,colIndex,cellFormat1);
			colIndex++;
			writeToCell(voiceConnectivityTO.getDeviceversion(),rowIndex,colIndex,cellFormat1);
			colIndex++;
			writeToCell(voiceConnectivityTO.getCellLocationCID(),rowIndex,colIndex,cellFormat1);
			colIndex++;
			writeToCell(voiceConnectivityTO.getCellLocationLAC(),rowIndex,colIndex,cellFormat1);
			colIndex++;
			writeToCell(voiceConnectivityTO.getDataState(),rowIndex,colIndex,cellFormat1);
			colIndex++;
			writeToCell(voiceConnectivityTO.getNetworkType(),rowIndex,colIndex,cellFormat1);
			colIndex++;
			writeToCell(voiceConnectivityTO.getSignalStrength(),rowIndex,colIndex,cellFormat1);
			colIndex++;
			writeToCell(new Double(voiceConnectivityTO.getLattitude()).toString(),rowIndex,colIndex,cellFormat1);
			colIndex++;
			writeToCell(new Double(voiceConnectivityTO.getLongitude()).toString(),rowIndex,colIndex,cellFormat1);
			colIndex++;
			rowIndex++;
		}
	}

	public static void renderRententionReport(String filePath,String testNames) {

		List<String> labelList = new ArrayList<String>();
		rowIndex = 1;
		colIndex = 1;
		labelList.add("Market Name");
		labelList.add("Test Name");
		labelList.add("Call_Timestamp");
		labelList.add("Call_Control_Event Name");
		
		
		labelList.add("DEVICE_IMEI");
		labelList.add("DEVICE_PHONENUMBER");
		labelList.add("DEVICE_PHONETYPE");
		
		labelList.add("DEVICE_MODEL");
		labelList.add("DEVICE_VERSION");
		labelList.add("CELLLOCATION_CID");
		labelList.add("CELLLOCATION_LAC");
		labelList.add("DATA_STATE");
		labelList.add("NETWORK TYPE");
		labelList.add("SIGNAL STRENGTH");
		labelList.add("LATTITUDE");
		labelList.add("LONGITUDE");
		//System.out.println("helooooooo");
		try {
			File exlFile = new File(filePath);
			instantiateFonts(exlFile, "Call Events");
			createHeaderLabels(labelList);
			List<VoiceConnectivityTO> vqList = getPreVoiceData(testNames);
//			System.out.println("vqList.isEmpty()---------"+vqList.isEmpty());
		/*	if(vqList.isEmpty()){
				vqList = getPreVoiceDataForLogcat(testNames);
			}*/
			renderReport(vqList);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		getPreVoiceDataForLogcat("D3CHV0905");
	}
}
