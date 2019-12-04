package com.stylefeng.guns.api.pay;

import com.stylefeng.guns.api.pay.vo.BaseResVO;
import com.stylefeng.guns.api.pay.vo.GetPayInfoResVO;
import com.stylefeng.guns.api.pay.vo.GetPayResultResVO;

public interface PayService {
    BaseResVO<GetPayInfoResVO> getPayInfo(Integer orderId);

    BaseResVO<GetPayResultResVO> getPayResult(Integer orderId, Integer tryNums);
}
