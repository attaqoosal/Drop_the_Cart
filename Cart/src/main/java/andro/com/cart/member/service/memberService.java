package andro.com.cart.member.service;

import java.util.ArrayList;

import andro.com.cart.member.model.memberVO;

public interface memberService {
	public int insert(memberVO vo);
	public int update(memberVO vo);
	public int delete(memberVO vo);
	public memberVO selectOne(memberVO vo);
	public memberVO selectID(memberVO vo);
	public ArrayList<memberVO> selectAll();
}
