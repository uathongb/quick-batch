package com.springbatch.quickbatch.model;

/**
 * @Author SHOCKBLAST
 * @PackageName com.springbatch.quickbatch.model
 * @ClassName UserInfo
 * @Date 2018/9/13
 * @Version 1.0
 * @Description:
 */
public class UserInfo {
    private String userName;

    private String idCard;

    private String cardNo;

    private String mobileNo;

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
