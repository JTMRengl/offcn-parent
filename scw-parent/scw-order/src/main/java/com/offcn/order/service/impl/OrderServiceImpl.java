package com.offcn.order.service.impl;

import com.netflix.discovery.converters.Auto;
import com.offcn.dycommon.enums.OrderStatusEnumes;
import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.mapper.TOrderMapper;
import com.offcn.order.po.TOrder;
import com.offcn.order.service.OrderService;
import com.offcn.order.service.ProjectServiceFeign;
import com.offcn.order.vo.req.OrderInfoSubmitVo;
import com.offcn.order.vo.resp.TReturn;
import com.offcn.utils.AppDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TOrderMapper orderMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectServiceFeign projectServiceFeign;

    @Override
    public TOrder saveOrder(OrderInfoSubmitVo vo) {
        TOrder order = new TOrder();

        String accessToken = vo.getAccessToken();
        String memberId = redisTemplate.opsForValue().get( accessToken );
        order.setMemberid(Integer.parseInt( memberId )  );


        //项目ID
        order.setProjectid(vo.getProjectid());
        //回报项目ID
        order.setReturnid(vo.getReturnid());
        //生成订单编号
        String orderNum = UUID.randomUUID().toString().replace("-","");
        order.setOrdernum(orderNum);

        //订单创建时间
        order.setCreatedate( AppDateUtils.getFormatTime());

        //回报 order微服务调用project微服务
        AppResponse<List<TReturn>> projectReturns = projectServiceFeign.getProjectReturns( vo.getProjectid() );
        List<TReturn> tReturns = projectReturns.getData();

        TReturn tReturn = tReturns.get( 0 );

        //计算回报金额   支持数量*支持金额+运费
        Integer totalMoney = vo.getRtncount() * tReturn.getSupportmoney() + tReturn.getFreight();
        order.setMoney( totalMoney );

        order.setRtncount( vo.getRtncount() );

        order.setStatus( OrderStatusEnumes.UNPAY.getCode() + "");
        //收货地址
        order.setAddress(vo.getAddress());
        //是否开发票
        order.setInvoice(vo.getInvoice().toString());
        //发票名头
        order.setInvoictitle(vo.getInvoictitle());
        //备注
        order.setRemark(vo.getRemark());
        orderMapper.insertSelective( order );

        return  order;
    }
}
