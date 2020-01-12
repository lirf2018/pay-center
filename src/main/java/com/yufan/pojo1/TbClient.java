package com.yufan.pojo1;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 21:29
 * 功能介绍:
 */
@Entity
@Table(name = "tb_client", schema = "pay_center", catalog = "")
public class TbClient {
    private int clientId;
    private String secretKey;
    private String clientName;
    private String clientCode;
    private String quitUrl;
    private String returnUrl;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte status;

    @Id
    @Column(name = "client_id", nullable = false)
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "secret_key", nullable = true, length = 50)
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Basic
    @Column(name = "client_name", nullable = true, length = 20)
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Basic
    @Column(name = "client_code", nullable = true, length = 30)
    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
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

    @Basic
    @Column(name = "update_time", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TbClient tbClient = (TbClient) o;
        return clientId == tbClient.clientId &&
                Objects.equals(secretKey, tbClient.secretKey) &&
                Objects.equals(clientName, tbClient.clientName) &&
                Objects.equals(clientCode, tbClient.clientCode) &&
                Objects.equals(quitUrl, tbClient.quitUrl) &&
                Objects.equals(returnUrl, tbClient.returnUrl) &&
                Objects.equals(createTime, tbClient.createTime) &&
                Objects.equals(updateTime, tbClient.updateTime) &&
                Objects.equals(status, tbClient.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, secretKey, clientName, clientCode, quitUrl, returnUrl, createTime, updateTime, status);
    }
}
