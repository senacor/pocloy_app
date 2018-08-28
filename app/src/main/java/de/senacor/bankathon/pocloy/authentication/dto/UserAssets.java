package de.senacor.bankathon.pocloy.authentication.dto;

import java.util.Date;

public class UserAssets {
    private String loyaltyCode;
    private String status;
    private String content;
    private Date paymentDate;

    public String getLoyaltyCode() {
        return loyaltyCode;
    }

    public void setLoyaltyCode(String loyaltyCode) {
        this.loyaltyCode = loyaltyCode;
    }

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
                "loyaltyCode='" + loyaltyCode + '\'' +
                ", status='" + status + '\'' +
                ", content='" + content + '\'' +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
