package andro.com.cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import andro.com.cart.member.model.memberDAO;
import andro.com.cart.member.model.memberDAOImpl;
import andro.com.cart.member.model.memberVO;

@Service
public class CartInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(CartInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("preHandle().....");
		memberDAO ms = new memberDAOImpl();
		String sPath = request.getServletPath();

		logger.info("sPath : " + sPath);

		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		String user_auth = (String) session.getAttribute("user_auth");
		logger.info("user_id : " + user_id);
		logger.info("user_auth : " + user_auth);
		
		//about login process
		if(sPath.compareTo("/m_loginOK.do")==0){
			logger.info("/loginOK.do");
			String ID = request.getParameter("memberID");
			String PW = request.getParameter("memberPW");
			memberVO vo= new memberVO();
			vo.setMemberID(ID);
			vo.setMemberPW(PW);
			logger.info("memberID: " + vo.getMemberID());
			logger.info("memberPW: " + vo.getMemberPW());
			
			memberVO vo2 = ms.login(vo);
			logger.info("memberAuth: " + vo2.getMemberAuth());
			
			if (vo2.getMemberNum()!=0) {
				if(vo2.getMemberAuth().equals("on")) {
					session.setAttribute("user_id", vo2.getMemberID());
					session.setAttribute("user_auth", vo2.getMemberAuth());
					session.setMaxInactiveInterval(300);
					response.sendRedirect("m_selectAll.do");
				}
				else if(vo2.getMemberAuth().equals("off")) {
					session.setAttribute("user_id", vo2.getMemberID());
					session.setAttribute("user_auth", vo2.getMemberAuth());
					session.setMaxInactiveInterval(300);
					response.sendRedirect("m_selectID.do?memberNum="+vo2.getMemberNum());
				}
				else {
					logger.info("Auth login failed...");
					response.sendRedirect("m_login.do");
				}
			}
			else {
				logger.info("login failed...");
				response.sendRedirect("m_login.do");
			}
		}

		else if(sPath.compareTo("/m_logout.do")==0){
			session.removeAttribute("user_id");
			session.removeAttribute("user_auth");
			response.sendRedirect("home.do");
		}
		
		//about login authority
		else if(sPath.compareTo("/m_insert.do")==0||sPath.compareTo("/m_insertOK.do")==0){
			if(user_id==null||user_auth==null) {
				logger.info("You can Login");
			}
			else if(!user_id.isEmpty()&&user_auth.equals("off")) {
				logger.info("Can't Insert normal user...for Security, AutoLogout.");
				session.removeAttribute("user_id");
				session.removeAttribute("user_auth");
				response.sendRedirect("m_login.do");
			}
		}
		
		else if(sPath.compareTo("/m_selectAll.do")==0||sPath.compareTo("/m_json_selectAll.do")==0||
				sPath.compareTo("/m_selectOne.do")==0||
				sPath.compareTo("/ct_selectAll.do")==0||sPath.compareTo("/ct_insert.do")==0||
				sPath.compareTo("/ct_insertOK.do")==0||sPath.compareTo("/ct_selectOne.do")==0||
				sPath.compareTo("/ct_updateOK.do")==0||sPath.compareTo("/ct_deleteOK.do")==0||
				sPath.compareTo("/cu_selectAll.do")==0||sPath.compareTo("/cu_insert.do")==0||
				sPath.compareTo("/cu_insertOK.do")==0||sPath.compareTo("/cu_selectOne.do")==0||
				sPath.compareTo("/cu_updateOK.do")==0||sPath.compareTo("/cu_deleteOK.do")==0||
				sPath.compareTo("/m_updateOK.do")==0||sPath.compareTo("/m_deleteOK.do")==0){
			if(user_id==null&&user_auth==null) {
				logger.info("Please Enter Admin...");
				response.sendRedirect("home.do");
			}
			else if(!user_id.isEmpty()&&user_auth.equals("off")) {
				logger.info("Can't Insert normal user...for Security, AutoLogout.");
				session.removeAttribute("user_id");
				session.removeAttribute("user_auth");
				response.sendRedirect("home.do");
			}
		}
		
		else if(sPath.compareTo("/m_selectID.do")==0||sPath.compareTo("/m_updateID.do")==0) {
			if(user_id==null&&user_auth==null) {
			logger.info("Please Enter User...");
			response.sendRedirect("home.do");
		}
		else if(!user_id.isEmpty()&&user_auth.equals("on")) {
			logger.info("Error route...");
			response.sendRedirect("m_selectAll.do");
		}};
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("postHandle().....");
		String sPath = request.getServletPath();
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		String user_auth = (String) session.getAttribute("user_auth");
		logger.info("user_id : " + user_id);
		logger.info("user_auth : " + user_auth);
		MemberController mc = new MemberController();
		
		if (sPath.compareTo("/m_insertOK.do") == 0) {
			if (mc.getFlag() != 0) {
				if (user_id == null && user_auth == null)
					response.sendRedirect("m_login.do");
				else if (!user_id.isEmpty() && user_auth.equals("on"))
					response.sendRedirect("m_selectAll.do");
				else
					response.sendRedirect("m_login.do");
			} else {
				logger.info("insert failed...");
				response.sendRedirect("m_login.do");
			}
		}
	}
}
