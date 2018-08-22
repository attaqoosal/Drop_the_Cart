package andro.com.cart.game.model;

import java.util.List;

public interface CouponDAO {

	public int cu_insert(CouponVO vo);

	public int cu_update(CouponVO vo);

	public int cu_delete(CouponVO vo);

	public CouponVO cu_selectOne(CouponVO vo);

	public List<CouponVO> cu_selectAll();

}
