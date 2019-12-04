package com.alipay.demo.trade.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import com.alipay.api.response.AlipayTradePrecreateResponse;

import com.alipay.demo.trade.Main;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.stylefeng.guns.api.cinema.CinemaService;
import com.stylefeng.guns.api.cinema.vo.MtimeCinema;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.pay.PayService;
import com.stylefeng.guns.api.pay.vo.BaseResVO;
import com.stylefeng.guns.api.pay.vo.GetPayInfoResVO;
import com.stylefeng.guns.api.pay.vo.GetPayResultResVO;
import com.stylefeng.guns.api.order.vo.MtimeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = PayService.class,loadbalance = "roundrobin" )
public class PayServiceImpl implements PayService {
    @Reference(interfaceClass = OrderService.class,check = false)
    OrderService orderService;
    @Reference(interfaceClass = CinemaService.class,check = false)
    CinemaService cinemaService;
    @Autowired
    Main main;


    @Override
    public BaseResVO<GetPayInfoResVO> getPayInfo(Integer orderId) {

        BaseResVO<GetPayInfoResVO> baseResVO = new BaseResVO<>();
        MtimeOrder mtimeOrder =  orderService.queryOrderById(orderId);
        MtimeCinema mtimeCinema = cinemaService.queryCinemaById(mtimeOrder.getCinemaId());
        AlipayF2FPrecreateResult result = main.test_trade_precreate(mtimeOrder, mtimeCinema);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                System.out.println("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();

                // 需要修改为运行机器上的路径
                //用http协议访问
                String filePath = String.format("C:\\nginx-1.15.12\\html/qr-%s.png",
                        response.getOutTradeNo());
                System.out.println("filePath:" + filePath);
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
                GetPayInfoResVO getPayInfoResVO = new GetPayInfoResVO();
                String getfilePath = String.format("http://localhost/qr-%s.png",
                        response.getOutTradeNo());
                getPayInfoResVO.setQRCodeAddress(getfilePath);
                getPayInfoResVO.setOrderId(orderId);
                baseResVO.setData(getPayInfoResVO);
                baseResVO.setStatus(0);
                baseResVO.setImgPre("");
                break;

            case FAILED:
                baseResVO.setStatus(1);
                baseResVO.setMsg("订单支付失败，请稍后重试");
                break;

            case UNKNOWN:
                baseResVO.setStatus(999);
                baseResVO.setMsg("系统出现异常，请联系管理员");
                break;
        }
        return baseResVO;
    }

    @Override
    public BaseResVO<GetPayResultResVO> getPayResult(Integer orderId, Integer tryNums) {
        BaseResVO<GetPayResultResVO> baseResVO = new BaseResVO<>();
        GetPayResultResVO getPayInfoResVO = new GetPayResultResVO();
        if(tryNums<= 3){
            AlipayF2FQueryResult result = main.test_trade_query(orderId);
            switch (result.getTradeStatus()) {
                case SUCCESS:
                    int i = orderService.updateOrderStatus(orderId,1);
                    getPayInfoResVO.setOrderId(orderId);
                    getPayInfoResVO.setOrderStatus(1);
                    getPayInfoResVO.setOrderMsg("支付成功");
                    baseResVO.setData(getPayInfoResVO);
                    baseResVO.setStatus(0);
                    break;

                case FAILED:
                    baseResVO.setStatus(1);
                    baseResVO.setMsg("支付失败！");
                    break;

                case UNKNOWN:
                    baseResVO.setStatus(999);
                    baseResVO.setMsg("系统出现异常，请联系管理员");
                    break;
            }
        }
        if(tryNums == 3){
            int i = orderService.updateOrderStatus(orderId,2);
        }
        return baseResVO;

    }

}
