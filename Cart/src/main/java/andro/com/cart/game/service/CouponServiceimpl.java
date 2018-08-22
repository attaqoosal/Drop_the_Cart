package andro.com.cart.game.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import andro.com.cart.game.model.CouponDAO;
import andro.com.cart.game.model.CouponVO;

@Service
public class CouponServiceimpl implements CouponService {
	private static final Logger logger = LoggerFactory.getLogger(CouponServiceimpl.class);

	@Autowired
	CouponDAO dao;

	public CouponServiceimpl() {
		logger.info("CouponServiceimpl()...");
	}

	@Override
	public int cu_insert(CouponVO vo) {
		logger.info("cu_insert()...");
		return dao.cu_insert(vo);
	}

	@Override
	public int cu_update(CouponVO vo) {
		logger.info("cu_update()...");
		return dao.cu_update(vo);
	}

	@Override
	public int cu_delete(CouponVO vo) {
		logger.info("cu_delete()...");
		return dao.cu_delete(vo);
	}

	@Override
	public CouponVO cu_selectOne(CouponVO vo) {
		logger.info("cu_selectOne()...");
		return dao.cu_selectOne(vo);
	}

	@Override
	public List<CouponVO> cu_selectAll() {
		logger.info("cu_selectAll()...");
		return dao.cu_selectAll();
	}
}