<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<c:if test="${userinfo == null}">
	<c:redirect url="/user.do"/>
</c:if>
<c:if test="${userinfo != null}">
	
			<div class="container" align="center">
				<div class="col-lg-8" align="center">
					<strong>${userinfo.username}</strong>님 환영합니다.
					<a href="${root}/user.do?act=logout">로그아웃</a>
					<a href="${root}/noticeList.do?act=list&key=&word=">뒤로가기</a>
				</div>
				<div class="col-lg-8">
				  <div class="jumbotron">
				    <h1>글작성 성공!!!</h1>      
				  </div>  
				  <p><a href="${root}/noticeList.do?act=list&key=&word=">글목록</a></p>
				</div>
			</div>
</c:if>
<%@ include file="/WEB-INF/views/template/footer.jsp" %>