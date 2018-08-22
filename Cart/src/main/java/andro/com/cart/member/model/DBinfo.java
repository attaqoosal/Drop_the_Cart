package andro.com.cart.member.model;

public interface DBinfo {
	String DRIVER_NAME = "oracle.jdbc.OracleDriver";
	String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	String USER_ID = "cartgame";
	String USER_PW = "hi123456";
	
	String SQL_LOGIN = "select * from member "
			+ "where memberid = ? and memberpw = ?";
}
