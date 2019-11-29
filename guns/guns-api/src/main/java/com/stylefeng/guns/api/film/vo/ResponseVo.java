package com.stylefeng.guns.api.film.vo;

import lombok.Data;

@Data
public class ResponseVo<T> {
    private int status;
    private T data;
    private String imgPre;
    private String msg;
    private int nowPage;
    private int totalPage;

    public ResponseVo() {
    }

    public static<T> ResponseVo success(String imgPre,int nowPage,int totalPage,T t){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setImgPre(imgPre);
        responseVo.setNowPage(nowPage);
        responseVo.setTotalPage(totalPage);
        responseVo.setStatus(0);
        responseVo.setData(t);

        return responseVo;
    }

    public static<T> ResponseVo serviceException(String msg){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(1);
        responseVo.setMsg(msg);

        return responseVo;
    }

    public static<T> ResponseVo systemException(String msg){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(999);
        responseVo.setMsg(msg);

        return responseVo;
    }
}
