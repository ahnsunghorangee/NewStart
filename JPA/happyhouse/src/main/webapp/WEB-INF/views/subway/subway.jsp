<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script src="https://unpkg.com/@google/markerclustererplus@4.0.1/dist/markerclustererplus.min.js"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCMXtnv5FOhNVw0q66dGzHxXIqFS_UUY3A&callback=initMap"></script>
<style>
#searchResult{
	overflow: scroll;
}

.myButton {
	box-shadow: 0px 10px 14px -7px #9fb4f2;
	background:linear-gradient(to bottom, #1d5dcc 5%, #172d47 100%);
	background-color:#1d5dcc;
	border-radius:42px;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:12px;
	font-weight:bold;
	padding:6px 9px;
	text-decoration:none;
	text-shadow:0px 1px 0px #283966;
	margin: 3px 3px 3px 3px;
}
.myButton:hover {
	background:linear-gradient(to bottom, #172d47 5%, #1d5dcc 100%);
	background-color:#172d47;
}
.myButton:active {
	position:relative;
	top:1px;
}

.myButtonSelected {
	box-shadow: 0px 10px 14px -7px #9fb4f2;
	background:linear-gradient(to bottom, #ffffff 5%, #ffffff 100%);
	background-color:#ffffff;
	border-radius:42px;
	border:5px solid #4e6096;
	display:inline-block;
	cursor:pointer;
	color:#23326e;
	font-family:Arial;
	font-size:12px;
	font-weight:bold;
	padding:6px 9px;
	text-decoration:none;
	text-shadow:0px 1px 0px #283966;
}
.myButtonSelected:hover {
	background:linear-gradient(to bottom, #ffffff 5%, #ffffff 100%);
	background-color:#ffffff;
}
.myButtonSelected:active {
	position:relative;
	top:1px;
}
</style>

<script type="text/javascript">
// 페이징처리를 위한 변수
var pageNo = 1;
var pageMax = 1;
var spp = 10;
var curAptName='';
var curDong='';

	/* 지 도 */
	var map;
	var markers = [];
	function initMap() {
		var multi = { //종로3가
			lat : 37.571607,
			lng : 126.991806 
		};
		map = new google.maps.Map(document.getElementById('map'), {
			zoom : 12,
			center : multi
		});
		/* var bounds = new google.maps.LatLngBounds(); */
		
		// 지하철정보 가져오기
		$.get("${root}/subway/list"
				,function(data, status){
					$.each(data, function(index, vo) {
						var pos = {lat: Number(vo.lat), lng: Number(vo.lng)}
						var myIcon = new google.maps.MarkerImage(
								"${root}/static/img/subway2.png", null, null, null,
								new google.maps.Size(30, 30));
						var marker = new google.maps.Marker({position: pos, map:map, icon: myIcon, title: vo.subname});
					});//each
					
				}//function
				, "json"
		);//get
		
		
		// 매물정보 가져오기 
		$.get("${root}/dealinfo/list2"
				,function(data, status){
					$.each(data, function(index, vo) {
						var pos = {lat: Number(vo.lat), lng: Number(vo.lng)}
						var myIcon = new google.maps.MarkerImage(
								"${root}/static/img/apart2.png", null, null, null,
								new google.maps.Size(35, 35));
						var marker = new google.maps.Marker({position: pos, map:map, icon: myIcon, title: vo.aptName});
						//마커에 리스너추가
						marker.addListener('click', function() {
							//마커를 클릭하면 zoom 하고, 아파트명을 띄우고, 
							curAptName = vo.aptName;
							curDong = vo.dong;
							map.setZoom(17);
							map.setCenter(marker.getPosition());
							var info = new google.maps.InfoWindow({
								content : vo.aptName
							});
							info.open(map, marker);
							// 아파트명,동으로 매물정보를 조회해서, 지도아래에 리스트를 띄어주자!
							pageNo = 1;
							// 요청한 데이터 총갯수
							$.get("${root}/dealinfo/dealCount"
									,{aptname: vo.aptName,
									  dong: vo.dong}
									,function(data, status){
										$('#test2').empty();
										pageMax = Math.ceil(data/spp);
										
										// 페이지 이동 버튼 만들기
										var startidx = 1;
										var endidx = pageMax>7? 7 : pageMax;
										for(var n=startidx; n<=endidx; n++){
											if(n == pageNo){
												$('#test2').append('<a class="myButtonSelected" > '+n+' </a>');
											}else{
												$('#test2').append('<a class="myButton" onclick="mvPage('+n+')"> '+n+' </a>');
											}
										}
										if(endidx < pageMax){
											$('#test2').append('<a class="myButton" onclick="mvPage('+pageMax+')" >...</a>');
										}
									}
									,"json"
							);//get
									
							// 해당아파트명+동 의 매물리스트
							$.get("${root}/dealinfo/dealbyAptname"
									,{ aptname: vo.aptName,
										dong: vo.dong,
										pageNo: pageNo,
										spp: spp}
							
									,function(data, status){
										makeList(data); //리스트만들기
								}//function
								,"json"
							);//get
							
						});
						markers.push(marker);
						/* bounds.extend(marker.getPosition()); */
					});//each
					/* map.fitBounds(bounds); */
					// 마커클러스터 
					const imagePath = "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m";
					const markerClusterer = new MarkerClusterer(map, markers, {imagePath: imagePath});
				}//function
				, "json"
		);//get

	} // end initMap
	
////// 지도 끝 ///////
	
// 목록에 <li> 추가하기
function makeList(data){
	// list[0] :왼쪽, list[1]:오른쪽 
	var list = ['<div style="width:50%; float:left;"> <ul> ', '<div style="width:50%; float:right;"> <ul> '];
	
	$.each(data, function(index,vo){
		// 상세정보 조회시 아파트이름, no 를 전달한다.
		let str = '<h3><a href= \" ${root}/detailinfo/'+vo.no+' \"> '+vo.aptName+'</a></h3>'
		+"거래금액: "+vo.dealAmount +"만원<br>"
		+ "면적: "+ vo.area+"㎡<br>"
		+"계약년도 "+vo.dealYear+"<br><br>";
		
		list[index%2] += '<li> <div class="dung">'
					+'<div class="RoomImg"> <img src=\"${root}/static/aptimg/aptex.jpg\" alt=\"제공되는 이미지가 없습니다.\" width="140" height="140" > </div> '
					+'<div class="RoomInfo"> '+ str +'</div>'
					+'</div> </li>';

	});
	list[0]+='</ul></div>';
	list[1]+='</ul></div>';
	
	$('#test1').html(list[0]+list[1]);
}

//페이지 이동 버튼 누르면 
function mvPage(num){
	
	// 페이지넘버로 데이터 조회
	$.get("${root}/dealinfo/dealbyAptname"
		,{ aptname: curAptName,
			dong: curDong,
		   pageNo: num,
		   spp: spp}
							
		,function(data, status){
			makeList(data); //리스트만들기
		}//function
		,"json"
	);//get
	
	// 버튼 목록 갱신
	$('#test2').empty();
	pageNo = num;
	var startidx = pageNo - 3;
	var endidx = pageNo + 3;
	if(startidx <= 0 ){
		endidx = endidx - startidx +1;
		startidx = 1;
		if(endidx > pageMax){
			endidx = pageMax;
		}
	}else if(endidx > pageMax){
		startidx = startidx- (endidx-pageMax);
		endidx= pageMax;
		if(startidx <= 0){
			startidx = 1;
		}
	}
	
	if(startidx>1){
		$('#test2').append('<a class="myButton" onclick="mvPage(1)" >...</a>');
	}
	for(var n=startidx; n<=endidx; n++){
		if(n == pageNo){
			$('#test2').append('<a class="myButtonSelected" > '+n+' </a>');
		}else{
			$('#test2').append('<a class="myButton" onclick="mvPage('+n+')"> '+n+' </a>');
		}
	}
	if(endidx < pageMax){
		$('#test2').append('<a class="myButton" onclick="mvPage('+pageMax+')" >...</a>');
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
		<div style="padding-left: 50px">
			<h3><strong>매물목록</strong></h3>
		</div>
		<div style="width: 100%; padding:50px 50px 50px 50px; margin-bottom: 200px">
			<!-- 매물목록 -->
			<div id="searchResult" style="width:25%; float:left; height: 500px; margin:auto;">
				<div id="test1"></div>
				<!-- <ul> -->
					<!-- <li></li> -->
				<!-- </ul> -->
				<div id="test2"></div>
				<!-- 페이지 이동버튼 -->
			</div>
			<!-- 지도 -->
			<div id="map" style="width: 70%; float:right; height: 500px; margin-bottom: 100px;"></div> <!-- margin: auto; -->
		</div>

	
<%@ include file="/WEB-INF/views/template/footer.jsp" %>