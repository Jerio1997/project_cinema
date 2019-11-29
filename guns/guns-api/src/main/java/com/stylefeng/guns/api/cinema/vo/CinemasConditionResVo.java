package com.stylefeng.guns.api.cinema.vo;

import java.io.Serializable;

public class CinemasConditionResVo implements Serializable {

    private static final long serialVersionUID = 806685795773907966L;
    private String cinemaAddress;
    private String cinemaName;
    private int minimumPrice;
    private int uuid;

    public String getCinemaAddress() {
        return cinemaAddress;
    }

    public void setCinemaAddress(String cinemaAddress) {
        this.cinemaAddress = cinemaAddress;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public int getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(int minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }
}
