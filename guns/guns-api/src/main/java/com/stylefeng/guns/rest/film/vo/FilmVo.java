package com.stylefeng.guns.rest.film.vo;

import java.io.Serializable;

/**
 * Author Jerio
 * CreateTime 2019/11/27 21:02
 **/
public class FilmVo implements Serializable {


    private static final long serialVersionUID = -2630329284485237104L;
    private Integer uuid;

    private String filmName;

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }
}
