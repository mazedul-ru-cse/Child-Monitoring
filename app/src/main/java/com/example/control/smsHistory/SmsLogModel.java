package com.example.control.smsHistory;

public class SmsLogModel {

    String smsBody ,smsDate , smsId ,  smsType;

    public SmsLogModel() {
    }

    public SmsLogModel(String smsBody, String smsDate, String smsId, String smsType) {
        this.smsBody = smsBody;
        this.smsDate = smsDate;
        this.smsId = smsId;
        this.smsType = smsType;
    }

    public String getSmsBody() {
        return smsBody;
    }

    public String getSmsDate() {
        return smsDate;
    }

    public String getSmsId() {
        return smsId;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsBody(String smsBody) {
        this.smsBody = smsBody;
    }

    public void setSmsDate(String smsDate) {
        this.smsDate = smsDate;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }
}
