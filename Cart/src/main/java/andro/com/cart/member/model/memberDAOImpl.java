package andro.com.cart.member.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class memberDAOImpl implements memberDAO {
	private static final Logger logger = LoggerFactory.getLogger(memberDAOImpl.class);

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int insert(memberVO vo) {
		logger.info("insert()");
		logger.info("ID: "+vo.getMemberID());
		logger.info("PW: "+vo.getMemberPW());
		logger.info("Auth: "+vo.getMemberAuth());
		
		int flag = sqlSession.insert("m_insert", vo);
		return flag;
	}

	@Override
	public int update(memberVO vo) {
		logger.info("insert()");
		logger.info("Num: "+vo.getMemberNum());
		logger.info("ID: "+vo.getMemberID());
		logger.info("PW: "+vo.getMemberPW());
		logger.info("Auth: "+vo.getMemberAuth());
		
		int flag = sqlSession.update("m_update", vo);
		return flag;
	}

	@Override
	public int delete(memberVO vo) {
		logger.info("delete()");
		logger.info("Num: "+vo.getMemberNum());
		int flag = sqlSession.delete("m_delete",vo);
		return flag;
	}

	@Override
	public memberVO selectOne(memberVO vo) {
		logger.info("selectOne()");
		logger.info("Num: "+vo.getMemberNum());
		memberVO vo2 = sqlSession.selectOne("m_selectOne", vo);
		return vo2;
	}

	@Override
	public memberVO selectID(memberVO vo) {
		logger.info("selectID()");
		logger.info("id: "+vo.getMemberID());
		return null;
	}
	
	@Override
	public ArrayList<memberVO> selectAll() {
		List<memberVO> list = sqlSession.selectList("m_selectAll");
		return (ArrayList<memberVO>)list;
	}

	@Override
	public memberVO login(memberVO vo) {
		try {
			Class.forName(DBinfo.DRIVER_NAME);
			System.out.println("driver successed..");
		} catch (ClassNotFoundException e) {
			System.out.println("driver failed..");
			e.printStackTrace();
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		memberVO vo2 = new memberVO();
		logger.info("ID: "+vo.getMemberID());
		logger.info("PW: "+vo.getMemberPW());

		try {
			conn = DriverManager.getConnection(DBinfo.URL, DBinfo.USER_ID, DBinfo.USER_PW);
			System.out.println("conn successed...");
			pstmt = conn.prepareStatement(DBinfo.SQL_LOGIN);
			pstmt.setString(1, vo.getMemberID());
			pstmt.setString(2, vo.getMemberPW());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo2.setMemberNum(rs.getInt("membernum"));
				vo2.setMemberID(rs.getString("memberid"));
				vo2.setMemberPW(rs.getString("memberpw"));
				vo2.setMemberAuth(rs.getString("memberauth"));
			}
		}
		catch (SQLException e) {
			System.out.println("Impl failed...");
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return vo2;
	}

}
