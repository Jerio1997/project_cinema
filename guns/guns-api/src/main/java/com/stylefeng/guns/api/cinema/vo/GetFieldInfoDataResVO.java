package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetFieldInfoDataResVO implements Serializable {

    private static final long serialVersionUID = 3951791910585266091L;

    private CinemaInfoBean cinemaInfo;

    private FilmInfoBean filmInfo;

    private HallInfoBean hallInfo;

    @Data
    public static class CinemaInfoBean implements Serializable {
        private static final long serialVersionUID = -8344143994157981298L;
        /**
         * cinemaAdress : 北京市顺义区华联金街购物中心
         * cinemaId : 1
         * cinemaName : 大地影院(顺义店)
         * cinemaPhone : 18500003333
         * imgUrl : cinema6.jpg
         */

        private String cinemaAdress;

        private int cinemaId;

        private String cinemaName;

        private String cinemaPhone;

        private String imgUrl;


    }

    @Data
    public static class FilmInfoBean implements Serializable {
        private static final long serialVersionUID = 7862445240187989413L;
        /**
         * actors :
         * filmCats : 喜剧,剧情
         * filmFields :
         * filmId : 2
         * filmLength : 117
         * filmName : 我不是药神
         * filmType : 国语2D
         * imgAddress : 238e2dc36beae55a71cabfc14069fe78236351.jpg
         */

        private String actors;

        private String filmCats;

        private String filmFields;

        private Integer filmId;

        private String filmLength;

        private String filmName;

        private String filmType;

        private String imgAddress;

    }

    @Data
    public static class HallInfoBean implements Serializable {
        private static final long serialVersionUID = -7207997779757020354L;
        /**
         * discountPrice :
         * hallFieldId : 1
         * hallName : 一号厅
         * price : 60
         * seatFile : seats/jumu.json
         * soldSeats : 11,12,13,14,15,16,17,18,19,20,21,22,10,45,37,38,48,43,40,50,47,29,27,26,44
         */

        private String discountPrice;

        private int hallFieldId;        //fieldId

        private String hallName;

        private Integer price;

        private String seatFile;

        private String soldSeats;

    }

}
