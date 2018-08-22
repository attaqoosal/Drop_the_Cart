package andro.com.cart.game.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CouponDAOimpl implements CouponDAO {
	private static final Logger logger = LoggerFactory.getLogger(CouponDAOimpl.class);

	@Autowired
	private SqlSession sqlSession;

	public CouponDAOimpl() {
		logger.info("CouponDAOimpl()....");
	}

	@Override
	public int cu_insert(CouponVO vo) {
		logger.info("cu_insert()....");
		logger.info("name: " + vo.getName());
		logger.info("price: " + vo.getPrice());
		logger.info("imgName: " + vo.getImgName());
		int flag = sqlSession.insert("cu_insert", vo);
		return flag;
	}

	@Override
	public int cu_update(CouponVO vo) {
		logger.info("cu_update()....");
		logger.info("num: " + vo.getNum());
		logger.info("name: " + vo.getName());
		logger.info("price: " + vo.getPrice());
		logger.info("imgName: " + vo.getImgName());
		int flag = sqlSession.update("cu_update", vo);
		return flag;
	}

	@Override
	public int cu_delete(CouponVO vo) {
		logger.info("cu_delete()....");
		logger.info("num: " + vo.getNum());
		int flag = sqlSession.delete("cu_delete", vo);
		return flag;
	}

	@Override
	public CouponVO cu_selectOne(CouponVO vo) {
		logger.info("cu_selectOne()....");
		logger.info("num: " + vo.getNum());
		logger.info("name: " + vo.getName());
		logger.info("price: " + vo.getPrice());
		logger.info("imgName: " + vo.getImgName());
		CouponVO vo2 = sqlSession.selectOne("cu_selectOne", vo);
		return vo2;
	}

	@Override
	public List<CouponVO> cu_selectAll() {
		List<CouponVO> list = sqlSession.selectList("cu_selectAll");
		return list;
	}
}