package com.springcloud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.springcloud.common.PageUtils;
import com.springcloud.entity.Orders;
import com.springcloud.service.OrdersService;
import com.springcloud.vo.ResultValue;

/**
 * 订单模块的控制层
 * 
 * @author 17384
 *
 */
@RestController
@RequestMapping("orders")
public class OrdersController {

	@Autowired
	private OrdersService ordersService;

	@RequestMapping(value = "/selectOrders")
	public ResultValue selectOrders(Orders orders, @RequestParam("pageNumber") Integer pageNumber) {
		ResultValue rv = new ResultValue();

		try {
			// 查找满足条件的订单信息
			PageInfo<Orders> pageInfo = this.ordersService.selectOrders(orders, pageNumber);
			// 从分页信息总获得订单信息
			List<Orders> list = pageInfo.getList();
			// 如果查询到了，满足条件的订单信息
			if (list != null && list.size() > 0) {
				// 设置结果的状态为0
				rv.setCode(0);
				// 创建Map集合
				Map<String, Object> map = new HashMap<>();
				// 将查询结果存入Map集合中
				map.put("ordersList", list);

				PageUtils pageUtils = new PageUtils(pageInfo.getPages() * PageUtils.PAGE_ROW_COUNT);
				pageUtils.setPageNumber(pageNumber);
				// 将分页信息以指定的名字存入Map集合中
				map.put("pageUtils", pageUtils);

				rv.setDateMap(map);
				return rv;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		rv.setCode(1);
		rv.setMessage("没有找到满足条件的订单信息！！！");
		return rv;
	}

	/**
	 * 修改指定编号订单的订单状态
	 * 
	 * @param orders 订单编号
	 * @return
	 */
	@RequestMapping(value = "/updateOrdersStatus")
	public ResultValue updateOrdersStatus(Orders orders) {
		ResultValue rv = new ResultValue();

		try {
			// 查找满足条件的商品信息
			Integer status = this.ordersService.updateOrdersStatus(orders);
			// 如果查询到了，满足条件的商品信息
			if (status > 0) {
				// 设置结果的状态为0
				rv.setCode(0);
				rv.setMessage("修改订单状态成功！！！");
				return rv;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		rv.setCode(1);
		rv.setMessage("修改订单状态失败！！！");
		return rv;

	}

	/**
	 * 查询指定日期范围内的销售额
	 * 
	 * @param orders 查询的限制条件
	 * @return 成功返回大于0的整数，否则返回小于0的整数
	 */
	@RequestMapping(value = "/selectGroup")
	public ResultValue selectGroup(Orders orders) {
		ResultValue rv = new ResultValue();

		try {
			List<Orders> list = this.ordersService.selectGroup(orders);
			if (list != null && list.size() > 0) {	// .size() 集合中元素的个数
				rv.setCode(0);
				// 创建两个集合，用于报讯柱状图x轴与y轴的数据
				List<String> x = new ArrayList<>();	// ArrayList<>(100) 可以指定长度	LinkList<>() 不能指定长度
				List<Double> y = new ArrayList<>();
				// 将查询结果中商品保存到x轴中，查询的销售量保存到y轴中
				for (Orders o : list) {
					x.add(o.getOrderMonth());
					y.add(o.getOrderPrice());
				}
				// 创建map集合
				Map<String, Object> map = new HashMap<>();
				// 将x轴和y轴数据存到map集合中
				map.put("x", x);
				map.put("y", y);
				rv.setDateMap(map);

				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		return rv;

	}

}
