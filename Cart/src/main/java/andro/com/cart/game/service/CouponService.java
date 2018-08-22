package andro.com.cart.game.service;

import java.util.List;

import andro.com.cart.game.model.CouponVO;

public interface CouponService {

	public int cu_insert(CouponVO vo);

	public int cu_update(CouponVO vo);

	public int cu_delete(CouponVO vo);

	public CouponVO cu_selectOne(CouponVO vo);

	public List<CouponVO> cu_selectAll();
}
