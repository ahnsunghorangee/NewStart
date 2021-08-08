package com.ssafy.projectboot.service;

import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.projectboot.dto.MemberDto;
import com.ssafy.projectboot.repo.MemberRepo;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberRepo memberRepo;
	
	@Override
	public MemberDto login(MemberDto memberDto, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 등록된 아이디가 없으면
		if(memberRepo.check_id(memberDto.getUserid()) == 0) {
			out.println("<script>");
			out.println("alert('등록된 아이디가 없습니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			return null;
		} else {
			String pw = memberDto.getUserpwd();// 입력하는 pw
			memberDto = memberRepo.login(memberDto); // db에 있는 정보와 비교
			// 비밀번호가 다를 경우
			if(!memberDto.getUserpwd().equals(pw)) {
				out.println("<script>");
				out.println("alert('비밀번호가 다릅니다.');");
				out.println("history.go(-1);");
				out.println("</script>");
				out.close();
				return null;
			// 이메일 인증을 하지 않은 경우
			}else if(!memberDto.getApproval_status().equals("true")) {
				out.println("<script>");
				out.println("alert('이메일 인증 후 로그인 하세요.');");
				out.println("history.go(-1);");
				out.println("</script>");
				out.close();
				return null;
            // 로그인 일자 업데이트 및 회원정보 리턴			
			}else {
				return memberDto;
			}
		}
		//return memberRepo.login(memberDto);
	}

	@Override
	public int joinUser(MemberDto memberDto, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if (memberRepo.check_id(memberDto.getUserid()) == 1) {
			out.println("<script>");
			out.println("alert('동일한 아이디가 있습니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			return 0;
		} else if (memberRepo.check_email(memberDto.getEmail()) == 1) {
			out.println("<script>");
			out.println("alert('동일한 이메일이 있습니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			return 0;
		} else {
			// 인증키 설정
			memberDto.setApproval_key(create_key());
			memberDto.setApproval_status("false");
			memberRepo.joinUser(memberDto);
			// 인증이메일 발송
			send_mail(memberDto, "join");
			return 1;
		}
		
		
	}

	@Override
	public void modifyUser(MemberDto memberDto) {
		memberRepo.modifyUser(memberDto);
	}

	@Override
	public void deleteUser(String userid) {
		memberRepo.deleteUser(userid);
	}

	@Override
	public MemberDto getUser(String userid) {
		return memberRepo.getUser(userid);
	}

	@Override
	public List<MemberDto> listUser() {
		return memberRepo.listUser();
	}

	@Override
	public MemberDto pwdFind(String userid, String username) {
		return memberRepo.pwdFind(userid, username);
	}
	
	@Override
	public int idCheck(String userid) {
		return memberRepo.idCheck(userid);
	}

	@Override
	public void selectDelete(String userid) {
		memberRepo.selectDelete(userid);
	}

	
	
	// 아이디 중복 검사
	@Override
	public void check_id(String userid, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		out.println(memberRepo.check_id(userid));
		out.close();
	}
	// 이메일 중복 검사
	@Override
	public void check_email(String email, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		out.println(memberRepo.check_email(email));
		out.close();
	}
	// naver SMTP: 인증키 생성
	@Override
	public String create_key() throws Exception {
		String key = "";
		Random rd = new Random();

		for (int i = 0; i < 8; i++) {
			key += rd.nextInt(10);
		}
		return key; // 8자리 랜덤한 숫자 생성
	}
	
	// 이메일 발송
	@Override
	public void send_mail(MemberDto memberDto, String div) throws Exception { // div -> 회원가입 || 비밀번호 찾기
		// Mail Server 설정 ( SMTP 설정한 아이디)
		String charSet = "utf-8";
		String hostSMTP = "smtp.naver.com";
		String hostSMTPid = "jugiaro5@naver.com"; // 본인 아이디
		String hostSMTPpwd = "sh95923517ahn"; // 비밀번호 입력

		// 보내는 사람 EMail, 제목, 내용
		String fromEmail = "jugiaro5@naver.com"; // 보내는 사람 email
		String fromName = "너굴사장";
		String subject = "제목이다 구리!"; // 제목
		String msg = "메시지다 구리!";
		
		
		if(div.equals("join")) { // 회원가입
			// 회원가입 메일 내용
			subject = "Spring Homepage 회원가입 인증 메일입니다.";
			msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
			msg += "<h3 style='color: blue;'>";
			msg += memberDto.getUserid() + "님 회원가입을 환영합니다.</h3>";
			msg += "<div style='font-size: 130%'>";
			msg += "하단의 인증 버튼 클릭 시 정상적으로 회원가입이 완료됩니다.</div><br/>";
			msg += "<form method='post' action='http://localhost:8081/projectboot/member/approval_member.do'>";
			msg += "<input type='hidden' name='email' value='" + memberDto.getEmail() + "'>";
			msg += "<input type='hidden' name='approval_key' value='" + memberDto.getApproval_key() + "'>";
			msg += "<input type='submit' value='인증'></form><br/></div>";
		} else if(div.equals("find_pw")) { // 비밀번호 찾기
			subject = "Spring Homepage 임시 비밀번호 입니다.";
			msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
			msg += "<h3 style='color: blue;'>";
			msg += memberDto.getUserid() + "님의 임시 비밀번호 입니다. 비밀번호를 변경하여 사용하세요.</h3>";
			msg += "<p>임시 비밀번호 : ";
			msg += memberDto.getUserpwd() + "</p></div>";
		}
		

		// 받는 사람 E-Mail 주소
		String mail = memberDto.getEmail();
		try {
			HtmlEmail email = new HtmlEmail();
			email.setDebug(true);
			email.setCharset(charSet);
			email.setSSL(true);
			email.setHostName(hostSMTP);
			email.setSmtpPort(587);

			email.setAuthentication(hostSMTPid, hostSMTPpwd);
			email.setTLS(true);
			email.addTo(mail, charSet);
			email.setFrom(fromEmail, fromName, charSet);
			email.setSubject(subject);
			email.setHtmlMsg(msg);
			email.send();
		} catch (Exception e) {
			System.out.println("메일발송 실패 : " + e);
		}
	}
	// 회원 인증
	@Override
	public void approval_member(MemberDto memberDto, HttpServletResponse response, HttpServletRequest request) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (memberRepo.approval_member(memberDto) == 0) { // 이메일 인증에 실패하였을 경우
			out.println("<script>");
			out.println("alert('잘못된 접근입니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
		} else { // 이메일 인증을 성공하였을 경우
			out.println("<script>");
			out.println("alert('인증이 완료되었습니다. 로그인 후 이용하세요.');");
			out.println("location.href=\""+request.getContextPath()+"/login/go\";");
			out.println("</script>");
			out.close();
		}
	}
	// 비밀번호 찾기
	@Override
	public void find_pw(HttpServletResponse response, MemberDto memberDto) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 아이디가 없으면
		if(memberRepo.check_id(memberDto.getUserid()) == 0) {
			out.print("아이디가 없습니다.");
			out.close();
		}
		// 가입에 사용한 이메일이 아니면
		else if(!memberDto.getEmail().equals(memberRepo.login(memberDto).getEmail())) {
			out.print("잘못된 이메일 입니다.");
			out.close();
		}else {
			// 임시 비밀번호 생성
			String pw = "";
			for (int i = 0; i < 12; i++) {
				pw += (char) ((Math.random() * 26) + 97);
			}
			memberDto.setUserpwd(pw);
			// 비밀번호 변경
			memberRepo.update_pw(memberDto);
			// 비밀번호 변경 메일 발송
			send_mail(memberDto, "find_pw");
			
			out.print("이메일로 임시 비밀번호를 발송하였습니다.");
			out.close();
		}
	}

}
