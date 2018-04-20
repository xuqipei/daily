package com.daily.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by xuqipei on 17-7-17.
 */
@Entity
@Table(name = "daily_merchant_table", schema = "daily", catalog = "")
public class DailyMerchantTable {
    private int id;
    private String tableNo;
    private Integer merchantId;
    private Integer bookStatus;
    private Integer sitReal;
    private Integer capacity;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "increment")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "table_no")
    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    @Basic
    @Column(name = "merchant_id")
    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    @Basic
    @Column(name = "book_status")
    public Integer getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(Integer bookStatus) {
        this.bookStatus = bookStatus;
    }

    @Basic
    @Column(name = "sit_real")
    public Integer getSitReal() {
        return sitReal;
    }

    public void setSitReal(Integer sitReal) {
        this.sitReal = sitReal;
    }

    @Basic
    @Column(name = "capacity")
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyMerchantTable that = (DailyMerchantTable) o;

        if (id != that.id) return false;
        if (tableNo != null ? !tableNo.equals(that.tableNo) : that.tableNo != null) return false;
        if (merchantId != null ? !merchantId.equals(that.merchantId) : that.merchantId != null) return false;
        if (bookStatus != null ? !bookStatus.equals(that.bookStatus) : that.bookStatus != null) return false;
        if (sitReal != null ? !sitReal.equals(that.sitReal) : that.sitReal != null) return false;
        if (capacity != null ? !capacity.equals(that.capacity) : that.capacity != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tableNo != null ? tableNo.hashCode() : 0);
        result = 31 * result + (merchantId != null ? merchantId.hashCode() : 0);
        result = 31 * result + (bookStatus != null ? bookStatus.hashCode() : 0);
        result = 31 * result + (sitReal != null ? sitReal.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        return result;
    }
}
