<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script type="text/javascript">
	$(function(){
		$("#findBtn").click(function(){
			$.ajax({
				url : "${root}/member/find_pw.do",
				type : "POST",
				data : {
					userid : $("#userid").val(),
					email : $("#email").val()
				},
				success : function(result) {
					alert(result);
				},
			})
		});
	})
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
	<h3>비밀번호 찾기</h3>
	<div class="col-lg-6" align="center">
		<label>아이디</label>
		<input class="single-input" type="text" id="userid" name="userid" required>
		<label>이메일</label>
		<input class="single-input" type="text" id="email" name="email" required>
		<button type="button" class="genric-btn primary circle" id=findBtn >찾기</button>
		<button type="button" class="genric-btn warning circle" onclick="history.go(-1);" >취소</button>
	</div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>