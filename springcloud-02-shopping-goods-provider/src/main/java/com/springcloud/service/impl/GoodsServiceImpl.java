package com.springcloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springcloud.dao.GoodsMapper;
import com.springcloud.entity.Goods;
import com.springcloud.service.GoodsService;

/**
 * 商品模型层的实现类，用于实现商品模块的方法
 * 
 * @author asus-1005
 *
 */
@Service
public class GoodsServiceImpl implements GoodsService {		

	@Autowired
	private GoodsMapper goodsMapper;
	
	@Transactional
	@Override
	public Integer insert(Goods goods) {
		return this.goodsMapper.insert(goods);
	}

	@Override
	public PageInfo<Goods> select(Goods goods, Integer pageNumber) {	//查询满足条件的商品信息(分页功能)
		//在商品名称李两端加上%
		goods.setGoodsName("%" + goods.getGoodsName() + "%");
		//设置分页信息
		PageHelper.startPage(pageNumber + 1,8);
		//查询满足条件的商品信息
		List<Goods> list = this.goodsMapper.select(goods);
		//返回分页信息
		
		return new PageInfo<>(list);
	}
	
	@Transactional
	@Override
	public Integer updateGoodsById(Goods goods) {	//根据条件修改商品信息
		return this.goodsMapper.updateGoodsById(goods);
	}
	
	
	@Override
	public Integer update(Goods goods) {	//修改指定编号的商品信息
		return this.goodsMapper.updateByPrimaryKey(goods);
	}

	@Override
	public List<Goods> selectGroup() {	//查询销量前十的商品信息
		return this.goodsMapper.selectGroup();
	}

}
