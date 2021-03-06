package com.to;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

public class BrowserBean implements Serializable{
	
	private static final long serialVersionUID = 8606023157764621343L;
	private String deviceName;
	private String marketName;
	private String httpAvgTotalLatency;
	private String httpAvgReadRate;
	private String timeStamp;
	private String httpAvgConnct;
	private Float pageDownloadSpeed;
	private String pageDownloadStartTime;
	private String pageDownloadEndTime;
	private String avgTime;
	
	private String testName;
	private String event_name;
	private String event_value;
	
	
	

	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getAvgTime() {
		return avgTime;
	}
	public void setAvgTime(String avgTime) {
		this.avgTime = avgTime;
	}
	public String getPageDownloadStartTime() {
		return pageDownloadStartTime;
	}
	public void setPageDownloadStartTime(String pageDownloadStartTime) {
		this.pageDownloadStartTime = pageDownloadStartTime;
	}
	public String getPageDownloadEndTime() {
		return pageDownloadEndTime;
	}
	public void setPageDownloadEndTime(String pageDownloadStartEnd) {
		this.pageDownloadEndTime = pageDownloadStartEnd;
	}
	public Float getPageDownloadSpeed() {
		return pageDownloadSpeed;
	}
	public void setPageDownloadSpeed(Float pageDownloadSpeed) {
		this.pageDownloadSpeed = pageDownloadSpeed;
	}
	public String getEvent_name() {
		return event_name;
	}
	public void setEvent_name(String eventName) {
		event_name = eventName;
	}
	public String getEvent_value() {
		return event_value;
	}
	public void setEvent_value(String eventValue) {
		event_value = eventValue;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public String getHttpAvgTotalLatency() {
		return httpAvgTotalLatency;
	}
	public void setHttpAvgTotalLatency(String httpAvgTotalLatency) {
		this.httpAvgTotalLatency = httpAvgTotalLatency;
	}
	public String getHttpAvgReadRate() {
		return httpAvgReadRate;
	}
	public void setHttpAvgReadRate(String httpAvgReadRate) {
		this.httpAvgReadRate = httpAvgReadRate;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getHttpAvgConnct() {
		return httpAvgConnct;
	}
	public void setHttpAvgConnct(String httpAvgConnct) {
		this.httpAvgConnct = httpAvgConnct;
	}
	
	
	private void populateKpiDataForCharts(List<String> deviceNamse,
			String[] marketId, String testName,
			org.json.me.JSONArray jsoArrMain, List<String> allMarketsList,
			List<String> allTestsList, List<String> allFilesList,HashMap<String, List<String>> marketFileMap,
			int VQTCount,int FinalMOCount,int FinalFTPCount,int FinalExternalCount)
			throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();
		for (int j = 0; j < allMarketsList.size(); j++) {
			if(!allMarketsList.get(j).equalsIgnoreCase("") ){
				String masterMarketId = allMarketsList.get(j).trim();
				for (int a = 0; a < deviceNamse.size(); a++) {
					String masterDeviceName = deviceNamse.get(a);
					//iterating over selected test list
					for (int b = 0; b < allTestsList.size(); b++) {
						String masterTesName = allTestsList.get(b);
						for (int c = 0; c < allFilesList.size(); c++) {
							String masterFileName = allFilesList.get(c);
							for (int k = 0; k < jsoArrMain.length(); k++) {
//								System.out.println("jsoArrMain.length(:"+jsoArrMain.length());
								int count=0;
								int vqtCount=0;
								int moCount=0;
								int ftpCount=0;
								int ExtCount=0;
								org.json.me.JSONObject configObi = new org.json.me.JSONObject(jsoArrMain.get(k).toString());
								String jsonDeviceName = configObi.getString("deviceName");
								String jsonTestName = configObi.getString("testName");
								String markets = configObi.getString("marketName");
								String mrktId=markets.substring(1, markets.length()-1);
								String mrktId_value[]=mrktId.split(",");
								count=count+mrktId_value.length;
								String tempFilesName = configObi.get("filesName").toString().replaceAll("\"", "").replace("[", "").replace("]", "");

								List<String> jsonFilesList = new ArrayList<String>(
										Arrays.asList(tempFilesName.split(",")));
								
//								System.out.println("configObi :"+configObi.toString());
								String tempMarketsList = configObi.get("marketName").toString().trim().replaceAll("\"", "").replace("[", "").replace("]", "");
//								System.out.println("tempMarketsList :"+tempMarketsList);
								
								List<String> tempjsonMarketsList = new ArrayList<String>(
										Arrays.asList(tempMarketsList.split(",")));
								List<String> jsonMarketsList = new ArrayList<String>();
								List<String> marketFilesList = marketFileMap.get(masterDeviceName+masterTesName+masterMarketId);
								String filesStr = "";
								for(int i=0;i<tempjsonMarketsList.size();i++){
//									System.out.println(tempjsonMarketsList.get(i)+"----airometric------"+tempjsonMarketsList.get(i).length());
									jsonMarketsList.add(tempjsonMarketsList.get(i).trim());
								}
								//iterating json array element for each combination to
								//construct chart data
								Map<String,List<String>> marketFiles = (Map<String,List<String>>)context.getExternalContext().getSessionMap().get("marketwiseFiles");
//								System.out.println("marketFiles :"+marketFiles);
//								System.out.println("masterFileName :"+masterFileName);
								boolean isFilePresent = false;
								for(int i=1;i<3;i++){
									if(null!=marketFiles.get(masterDeviceName+"_"+masterTesName+"_"+masterMarketId+"_"+i)&&
											marketFiles.get(masterDeviceName+"_"+masterTesName+"_"+masterMarketId+"_"+i).contains(masterFileName.replaceAll("__--__",":" ))){
										isFilePresent = true;
									}
								}
								if ((null!=jsonFilesList)&&jsonDeviceName.equalsIgnoreCase(masterDeviceName)
										&& jsonTestName.equalsIgnoreCase(masterTesName)
										&& jsonMarketsList.contains(masterMarketId)
										&& isFilePresent) {
									new BrowserDaoImpl().getPageDownloadThroughPut(jsonTestName,masterMarketId);
								}
								}
							}
						}
					}
				}
			}
		}

	public static void main(String[] args) {
		try{
			SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
			Date date = format2.parse("8/6/2013  4:33:45 AM");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
