package com.daily.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by xuqipei on 17-7-17.
 */
@Entity
@Table(name = "daily_menu", schema = "daily", catalog = "")
public class DailyMenu {
    private int id;
    private String name;
    private String bannerUrl;
    private Integer categoryId;
    private String menuOption;
    private BigDecimal price;
    private String description;
    private Integer discount;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "banner_url")
    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    @Basic
    @Column(name = "category_id")
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "menu_option")
    public String getMenuOption() {
        return menuOption;
    }

    public void setMenuOption(String menuOption) {
        this.menuOption = menuOption;
    }

    @Basic
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "discount")
    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyMenu dailyMenu = (DailyMenu) o;

        if (id != dailyMenu.id) return false;
        if (name != null ? !name.equals(dailyMenu.name) : dailyMenu.name != null) return false;
        if (bannerUrl != null ? !bannerUrl.equals(dailyMenu.bannerUrl) : dailyMenu.bannerUrl != null) return false;
        if (categoryId != null ? !categoryId.equals(dailyMenu.categoryId) : dailyMenu.categoryId != null) return false;
        if (menuOption != null ? !menuOption.equals(dailyMenu.menuOption) : dailyMenu.menuOption != null) return false;
        if (price != null ? !price.equals(dailyMenu.price) : dailyMenu.price != null) return false;
        if (description != null ? !description.equals(dailyMenu.description) : dailyMenu.description != null) return false;
        if (discount != null ? !discount.equals(dailyMenu.discount) : dailyMenu.discount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (bannerUrl != null ? bannerUrl.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (menuOption != null ? menuOption.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        return result;
    }
}
