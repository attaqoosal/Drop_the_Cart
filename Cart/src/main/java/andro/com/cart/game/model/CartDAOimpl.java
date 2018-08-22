package andro.com.cart.game.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CartDAOimpl implements CartDAO {
	private static final Logger logger = LoggerFactory.getLogger(CartDAOimpl.class);

	@Autowired
	private SqlSession sqlSession;

	public CartDAOimpl() {
		logger.info("CartDAOimpl()....");
	}

	@Override
	public int ct_insert(CartVO vo) {
		logger.info("ct_insert()....");
		logger.info("name: " + vo.getName());
		logger.info("price: " + vo.getPrice());
		logger.info("imgName: " + vo.getImgName());
		int flag = sqlSession.insert("ct_insert", vo);
		return flag;
	}

	@Override
	public int ct_update(CartVO vo) {
		logger.info("ct_update()....");
		logger.info("num: " + vo.getNum());
		logger.info("name: " + vo.getName());
		logger.info("price: " + vo.getPrice());
		logger.info("imgName: " + vo.getImgName());
		int flag = sqlSession.update("ct_update", vo);
		return flag;
	}

	@Override
	public int ct_delete(CartVO vo) {
		logger.info("ct_delete()....");
		logger.info("num: " + vo.getNum());
		int flag = sqlSession.delete("ct_delete", vo);
		return flag;
	}

	@Override
	public CartVO ct_selectOne(CartVO vo) {
		logger.info("ct_selectOne()....");
		logger.info("num: " + vo.getNum());
		logger.info("name: " + vo.getName());
		logger.info("price: " + vo.getPrice());
		logger.info("imgName: " + vo.getImgName());
		CartVO vo2 = sqlSession.selectOne("ct_selectOne", vo);
		return vo2;
	}

	@Override
	public List<CartVO> ct_selectAll() {
		List<CartVO> list = sqlSession.selectList("ct_selectAll");
		return list;
	}
}
