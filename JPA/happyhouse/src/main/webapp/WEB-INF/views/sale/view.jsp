<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/template/header.jsp"%>

<script
	src="https://unpkg.com/@google/markerclustererplus@4.0.1/dist/markerclustererplus.min.js"></script>
<script async defer
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCMXtnv5FOhNVw0q66dGzHxXIqFS_UUY3A&callback=initMap"></script>
<script src="${root}/static/proj4js/proj4js-combined.js"></script>

<script>

function modifydo(){
	alert("수정");
	$("#saleform").attr("action", "${root}/sale/modify/${sales.saleno}").submit();
}
function deletedo(){
	alert("삭제");
	$("#saleform").attr("action", "${root}/sale/delete/${sales.saleno}").submit();
}
/*
$(document).ready(function(){
	$('#modifyBtn').click(function() {
		

	});
});
*/
function update(){
	var sale={
			userid: ${userinfo.userid},
			saleno: ${sales.saleno},
			dealAmount: $('#dealAmount').val(),
			buildYear: $('#buildYear').val(),
			area: $('#area').val(),
			floor: $('#floor').val()
	};
	console.log(sale);
	
	$.ajax({
		url:"${root}/dealinfo/"+${sales.saleno},  // 컨트롤러로 이동
		type:"PUT",
		data: JSON.stringify(sale),
		contentType:"application/json;charset=utf-8", // 보내는 데이터
		dataType:"json", // 넘어오는 데이터
		success:function(data) {
			console.log(data);
			alert("수정완료!");
		},
		error:function(xhr,status,msg){
			console.log("상태값 : " + status + " Http에러메시지 : "+msg);
		}
	});
}

</script>

<!--  Hero Start -->
<div
	class="hero-area2  slider-height2 hero-overly2 d-flex align-items-center ">
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

<div class="container" align="center">
	<div class="col-lg-6" align="center">
		<form method="get" action="" id="saleform">
			<div class="form-group" align="left">
				<label for="AptName">아파트이름</label>
				<input type="text" readonly="readonly" class="form-control" id="AptName" name="AptName" placeholder=""value="${sales.aptName}">
			</div>
			<div class="form-group" align="left">
				<label for="dong">동</label>
				<input type="text" readonly="readonly" class="form-control" id="dong" name="dong" placeholder=""value="${sales.dong}">
			</div>
			<div class="form-group" align="left">
				<label for="dealAmount">매매가</label>
				<input type="text" class="form-control" id="dealAmount" name="dealAmount" placeholder=""value="${sales.dealAmount}">
			</div>
			<div class="form-group" align="left">
				<label for="buildYear">건설년도</label>
				<input type="text" class="form-control" id="buildYear" name="buildYear" placeholder="" value="${sales.buildYear}">
			</div>
			
			
			<div class="form-group" align="left">
				<label for="area">평수</label>
				<input type="text"  class="form-control" id="area" name="area" placeholder=""value="${sales.area}">
			</div>
			<div class="form-group" align="left">
				<label for="floor">층</label><br>
				<input type="text" class="form-control" id="floor" name="floor" placeholder=""value="${sales.floor}">
			</div>
			
			<div class="form-group" align="center">
				<button type="button" class="genric-btn success circle arrow" id="modifyBtn" onclick="javascript:modifydo()">수정</button>
				<button type="button" class="genric-btn danger circle arrow" id="deleteBtn" onclick="javascript:deletedo()">삭제</button>
				<button type="reset" class="genric-btn warning circle arrow" id="cancleBtn" onclick="location.href='${root}/sale/list/${userinfo.userid}'">취소</button>
			</div>
		</form>
	</div>
</div>



<%@ include file="/WEB-INF/views/template/footer.jsp"%>