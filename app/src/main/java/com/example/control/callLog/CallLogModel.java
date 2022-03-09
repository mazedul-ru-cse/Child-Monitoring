package com.example.control.callLog;

public class CallLogModel {
    String callDate, callDuration, callType, callTime, contactName, phNumber;

    public  CallLogModel(){

    }

    public CallLogModel(String callDate, String callDuration, String callTime ,String callType, String contactName, String phNumber) {
        this.callDate = callDate;
        this.callDuration = callDuration;
        this.callType = callType;
        this.callTime = callTime;
        this.contactName = contactName;
        this.phNumber = phNumber;
    }

    public String getCallDate() {
        return callDate;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public String getCallType() {
        return callType;
    }

    public String getCallTime() {
        return callTime;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

}
