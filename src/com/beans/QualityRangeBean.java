package com.beans;

import javax.annotation.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.model.DBUtil;
import com.report.helper.QualityRangeHelper;
import com.to.QualityRangeTo;

@ManagedBean
@RequestScoped
public class QualityRangeBean {

	private int upperRangeForGood;
	private int lowerRangeForGood;
	private int upperRangeForAvg;
	private int lowerRangeForAvg;
	private int upperRangeForPoor;
	private int lowerRangeForPoor;

	private int lteupperRangeForGood;
	private int ltelowerRangeForGood;
	private int lteupperRangeForAvg;
	private int ltelowerRangeForAvg;
	private int lteupperRangeForPoor;
	private int ltelowerRangeForPoor;

	private int custName;
	
	private String customeName;
	


	
	

	public String getCustomeName() {
		return customeName;
	}

	public void setCustomeName(String customeName) {
		this.customeName = customeName;
	}

	public int getCustName() {
		return custName;
	}

	public void setCustName(int custName) {
		this.custName = custName;
	}

	public int getLteupperRangeForGood() {
		return lteupperRangeForGood;
	}

	public void setLteupperRangeForGood(int lteupperRangeForGood) {
		this.lteupperRangeForGood = lteupperRangeForGood;
	}

	public int getLtelowerRangeForGood() {
		return ltelowerRangeForGood;
	}

	public void setLtelowerRangeForGood(int ltelowerRangeForGood) {
		this.ltelowerRangeForGood = ltelowerRangeForGood;
	}

	public int getLteupperRangeForAvg() {
		return lteupperRangeForAvg;
	}

	public void setLteupperRangeForAvg(int lteupperRangeForAvg) {
		this.lteupperRangeForAvg = lteupperRangeForAvg;
	}

	public int getLtelowerRangeForAvg() {
		return ltelowerRangeForAvg;
	}

	public void setLtelowerRangeForAvg(int ltelowerRangeForAvg) {
		this.ltelowerRangeForAvg = ltelowerRangeForAvg;
	}

	public int getLteupperRangeForPoor() {
		return lteupperRangeForPoor;
	}

	public void setLteupperRangeForPoor(int lteupperRangeForPoor) {
		this.lteupperRangeForPoor = lteupperRangeForPoor;
	}

	public int getLtelowerRangeForPoor() {
		return ltelowerRangeForPoor;
	}

	public void setLtelowerRangeForPoor(int ltelowerRangeForPoor) {
		this.ltelowerRangeForPoor = ltelowerRangeForPoor;
	}

	public int getUpperRangeForGood() {
		return upperRangeForGood;
	}

	public void setUpperRangeForGood(int upperRangeForGood) {
		this.upperRangeForGood = upperRangeForGood;
	}

	public int getLowerRangeForGood() {
		return lowerRangeForGood;
	}

	public void setLowerRangeForGood(int lowerRangeForGood) {
		this.lowerRangeForGood = lowerRangeForGood;
	}

	public int getUpperRangeForAvg() {
		return upperRangeForAvg;
	}

	public void setUpperRangeForAvg(int upperRangeForAvg) {
		this.upperRangeForAvg = upperRangeForAvg;
	}

	public int getLowerRangeForAvg() {
		return lowerRangeForAvg;
	}

	public void setLowerRangeForAvg(int lowerRangeForAvg) {
		this.lowerRangeForAvg = lowerRangeForAvg;
	}

	public int getUpperRangeForPoor() {
		return upperRangeForPoor;
	}

	public void setUpperRangeForPoor(int upperRangeForPoor) {
		this.upperRangeForPoor = upperRangeForPoor;
	}

	public int getLowerRangeForPoor() {
		return lowerRangeForPoor;
	}

	public void setLowerRangeForPoor(int lowerRangeForPoor) {
		this.lowerRangeForPoor = lowerRangeForPoor;
	}

	public String configPageToChangeRanges() {
		FacesContext context = FacesContext.getCurrentInstance();
		String userrole = context.getExternalContext().getSessionMap().get(
				"loggedInUserRoleID").toString();
		context.getExternalContext().getSessionMap().put("loggedInUserRoleID",
				userrole);
		context.getExternalContext().getSessionMap().put("userRangesToList",
				new QualityRangeHelper().getAllUserRanges());
		context.getExternalContext().getSessionMap().put("createRangePage",
				"true");
		context.getExternalContext().getSessionMap().remove("reportBean");
		return "configPageToChangeRanges";
	}

	public String getRangesToEdit() {
		QualityRangeHelper qualityHelper = new QualityRangeHelper();
		FacesContext context = FacesContext.getCurrentInstance();
		String cutomizeId = context.getExternalContext()
				.getRequestParameterMap().get("custName");
		//System.out.println("cutomizeId---------" + cutomizeId);
		QualityRangeTo qualityLteTo = qualityHelper
				.getLteQualityrange(cutomizeId);
		QualityRangeTo qualityTo = qualityHelper
				.getNonLteQualityrange(cutomizeId);
		context.getExternalContext().getSessionMap().put("createRangePage",
		"false");
		context.getExternalContext().getSessionMap().put("upperRangeForGood",
				qualityTo.getUpperRangeForGood());
		context.getExternalContext().getSessionMap().put("lowerRangeForGood",
				qualityTo.getLowerRangeForGood());
		context.getExternalContext().getSessionMap().put("upperRangeForPoor",
				qualityTo.getUpperRangeForPoor());
		context.getExternalContext().getSessionMap().put("lowerRangeForPoor",
				qualityTo.getLowerRangeForPoor());
		context.getExternalContext().getSessionMap().put(
				"upperRangeForAverage", qualityTo.getUpperRangeForAvg());
		context.getExternalContext().getSessionMap().put(
				"lowerRangeForAverage", qualityTo.getLowerRangeForAvg());

		context.getExternalContext().getSessionMap().put(
				"lteupperRangeForGood", qualityLteTo.getUpperRangeForGood());
		context.getExternalContext().getSessionMap().put(
				"ltelowerRangeForGood", qualityLteTo.getLowerRangeForGood());
		context.getExternalContext().getSessionMap().put(
				"lteupperRangeForPoor", qualityLteTo.getUpperRangeForPoor());
		context.getExternalContext().getSessionMap().put(
				"ltelowerRangeForPoor", qualityLteTo.getLowerRangeForPoor());
		context.getExternalContext().getSessionMap().put(
				"lteupperRangeForAverage", qualityLteTo.getUpperRangeForAvg());
		context.getExternalContext().getSessionMap().put(
				"ltelowerRangeForAverage", qualityLteTo.getLowerRangeForAvg());

		setUpperRangeForGood(qualityTo.getUpperRangeForGood());
		setLowerRangeForGood(qualityTo.getLowerRangeForGood());
		setUpperRangeForAvg(qualityTo.getUpperRangeForAvg());
		setLowerRangeForAvg(qualityTo.getLowerRangeForAvg());
		setUpperRangeForPoor(qualityTo.getUpperRangeForPoor());
		setLowerRangeForPoor(qualityTo.getLowerRangeForPoor());

		setLteupperRangeForGood(qualityLteTo.getUpperRangeForGood());
		setLtelowerRangeForGood(qualityLteTo.getLowerRangeForGood());
		setLteupperRangeForAvg(qualityLteTo.getUpperRangeForAvg());
		setLtelowerRangeForAvg(qualityLteTo.getLowerRangeForAvg());
		setLteupperRangeForPoor(qualityLteTo.getUpperRangeForPoor());
		setLtelowerRangeForPoor(qualityLteTo.getLowerRangeForPoor());
//		System.out
//				.println("context.getExternalContext().getSessionMap()-------------"
//						+ context.getExternalContext().getSessionMap());
		return "configPageToChangeRanges";
	}

	public String saveRanges() throws Exception {
		//System.out.println("heloooooooooooo");
		FacesContext context = FacesContext.getCurrentInstance();
		String loggedInUserID = context.getExternalContext().getSessionMap().get(
				"loggedInUserID").toString();
		QualityRangeHelper qrh = new QualityRangeHelper();
		int customId = qrh.insertRangeName(customeName, loggedInUserID, DBUtil.getConnection().createStatement());
		qrh.createRanges(lowerRangeForGood, upperRangeForGood, "GOOD", "NONLTE",customId);
		qrh.createRanges(lowerRangeForPoor, upperRangeForPoor, "POOR", "NONLTE",customId);
		qrh.createRanges(lowerRangeForAvg, upperRangeForAvg, "AVERAGE", "NONLTE",customId);
		qrh.createRanges(ltelowerRangeForGood, lteupperRangeForGood, "GOOD",
				"LTE",customId);
		qrh.createRanges(ltelowerRangeForPoor, lteupperRangeForPoor, "POOR",
				"LTE",customId);
		qrh.createRanges(ltelowerRangeForAvg, lteupperRangeForAvg, "AVERAGE",
				"LTE",customId);

		QualityRangeHelper qualityHelper = new QualityRangeHelper();
		QualityRangeTo qualityLteTo = qualityHelper.getNonLteQualityrange("1");
		QualityRangeTo qualityTo = qualityHelper.getLteQualityrange("1");

		setUpperRangeForGood(0);
		setLowerRangeForGood(0);
		setUpperRangeForAvg(0);
		setLowerRangeForAvg(0);
		setUpperRangeForPoor(0);
		setLowerRangeForPoor(0);

		setLteupperRangeForGood(0);
		setLtelowerRangeForGood(0);
		setLteupperRangeForAvg(0);
		setLtelowerRangeForAvg(0);
		setLteupperRangeForPoor(0);
		setLtelowerRangeForPoor(0);
		context.getExternalContext().getSessionMap().put("userRangesToList",
				new QualityRangeHelper().getAllUserRanges());
		return "configPageToChangeRanges";
	}

	public String updateRanges() {
		FacesContext context = FacesContext.getCurrentInstance();
		//System.out.println("getRequestParameterMap-------------"
//				+ context.getExternalContext().getRequestParameterMap());
		String custName = context.getExternalContext().getRequestParameterMap()
				.get("custName");
		new QualityRangeHelper().updateRanges(lowerRangeForGood,
				upperRangeForGood, lowerRangeForPoor, upperRangeForPoor,
				lowerRangeForAvg, upperRangeForAvg, ltelowerRangeForGood,
				lteupperRangeForGood, ltelowerRangeForPoor,
				lteupperRangeForPoor, ltelowerRangeForAvg, lteupperRangeForAvg,
				custName);
		context.getExternalContext().getSessionMap().put("userRangesToList",
				new QualityRangeHelper().getAllUserRanges());
		return "configPageToChangeRanges";
	}
}
