package com.daily.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by xuqipei on 17-7-17.
 */
@Entity
@Table(name = "daily_merchant", schema = "daily", catalog = "")
public class DailyMerchant {
    private int id;
    private String loginName;
    private String loginPassword;
    private String merchantName;
    private String address;
    private String contact;
    private String bannerUrl;

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
    @Column(name = "login_name")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Basic
    @Column(name = "login_password")
    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    @Basic
    @Column(name = "merchant_name")
    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Basic
    @Column(name = "banner_url")
    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyMerchant that = (DailyMerchant) o;

        if (id != that.id) return false;
        if (loginName != null ? !loginName.equals(that.loginName) : that.loginName != null) return false;
        if (loginPassword != null ? !loginPassword.equals(that.loginPassword) : that.loginPassword != null)
            return false;
        if (merchantName != null ? !merchantName.equals(that.merchantName) : that.merchantName != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (bannerUrl != null ? !bannerUrl.equals(that.bannerUrl) : that.bannerUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (loginPassword != null ? loginPassword.hashCode() : 0);
        result = 31 * result + (merchantName != null ? merchantName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (bannerUrl != null ? bannerUrl.hashCode() : 0);
        return result;
    }
}
