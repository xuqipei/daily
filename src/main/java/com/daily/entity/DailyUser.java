package com.daily.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by xuqipei on 17-7-17.
 */
@Entity
@Table(name = "daily_user", schema = "daily", catalog = "")
public class DailyUser {
    private int id;
    private String username;
    private String password;
    private String mobilephone;
    private String nickname;
    private Integer age;
    private Timestamp brithday;
    private String gender;
    private Timestamp createTime;

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
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "mobilephone")
    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Basic
    @Column(name = "brithday")
    public Timestamp getBrithday() {
        return brithday;
    }

    public void setBrithday(Timestamp brithday) {
        this.brithday = brithday;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "create_time")
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

        DailyUser dailyUser = (DailyUser) o;

        if (id != dailyUser.id) return false;
        if (username != null ? !username.equals(dailyUser.username) : dailyUser.username != null) return false;
        if (password != null ? !password.equals(dailyUser.password) : dailyUser.password != null) return false;
        if (mobilephone != null ? !mobilephone.equals(dailyUser.mobilephone) : dailyUser.mobilephone != null)
            return false;
        if (nickname != null ? !nickname.equals(dailyUser.nickname) : dailyUser.nickname != null) return false;
        if (age != null ? !age.equals(dailyUser.age) : dailyUser.age != null) return false;
        if (brithday != null ? !brithday.equals(dailyUser.brithday) : dailyUser.brithday != null) return false;
        if (gender != null ? !gender.equals(dailyUser.gender) : dailyUser.gender != null) return false;
        if (createTime != null ? !createTime.equals(dailyUser.createTime) : dailyUser.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (mobilephone != null ? mobilephone.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (brithday != null ? brithday.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
