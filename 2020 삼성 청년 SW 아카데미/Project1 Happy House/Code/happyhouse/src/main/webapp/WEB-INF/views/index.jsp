<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script src="https://unpkg.com/@google/markerclustererplus@4.0.1/dist/markerclustererplus.min.js"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCMXtnv5FOhNVw0q66dGzHxXIqFS_UUY3A&callback=initMap"></script>

<style>
.label{
	font-family:'이롭게 바탕체';
	text-align: center;
	white-space: nowrap;
	padding: 2px;
}
div.RoomImg{
  position: relative;
  width: 140px;
  height: 140px;
}

div.heart{
  position: absolute;
  top: 5px;
  left:110px;
}

.selectbox{
	display:table;
	margin:0 auto;
}
.selectbox .select-form {
	display: table-cell;
	text-align: center;
	vertical-align: middle;
}
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
//페이징처리를 위한 변수
var pageNo = 1;
var pageMax = 1;
var spp = 10;
var curAptName='';
var curDong='';

// <li id="sido" data-value="0">도/광역시</li>
$(document).ready(function(){
	$.get("${root}/selectbox/sido"
		,function(data, status){
			$.each(data, function(index, vo) {
				$("#sido").append("<option value='"+vo.sido_code+"'>"+vo.sido_name+"</option>");
			});//each
		}//function
		, "json"
	);//get
});//ready

$(document).ready(function(){
	$("#sido").change(function() {
		$.get("${root}/selectbox/gugun"
				,{sido:$("#sido").val()}
				,function(data, status){
					$("#gugun").empty();
					$("#gugun").append('<option value="0">시/도/군</option>');
					$.each(data, function(index, vo) {
						$("#gugun").append("<option value='"+vo.gugun_code+"'>"+vo.gugun_name+"</option>");
					});//each
				}//function
				, "json"
		);//get
	});//change
	$("#gugun").change(function() {
		$.get("${root}/selectbox/dong"
				,{gugun:$("#gugun").val()}
				,function(data, status){
					$("#dong").empty();
					$("#dong").append('<option value="0">동</option>');
					$.each(data, function(index, vo) {
						$("#dong").append("<option value='"+vo.dong+"'>"+vo.dong+"</option>");
					});//each
				}//function
				, "json"
		);//get
	});//change
	$("#dong").change(function() { //동을 선택하면
		
		//맵을 새로 만들고 
		 map = new google.maps.Map(document.getElementById('map'), {
		        zoom: 11,
		        center: {lat: 1, lng: 1}
		 });
		 google.maps.event.addListener(map, 'zoom_changed', function(){ //리스너를 등록
		    	
				var zoom = map.getZoom();
				if(zoom <= 12){ //줌이 12보다 작으면 구마커만 보이게!
					initMap();
				}
		})//end addListener 
		
		pageNo = 1;
		// 1. 해당 동에 매물 총 갯수를 얻어서 => 페이징 처리 버튼을 만든다. 1 2 3 4 5 6 7 ... 
		$.get("${root}/selectbox/dealCount"
			,{gugun: $("#gugun").val(), // 11110 
			  dong:$("#dong").val()} // 견지동
			,function(data, status){
				$('#test2').empty();
				pageMax = Math.ceil(data/spp); // pageMax = 소수점이하올림(총매물갯수/10)
				console.log($("#dong").val()+"의 총 매물 갯수: "+data);		
				console.log("마지막페이지: "+pageMax);
				// 페이지 이동 버튼 만들기
				var startidx = 1;
				var endidx = pageMax > 7 ? 7 : pageMax;
				for(var n=startidx; n<=endidx; n++){
					if(n == pageNo){
						$('#test2').append('<a class="myButtonSelected" > '+n+' </a>');
					}else{
						$('#test2').append('<a class="myButton" onclick="mvPage('+n+',2)"> '+n+' </a>'); //2는 동으로 검색했을때 페이징처리
					}
				}
				if(endidx < pageMax){ // ...버튼 추가
					$('#test2').append('<a class="myButton" onclick="mvPage('+pageMax+',2)" >...</a>'); //2는 동으로 검색했을때 페이징처리
				}
			}
			,"json"
		);//get

		/* 여기 수정해야 해 ~!!~!~!~!~!~!~!*/
		// 2. 구+동으로 매물리스트 가져오기
		$.get("${root}/selectbox/aptbygudong"
				,{gugun: $("#gugun").val(),
				  dong:$("#dong").val(), 
				  pageNo: pageNo,
				  spp: spp }
				,function(data, status){
					makeList(data); //리스트 만들기
				}//function
				, "json"
		);//get
		
		deleteMarkers(markers);
		$.get("${root}/selectbox/houseinfo"
				,{dong:$("#dong").val()}
				,function(data, status){
					$.each(data, function(index, vo){
						
			            var lat = (vo.lat).replace(/\"/g,'');
			            var lng = (vo.lng).replace(/\"/g,'');
			            var flag = 1;
			            /* console.log("위도: "+ lat+" 경도: "+lng); */
			            var myLatLng = new google.maps.LatLng({lat: Number(lat), lng: Number(lng)});
						addMarker(myLatLng, vo.aptName);
						
					});//each
					
				}//function
				, "json"
		);//get
		
	});//change
	
});// end ready

//목록에 <li> 추가하기
function makeList(data){ //data : 구+동으로 검색한 모든 매물리스트
	// list[0] :왼쪽, list[1]:오른쪽 
	var list = ['<div style="width:50%; float:left;"> <ul> ', '<div style="width:50%; float:right;"> <ul> '];
	var imgpath;
	$.each(data, function(index,vo){
		// 상세정보 조회시 아파트이름, no 를 전달한다.
		let str = '<h3><a href= \" ${root}/detailinfo/'+vo.no+' \"> '+vo.aptName+'</a></h3>'
		+"거래금액: "+vo.dealAmount +"만원<br>"
		+ "면적: "+ vo.area+"㎡<br>"
		+"계약년도 "+vo.dealYear+"<br><br>";
		
		// 사용자아이디+no 로 zzim테이블에 데이터가 있는지 조회. ( 비동기라 여기 결과받기도 전에 아래 코드가 실행해버림 ... -> 그래서 동기로 수정.)
		$.ajax({
			type:'GET',
			async: false,
			url: "${root}/favorite/zzim",
			data: {no: vo.no},
			success: function(data,status){
				if(data==1){// 결과가 1이면, 이미 찜목록에 존재함.
					console.log('chheart에 들어옴 ');
					imgpath = "${root}/static/img/ckheart.png";
				}else{ // 업스면 => img : noheart
					console.log('noheart에 들어옴 ');
					imgpath = "${root}/static/img/noheart.png";
				}
			},
			dataType: 'json'
		});
		
		/* var aptimg = new Image(); */
		/* aptimg.src= "${root}/static/aptimg/"+vo.aptName+".jpg"; */
		/* aptimg.src = "${root}/static/aptimg/aptex.jpg";
		aptimg.onload = function(){
			//alert("파일있음");
		} */
		/* aptimg.onerror = function(){
			//alert("파일없음!");
			this.src = "${root}/static/aptimg/aptex.jpg";
		} */
		var onerror="javascript:this.src='${root}/static/aptimg/aptex.jpg'"
		var aptimg = "${root}/static/aptimg/"+vo.aptName+".jpg";
		var onerrorImg = "this.src= '${root}/static/aptimg/aptex.jpg'";
		list[index%2] += '<li> <div class="dong">'
					/* +'<div class="RoomImg"> <img src=\"${root}/static/aptimg/'+vo.aptName+'.jpg\" alt=\"제공되는 이미지가 없습니다.\" width="140" height="140" >' */
					+'<div class="RoomImg"> <img src='+ aptimg +' onerror ='+ onerror+'  alt=\"제공되는 이미지가 없습니다.\" width="140" height="140" >'
					+'<div class="heart"> <img id="'+vo.no+'" onclick="toggleHeart('+vo.no+')" src='+ imgpath +' width="25" height="25"> </div> </div> '
					+'<div class="RoomInfo"> '+ str +'</div>'
					+'</div> </li>';
					
		console.log(vo.aptName);

	});// end for
	list[0]+='</ul></div>';
	list[1]+='</ul></div>';
	
	$('#test1').html(list[0]+list[1]);
}// end makeList


//하트 누르면 토글!
function toggleHeart(no){ // 해당 매물번호 : no를 인자로. 
	// 하트버튼을 눌러서 여기로 들어옴.
	var img1='';
	// 1. zzim테이블에 있는지 조회
	$.get("${root}/favorite/zzim"
			,{no:no}
			,function(data, status){
				// 있으면=> 1, 없으면=> 0 반환.
				if(data<1){// 2. 없으면, 찜목록에 추가!  insert
					/* $.post("${root}/favorite/addzzim/"
							,{"no":no}
							,function(data, status){
								console.log("찜목록에 "+no+"번 매물이 추가되었습니다.")	
							}//function
							, "json"
					);//post */
					$.ajax({
						url:"${root}/favorite/zzim/",
						data: {no: no},
						type: 'post',
						success: function(data){
							console.log("찜목록에 "+no+"번 매물이 추가되었습니다.");	
						}
					});
					img1 = document.getElementById(no);
					img1.src = "${root}/static/img/ckheart.png";
					
				}else{// 3. 있으면, 이미추가되있던걸 삭제! delete
					//삭제
					$.ajax({
						url:"${root}/favorite/zzim/",
						data: {no: no},
						type: 'delete',
						success: function(data){
							console.log("찜목록에 "+no+"번 매물이 삭제되었습니다.")	
						}
					});
					img1 = document.getElementById(no);
					img1.src = "${root}/static/img/noheart.png";
				}
			}//function
			, "json"
	);//get
	
}





//페이지 이동 버튼 누르면 
function mvPage(num, type){
	
	if(type==1){//type=1 : 아파트 개별 조회
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
	}else if(type==2){ // type=2 : 동으로 조회
		$.get("${root}/selectbox/aptbygudong"
				,{gugun: $("#gugun").val(),
				  dong:$("#dong").val(), 
				  pageNo: num,
				  spp: spp }
				,function(data, status){
					makeList(data); //리스트 만들기
				}//function
				, "json"
		);//get
	}
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
		$('#test2').append('<a class="myButton" onclick="mvPage(1,'+type+')" >...</a>');
	}
	for(var n=startidx; n<=endidx; n++){
		if(n == pageNo){
			$('#test2').append('<a class="myButtonSelected" > '+n+' </a>');
		}else{
			$('#test2').append('<a class="myButton" onclick="mvPage('+n+','+type+')"> '+n+' </a>');
		}
	}
	if(endidx < pageMax){
		$('#test2').append('<a class="myButton" onclick="mvPage('+pageMax+','+type+')" >...</a>');
	}
}

/* 지 도 */
var map;
var Gumarkers= [];
var markers = [];
var Subwaymarkers=[];
var locations =[];
locations = [
	  ['도봉구'	,	37.6658609	,	127.0317674	],
		 ['은평구'	,	37.6176125	,	126.9227004	],
		 ['동대문구'	,	37.5838012	,	127.0507003	],
		 ['동작구'	,	37.4965037	,	126.9443073	],
		 ['금천구'	,	37.4600969	,	126.9001546	],
		 ['구로구'	,	37.4954856	,	126.858121	],
		 ['종로구'	,	37.5990998	,	126.9861493	],
		 ['강북구'	,	37.6469954	,	127.0147158	],
		 ['중랑구'	,	37.5953795	,	127.0939669	],
		 ['강남구'	,	37.4959854	,	127.0664091	],
		 ['강서구'	,	37.5657617	,	126.8226561	],
		 ['중구'	,	37.5579452	,	126.9941904	],
		 ['강동구'	,	37.5492077	,	127.1464824	],
		 ['광진구'	,	37.5481445	,	127.0857528	],
		 ['마포구'	,	37.5622906	,	126.9087803	],
		 ['서초구'	,	37.4769528	,	127.0378103	],
		 ['성북구'	,	37.606991	,	127.0232185	],
		 ['노원구'	,	37.655264	,	127.0771201	],
		 ['송파구'	,	37.5048534	,	127.1144822	],
		 ['서대문구'	,	37.5820369	,	126.9356665	],
		 ['양천구'	,	37.5270616	,	126.8561534	],
		 ['영등포구'	,	37.520641	,	126.9139242	],
		 ['관악구'	,	37.4653993	,	126.9438071	],
		 ['성동구'	,	37.5506753	,	127.0409622	],
		 ['용산구'	,	37.5311008	,	126.9810742	]
];

function initMap() {
	var zoom_level = 11;
	var infowindow = new google.maps.InfoWindow();		
    var multi = {//종로3가
    		lat : 37.571607,
			lng : 126.991806 
    };
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: zoom_level,
        center: multi
    });
    
    for (var i = 0; i < locations.length; i++) {
    	var myIcon = new google.maps.MarkerImage(
				"${root}/static/img/guname.png", null, null, null,
				new google.maps.Size(40, 40));//가로,세로 
		var Gumarker = new google.maps.Marker({ //마커 만들기
	        id:i,
	        title : locations[i][0],
	        label : {text:locations[i][0], fontFamily:'이롭게 바탕체', fontSize:'12px'},
	        icon: myIcon,
	        position: new google.maps.LatLng(locations[i][1], locations[i][2]),
	        map: map
	      });
		  Gumarkers.push(Gumarker);
	      
	      if(Gumarker)
	      {
	    	  Gumarker.addListener('click', function() {
	        		console.log(this.title);
	         		map.setZoom(12);
	         		map.panTo(this.getPosition());
	          });
	      }
    }// end for
    
 // 지하철정보 가져오기
	$.get("${root}/subway/list"
			,function(data, status){
				$.each(data, function(index, vo) {
					var pos = {lat: Number(vo.lat), lng: Number(vo.lng)}
					var myIcon = new google.maps.MarkerImage(
							"${root}/static/img/subway2.png", null, null, null,
							new google.maps.Size(30, 30));
					var marker = new google.maps.Marker({position: pos, map:map, icon: myIcon, title: vo.subname });
					Subwaymarkers.push(marker);
				});//each
				deleteMarkers(Subwaymarkers);
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
											$('#test2').append('<a class="myButton" onclick="mvPage('+n+',1)"> '+n+' </a>');
										}
									}
									if(endidx < pageMax){
										$('#test2').append('<a class="myButton" onclick="mvPage('+pageMax+',1)" >...</a>');
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
						
					});//end addListener
					markers.push(marker);
				});//each
				var copy_markers = [];
				var imagePath = "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m";
				var markerClusterer = new MarkerClusterer(map, copy_markers, {imagePath: imagePath});
				
				// 맵에서 줌이 변화하면 발동하는 이벤트리스너 등록
			    google.maps.event.addListener(map, 'zoom_changed', function(){
					var zoom = map.getZoom();
					console.log(zoom);
					if(zoom_level > 11 && zoom <= 11){ //줌이 11보다 작으면 구마커만 보이게!
						deleteMarkers(Subwaymarkers);
						deleteMarkers(markers);
						copy_markers = [];
						markerClusterer.clearMarkers();
						showMarkers(Gumarkers);
					} else if(zoom_level <= 11 && zoom > 11) { 
						deleteMarkers(Gumarkers);
						showMarkers(Subwaymarkers);
						showMarkers(markers);
						copy_markers = markers;
						markerClusterer = new MarkerClusterer(map, copy_markers, {imagePath: imagePath});
						markerClusterer.setMaxZoom(15);
					}
					zoom_level = zoom;
				})//end addListener 
				
				deleteMarkers(markers);
				
			}//function
			, "json"
	);//get
	
	
}// end initMap

function reset(){
	$("#sido").val("0").prop("selected", true);
	$("#gugun").val("0").prop("selected", true);
	$("#dong").val("0").prop("selected", true);
	$('#test1').empty();
	$('#test2').empty();
	initMap();
}

// Adds a marker to the map and push to the array.
function addMarker(location, name) {
	var myIcon = new google.maps.MarkerImage(
			"${root}/static/img/apart.png", null, null, null,
			new google.maps.Size(35, 35));
	var marker = new google.maps.Marker({
		position: location,
		/* label: name, */
		icon: myIcon
	});
	marker.addListener('click', function() {
		map.setZoom(17);
		map.setCenter(marker.getPosition());
        var info = new google.maps.InfoWindow({
            content: name
        });
        info.open(map, marker);
	});
	
	map.setZoom(15);
	map.setCenter(location);
	marker.setMap(map);
    /* markers.push(marker); */
}


function setMapOnAll(map,tmp_markers) {
    for (var i = 0; i < tmp_markers.length; i++) {
    	tmp_markers[i].setMap(map);
    }
}

// 마커를 맵위에 set
function showMarkers(tmp_markers) {
    setMapOnAll(map,tmp_markers);
}

// 마커들 제거.
function deleteMarkers(tmp_markers) { 
	setMapOnAll(null, tmp_markers);
	/* tmp_markers = []; */
}
/* 지도 끝 */
</script>


<!-- Hero Area Start-->
<div class="slider-area hero-overly">
    <div class="single-slider hero-overly  slider-height d-flex align-items-center">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-xl-8 col-lg-9">
                 <!-- Hero Caption -->
                 <div class="hero__caption">
                     <span>Happy House</span>
                     <h1>놀러오세요 싸피의숲</h1>
                 </div>
                 <!--Hero form -->
                 
                 
               </div>
            </div>
        </div>
    </div>
</div>
<!--Hero Area End-->

<!-- Popular Locations Start -->
<div class="popular-location section-padding30">
	<div class="row">
		<div class="col-lg-12">
			<!-- Section Tittle -->
			<div class="section-tittle text-center mb-80">
				<span>Most visited places</span>
				<h2>어느지역 매물을 찾으시나요 ?</h2>
			</div>
		</div>
	</div>
	
	<!-- 지도 -->
	<!-- <div class="row justify-content-between">
		<div class="col-lg-12">
			<div id="map" style="width: 100%; height: 500px; margin: auto;"></div>
		</div>
	</div> -->
	<div style="padding-left: 50px">
		<h3>
			<strong>매물목록</strong>
		</h3>
	</div>
	<div style="width: 100%; padding: 50px 50px 50px 50px; margin-bottom: 200px">
		
		<!-- 매물목록 -->
		<div id="searchResult"
			style="width: 25%; float: left; height: 500px; margin: auto;">
			<div id="test1"></div>
			<!-- <ul> -->
			<!-- <li></li> -->
			<!-- </ul> -->
			<div id="test2"></div>
			<!-- 페이지 이동버튼 -->
		</div>
		
		<!-- select box -->
		<div class="col-lg-12" style="padding-bottom: 20px; width: 75%; float: left;">
			<div class="row selectbox">
				<div class="select-form" style="padding-right: 10px">
					<div class="select-itms">
						<select id="sido" name="sido">
							<option value="0">도/광역시</option>
						</select>
					</div>
				</div>

				<div class="select-form" style="padding-right: 10px">
					<div class="select-itms">
						<select id="gugun" name="gugun">
							<option value="0">시/구/군</option>
						</select>
					</div>
				</div>

				<div class="select-form" style="padding-right: 10px">
					<div class="select-itms">
						<select id="dong" name="dong">
							<option value="0">동</option>
						</select>
					</div>
				</div>
				
				<div class="select-form" style="padding-right: 10px">
					<div class="select-itms">
						<input type="button" value="되돌리기" onclick="reset()">
					</div>
				</div>
			</div>
		</div>

		<!-- 지도 -->
		<div id="map" style="width: 70%; float: right; height: 500px; margin-bottom: 100px;"></div>
		<!-- margin: auto; -->
	</div>

</div>
<!-- Popular Locations End -->



	
	
<%@ include file="/WEB-INF/views/template/footer.jsp" %>