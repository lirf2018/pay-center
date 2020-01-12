package com.yufan.pojo;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 18:52
 * 功能介绍:
 */
@Entity
@Table(name = "tb_client_config", schema = "pay_center", catalog = "")
public class TbClientConfig {
    private int id;
    private Integer clientId;
    private String businessCode;
    private String quitUrl;
    private String returnUrl;
    private Timestamp createTime;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "client_id", nullable = true)
    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "business_code", nullable = true, length = 30)
    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    @Basic
    @Column(name = "quit_url", nullable = true, length = 200)
    public String getQuitUrl() {
        return quitUrl;
    }

    public void setQuitUrl(String quitUrl) {
        this.quitUrl = quitUrl;
    }

    @Basic
    @Column(name = "return_url", nullable = true, length = 200)
    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TbClientConfig that = (TbClientConfig) o;
        return id == that.id &&
                Objects.equals(clientId, that.clientId) &&
                Objects.equals(businessCode, that.businessCode) &&
                Objects.equals(quitUrl, that.quitUrl) &&
                Objects.equals(returnUrl, that.returnUrl) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, businessCode, quitUrl, returnUrl, createTime);
    }
}
