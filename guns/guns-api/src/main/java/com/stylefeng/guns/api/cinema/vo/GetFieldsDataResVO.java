package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GetFieldsDataResVO implements Serializable {
    private static final long serialVersionUID = -2741669332875335231L;
    private CinemaInfoBean cinemaInfo;
    private List<FilmListBean> filmList;
    @Data
    public static class CinemaInfoBean implements Serializable {
        private static final long serialVersionUID = -5479201348173479268L;
        /**
             * cinemaAdress : 北京市顺义区华联金街购物中心
             * cinemaId : 1
             * cinemaName : 大地影院(顺义店)
             * cinemaPhone : 18500003333
             * imgUrl : cinema6.jpg
             */
            //cinemaAddress
            private String cinemaAdress;

            private Integer cinemaId;

            private String cinemaName;

            private String cinemaPhone;

            private String imgUrl;
        }
    @Data
    public static class FilmListBean implements Serializable{
        private static final long serialVersionUID = -6688826676840574663L;
        /**
             * actors : 程勇,曹斌,吕受益,刘思慧
             * filmCats : 喜剧,剧情
             * filmFields : [{"beginTime":"09:50","endTime":"11:20","fieldId":1,"hallName":"一号厅","language":"国语2D","price":"60"}]
             * filmId : 2
             * filmLength : 117
             * filmName : 我不是药神
             * filmType : 国语2D
             * imgAddress : 238e2dc36beae55a71cabfc14069fe78236351.jpg
             */

            private String actors;  //*table mtime_hall_film_info_t

            private String filmCats;

            private Integer filmId;

            private String filmLength;

            private String filmName;

            private String filmType;    // table mtime_hall_film_info_t film_language

            private String imgAddress;// table mtime_hall_film_info_t */

            private List<FilmFieldsBean> filmFields;


            @Data
            public static class FilmFieldsBean implements Serializable {
                private static final long serialVersionUID = 1100395860762526239L;
                /**
                 * beginTime : 09:50
                 * endTime : 11:20
                 * fieldId : 1
                 * hallName : 一号厅
                 * language : 国语2D
                 * price : 60
                 */

                private String beginTime;//* table field

                private String endTime;

                private Integer fieldId;

                private String hallName;// table field*/

                private String language;   // table mtime_hall_film_info_t film_language

                private Integer price;  // table field



            }
        }

}
