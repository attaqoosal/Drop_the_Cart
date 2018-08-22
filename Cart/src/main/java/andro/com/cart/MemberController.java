package andro.com.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import andro.com.cart.game.model.CartVO;
import andro.com.cart.game.model.CouponVO;
import andro.com.cart.game.service.CartService;
import andro.com.cart.game.service.CartServiceimpl;
import andro.com.cart.member.model.memberVO;
import andro.com.cart.member.service.memberService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MemberController {
	
	private int flag;
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	memberService ms;
	
	public int getFlag(){
		return flag;
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = {"/","/home.do"}, method = RequestMethod.GET)
	public String home() {
		
		return "home";
	}
	
	@RequestMapping(value = "/m_login.do", method = RequestMethod.GET)
	public String m_login() {
		
		return "member/m_login";
	}
	
	@RequestMapping(value = "/m_insert.do", method = RequestMethod.GET)
	public String m_insert() {
		
		return "member/m_insert";
	}
	
	@RequestMapping(value = "/m_insertOK.do", method = RequestMethod.POST)
	public String m_insertOK(memberVO vo, HttpServletRequest request) {
		logger.info("/insertOK.do");
		logger.info("memberID: " + vo.getMemberID());
		logger.info("memberPW: " + vo.getMemberPW());
		logger.info("memberAuth: " + vo.getMemberAuth());
		
		if(vo.getMemberPW().isEmpty()||vo.getMemberPW().equals("")) {
			logger.info("Enter Password!!");
			return "member/m_insert";
		}
		
		if(vo.getMemberID().equals("admin")) {
			logger.info("Tip: Auth on only can ID=Admin!!");
			vo.setMemberAuth("on");
		}
		else if(!vo.getMemberID().equals("admin")) {
			logger.info("Tip: Auth on only can ID=Admin!!");
			vo.setMemberAuth("off");
		}

		List<memberVO> vos = ms.selectAll();
		for (memberVO vo2 : vos) {
			if(vo.getMemberID().equals(vo2.getMemberID())) {
				logger.info("same id...");
				return "member/m_insert";
			}
		}
		
		flag = ms.insert(vo);

		return "home";
	}
	
	@RequestMapping(value = "/m_loginOK.do", method = RequestMethod.POST)
	public String m_loginOK(memberVO vo) {
		return "member/m_insert";
	}

	
	@RequestMapping(value = "/m_selectAll.do", method = RequestMethod.GET)
	public String m_selectAll() {
		logger.info("/selectAll.do");

		return "member/m_selectAll";
	}

	@RequestMapping(value = "/m_json_selectAll.do", method = RequestMethod.POST)
	@ResponseBody
	public List<memberVO> m_json_selectAll() {
		logger.info("/json_selectAll.do");
		List<memberVO> list = ms.selectAll();
		logger.info("list.size():" + list.size());
		return list;
	}
	
	@RequestMapping(value = "/m_selectOne.do", method = RequestMethod.GET)
	public String m_selectOne(memberVO vo, Model model) {
		logger.info("/selectOne.do");
		logger.info("memberNum:" + vo.getMemberNum());

		memberVO vo2 = ms.selectOne(vo);
		vo = vo2;
		model.addAttribute("vo", vo);
		return "member/m_selectOne";
	}
	
	@RequestMapping(value = "/m_selectID.do", method = RequestMethod.GET)
	public String m_selectID(memberVO vo, Model model) {
		logger.info("/selectID.do");
		logger.info("memberNum:" + vo.getMemberNum());

		memberVO vo2 = ms.selectOne(vo);
		vo = vo2;
		model.addAttribute("vo", vo);
		return "member/m_selectID";
	}
	
	@RequestMapping(value = "/m_updateID.do", method = RequestMethod.POST)
	public String m_updateID(memberVO vo, Model model) {
		logger.info("/updateID.do");
		logger.info("memberNum:" + vo.getMemberNum());
		logger.info("memberID: " + vo.getMemberID());
		logger.info("memberPW: " + vo.getMemberPW());
		logger.info("memberAuth: " + vo.getMemberAuth());
		
		if(vo.getMemberPW().isEmpty()||vo.getMemberPW().equals("")) {
			logger.info("Enter Password!!");
			model.addAttribute("vo",vo);
			return "redirect:m_selectOne.do?memberNum="+vo.getMemberNum();
		}
		
		if(vo.getMemberID().equals("admin")) {
			logger.info("Tip: Auth on only can ID=Admin!!");
			vo.setMemberAuth("on");
		}
		else if(!vo.getMemberID().equals("admin")) {
			logger.info("Tip: Auth on only can ID=Admin!!");
			vo.setMemberAuth("off");
		}

		flag = ms.update(vo);
		if (flag != 0) {
			logger.info("update success...!");
			model.addAttribute("vo",vo);
			return "redirect:m_selectID.do?memberNum="+vo.getMemberNum();
		}
		else {
			logger.info("update failed...");
			model.addAttribute("vo",vo);
			return "redirect:m_selectID.do?memberNum="+vo.getMemberNum();
		}
	}
	
	@RequestMapping(value = "/m_updateOK.do", method = RequestMethod.POST)
	public String m_updateOK(memberVO vo, Model model) {
		logger.info("/updateOK.do");
		logger.info("memberNum: " + vo.getMemberNum());
		logger.info("memberID: " + vo.getMemberID());
		logger.info("memberPW: " + vo.getMemberPW());
		logger.info("memberAuth: " + vo.getMemberAuth());
		
		if(vo.getMemberPW().isEmpty()||vo.getMemberPW().equals("")) {
			logger.info("Enter Password!!");
			model.addAttribute("vo",vo);
			return "redirect:m_selectOne.do?memberNum="+vo.getMemberNum();
		}
		
		if(vo.getMemberID().equals("admin")) {
			logger.info("Tip: Auth on only can ID=Admin!!");
			vo.setMemberAuth("on");
		}
		else if(!vo.getMemberID().equals("admin")) {
			logger.info("Tip: Auth on only can ID=Admin!!");
			vo.setMemberAuth("off");
		}

		flag = ms.update(vo);
		if (flag != 0) {
				return "redirect:m_selectAll.do";
		}
		else {
			logger.info("update failed...");
			model.addAttribute("vo",vo);
			return "redirect:m_selectOne.do?memberNum="+vo.getMemberNum();
		}
	}
	
	@RequestMapping(value = "/m_deleteOK.do", method = RequestMethod.GET)
	public String m_deleteOK(memberVO vo) {
		logger.info("/deleteOK.do");
		
		flag = ms.delete(vo);
		logger.info(String.valueOf(flag));
		if(flag>0) {
			logger.info("delete OK...");
			return "redirect:m_selectAll.do";
		}
		else
			logger.info("delete fail...");
			return "redirect:m_selectAll.do";
	}
	
	@RequestMapping(value = "/m_logout.do", method = RequestMethod.GET)
	public String m_logout() {
		return "member/m_login";
	}

	//안드로이드와 연계시 작동, 안드로이드에서 게임이 완료되면 상품을 로그인 한 계정에 등록함
	@RequestMapping(value = "/android_updateCtCu.do", method = RequestMethod.POST)
	@ResponseBody
	public int android_updateCtCu(ArrayList<int[]> responseData, String sessionID) {
		logger.info("/updateOK.do");
		logger.info("responseData: " + responseData);
		memberVO vo = new memberVO();
		ArrayList<int[]> mbCart = new ArrayList<int[]>();
		ArrayList<int[]> mbCoupon = new ArrayList<int[]>();

		for(int i = 0; i < responseData.size(); i++) {
			if(responseData.get(i)[1]==0) {
				mbCart.add(responseData.get(i));
			}
			else if(responseData.get(i)[1]==1) {
				mbCoupon.add(responseData.get(i));
			}
			else {
				logger.info("data Error...");
			}
		}
		vo.setMemberID(sessionID);
		memberVO vo2 = ms.selectID(vo);
		vo2.setMemberCart(mbCart);
		vo2.setMemberCoupon(mbCoupon);
		flag = ms.update(vo2);
		return flag;
	}
	
	//안드로이드와 연계시 작동, 게임한 이후 결제한 카트 리스트를 보여줌
	@RequestMapping(value = "/m_CtCuJsonSelect.do", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<CartVO> m_CtCuJsonSelect(memberVO vo) {
		logger.info("/crct_jsonSelect.do");
		logger.info("num:" + vo.getMemberNum());
		ArrayList<CartVO> list = new ArrayList<CartVO>();
		CartService cts = new CartServiceimpl();

		memberVO mvo = ms.selectOne(vo);
		if(mvo.getMemberCart()==null||mvo.getMemberCoupon()==null) {
			logger.info("Can't Parsing data");
		}
		else {
			ArrayList<int[]> ct_list = mvo.getMemberCart();
			for (int i = 0; i < mvo.getMemberCoupon().size(); i++) {
				ct_list.add(mvo.getMemberCoupon().get(i));
			}

			for (int i = 0; i < mvo.getMemberCoupon().size(); i++) {
				CartVO ctvo = new CartVO();
				ctvo.setNum(ct_list.get(i)[0]);
				CartVO ctvo2 = cts.ct_selectOne(ctvo);
				if (ct_list.get(i)[1] == 0) {
					ctvo.setCtCuCheck("cart");
				} else if (ct_list.get(i)[1] == 1) {
					ctvo.setCtCuCheck("coupon");
				} else {
					logger.info("setCtCuCheck error...");
				}
				ctvo2.setCount(ct_list.get(i)[2]);
				list.add(ctvo2);
			}
		}
		
		return list;
	}
}
