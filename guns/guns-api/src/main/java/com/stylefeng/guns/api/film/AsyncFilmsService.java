package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

public interface AsyncFilmsService {

    FilmDescVO getFilmDesc(String filmId);

    ImgVO getImgs(String filmId);

    ActorVO getDectInfo(String filmId);

    List<ActorVO> getActors(String filmId);

}