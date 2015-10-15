package com.preprocessing.reports;

import java.util.Locale;
import java.util.ResourceBundle;

public class  PreprocessExport {

	public static String exportReport(String kpiName,String testNames,String allMarkets) {
		
		testNames = testNames.replaceAll(",", "','");
	    Locale currentLocale = new Locale("es");
		ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource",currentLocale);
		String folder=resourceBundle.getString("DOWNLOAD_FOLDER");
		String filePath = folder+"\\Precalculation\\PreCalculationInfo.xls";;
//		System.out.println(filePath);
		
		new RenderHelper().getReport(testNames,kpiName,filePath,allMarkets);
		return filePath;
	}

//	public static void main(String[] args) {
//		exportReport("VC","DCvoice700","");
//	}
}
