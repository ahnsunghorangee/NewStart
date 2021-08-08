<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script type="text/javascript">

function modify() {
	window.location.href = "${root}/member/modify";
}

function remove(){
	var con_test = confirm("탈퇴 하시겠습니까?");
	if(con_test == true){
		$("#memberform").attr("action", "${root}/member/delete/${userinfo.userid}").submit();
			// document.location.href="${root}/member/delete/${userinfo.userid}";
	}
	else {
        return false;
    }
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
		<form method="post" action="" id="memberform">
		
			<div class="form-group" align="left">
				<label for="userid">아이디</label>
				<input type="text" readonly="readonly" class="form-control" id="userid" name="userid" placeholder=""value="${userinfo.userid}">
			</div>
			<div class="form-group" align="left">
				<label for="username">이름</label>
				<input type="text" readonly="readonly"  class="form-control" id="username" name="username" placeholder="" value="${userinfo.username}">
			</div>
			<div class="form-group" align="left">
				<label for="userpwd">비밀번호</label>
				<input type="password" readonly="readonly"  class="form-control" id="userpwd" name="userpwd" placeholder=""value="${userinfo.userpwd}">
			</div>
			<div class="form-group" align="left">
				<label for="email">이메일</label><br>
				<input type="text" readonly="readonly" class="form-control" id="email" name="email" placeholder=""value="${userinfo.email}">
			</div>
			<div class="form-group" align="left">
				<label for="adress">주소</label><br>
				<input type="text" readonly="readonly" class="form-control" id="address" name="adress" placeholder=""value="${userinfo.address}">
			</div>
			</form>
	</div>
</div>

<div class="form-group" align="center">
	<button type="button" class="btn btn-primary" id="modifyBtn" onclick="javascript:modify(); ">수정</button>
	<button type="button" class="btn btn-warning" id="removeBtn" onclick="javascript:remove(); ">탈퇴하기</button>
	<button type="reset" class="btn btn-primary" id="cancleBtn" onclick="location.href='${root}/index'">취소</button>
</div>


<%@ include file="/WEB-INF/views/template/footer.jsp" %>