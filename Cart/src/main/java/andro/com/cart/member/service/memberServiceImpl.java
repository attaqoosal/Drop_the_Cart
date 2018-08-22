package andro.com.cart.member.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import andro.com.cart.member.model.memberDAO;
import andro.com.cart.member.model.memberDAOImpl;
import andro.com.cart.member.model.memberVO;

@Service
public class memberServiceImpl implements memberService {

	@Autowired
	memberDAO dao;
	
	@Override
	public int insert(memberVO vo) {
		return dao.insert(vo);
	}

	@Override
	public int update(memberVO vo) {
		return dao.update(vo);
	}

	@Override
	public int delete(memberVO vo) {
		return dao.delete(vo);
	}

	@Override
	public memberVO selectOne(memberVO vo) {
		return dao.selectOne(vo);
	}

	@Override
	public memberVO selectID(memberVO vo) {
		return dao.selectID(vo);
	}
	
	@Override
	public ArrayList<memberVO> selectAll() {
		return dao.selectAll();
	}
}
