package BusStation;

import javafx.beans.property.StringProperty;

public class ExtimeInfo {
	String EXTIME_MIN;
	String ROUTE_NO;
	String LAST_STOP_ID;
	String ROUTE_TP;
	String STATUS_POS;
	
	public ExtimeInfo(String EXTIME_MIN,String ROUTE_NO,String ROUTE_TP,String LAST_STOP_ID,String STATUS_POS) {
		this.EXTIME_MIN = EXTIME_MIN;
		this.ROUTE_NO = ROUTE_NO;
		this.ROUTE_TP = ROUTE_TP;
		this.LAST_STOP_ID = LAST_STOP_ID;
		this.STATUS_POS = STATUS_POS;
	}
	
	public String getROUTE_TP() {
		return ROUTE_TP;
	}

	public void setROUTE_TP(String rOUTE_TP) {
		ROUTE_TP = rOUTE_TP;
	}

	public String getEXTIME_MIN() {
		return EXTIME_MIN;
	}
	public void setEXTIME_MIN(String eXTIME_MIN) {
		EXTIME_MIN = eXTIME_MIN;
	}
	public String getROUTE_NO() {
		return ROUTE_NO;
	}
	public void setROUTE_NO(String rOUTE_NO) {
		ROUTE_NO = rOUTE_NO;
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
