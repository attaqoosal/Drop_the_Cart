package andro.com.cart.game.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import andro.com.cart.game.model.CartDAO;
import andro.com.cart.game.model.CartVO;

@Service
public class CartServiceimpl implements CartService {
	private static final Logger logger = LoggerFactory.getLogger(CartServiceimpl.class);

	@Autowired
	CartDAO dao;

	public CartServiceimpl() {
		logger.info("CartServiceimpl()...");
	}

	@Override
	public int ct_insert(CartVO vo) {
		logger.info("ct_insert()...");
		return dao.ct_insert(vo);
	}

	@Override
	public int ct_update(CartVO vo) {
		logger.info("ct_update()...");
		return dao.ct_update(vo);
	}

	@Override
	public int ct_delete(CartVO vo) {
		logger.info("ct_delete()...");
		return dao.ct_delete(vo);
	}

	@Override
	public CartVO ct_selectOne(CartVO vo) {
		logger.info("ct_selectOne()...");
		return dao.ct_selectOne(vo);
	}

	@Override
	public List<CartVO> ct_selectAll() {
		logger.info("ct_selectAll()...");
		return dao.ct_selectAll();
	}
}
