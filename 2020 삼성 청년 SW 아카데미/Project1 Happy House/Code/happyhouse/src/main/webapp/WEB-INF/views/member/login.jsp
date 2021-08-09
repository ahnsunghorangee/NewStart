<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script type="text/javascript">
function login() {
	if(document.getElementById("userid").value == "") {
		alert("아이디 입력!!!");
		return;
	} else if(document.getElementById("userpwd").value == "") {
		alert("비밀번호 입력!!!");
		return;
	} else {
		document.getElementById("loginform").action = "${root}/member/login";
		document.getElementById("loginform").submit();
	}
}
	 
function moveJoin() {
		//document.location.href = "${root}/main.do?act=mvjoin";
	document.location.href = "${root}/member/join";
}

// 비밀번호 찾기 페이지로 이동
function moveFindpwd(){
	document.location.href = "${root}/member/findpwd";
}

</script>

<!-- Hero Start-->
<div class="hero-area2  slider-height2 hero-overly2 d-flex align-items-center ">
    <div class="container">
        <div class="row">
            <div class="col-xl-12">
                <div class="hero-cap text-center pt-50">
                </div>
            </div>
        </div>
    </div>
</div>
<!--Hero End -->
<br>
<br>

	<div class="container" align="center">
		<div class="col-lg-6" align="center">
			<form id="loginform" method="post" action="">
				<div class="form-group" align="left">
					<label for="">아이디</label>
					<input type="text" class="form-control" id="userid" name="userid" placeholder="" value="${saveid}">
				</div>
				<div class="form-group" align="left">
					<label for="">비밀번호</label>
					<input type="password" class="form-control" id="userpwd" name="userpwd" placeholder="" onkeydown="javascript:if(event.keyCode == 13) {login();}">
				</div>
				<div class="form-group form-check" align="right">
				    <label class="form-check-label">
				      <input class="form-check-input" type="checkbox" id="idsave" name="idsave" value="saveok"${idck}> 아이디저장 
				    </label>
				</div>
				<div class="form-group" align="center">
					<button type="button" class="genric-btn success circle arrow" onclick="javascript:login();">로그인</button>
					<button type="button" class="genric-btn warning circle arrow" onclick="javascript:moveJoin();">회원가입</button>
					<button type="button" class="genric-btn primary circle arrow" onclick="javascript:moveFindpwd();">비밀번호 찾기</button>
				</div>
			</form>
		</div>
	</div>
<%@ include file="/WEB-INF/views/template/footer.jsp" %>