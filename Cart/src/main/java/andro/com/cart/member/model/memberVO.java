package andro.com.cart.member.model;

import java.util.ArrayList;

public class memberVO {
	private int memberNum;
	private String memberID;
	private String memberPW;
	private ArrayList<int[]> memberCart;
	private ArrayList<int[]> memberCoupon;
	private String memberAuth;
	
	public int getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public String getMemberPW() {
		return memberPW;
	}
	public void setMemberPW(String memberPW) {
		this.memberPW = memberPW;
	}
	public ArrayList<int[]> getMemberCart() {
		return memberCart;
	}
	public void setMemberCart(ArrayList<int[]> memberCart) {
		this.memberCart = memberCart;
	}
	public ArrayList<int[]> getMemberCoupon() {
		return memberCoupon;
	}
	public void setMemberCoupon(ArrayList<int[]> memberCoupon) {
		this.memberCoupon = memberCoupon;
	}
	public String getMemberAuth() {
		return memberAuth;
	}
	public void setMemberAuth(String memberAuth) {
		this.memberAuth = memberAuth;
	}
}
