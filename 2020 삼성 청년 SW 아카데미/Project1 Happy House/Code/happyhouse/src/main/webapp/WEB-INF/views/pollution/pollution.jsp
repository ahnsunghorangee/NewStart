<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script type="text/javascript">
$(document).ready(function(){
	
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
		url:"${root}/pollution/",  // 컨트롤러로 이동
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
	$.ajax({
		url:"${root}/pollution/"+input,  // 컨트롤러로 이동
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
	$.each(data, function(idx, pollution){
		list += "<tr><td>"+pollution.name+'</td>'
				+'<td>'+pollution.address+'</td>'
				+'<td>'+pollution.dong+'</td></tr>';
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
                    <h2>'오염시설'이다 구리!</h2>
                </div>
            </div>
        </div>
    </div>
</div>
<!--Hero End -->

	
<c:if test="${userinfo != null}">

<form name="pageform" id="pageform" method="GET" action="">
	<input type="hidden" name="act" id="act" value="list">
	<input type="hidden" name="pg" id="pg" value="">
	<input type="hidden" name="key" id="key" value="${key}">
	<input type="hidden" name="word" id="word" value="${word}">
</form>


<form id="searchform" method="get" class="form-inline" action="">
	<table class="table table-borderless">
		<tr>
			<td align="right">
				<select class="form-control" name="key" id="key">
				      <option value="shopname" selected="selected">오염시설 이름</option>
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
			<th>오염시설 이름</th>
			<th>주소</th>
			<th>동</th>
		</tr>
	</thead>
	<tbody id="ajaxtbody">
	</tbody>
	
</table>
	
</c:if>



<%@ include file="/WEB-INF/views/template/footer.jsp" %>