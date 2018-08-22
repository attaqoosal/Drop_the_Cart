package andro.com.cart.game.model;

import java.util.List;

public interface CartDAO {

	public int ct_insert(CartVO vo);

	public int ct_update(CartVO vo);

	public int ct_delete(CartVO vo);

	public CartVO ct_selectOne(CartVO vo);

	public List<CartVO> ct_selectAll();

}
