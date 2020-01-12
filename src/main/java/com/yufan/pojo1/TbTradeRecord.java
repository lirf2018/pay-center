package com.yufan.pojo1;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 18:52
 * 功能介绍:
 */
@Entity
@Table(name = "tb_trade_record", schema = "pay_center", catalog = "")
public class TbTradeRecord {
    private int id;
    private String orderNo;
    private String tradeNo;
    private String partnerTradeNo;
    private Byte recordType;
    private Byte status;
    private String remark;
    private Timestamp createTime;
    private Timestamp updateTime;
    private BigDecimal price;
    private Byte payWay;
    private String tradeAcount;
    private Timestamp finishTime;
    private Timestamp submitTime;
    private String returnUrl;

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "order_no", nullable = true, length = 100)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Basic
    @Column(name = "trade_no", nullable = true, length = 100)
    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Basic
    @Column(name = "partner_trade_no", nullable = true, length = 100)
    public String getPartnerTradeNo() {
        return partnerTradeNo;
    }

    public void setPartnerTradeNo(String partnerTradeNo) {
        this.partnerTradeNo = partnerTradeNo;
    }

    @Basic
    @Column(name = "record_type", nullable = true)
    public Byte getRecordType() {
        return recordType;
    }

    public void setRecordType(Byte recordType) {
        this.recordType = recordType;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "price", nullable = true, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "pay_way", nullable = true)
    public Byte getPayWay() {
        return payWay;
    }

    public void setPayWay(Byte payWay) {
        this.payWay = payWay;
    }

    @Basic
    @Column(name = "trade_acount", nullable = true, length = 100)
    public String getTradeAcount() {
        return tradeAcount;
    }

    public void setTradeAcount(String tradeAcount) {
        this.tradeAcount = tradeAcount;
    }

    @Basic
    @Column(name = "finish_time", nullable = true)
    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    @Basic
    @Column(name = "submit_time", nullable = true)
    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    @Basic
    @Column(name = "return_url", nullable = true, length = 200)
    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TbTradeRecord that = (TbTradeRecord) o;
        return id == that.id &&
                Objects.equals(orderNo, that.orderNo) &&
                Objects.equals(tradeNo, that.tradeNo) &&
                Objects.equals(partnerTradeNo, that.partnerTradeNo) &&
                Objects.equals(recordType, that.recordType) &&
                Objects.equals(status, that.status) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(price, that.price) &&
                Objects.equals(payWay, that.payWay) &&
                Objects.equals(tradeAcount, that.tradeAcount) &&
                Objects.equals(finishTime, that.finishTime) &&
                Objects.equals(submitTime, that.submitTime) &&
                Objects.equals(returnUrl, that.returnUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNo, tradeNo, partnerTradeNo, recordType, status, remark, createTime, updateTime, price, payWay, tradeAcount, finishTime, submitTime, returnUrl);
    }
}
