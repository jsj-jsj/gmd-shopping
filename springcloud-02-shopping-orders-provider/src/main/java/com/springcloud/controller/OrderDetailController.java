package com.springcloud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.springcloud.common.PageUtils;
import com.springcloud.entity.OrderDetail;
import com.springcloud.service.OrdersDetailService;
import com.springcloud.vo.ResultValue;

/**
 * 订单明细模块的控制层
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("orderDetail")
public class OrderDetailController {

	@Autowired
	private OrdersDetailService ordersDetailService;
	
	@RequestMapping(value="/selectByOrderId")
	public ResultValue selectByOrderId(@RequestParam("orderId") Integer orderId, @RequestParam("pageNumber") Integer pageNumber) {
		ResultValue rv = new ResultValue();
		
		try {
			//调用service相应的方法查询所有一级类别的信息，并保存查询结果
			PageInfo<OrderDetail> pageInfo = this.ordersDetailService.selectByOrderId(orderId, pageNumber);
			List<OrderDetail> list = pageInfo.getList();
			//如果查询成功
			if(list != null && list.size() > 0) {
				//设置结果状态标记为0
				rv.setCode(0);
				//创建Map集合
				Map<String, Object> map = new HashMap<>();
				//将查询结果存入Map集合中
				map.put("orderDetailList",list);
				
				PageUtils pageUtils = new PageUtils(pageInfo.getPages() * 5);
				pageUtils.setPageNumber(pageNumber);
				map.put("pageUtils", pageUtils);
				
				//来讲Map集合存入ResultValue对象中
				rv.setDateMap(map);
				//返回ResultValue对象
				return rv;
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		rv.setMessage("没有找到满足条件的订单明细信息！！！");
		return rv;
	}
}
