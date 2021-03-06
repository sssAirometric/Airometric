package com.dao;

import java.util.List;
import java.util.Map;

import com.report.to.SaveConfigTo;
import com.to.CommentsTo;
import com.to.ConfigBean;
import com.to.DeviceInfoTO;
import com.to.DeviceTo;
import com.to.FileHistoryTO;
import com.to.ReportBean;
import com.to.STGDevice;
import com.to.STGNetTest;

public interface ReportDao {
	public List<STGDevice> getTestNameMapDetailsResults(String testCaseName,String marketmapId);
	public List<STGDevice> getTestNameMapDetailsResultsForVQT(String testCaseName,String marketmapId,List<String>vqtlist);
	public List<STGDevice> getTestNameMapMarketDetailsResults(
			String testCaseName, String Market);

	public List<STGDevice> getTestNameDashboardDetails(String testCaseName);
	public List<String> getMinandMaxTimestampforNetCall(String testCaseName,String marketId);
	public List<DeviceInfoTO> getTestNameThroughputDetailsResults(
			String testCaseName,String marketId,String testtype);

	public List<DeviceInfoTO> getTestNameMultipleThroughputDetailsResults(
			String testCaseName,String marketId,String test_type);

	public boolean getCycleTestNames(String testCaseName);

	public boolean getCycleTestNamesInDeviceInfo(String testCaseName,String marketId);

	public boolean getCycleTestNamesMarketInDeviceInfo(String testCaseName,
			String market);

	public List<STGNetTest> getTestNameMapDetailsResultsForNetTest(
			String testCaseName);

	public Map<String, String> getTestNames(String deviceName);

	public Map<String, String> getMarketIds(String testname, String deviceName,String testType);
	
	public Map<String, String> getDeviceForTest(String testname);

	public Map<String, String> getModelNames(String userIds,String roleName);

	public String getTestId(String testName);

	public  List<Float> getDownlinkParmatersForGraph(String deviceName,
			String marketId, String testname, String filesName);
	public String getDeviceAvgVoiceQty(String deviceName, String marketName,
			String testName);

	public void insertComments(CommentsTo commentTo);

	public void insertSummaryComments(CommentsTo commentTo);

	public DeviceTo getRefDevice(String testCaseName);

	public String getMarketName(String MarketName);
	
	public String getMarketIdList(String MarketName);
	
	public String getMarketNameList(String MarketName);
	
	public String getNeighbourInfo(List<String> neighbourInfoList);

	public List<String> getNetworkdetails(String networkName,
			DeviceInfoTO deviceInfo, String SIGNALSTRENGTH_GSM,
			String SIGNALSTRENGTH_LTE, String SIGNALSTRENGTH_GSM1,
			List signalStrengthList, List signalStrengthListRating,
			List signalStrengthSnrList, List signalStrengthCDMACIOList,
			List signalStrengthEVDOECIOList, List signalStrengthLTERSRPList,
			List signalStrengthLTERSRQList, List signalStrengthLTERSSNRList,
			List signalStrengthLTECQIList, List signalStrengthLt, int i,
			List<DeviceInfoTO> deviceInfoList);

	public Map<String, String> getTestTypes(String deviceName,
			String tesName);
	
	public Map<String, String> getFiles(String testname, String deviceName,
			String testType,String marketId);
	public void insertKpiScore(Integer savedTestConfigId,Map<Integer, SaveConfigTo> kpiScoreMap,Integer userId, String testCaseName, String marketId, String valueSummary);
	public void insertKpiComments(Integer savedTestConfigId,Map<Integer,String> kpiCommentMap,Integer userId);
	public Integer insertConfigMaster(String configName, Integer userId, Integer statusId);
	public List<String> getConfigurationNames();
	public Integer getConfigurationId(String configName);

	public void insertConfigComments(Integer savedTestConfigId, Integer userId, String testSummaryComment);

	public void insertConfigCompare(Integer savedTestConfigId, Integer userId, String lConfigRouteName, String lDataType, String lMarkgetId, String lFileName, String lDeviceName);	

	public void manageCounter(Integer userId,Integer counterType);
	public List<FileHistoryTO> getMarketFiledetails(List<String>myList);
	public List<String> getFilesForMarket(String testname, String deviceName,String testType, String marketId);
	public Map<String, String> getMarketForTest(String testcaseName);
	public Map<String, String> getTestTypeForMarket(String testcaseName,String marketId);
	public Map<String, String> getFreqBandList(String testcaseName);
	public String getThroughput(int i,String eventName,DeviceInfoTO deviceInfo,
			 String currTxBytes,String THROUGHPUT,double txvalue,String throughputTX,String prevTxBytes,String throughputRX,String currRxBytes
			 ,double rxvalue,String prevRxBytes,double mainValue,String throughputMain);
	public Map<String, String> getConfigurations();
	public List<ConfigBean> getConfigurationDetails(String configurationId);
	
	public List<String> getMinandMaxTimestampforVQT(String testCaseName,String marketId);
	public Integer getGeneratedMapCount(Integer counterType, Integer tUserId);
	public List<ReportBean> getConfigurationComments(String configurationId);
}
