package de.senacor.bankathon.pocloy.authentication.dto;

import java.util.Date;

public class UserAssets {
    //uuid 
    private String codeId;
    private String status;
    //sticker 
    private String content;
    private Date paymentDate;

    public String getLoyaltyCode() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getCodeId() { return codeId; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "UserAssets{" +
                "loyaltyCode='" + codeId + '\'' +
                ", status='" + status + '\'' +
                ", content='" + content + '\'' +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
