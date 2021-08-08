<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script src="https://unpkg.com/@google/markerclustererplus@4.0.1/dist/markerclustererplus.min.js"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCMXtnv5FOhNVw0q66dGzHxXIqFS_UUY3A&callback=initMap"></script>
<script src="${root}/static/proj4js/proj4js-combined.js"></script>

<script	type="text/javascript">
$(document).ready(function(){
	viewList();
});

function viewList(){
	// CRUD 중 R
	$.ajax({
		url:'${root}/dealinfo/recentList',  // 컨트롤러로 이동
		type:'GET',
		contentType:'application/json;charset=utf-8', // 보내는 데이터
		dataType:'json', // 넘어오는 데이터
		success:function(data) {
			console.log(data);
			makeList(data);
		},
		error:function(xhr,status,msg){
			console.log("상태값 : " + status + " Http에러메시지 : "+msg);
		}
	});
}
function makeList(data){
	//alert(data);
	var list = '';
	
	$.each(data, function(idx, sales){ // data는 sales
		list += '<article class=\"blog_item\"><div class=\"blog_item_img\"><img class=\"card-img rounded-0\" src=\"${root}/static/aptimg/'+sales.aptName+'.jpg\">'
			+'<a href=\"#\" class=\"blog_item_date\"><h3>'+sales.dealMonth+'</h3><p>'+sales.dealDay+'</p></a></div>'
			+'<div class=\"blog_details\"><a class=\"d-inline-block\" href=\"#\"><h2>'+sales.aptName+'</h2></a>'
			+'<p>매물금액: '+sales.dealAmount+'</p><p>등록한 사람: '+sales.userid+'</p>'
			+'</div></article>';
		console.log(sales.aptName);
	});
	$('#recentList').html(list); // html: list를 isbnlist부분에 추가시킨다.
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



<section class="blog_area section-padding">
	<div class="container">
		<div class="row">
			<div class="col-lg-8 mb-5 mb-lg-0">
				<div class="blog_left_sidebar">
					<article id="recentList"></article>       
				</div>
			</div>
		</div>
	</div>
</section>

                               


<%@ include file="/WEB-INF/views/template/footer.jsp" %>