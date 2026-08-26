package com.insurance.insuranceportal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponse {

    private Long paymentId;

    private String paymentReference;

    private Long policyId;

    private String policyNumber;

    private Long userId;

    private String customerName;

    private BigDecimal amount;

    private String paymentMethod;

    private String status;

    private String transactionId;

    private LocalDateTime paidAt;

    private String message;

    public PaymentResponse() {
    }

    public PaymentResponse(
            Long paymentId,
            String paymentReference,
            Long policyId,
            String policyNumber,
            Long userId,
            String customerName,
            BigDecimal amount,
            String paymentMethod,
            String status,
            String transactionId,
            LocalDateTime paidAt,
            String message
    ) {
        this.paymentId = paymentId;
        this.paymentReference = paymentReference;
        this.policyId = policyId;
        this.policyNumber = policyNumber;
        this.userId = userId;
        this.customerName = customerName;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.transactionId = transactionId;
        this.paidAt = paidAt;
        this.message = message;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(
            String paymentReference
    ) {
        this.paymentReference = paymentReference;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(
            String policyNumber
    ) {
        this.policyNumber = policyNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(
            String customerName
    ) {
        this.customerName = customerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(
            String paymentMethod
    ) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(
            String transactionId
    ) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}