package andro.com.cart.member.model;

import java.util.ArrayList;

public interface memberDAO {
	public int insert(memberVO vo);
	public int update(memberVO vo);
	public int delete(memberVO vo);
	public memberVO selectOne(memberVO vo);
	public memberVO selectID(memberVO vo);
	public ArrayList<memberVO> selectAll();
	public memberVO login(memberVO vo);
}
