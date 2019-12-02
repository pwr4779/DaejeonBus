package BusStation;

import javafx.beans.property.StringProperty;

public class ExtimeInfo {
	String EXTIME_MIN;
	String ROUTE_CD;
	String LAST_STOP_ID;
	String STATUS_POS;
	
	public ExtimeInfo(String EXTIME_MIN,String ROUTE_CD,String LAST_STOP_ID,String STATUS_POS) {
		this.EXTIME_MIN = EXTIME_MIN;
		this.ROUTE_CD = ROUTE_CD;
		this.LAST_STOP_ID = LAST_STOP_ID;
		this.STATUS_POS = STATUS_POS;
	}
	
	public String getEXTIME_MIN() {
		return EXTIME_MIN;
	}
	public void setEXTIME_MIN(String eXTIME_MIN) {
		EXTIME_MIN = eXTIME_MIN;
	}
	public String getROUTE_CD() {
		return ROUTE_CD;
	}
	public void setROUTE_CD(String rOUTE_TP) {
		ROUTE_CD = rOUTE_TP;
	}
	
	public String getLAST_STOP_ID() {
		return LAST_STOP_ID;
	}
	public void setLAST_STOP_ID(String lAST_STOP_ID) {
		LAST_STOP_ID = lAST_STOP_ID;
	}
	public String getSTATUS_POS() {
		return STATUS_POS;
	}
	public void setSTATUS_POS(String sTATUS_POS) {
		STATUS_POS = sTATUS_POS;
	}
	
}
