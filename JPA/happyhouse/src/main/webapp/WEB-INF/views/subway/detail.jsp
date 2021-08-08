<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script src="https://unpkg.com/@google/markerclustererplus@4.0.1/dist/markerclustererplus.min.js"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCMXtnv5FOhNVw0q66dGzHxXIqFS_UUY3A&callback=initMap"></script>

<%-- <c:if test="${userinfo == null}">
	<c:redirect url="/user.do"/>
</c:if>  --%>

<style>
table{
	border-collapse: separate;
	border-spacing: 20px 20px;
}
</style>
	
<script type="text/javascript">
/* 페이징을 위한 전역변수 선언*/
var pageNo = 1;
var pageMax = 1;
var spp = 10;
var aptimgpath= "${root}/static/aptimg/"+"${house.aptName}"+".jpg";

// 아파트 이미지 설정!
$(document).ready(function(){
	var image = document.getElementById('aptimg');
	image.src = "${root}/static/aptimg/"+"${house.aptName}"+".jpg";
	console.log(image.src);
});

// 지도에 마커를 변경하기 위한 변수 : shop
var shop='';

$(document).ready(function(){
	$.get("${root}/shop/large"
		,function(data, status){
			$.each(data, function(index, vo) {
				$("#largeCode").append("<option value='"+vo.code1+"'>"+vo.codename1+"</option>");
				console.log(vo.code1+" "+vo.codename1);
			});//each
		}//function
		, "json"
	);//get
});//ready

$(document).ready(function(){
	$("#largeCode").change(function() { //대분류 어떤거 하나를 선택해서 값이 바뀌면, 
		$.get("${root}/shop/middle"
				,{largecode:$("#largeCode").val()}
				,function(data, status){
					$("#middleCode").empty();
					$("#middleCode").append('<option value="0">—중분류 선택—</option>');
					$.each(data, function(index, vo) {
						$("#middleCode").append("<option value='"+vo.code2+"'>"+vo.codename2+"</option>");
					});//each
				}//function
				, "json"
		);//get
	});//change
	
	$("#middleCode").change(function() { //중분류 어떤거 하나 선택해서 값이 바뀌면, 
		$.get("${root}/shop/small"
				,{middlecode:$("#middleCode").val()}
				,function(data, status){
					$("#smallCode").empty();
					$("#smallCode").append('<option value="0">—소분류 선택—</option>');
					$.each(data, function(index, vo) {
						$("#smallCode").append("<option value='"+vo.code3+"'>"+vo.codename3+"</option>");
					});//each
				}//function
				, "json"
		);//get
	});//change
	$("#smallCode").change(function() { //소분류를 선택하면 
		pageNo = 1;
		deleteMarkers();// 다른거 선택했을때 마커한번 싹다 비움 
		$.get("${root}/shop/shopinfo"
				,{smallcode:$("#smallCode").val(), page: pageNo}
				,function(data, status){
					$("#searchResult").empty();
					$.each(data, function(index, vo) {
						if(( pageNo - 1 )* spp <= index & index < pageNo*spp ){
							let str = "<h6>"+vo.shopname+"</h6>"+"상세업종: "+vo.codename3 +"<br>"+ "주소: "+ vo.address+"<br><br>";
							$("#searchResult").append(str);
						}
			            var lat = (vo.lat).replace(/\"/g,'');
			            var lng = (vo.lng).replace(/\"/g,'');
			            console.log("위도: "+ lat+" 경도: "+lng);
			            var myLatLng = new google.maps.LatLng({lat: Number(lat), lng: Number(lng)});
			            shop = vo.code1; // 9가지로 구분. D:소매, S:의료, Q:음식, F:생활서비스, R: 학문/교육, L: 부동산, O: 숙박, N:관광/여가/오락, P:스포츠
						addMarker(myLatLng, vo.shopname);
						
					});//each
					
					pageMax = (data.length -1)/spp +1 ;
					$("#searchResult").append("<button id='prevPage'>이전</button>");
					for(var i=1; i<= pageMax; i++){
						let fontcolor = 'black';
						if(i==pageNo) fontcolor='red'
						/* $("#searchResult").append('<div onclick="goPage('+i+')">') */
						$("#searchResult").append('<font color="'+fontcolor+'">  '+i+'  </font>')
						/* $("#searchResult").append('</div>') */
					}
					$("#searchResult").append("<button id='nextPage'>다음</button>");
					$("#prevPage").click(prev)
					$("#nextPage").click(next)
					
				}//function
				, "json"
		);//get
		showMarkers();
		
	});//change
	
});
/* 페이징처리를 위한 함수 */
function prev(){
	if(pageNo>1){
		pageNo = pageNo - 1;
		$.get("${root}/shop/shopinfo"
				,{smallcode:$("#smallCode").val(), page: pageNo}
				,function(data,status){
					makePageButton(data,status)
				}
				, "json"
		);//get
	}
}
function next(){
	if(pageNo<pageMax){
		pageNo = pageNo + 1;
		$.get("${root}/shop/shopinfo"
				,{smallcode:$("#smallCode").val(), page: pageNo}
				,function(data,status){
					makePageButton(data,status)
				}
				, "json"
		);//get
	}
}
function goPage(p){
		pageNo = p
		$.get("${root}/shop/shopinfo"
				,{smallcode:$("#smallCode").val(), page: pageNo}
				,function(data,status){
					makePageButton(data,status)
				}
				, "json"
		);//get
}

function makePageButton(data, status){
	$("#searchResult").empty();
	$.each(data, function(index, vo) {
		if(( pageNo - 1 )* spp <= index & index < pageNo*spp ){
			let str = "<h6>"+vo.shopname+"</h6>"+"상세업종: "+vo.codename3 +"<br>"+ "주소: "+ vo.address+"<br><br>";
			$("#searchResult").append(str);
		}
	});//each
	$("#searchResult").append("<button id='prevPage'>이전</button>");
	for(var i=1; i<= pageMax; i++){
		let fontcolor = 'black';
		if(i==pageNo) fontcolor='red'
		/* $("#searchResult").append('<div onclick="goPage('+i+')">') */
		$("#searchResult").append('<font color="'+fontcolor+'">  '+i+'  </font>')
		/* $("#searchResult").append('</div>') */
	}
	$("#searchResult").append("<button id='nextPage'>다음</button>");
	$("#prevPage").click(prev)
	$("#nextPage").click(next)
}

/* 지도 */
var map;
var markers = [];

function initMap() {
    
    var curhouse = new google.maps.LatLng(${house.lat}, ${house.lng});
    var mapCanvas = document.getElementById('map');
    var mapOptions = {center: curhouse, zoom: 14};
    map = new google.maps.Map(mapCanvas, mapOptions);
    var myHouse = new google.maps.Circle({
        center: curhouse,
        radius: 400,
        strokeColor: "#0000FF",
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: "#0000FF",
        fillOpacity: 0.4
      });
    myHouse.setMap(map);
    
}

//주소를 받아서 지도에 위치찍기 
function geocode(jsonData, flag) {
	let idx = 0;
    clearMarkers();
	$.each(jsonData, function(index, vo) {
		$.get("https://maps.googleapis.com/maps/api/geocode/json"
				,{	key:'AIzaSyCMXtnv5FOhNVw0q66dGzHxXIqFS_UUY3A'
					, address: vo.address
				}
				, function(data, status) {
		            addgreenMarker(data.results[0].geometry.location, vo.name, flag);
				}
				, "json"
		);//get
	});//each
}

// addMarker(위치, 이름) : 위치,이름 지정해주면 마커하나를 만든다.
function addMarker(location, name) { //location형태 : {lat: 11.01, lng: 12.11  }
	var myIcon='';
	
	switch(shop){
	case "D"://소매
		myIcon=new google.maps.MarkerImage("${root}/static/img/retail.png", null, null, null, new google.maps.Size(35,35));
		break;
	case "S"://의료
		myIcon=new google.maps.MarkerImage("${root}/static/img/medical.png", null, null, null, new google.maps.Size(35,35));
		break;
	case "Q"://음식
		myIcon=new google.maps.MarkerImage("${root}/static/img/food.png", null, null, null, new google.maps.Size(35,35));
		break;
	case "F"://생활서비스
		myIcon=new google.maps.MarkerImage("${root}/static/img/service.png", null, null, null, new google.maps.Size(35,35));
		break;
	case "R"://학문/교육
		myIcon=new google.maps.MarkerImage("${root}/static/img/edu.png", null, null, null, new google.maps.Size(35,35));
		break;
	case "L"://부동산
		myIcon=new google.maps.MarkerImage("${root}/static/img/realty.png", null, null, null, new google.maps.Size(35,35));
		break;
	case "O"://숙박
		myIcon=new google.maps.MarkerImage("${root}/static/img/hotel.png", null, null, null, new google.maps.Size(35,35));
		break;
	case "N": //관광/여가/오락
		myIcon=new google.maps.MarkerImage("${root}/static/img/enter.png", null, null, null, new google.maps.Size(35,35));
		break;
	case "P"://스포츠
		myIcon=new google.maps.MarkerImage("${root}/static/img/sports.png", null, null, null, new google.maps.Size(35,35));
		break;
		
	}
	
	var marker = new google.maps.Marker({
		position: location,
		/* label: name, */
		icon: myIcon,
		title: name
	});
	
	marker.addListener('click', function() {
		map.setZoom(17);
		map.setCenter(marker.getPosition());
        var info = new google.maps.InfoWindow({
            content: name
        });
        info.open(map, marker);
	});
	marker.setMap(map);
    markers.push(marker);
}

//녹지 or 오염지역 체크 시 이벤트 발생
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
							addgreenMarker(myLatLng, vo.name,flag);
							
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

function addgreenMarker(location, name, flag) {
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

	var marker = new google.maps.Marker({
		position : location,
		icon : myIcon,
		title : name
	});

	marker.addListener('click', function() {
		map.setZoom(17);
		map.setCenter(marker.getPosition());
		var info = new google.maps.InfoWindow({
			content : name
		});
		info.open(map, marker);
	});
	marker.setMap(map);
	markers.push(marker);
}

function setMapOnAll(map) {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
}

function clearMarkers() {
    setMapOnAll(null);
}

function showMarkers() {
    setMapOnAll(map);
}

function deleteMarkers() {
    clearMarkers();
    markers = [];
}


</script>

<!-- Hero Start-->
<div class="hero-area2  slider-height2 hero-overly2 d-flex align-items-center ">
    <div class="container">
        <div class="row">
            <div class="col-xl-12">
                <div class="hero-cap text-center pt-50">
                    <!--  -->
                </div>
            </div>
        </div>
    </div>
</div>
<!--Hero End -->
<br>
<br>
		<div class="container" align="center">
			<div class="col-lg-8" align="center">
				<table style="margin-bottom:100px">
					<tr>
						<img id="aptimg" src= "" onerror="this.src= '${root}/static/aptimg/aptex.jpg'" alt="제공되는 이미지가 없습니다." width="600" height="300" >
					</tr>
					<tr>
						<td colspan="6" align="center"> <h3> ${house.aptName} </h3> </td>
					</tr>
					<tr>
						<td colspan="6"> [ 확인매물 ${house.dealYear}.${house.dealMonth}.${house.dealDay} ]</td>
					</tr>
					<tr>
						 <td colspan="2">  ${house.dong} </td> 
						 <td colspan="2"> ${house.jibun} 지번 </td>
						 <td colspan="2"> ${house.floor} 층</td>
					</tr>
					<tr>
						<td> ${house.dealAmount}만원 </td>
						<td></td>
						<td> 면적 : ${house.area} ㎡ </td>
						<td></td>
						<td> 준공년도 : ${house.buildYear} </td>
						<td></td>
					</tr>
				</table>
			</div>
		</div>
	
	<!-- 위치 및 주변시설  -->
	
        <div align="center" style="margin-bottom: 30px;">
        		<h1> 위치 및 주변시설 </h1>
        		<!-- select box -->
        		<div class="col-lg-12" style= "width: 100%;">
					<div class="row" style="width:100%; margin-left:250px">
						<div style="margin-right: 20px"> <h5> 상권정보 상세조회 </h5> </div>
						<div class="select-form" style="padding-right: 10px">
							<div class="select-itms">
								<select name="largeCode" id="largeCode">
									<option value="all" >—대분류 선택—</option>
								</select>
							</div>
						</div>
		
						<div class="select-form" style="padding-right: 10px">
							<div class="select-itms">
								<select name="middleCode" id="middleCode">
									<option value="all" >—중분류 선택—</option>
								</select>
							</div>
						</div>
		
						<div class="select-form" style="padding-right: 10px">
							<div class="select-itms">
								<select name="smallCode" id="smallCode">
								<option value="all" >—소분류 선택—</option>
							</select>
							</div>
						</div>
					</div>
					<form>
	        			<input type="checkbox" id="green" value="green"> 녹지
						<input type="checkbox" id="pollution" value="pollution"> 오염시설
       				</form>
				</div>
		</div>
        
        
        <div align="center" id="map" style="width: 80%; height: 600px; margin:auto"></div>
        
            
            
            <div class="col-xl-4 col-lg-4">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="small-section-tittle2 mb-45">
                            <!-- <div id="searchResult"></div> -->
                        </div>
                    </div>
                </div>
                
			</div>
			

				
<%@ include file="/WEB-INF/views/template/footer.jsp" %>