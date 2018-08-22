package andro.com.cart.game.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class CouponVO {
	private static final Logger logger = LoggerFactory.getLogger(CouponVO.class);

	private int num;
	private String name;
	private String price;
	private String imgName;
	private String ctCuCheck;
	private int count;

	private MultipartFile multipartFile;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public static Logger getLogger() {
		return logger;
	}

	public String getCtCuCheck() {
		return ctCuCheck;
	}

	public void setCtCuCheck(String ctCuCheck) {
		this.ctCuCheck = ctCuCheck;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
