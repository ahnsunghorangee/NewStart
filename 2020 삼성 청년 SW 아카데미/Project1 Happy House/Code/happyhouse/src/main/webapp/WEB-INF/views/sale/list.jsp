<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script src="https://unpkg.com/@google/markerclustererplus@4.0.1/dist/markerclustererplus.min.js"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCMXtnv5FOhNVw0q66dGzHxXIqFS_UUY3A&callback=initMap"></script>
<script src="${root}/static/proj4js/proj4js-combined.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	viewList();
	
	$('#delete').click(function() {
		if(confirm("정말 삭제???")) {
		$('#saleform').attr('action', '${root}/sale/selectDelete').submit();
		}
	});
});

function viewList(){
	$.ajax({
		url:"${root}/dealinfo/searchUserRegister/${userinfo.userid}",  // 컨트롤러로 이동
		type:"GET",
		contentType:"application/json;charset=utf-8", // 보내는 데이터
		dataType:"json", // 넘어오는 데이터
		success:function(data) {
			makeList(data);
		},
		error:function(xhr,status,msg){
			console.log("상태값 : " + status + " Http에러메시지 : "+msg);
		}
	});
}

function makeList(data){
	var list = '';
	console.log("list: "+data)
	$.each(data, function(idx, sale){
		list += '<tr><td> '+ sale.saleno+'</td>'
				+'<td>'+sale.userid+'</td>'
				+'<td> <a href="${root}/sale/getSaleno/'+sale.saleno + '">'+sale.aptName+'</td>'
				+'<td><input type=\"checkbox\" id=\"del\" name=\"del\" value=\"'+sale.saleno+'\"></tr>';
	});
	console.log(list);
	$('#ajaxtbody').html(list);
}


</script>

<!--  Hero Start -->
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
<!-- Hero End  -->

<div class="button-group-area mt-40" align="center">
	<a href="${root}/sale/register" class="genric-btn primary e-large">방 내놓기</a>
	<a href="${root}/sale/list" class="genric-btn primary e-large">내 방 관리</a>
	<a href="${root}/sale/recent" class="genric-btn primary e-large">최근 올라온 방</a>
</div>
<br>
<br>
<div>
	<form method="post" action="" id="saleform">
		<table id="clicktable" class="table table-active">
			<thead>
				<tr>
					<th>등록번호</th>
					<th>아이디</th>
					<th>주소</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody id="ajaxtbody">
				
			</tbody>
		</table>
		<button type="button" class="btn btn-link" id="delete">선택 항목 삭제</button>
	</form>
</div>



<%@ include file="/WEB-INF/views/template/footer.jsp" %>