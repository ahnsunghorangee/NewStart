<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
	viewList();
	
	$('#delete').click(function() {
		if(confirm("정말 삭제???")) {
		$('#deleteform').attr('action', '${root}/member/selectDelete').submit();
		}
	});
});

function viewList(){
	
	$.ajax({
		url:"${root}/member/selectAll",  // 컨트롤러로 이동
		type:"GET",
		contentType:"application/json;charset=utf-8", // 보내는 데이터
		dataType:"json", // 넘어오는 데이터
		success:function(data) {
			makeList(data);
			console.log("member리스트얻어옴");
		},
		error:function(xhr,status,msg){
			console.log("상태값 : " + status + " Http에러메시지 : "+msg);
		}
	});
}

function makeList(data){
	var list = '';
	console.log("list: "+data)
	$.each(data, function(idx, member){
		list += '<tr><td> <a href="${root}/member/getUser?userid='+member.userid + '">'+member.userid+'</td>'
				+'<td>'+member.username+'</td>'
				+'<td>'+member.email+'</td>'
				+'<td>'+member.address+'</td>'
				+'<td><input type=\"checkbox\" id=\"del\" name=\"del\" value=\"'+member.userid+'\"></tr>';
	});
	console.log(list);
	$('#ajaxtbody').html(list);
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
	<div class="col-lg-12" align="center">
	
		<c:if test="${users.size() != 0}">
			<form method="post" action="" id="deleteform">
			<table class="table table-active">
				<thead>
					<tr>
						<td>아이디</td>
						<td>이름</td>
						<td>이메일</td>
						<td>주소</td>
						<td>삭제</td>
					</tr>
				</thead>
				<tbody id="ajaxtbody">
				</tbody>
			</table>
			<table class="table table-borderless">
				<tr>
					<td><button type="button" class="btn btn-link" id="delete">선택 항목 삭제</button></td>
				</tr>
			</table>
			</form>
		</c:if>
		
		<c:if test="${users.size() == 0}">
			<table class="table table-active">
				<thead>
					<tr>
						<td>아이디</td>
						<td>이름</td>
						<td>이메일</td>
						<td>주소</td>
					</tr>
				</thead>
				<tbody id="ajaxtbody">
					<tr class="table-info" align="center">
						<td>회원가입한 회원이 없습니다.</td>
					</tr>
				</tbody>
			</table>
		</c:if>
</div>
</div>
	
<%@ include file="/WEB-INF/views/template/footer.jsp" %>