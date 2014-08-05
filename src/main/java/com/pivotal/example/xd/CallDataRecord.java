package com.pivotal.example.xd;


public class CallDataRecord {
	
	public CallDataRecord(String cdrType, long imsi, long imei, long callerNo,
			long calleeNo, long recordingEntity, long areaCode,
			long callReference, double callDuration, String answerTime,
			String seizureTime, String releaseTime, long causeForTermination,
			long basicService, long mscAddress) {
		this.cdrType = cdrType;
		this.imsi = imsi;
		this.imei = imei;
		this.callerNo = callerNo;
		this.calleeNo = calleeNo;
		this.recordingEntity = recordingEntity;
		this.areaCode = areaCode;
		this.callReference = callReference;
		this.callDuration = callDuration;
		this.answerTime = answerTime;
		this.seizureTime = seizureTime;
		this.releaseTime = releaseTime;
		this.causeForTermination = causeForTermination;
		this.basicService = basicService;
		this.mscAddress = mscAddress;
	}
	public String getCdrType() {
		return cdrType;
	}
	public void setCdrType(String cdrType) {
		this.cdrType = cdrType;
	}
	public long getImsi() {
		return imsi;
	}
	public void setImsi(long imsi) {
		this.imsi = imsi;
	}
	public long getImei() {
		return imei;
	}
	public void setImei(long imei) {
		this.imei = imei;
	}
	public long getCallerNo() {
		return callerNo;
	}
	public void setCallerNo(long callerNo) {
		this.callerNo = callerNo;
	}
	public long getCalleeNo() {
		return calleeNo;
	}
	public void setCalleeNo(long calleeNo) {
		this.calleeNo = calleeNo;
	}
	public long getRecordingEntity() {
		return recordingEntity;
	}
	public void setRecordingEntity(long recordingEntity) {
		this.recordingEntity = recordingEntity;
	}
	public long getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(long areaCode) {
		this.areaCode = areaCode;
	}
	public long getCallReference() {
		return callReference;
	}
	public void setCallReference(long callReference) {
		this.callReference = callReference;
	}
	public double getCallDuration() {
		return callDuration;
	}
	public void setCallDuration(double callDuration) {
		this.callDuration = callDuration;
	}
	public String getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(String answerTime) {
		this.answerTime = answerTime;
	}
	public String getSeizureTime() {
		return seizureTime;
	}
	public void setSeizureTime(String seizureTime) {
		this.seizureTime = seizureTime;
	}
	public String getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	public long getCauseForTermination() {
		return causeForTermination;
	}
	public void setCauseForTermination(long causeForTermination) {
		this.causeForTermination = causeForTermination;
	}
	public long getBasicService() {
		return basicService;
	}
	public void setBasicService(long basicService) {
		this.basicService = basicService;
	}
	public long getMscAddress() {
		return mscAddress;
	}
	public void setMscAddress(long mscAddress) {
		this.mscAddress = mscAddress;
	}
	private String cdrType;
	private long imsi;
	private long imei;
	private long callerNo;
	private long calleeNo;
	private long recordingEntity;
	private long areaCode;
	private long callReference;
	private double callDuration;
	private String answerTime;
	private String seizureTime;
	private String releaseTime;
	private long causeForTermination;
	private long basicService;
	private long mscAddress;

}
