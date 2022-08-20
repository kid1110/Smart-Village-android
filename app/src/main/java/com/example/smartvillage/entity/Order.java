package com.example.smartvillage.entity;

import java.io.Serializable;

public class Order implements Serializable {
    private Integer oid;
    private Integer uid;
    private String address;
    private Integer typeId;
    private String orderDate;

    public Order(Integer oid, Integer uid, String address, Integer typeId, String orderDate) {
        this.oid = oid;
        this.uid = uid;
        this.address = address;
        this.typeId = typeId;
        this.orderDate = orderDate;
    }

    public Order(Integer uid, String address, Integer typeId, String orderDate) {
        this.uid = uid;
        this.address = address;
        this.typeId = typeId;
        this.orderDate = orderDate;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid=" + oid +
                ", uid=" + uid +
                ", address='" + address + '\'' +
                ", typeId=" + typeId +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}
