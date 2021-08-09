<!-- 글작성 버튼은 관리자에게만 보이며, 일반 사용자는 글목록만 볼 수 있다. (글작성 버튼 등 안 보이게) -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>
<script type="text/javascript">
$(document).ready(function(){
 	
	viewList(); //화면열리자마자 리스트 보여주기 
	$('#searchBtn').click(function(){
		if (document.getElementById("searchword").value == "") {
			alert("모든 목록 조회!!");
			viewList();
		} else{
			var input = document.getElementById("searchword").value;
			alert(input);
			searchList(input);
		}
	});
	$('#mvwrite').click(function(){
		$(location).attr('href', '${root}/notice/write');		
	});
	
});

function viewList(){
	
	$.ajax({
		url:"${root}/notice/",  // 컨트롤러로 이동
		type:"GET",
		contentType:"application/json;charset=utf-8", // 보내는 데이터
		dataType:"json", // 넘어오는 데이터
		success:function(data) {
			makeList(data);
			console.log("notice리스트얻어옴");
		},
		error:function(xhr,status,msg){
			console.log("상태값 : " + status + " Http에러메시지 : "+msg);
		}
	});
}

function searchList(input){
	$.ajax({
		url:"${root}/notice/"+input,  // 컨트롤러로 이동
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
	$.each(data, function(idx, notice){
			list += "<div class=\"col-lg-3 col-md-6 col-sm-6\">"
			+"<div class=\"single-cat text-center mb-50\">"
			+"<div class=\"cat-icon\">"
			+"<span class=\"flaticon-drink\"></span></div>"
			+"<div class=\"cat-cap\">"
			+"<h5>"+'<a href="${root}/notice/upform/'+ notice.articleno +' ">'+notice.articleno+"</a> </h5>"
			+"<p>"+notice.subject+"</p>"
			+"<p>"+notice.click+"</p>"
			+"<p>"+notice.regtime+"</p>"
			+"</div></div></div>";
	});
	console.log(list);
	$('#ajaxdiv').html(list);
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




	

<!-- Categories Area Start -->
<div class="categories-area section-padding30">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <!-- Section Tittle -->
                <div class="section-tittle text-center mb-80">
                    <span>We are offering for you</span>
                    <h2>공지사항</h2>
                </div>
            </div>
        </div>
        
        
        <div align="right">
			<!-- 관리자일 때만 글쓰기 버튼 보임 -->		
			<c:if test="${userinfo.userid eq 'admin'}"> 
				<button type="button" class="btn btn-primary" id="mvwrite">글쓰기</button>
			</c:if>
			
			<form id="searchform" method="get" class="form-inline" action="">
				<select class="form-control" name="key" id="key">
				      <option value="shopname" selected="selected">글제목</option>
				</select>
				<input type="text" class="form-control" placeholder="검색어 입력" name="searchword" id="searchword">
				<button type="button" id="searchBtn" class="btn btn-primary">검색</button>
			</form>
		</div>
		<br>
		<br>
		
        <div class="row" id="ajaxdiv"> 
        	<!-- ////////////// -->
        	<!-- 공지사항 목록 추가 -->
        	<!-- ////////////// -->
        </div>
    </div>
</div>
<!-- Categories Area End -->
	
<%@ include file="/WEB-INF/views/template/footer.jsp" %>