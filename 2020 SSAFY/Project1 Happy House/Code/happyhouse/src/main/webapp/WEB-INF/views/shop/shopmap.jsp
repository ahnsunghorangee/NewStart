<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script src="https://unpkg.com/@google/markerclustererplus@4.0.1/dist/markerclustererplus.min.js"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCMXtnv5FOhNVw0q66dGzHxXIqFS_UUY3A&callback=initMap"></script>

<script type="text/javascript">
/* 페이징을 위한 전역변수 선언*/
var pageNo = 1;
var pageMax = 1;
var spp = 10;

/* 지도에 마커를 변경하기 위한 변수 */
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
    var multi = {
        lat: 37.5665734,
        lng: 126.978179
    };
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 12,
        center: multi
    });
    // This event listener will call addMarker() when the map is clicked.
    addMarker(multi);
    showMarkers();
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

</script>


<!-- Hero Start-->
<div class="hero-area2  slider-height2 hero-overly2 d-flex align-items-center ">
    <div class="container">
        <div class="row">
            <div class="col-xl-12">
                <div class="hero-cap text-center pt-50">
                    <h2>'상권정보'이다 구리!</h2>
                </div>
            </div>
        </div>
    </div>
</div>
<!--Hero End -->

<!-- listing Area Start -->
<div class="listing-area pt-120 pb-120">
    <div class="container">
        <div class="row">
        	<form class="form-inline" id="frm" action="shopmap.jsp">
				<input type='hidden' name='dongcode' value='' id='dongcode'/>
				<input type='hidden' name='dong' value='' id='dong'/>
				<div class="form-group md">
					<select class="form-control" name="largeCode" id="largeCode">
						<option value="all" >—대분류 선택—</option>
					</select>
				</div>
				<div class="form-group md-1">
					<select class="form-control" name="middleCode" id="middleCode">
						<option value="all" >—중분류 선택—</option>
					</select>
				</div>
				<div class="form-group md-1">
					<select class="form-control" name="smallCode" id="smallCode">
						<option value="all" >—소분류 선택—</option>
					</select>
				</div>
			</form>
        </div>
        
        <br>
        <br>
        
        <div class="row">
        	<!-- Left content -->
        	<div class="col-xl-8 col-lg-8 col-md-6">
	            <div class="row">
	                <div class="col-lg-12">
	                    <div class="count mb-35">
	                        <div class='col-12 justify-content-center' id="map" style="width: 1500px; height: 600px; margin: auto;"></div>
	                    </div>
	                </div>
	            </div>
            </div>
            
            
            <div class="col-xl-4 col-lg-4">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="small-section-tittle2 mb-45">
                        	
                            <div id="searchResult"></div>
                        </div>
                    </div>
                </div>
                
			</div>
		</div>
	</div>
</div>
		
		
		
		

	
<%@ include file="/WEB-INF/views/template/footer.jsp" %>