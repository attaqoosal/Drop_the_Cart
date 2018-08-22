package andro.com.cart.game.service;

import java.util.List;

import andro.com.cart.game.model.CartVO;

public interface CartService {

	public int ct_insert(CartVO vo);

	public int ct_update(CartVO vo);

	public int ct_delete(CartVO vo);

	public CartVO ct_selectOne(CartVO vo);

	public List<CartVO> ct_selectAll();

}
