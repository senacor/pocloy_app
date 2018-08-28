package de.senacor.bankathon.pocloy.authentication.dto;

import java.util.Date;

public class UnpackedSticker {
    private String status;
    private String content;
    private Date paymentDate;
    private String codeId;

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

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }


    @Override
    public String toString() {
        return "UnpackedSticker{" +
                "loyaltyCode='" + codeId + '\'' +
                ", status='" + status + '\'' +
                ", content='" + content + '\'' +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
