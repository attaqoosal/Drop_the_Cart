package andro.com.cart;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import andro.com.cart.game.model.CartVO;
import andro.com.cart.game.model.CouponVO;
import andro.com.cart.game.service.CartService;
import andro.com.cart.game.service.CouponService;
import andro.com.cart.member.model.memberVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CartController {

	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	@Autowired
	CartService cts;
	@Autowired
	CouponService cus;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	@RequestMapping(value = "/ctcu_json_selectAll.do", method = RequestMethod.GET)
	@ResponseBody
	public List<Object> ctcu_json_selectAll(Model model) {
		logger.info("/ctcu_json_selectAll.do");
		
		List<Object> list = new ArrayList<>();
		List<CartVO> list_ct = cts.ct_selectAll(); 
		List<CouponVO> list_cu = cus.cu_selectAll();
		for (CartVO vo : list_ct) {
			int i = 0;
			list.add(vo);
			i++;
		}
		for (CouponVO vo : list_cu) {
			int i = 0;
			list.add(vo);
			i++;
		}
		logger.info(list.toString());
		return list;
	}
	
	@RequestMapping(value = "/ct_selectAll.do", method = RequestMethod.GET)
	public String ct_selectAll(Model model) {
		logger.info("/ct_selectAll.do");

		List<CartVO> list = cts.ct_selectAll();
		logger.info("list.size(): " + list.size());
		model.addAttribute("list", list);
		return "cart/ct_selectAll";
	}
	
	@RequestMapping(value = "/ct_insert.do", method = RequestMethod.GET)
	public String ct_insert() {
		logger.info("/ct_insert.do");
		return "cart/ct_insert";
	}

	@RequestMapping(value = "/ct_insertOK.do", method = RequestMethod.POST)
	public String ct_insertOK(@ModelAttribute("vo") CartVO vo, HttpServletRequest request, Model model) {
		logger.info("method ct_insertOK! getName = {}", vo.getName());
		logger.info("method ct_insertOK! getPrice = {}", vo.getPrice());
		vo.setCtCuCheck("cart");
		
		int flag = 0;
		String saveName = "";
		MultipartFile multipartFile = vo.getMultipartFile();

		if (!multipartFile.isEmpty()) {
			saveName = multipartFile.getOriginalFilename();
			long nowTime = System.currentTimeMillis();
			String imgName = nowTime + saveName;

			vo.setImgName(imgName);
			logger.info("method ct_insertOK! OriginalFilename = {}", imgName);

			String realPath = request.getRealPath("resources/uploadimg");
			logger.info("method ct_insertOK! realPath = {}", realPath);

			File origin_img = new File(realPath + "/" + imgName);
			try {
				multipartFile.transferTo(origin_img);

				//// create thumbnail image/////////
				BufferedImage original_buffer_img = ImageIO.read(origin_img);
				BufferedImage thumb_buffer_img = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
				Graphics2D graphic = thumb_buffer_img.createGraphics();
				graphic.drawImage(original_buffer_img, 0, 0, 50, 50, null);

				File thumb_file = new File(realPath + "/thumb_" + imgName);
				ImageIO.write(thumb_buffer_img, "jpg", thumb_file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			model.addAttribute("nulled", 1);
			return "redirect:ct_insert.do";
		}
		flag = cts.ct_insert(vo);
		if (flag == 0)
			return "cart/insert?fail=1";
		else
			return "redirect:ct_selectAll.do";
	}

	@RequestMapping(value = "/ct_selectOne.do", method = RequestMethod.GET)
	public String ct_selectOne(CartVO vo, Model model) {
		logger.info("/ct_selectOne.do");
		logger.info("num:" + vo.getNum());

		CartVO vo2 = cts.ct_selectOne(vo);
		model.addAttribute("vo2", vo2);
		return "cart/ct_selectOne";
	}

	@RequestMapping(value = "/ct_updateOK.do", method = RequestMethod.POST)
	public String ct_updateOK(@ModelAttribute("vo") CartVO vo, HttpServletRequest request, Model model) {
		logger.info("method ct_updateOK! getName = {}", vo.getName());
		logger.info("method ct_updateOK! getPrice = {}", vo.getPrice());
		CartVO vo2 = new CartVO();
		vo.setCtCuCheck("cart");

		int flag = 0;
		String saveName = "";
		vo2 = cts.ct_selectOne(vo);
		MultipartFile multipartFile = vo.getMultipartFile();

		if (!multipartFile.isEmpty()) {
			saveName = multipartFile.getOriginalFilename();
			long nowTime = System.currentTimeMillis();
			String imgName = nowTime + saveName;

			vo.setImgName(imgName);
			logger.info("method ct_updateOK! OriginalFilename = {}", imgName);

			String realPath = request.getRealPath("resources/uploadimg");
			logger.info("method ct_updateOK! realPath = {}", realPath);
			
			File deletefile = new File(realPath + "/" + vo2.getImgName());
			File deletethumb = new File(realPath + "/thumb_" + vo2.getImgName());
			if (deletefile.exists()) {
				if (deletefile.delete()) {
					logger.info("Delete Success...");
				} else {
					logger.info("Delete fail...");
				}
			} else {
				logger.info("File not Exists...");
			}

			if (deletethumb.exists()) {
				if (deletethumb.delete()) {
					logger.info("Delete Success...");
				} else {
					logger.info("Delete fail...");
				}
			} else {
				logger.info("File not Exists...");
			}
			
			try {
				File origin_img = new File(realPath + "/" + imgName);
				multipartFile.transferTo(origin_img);

				//// create thumbnail image/////////
				BufferedImage original_buffer_img = ImageIO.read(origin_img);
				BufferedImage thumb_buffer_img = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
				Graphics2D graphic = thumb_buffer_img.createGraphics();
				graphic.drawImage(original_buffer_img, 0, 0, 50, 50, null);

				File thumb_file = new File(realPath + "/thumb_" + imgName);
				ImageIO.write(thumb_buffer_img, "jpg", thumb_file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {
			model.addAttribute("nulled", 1);
			model.addAttribute("num", vo2.getNum());
			return "redirect:ct_selectOne.do?num=" + vo2.getNum();
		}
		flag = cts.ct_update(vo);
		if (flag == 0) {
			model.addAttribute("vo2", vo2);
			return "cart/ct_selectOne?fail=1";
		} else
			return "redirect:ct_selectAll.do";
	}

	@RequestMapping(value = "/ct_deleteOK.do", method = RequestMethod.GET)
	public String ct_deleteOK(@ModelAttribute("vo") CartVO vo, HttpServletRequest request) {
		logger.info("/ct_deleteOK.do");
		logger.info("num:" + vo.getNum());
		
		CartVO vo2 = cts.ct_selectOne(vo);
		String realPath = request.getRealPath("resources/uploadimg");
		File deletefile = new File(realPath + "/" + vo2.getImgName());
		File deletethumb = new File(realPath + "/thumb_" + vo2.getImgName());
		
		int flag = cts.ct_delete(vo);
		logger.info(String.valueOf(flag));

		if (deletefile.exists()) {
			if (deletefile.delete()) {
				logger.info("Delete Success...");
			} else {
				logger.info("Delete fail...");
			}
		} else {
			logger.info("File not Exists...");
		}

		if (deletethumb.exists()) {
			if (deletethumb.delete()) {
				logger.info("Delete Success...");
			} else {
				logger.info("Delete fail...");
			}
		} else {
			logger.info("File not Exists...");
		}
		return "redirect:ct_selectAll.do";
	}

	@RequestMapping(value = "/cu_selectAll.do", method = RequestMethod.GET)
	public String cu_selectAll(Model model) {
		logger.info("/cu_selectAll.do");

		List<CouponVO> list = cus.cu_selectAll();
		logger.info("list.size(): " + list.size());
		model.addAttribute("list", list);
		return "coupon/cu_selectAll";
	}

	@RequestMapping(value = "/cu_insert.do", method = RequestMethod.GET)
	public String cu_insert() {
		logger.info("/cu_insert.do");
		return "coupon/cu_insert";
	}

	@RequestMapping(value = "/cu_insertOK.do", method = RequestMethod.POST)
	public String cu_insertOK(@ModelAttribute("vo") CouponVO vo, HttpServletRequest request, Model model) {
		logger.info("method cu_insertOK! getName = {}", vo.getName());
		logger.info("method cu_insertOK! getPrice = {}", vo.getPrice());
		vo.setCtCuCheck("coupon");

		int flag = 0;
		String saveName = "";
		MultipartFile multipartFile = vo.getMultipartFile();

		if (!multipartFile.isEmpty()) {
			saveName = multipartFile.getOriginalFilename();
			long nowTime = System.currentTimeMillis();
			String imgName = nowTime + saveName;

			vo.setImgName(imgName);
			logger.info("method cu_insertOK! OriginalFilename = {}", imgName);

			String realPath = request.getRealPath("resources/uploadimg");
			logger.info("method cu_insertOK! realPath = {}", realPath);

			File origin_img = new File(realPath + "/" + imgName);
			try {
				multipartFile.transferTo(origin_img);

				//// create thumbnail image/////////
				BufferedImage original_buffer_img = ImageIO.read(origin_img);
				BufferedImage thumb_buffer_img = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
				Graphics2D graphic = thumb_buffer_img.createGraphics();
				graphic.drawImage(original_buffer_img, 0, 0, 50, 50, null);

				File thumb_file = new File(realPath + "/thumb_" + imgName);
				ImageIO.write(thumb_buffer_img, "jpg", thumb_file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			model.addAttribute("nulled", 1);
			return "redirect:cu_insert.do";
		}
		flag = cus.cu_insert(vo);
		if (flag == 0)
			return "coupon/insert?fail=1";
		else
			return "redirect:cu_selectAll.do";
	}

	@RequestMapping(value = "/cu_selectOne.do", method = RequestMethod.GET)
	public String cu_selectOne(CouponVO vo, Model model) {
		logger.info("/cu_selectOne.do");
		logger.info("num:" + vo.getNum());

		CouponVO vo2 = cus.cu_selectOne(vo);
		model.addAttribute("vo2", vo2);
		return "coupon/cu_selectOne";
	}

	@RequestMapping(value = "/cu_updateOK.do", method = RequestMethod.POST)
	public String updateOK(@ModelAttribute("vo") CouponVO vo, HttpServletRequest request, Model model) {
		logger.info("method cu_updateOK! getName = {}", vo.getName());
		logger.info("method cu_updateOK! getPrice = {}", vo.getPrice());
		CouponVO vo2 = new CouponVO();
		vo.setCtCuCheck("coupon");
		
		int flag = 0;
		String saveName = "";
		vo2 = cus.cu_selectOne(vo);
		MultipartFile multipartFile = vo.getMultipartFile();

		if (!multipartFile.isEmpty()) {
			saveName = multipartFile.getOriginalFilename();
			long nowTime = System.currentTimeMillis();
			String imgName = nowTime + saveName;

			vo.setImgName(imgName);
			logger.info("method cu_updateOK! OriginalFilename = {}", imgName);

			String realPath = request.getRealPath("resources/uploadimg");
			logger.info("method cu_updateOK! realPath = {}", realPath);
			File deletefile = new File(realPath + "/" + vo2.getImgName());
			File deletethumb = new File(realPath + "/thumb_" + vo2.getImgName());
			if (deletefile.exists()) {
				if (deletefile.delete()) {
					logger.info("Delete Success...");
				} else {
					logger.info("Delete fail...");
				}
			} else {
				logger.info("File not Exists...");
			}

			if (deletethumb.exists()) {
				if (deletethumb.delete()) {
					logger.info("Delete Success...");
				} else {
					logger.info("Delete fail...");
				}
			} else {
				logger.info("File not Exists...");
			}
			
			try {
				File origin_img = new File(realPath + "/" + imgName);
				multipartFile.transferTo(origin_img);

				//// create thumbnail image/////////
				BufferedImage original_buffer_img = ImageIO.read(origin_img);
				BufferedImage thumb_buffer_img = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
				Graphics2D graphic = thumb_buffer_img.createGraphics();
				graphic.drawImage(original_buffer_img, 0, 0, 50, 50, null);

				File thumb_file = new File(realPath + "/thumb_" + imgName);
				ImageIO.write(thumb_buffer_img, "jpg", thumb_file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			model.addAttribute("nulled", 1);
			model.addAttribute("num", vo2.getNum());
			return "redirect:cu_selectOne.do?num=" + vo2.getNum();
		}
		flag = cus.cu_update(vo);
		if (flag == 0) {
			model.addAttribute("vo2", vo2);
			return "coupon/cu_selectOne?fail=1";
		} else
			return "redirect:cu_selectAll.do";
	}

	@RequestMapping(value = "/cu_deleteOK.do", method = RequestMethod.GET)
	public String deleteOK(@ModelAttribute("vo") CouponVO vo, HttpServletRequest request) {
		logger.info("/cu_deleteOK.do");
		logger.info("num:" + vo.getNum());
		
		CouponVO vo2 = cus.cu_selectOne(vo);
		String realPath = request.getRealPath("resources/uploadimg");
		File deletefile = new File(realPath + "/" + vo2.getImgName());
		File deletethumb = new File(realPath + "/thumb_" + vo2.getImgName());
		
		int flag = cus.cu_delete(vo);
		logger.info(String.valueOf(flag));

		if (deletefile.exists()) {
			if (deletefile.delete()) {
				logger.info("Delete Success...");
			} else {
				logger.info("Delete fail...");
			}
		} else {
			logger.info("File not Exists...");
		}

		if (deletethumb.exists()) {
			if (deletethumb.delete()) {
				logger.info("Delete Success...");
			} else {
				logger.info("Delete fail...");
			}
		} else {
			logger.info("File not Exists...");
		}
		return "redirect:cu_selectAll.do";
	}
}
