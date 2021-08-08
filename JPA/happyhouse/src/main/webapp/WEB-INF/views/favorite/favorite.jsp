<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script src="https://unpkg.com/@google/markerclustererplus@4.0.1/dist/markerclustererplus.min.js"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCMXtnv5FOhNVw0q66dGzHxXIqFS_UUY3A&callback=initMap"></script>
<style>
<!--

-->
</style>
<script type="text/javascript">
var pageNo = 1;
var pageMax = 1;

$(document).ready(function(){
	/* 페이지 로딩되자마자 (시도코드, 시도이름)리스트를 얻어온다. */
	$.get("${root}/selectbox/sido"
		,function(data, status){
			$.each(data, function(index, vo) {
				$("#sido").append("<option value='"+vo.sido_code+"'>"+vo.sido_name+"</option>");
				console.log(vo.sido_name+" "+vo.sido_code);
			});//each
		}//function
		, "json"
	);//get
	
	/* 페이지 로딩되자마자 목록 보여준다. */
	viewList();
	
	/* 조회버튼 클릭해도 목록 보여준다. */
	$('#listBtn').click(function() {
		viewList();
	})
	
}); //ready

$(document).ready(function(){
	$("#sido").change(function() { /* 시/도를 결정하면(change) 구/군 리스트를 얻어온다. */
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
		console.log($("#sido").val());

	});//change
	
	
	$("#gugun").change(function() { /* 구/군을 결정하면(change) 동 리스트를 얻어온다. */
		
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
	
	$("#dong").change(function() {
	});//change
	
});

// 녹지 or 오염지역 체크 시 이벤트 발생
$(document).ready(function(){
	// 녹지
	$("#green").change(function(){
		if($("#green").is(":checked")){

			deleteMarkers();// 마커한번 싹 다 비움 
			$.get("${root}/green/list"
					,{amu : 1}
					,function(data, status){
						
						$.each(data, function(index, vo){
							
				            var lat = (vo.lat).replace(/\"/g,'');
				            var lng = (vo.lng).replace(/\"/g,'');
				            var flag = 1;
				            console.log("위도: "+ lat+" 경도: "+lng);
				            var myLatLng = new google.maps.LatLng({lat: Number(lat), lng: Number(lng)});
							addMarker(myLatLng, vo.name,flag);
							
						});//each
						
					}//function
					, "json"
			);//get
			
			/* showMarkers(); */
			
		}else{
			/* alert("녹지 체크박스를 해제했음! ") */
			deleteMarkers();// 마커한번 싹 다 비움 
		}
	});
	// 오염지역  
	$("#pollution").change(function(){
		if($("#pollution").is(":checked")){

			deleteMarkers();// 마커한번 싹 다 비움 
			$.get("${root}/pollution/list"
					,{amu : 1}
					,function(data, status){
						var flag = 2;
						geocode(data,flag);// pollution은 위도,경도 데이터가 없어서 주소로 맵에 찍어야함.						
					}//function
					, "json"
			);//get
			
			showMarkers();
			
		}else{
			/* alert("오염지역 체크박스를 해제했음! ") */
			deleteMarkers();// 마커한번 싹 다 비움 
		}
	});
	
});

//목록보기
function viewList() {
	$.ajax({
		url : '${root}/favorite/list',
		type : 'GET',
		contentType : 'application/json;charset=utf-8',
		dataType : 'json',
		success : function(data) {
			makeList(data);
		},
		error : function(xhr, status, msg) {
			console.log("상태값 : " + status + " Http에러메시지 : " + msg);
		}
	});
}

//목록 <li> 추가하기 
function makeList(data) {
	var list = '';
	$.each(data, function(idx, vo) {
		list += '<li>'+vo.si + ' '+ vo.gu+' '+ vo.dong
				+ ' <input type="checkbox" class="heart" id="del'+idx+'" name="del" value="'+vo.favno+'"> <label for="del'+idx+'">❤</label> </li>';
	});

	$('#searchResult').html(list);
}

// 주소를 받아서 지도에 위치찍기 
function geocode(jsonData, flag) {
	let idx = 0;
    clearMarkers();
	$.each(jsonData, function(index, vo) {
		$.get("https://maps.googleapis.com/maps/api/geocode/json"
				,{	key:'AIzaSyCMXtnv5FOhNVw0q66dGzHxXIqFS_UUY3A'
					, address: vo.address
				}
				, function(data, status) {
		            addMarker(data.results[0].geometry.location, vo.name, flag);
				}
				, "json"
		);//get
	});//each
}

/* 등록버튼 누름 */
function addFavorite() {
	console.log("등록버튼누름 ");
	$.get("${root}/favorite/add"
			,{  gu: $("#gugun option:selected").text() ,
				dong:$("#dong").val(), 
				page: pageNo }
			,function(data, status){
				$("#searchResult").empty();
				makeList(data);
				
			}//function
			, "json"
	);//get
}



$(document).ready(function() {
	$('#delete').click(function() {
			if (confirm("선택하신지역을 관심목록에서 삭제하시겠습니까? ")) {
				$('#deleteform').attr('action','${root}/favorite/selectDelete').submit();
			}
	});
});


	/* 지 도 */
	var map;
	var markers = [];
	var Gumarkers= [];
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
		var multi = {
			lat : 37.5665734,
			lng : 126.978179
		};
		map = new google.maps.Map(document.getElementById('map'), {
			zoom : 11,
			center : multi
		});
		//관심지역목록 가져오기
		$.ajax({
			url : '${root}/favorite/list',
			type : 'GET',
			contentType : 'application/json;charset=utf-8',
			dataType : 'json',
			success : function(data) {
				$.each(data, function(idx, vo) {
					for (var i = 0; i < locations.length; i++) {
						console.log(locations[i]);
						if(vo.gu == locations[i][0]){
							var lo = { lat: locations[i][1] , lng: locations[i][2] };
							addMarker(lo, vo.gu , 3 );
						}
					}
					
				});
			},
			error : function(xhr, status, msg) {
				console.log("상태값 : " + status + " Http에러메시지 : " + msg);
			}
		});
	}

	// Adds a marker to the map and push to the array.
	function addMarker(location, name, flag) {
		var myIcon;
		if (flag == 1) {
			myIcon = new google.maps.MarkerImage(
					"${root}/static/img/green.png", null, null, null,
					new google.maps.Size(35, 35));
		}
		if (flag == 2) {
			myIcon = new google.maps.MarkerImage(
					"${root}/static/img/pollution.png", null, null, null,
					new google.maps.Size(35, 35));
		}
		if(flag ==3){
			myIcon = new google.maps.MarkerImage(
					"${root}/static/img/guname.png", null, null, null,
					new google.maps.Size(35, 35));
		}

		var marker = new google.maps.Marker({
			position : location,
			label : {text:name, fontFamily:'이롭게 바탕체', fontSize:'12px'},
			icon : myIcon,
			title : name
		});

		marker.addListener('click', function() {
			map.setZoom(13);
			map.setCenter(marker.getPosition());
			var info = new google.maps.InfoWindow({
				content : name
			});
			info.open(map, marker);
		});
		marker.setMap(map);
		markers.push(marker);
	}

	// Sets the map on all markers in the array.
	function setMapOnAll(map) {
		for (var i = 0; i < markers.length; i++) {
			markers[i].setMap(map);
		}
	}

	// Removes the markers from the map, but keeps them in the array.
	function clearMarkers() {
		setMapOnAll(null);
	}

	// Shows any markers currently in the array.
	function showMarkers() {
		setMapOnAll(map);
	}

	// Deletes all markers in the array by removing references to them.
	function deleteMarkers() {
		clearMarkers();
		markers = [];
	}
	///// 지도 끝 //////
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

<c:if test="${userinfo != null}">
	<div class="row" style="margin-left:50px; margin-right:50px">
		<div style="width:25%">
			<form action="#" class="search-box">
				<div class="select-form">
					<div class="select-itms" style="margin-bottom:50px">
						<select name="sido" id="sido">
							<option value="0">도/광역시</option>
						</select>
						<select name="gugun" id="gugun">
							<option value="0">시/구/군</option>
						</select>
						<select name="dong" id="dong">
							<option value="0">동</option>
						</select>
						<button class="btn btn-primary" type="button" onclick="addFavorite();">관심지역 추가</button>
					</div>
				</div>
			</form>
			<div>
				<form method="post" action="" id="deleteform">
					<h3><strong>나의 관심지역 목록 </strong></h3>
					<ul id="searchResult">
							<!-- ////////////////// -->
							<!-- 이부분에 목록 추가할꺼야  -->
							<!-- ////////////////// -->
					</ul>
					<button type="button" class="btn btn-link" id="delete">선택지역삭제</button>
				</form>
			</div>
		</div>
		<div style="margin-right:50px; width: 65%;" >
			<div>
				<h3><strong>관심지역 둘러보기</strong></h3>
			<form>
				<input type="checkbox" id="green" value="green"> 녹지
				<input type="checkbox" id="pollution" value="pollution"> 오염시설
				<input type="checkbox" id="subway" value="subway"> 지하철역
				<input type="checkbox" id="store" value="store"> 편의점
				<input type="checkbox" id="store" value="store"> 약국
			</form>
			<div id="map" style="width: 100%; height: 500px; margin: auto;"></div>
			</div>
		</div>
	</div>	

</c:if>
	
<%@ include file="/WEB-INF/views/template/footer.jsp" %>