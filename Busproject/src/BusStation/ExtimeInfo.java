package BusStation;


public class ExtimeInfo {
	private String EXTIME_MIN;
	private String ROUTE_NO;
	private String ROUTE_TP;
	private String STATUS_POS;
	private String DESTINATION;
	private String MSG_TP;
	
	public ExtimeInfo(String EXTIME_MIN,String ROUTE_NO,String ROUTE_TP,String STATUS_POS, String DESTINATION, String MSG_TP) {
		this.EXTIME_MIN = EXTIME_MIN;
		this.ROUTE_NO = ROUTE_NO;
		this.ROUTE_TP = ROUTE_TP;
		this.STATUS_POS = STATUS_POS;
		this.DESTINATION =DESTINATION;
		this.MSG_TP =MSG_TP;
	}
	
	public String getDESTINATION() {
		return DESTINATION;
	}

	public void setDESTINATION(String dESTINATION) {
		DESTINATION = dESTINATION;
	}

	public String getMSG_TP() {
		return MSG_TP;
	}

	public void setMSG_TP(String mSG_TP) {
		MSG_TP = mSG_TP;
	}
	
	public String getROUTE_TP() {
		return this.ROUTE_TP;
	}

	public void setROUTE_TP(String rOUTE_TP) {
		this.ROUTE_TP = rOUTE_TP;
	}

	public String getEXTIME_MIN() {
		return this.EXTIME_MIN;
	}
	public void setEXTIME_MIN(String eXTIME_MIN) {
		this.EXTIME_MIN = eXTIME_MIN;
	}
	public String getROUTE_NO() {
		return this.ROUTE_NO;
	}
	public void setROUTE_NO(String rOUTE_NO) {
		this.ROUTE_NO = rOUTE_NO;
	}

	public String getSTATUS_POS() {
		return this.STATUS_POS;
	}
	public void setSTATUS_POS(String sTATUS_POS) {
		this.STATUS_POS = sTATUS_POS;
	}
	
}
