package com.springcloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springcloud.dao.OrderDetailMapper;
import com.springcloud.entity.OrderDetail;
import com.springcloud.service.OrdersDetailService;

/**
 * 订单明细模块的模型层的实现类，用于实现订单明细模块的方法
 * 
 * @author Jiangshengju
 *
 */
@Service
public class OrdersDetailServiceImpl implements OrdersDetailService {

	@Autowired
	private OrderDetailMapper ordersDetailMapper;

	@Override
	public PageInfo<OrderDetail> selectByOrderId(Integer orderId, Integer pageNumber) {
		PageHelper.startPage(pageNumber + 1, 5);
		List<OrderDetail> list = this.ordersDetailMapper.selectByOrderId(orderId);
		return new PageInfo<>(list);
	}
}
