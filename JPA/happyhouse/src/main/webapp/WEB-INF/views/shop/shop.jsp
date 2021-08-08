<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script type="text/javascript">
$(document).ready(function(){
 	viewList();
	$('#searchBtn').click(function(){
		if (document.getElementById("searchword").value == "") {
			alert("모든 목록 조회!!");
			viewList();
		} else{
			alert("이름으로 검색!!");
			var input = document.getElementById("searchword").value;
			alert(input);
			searchList(input);
		}
	});
});

function viewList(){
	$.ajax({
		url:"${root}/shop/",  // 컨트롤러로 이동
		type:"GET",
		contentType:"application/json;charset=utf-8", // 보내는 데이터
		dataType:"json", // 넘어오는 데이터
		success:function(data) {
			makeList(data);
			console.log("aaa");
		},
		error:function(xhr,status,msg){
			console.log("상태값 : " + status + " Http에러메시지 : "+msg);
		}
	});
}

function searchList(input){
// 	alert('${root}/shop/');
	$.ajax({
		url:"${root}/shop/"+input,  // 컨트롤러로 이동
		type:"GET",
		contentType:"application/json;charset=utf-8", // 보내는 데이터
		dataType:"json", // 넘어오는 데이터
		success:function(data) {
			makeList(data);
			console.log("aaa");
		},
		error:function(xhr,status,msg){
			console.log("상태값 : " + status + " Http에러메시지 : "+msg);
		}
	});
}

function makeList(data){
	var list = '';
	console.log("list: "+data)
	$.each(data, function(idx, shop){
		list += "<tr><td>"+shop.shopname+'</td>'
				+'<td>'+shop.codename1+'</td>'
				+'<td>'+shop.codename4+'</td>'
				+'<td>'+shop.city+'</td>'
				+'<td>'+shop.gu+'</td>'
				+'<td>'+shop.dong+'</td></tr>';
	});
	console.log(list);
	$('#ajaxtbody').html(list);
}
</script>

	




	<h2>상권정보</h2>
	
	<form id="searchform" method="get" class="form-inline" action="">
		<table class="table table-borderless">
			<tr>
				<td align="right">
					<select class="form-control" name="key" id="key">
					      <option value="shopname" selected="selected">상점 이름</option>
					</select>
					<input type="text" class="form-control" placeholder="검색어 입력" name="searchword" id="searchword">
					<button type="button" id="searchBtn" class="btn btn-primary">검색</button>
				</td>
			</tr>
		</table>
	</form>
	
	<span>검색결과</span>
	
		<table class="table table-active">
			<thead>
				<tr>
					<th>상점이름</th>
					<th>대분류</th>
					<th>소분류</th>
					<th>시</th>
					<th>구</th>
					<th>층</th>
				</tr>
			</thead>
			<tbody id="ajaxtbody">
				
			
			
			
			</tbody>
		</table>

		
	
<%@ include file="/WEB-INF/views/template/footer.jsp" %>