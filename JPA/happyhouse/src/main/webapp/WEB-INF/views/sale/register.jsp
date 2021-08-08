<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script src="https://unpkg.com/@google/markerclustererplus@4.0.1/dist/markerclustererplus.min.js"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCMXtnv5FOhNVw0q66dGzHxXIqFS_UUY3A&callback=initMap"></script>
<script src="${root}/static/proj4js/proj4js-combined.js"></script>

<script type="text/javascript">

$(document).ready(function(){

	// 파일 업로드 관련 ajax
	$('#btnSubmit').click(function (event) {
        event.preventDefault();
        
        var form = $('#fileUploadForm')[0];
        var data = new FormData(form);
        data.append('customField', '추가필드');
        $('#btnSubmit').prop('disabled', true);

        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: '${root}/sale/upload/multi',
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                $('#result').text(data);
                console.log('SUCCESS : ', data);
                $('#btnSubmit').prop('disabled', false);
            },
            error: function (e) {
                $('#result').text(e.responseText);
                console.log('ERROR : ', e);
                $('#btnSubmit').prop('disabled', false);
            }
        });
        
    }); // end 파일 업로드
    
    // 매물 등록 버튼
    $("#registerBtn").click(function() {
    	$("#form").attr("action", "${root}/sale/register/${userinfo.userid}").submit();
    });
});//ready


var saveX, saveY;

/* 아파트 입력 및 좌표 얻어오기 */

// https://www.juso.go.kr/addrlink/devAddrLinkRequestGuide.do?menu=coordApi : 주소검색 위도경도 api 주소
function goPopup(){
	window.name="jusoPopup";
	// 주소검색을 수행할 팝업 페이지를 호출합니다.
	// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrCoordUrl.do)를 호출하게 됩니다.
	var pop = window.open("${root}/jusoPopup/go","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
}

// 콜백함수 할 때 매개변수로 전부 다 불러와야 됨. 안 그러면 내가 원하는 값을 못 가져옴.
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo,entX,entY){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
		
		/*
		document.form.code.value = admCd;
		document.form.bdNm.value = bdNm;
		document.form.AptName.value = detBdNmList;
		document.form.siNm.value = siNm;
		document.form.sggNm.value = sggNm;
		document.form.dong.value = emdNm;
		document.form.jibun.value = lnbrMnnm;
		document.form.entX.value = entX;
		document.form.entY.value = entY;
		*/
		
		document.form.roadFullAddr.value = roadFullAddr;
		document.form.roadAddrPart1.value = roadAddrPart1;
		document.form.roadAddrPart2.value = roadAddrPart2;
		document.form.addrDetail.value = addrDetail;
		document.form.engAddr.value = engAddr;
		document.form.jibunAddr.value = jibunAddr;
		document.form.zipNo.value = zipNo;
		document.form.admCd.value = admCd;
		document.form.rnMgtSn.value = rnMgtSn;
		document.form.bdMgtSn.value = bdMgtSn;
		document.form.detBdNmList.value = detBdNmList;
		document.form.bdNm.value = bdNm;
		document.form.bdKdcd.value = bdKdcd;
		document.form.siNm.value = siNm;
		document.form.sggNm.value = sggNm;
		document.form.emdNm.value = emdNm;
		document.form.liNm.value = liNm;
		document.form.rn.value = rn;
		document.form.udrtYn.value = udrtYn;
		document.form.buldMnnm.value = buldMnnm;
		document.form.buldSlno.value = buldSlno;
		document.form.mtYn.value = mtYn;
		document.form.lnbrMnnm.value = lnbrMnnm;
		document.form.lnbrSlno.value = lnbrSlno;
		document.form.emdNo.value = emdNo;
		document.form.entX.value = entX;
		document.form.entY.value = entY;
		
		saveX = entX;
		saveY = entY;
		
		
		transform();
}

/* x,y좌표 위도경도로 변환 */


function transform() {
	
    var srcX = saveX;
    var srcY = saveY;

    Proj4js.reportError = function (msg) {
        alert("Error : " + msg);
    }

    // WGS84 경위도: GPS가 사용하는 좌표계
    Proj4js.defs["EPSG:4326"] = "+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs";
    // utm-k
    Proj4js.defs["EPSG:5179"] = "+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +units=m +no_defs";
    
    var wgs84 = new Proj4js.Proj("EPSG:4326");
    var grs80 = new Proj4js.Proj("EPSG:5179");

    var p = new Proj4js.Point(srcX, srcY);
    Proj4js.transform(grs80,wgs84, p);

    var destX = p.x;
    var destY = p.y;
    
    $("#entY").val(destX);
	$("#entX").val(destY);
    
    // 지도에 마커 찍기
    console.log("result: " + destY+ " " + destX);
    deleteMarkers();
    var myLatLng = new google.maps.LatLng({lat: Number(destY), lng: Number(destX)});
    /* addMarker(myLatLng); */
    var marker = new google.maps.Marker({
		position: myLatLng
    });
    
	map.setZoom(13);
	map.setCenter(marker.getPosition());
	map.panTo(marker.getPosition());
    marker.setMap(map);
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
    
}

function addMarker(location, name) {
	var marker = new google.maps.Marker({
		position: location,
		label: name,
		title: name
	});
	marker.addListener('click', function() {
		map.setZoom(17);
		map.setCenter(marker.getPosition());
		map.panTo(marker.getPosition());
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

// 이미지 업로드 시 미리보기
function setThumbnail(event) {
	for (var image of event.target.files) {
		var reader = new FileReader();
		
		reader.onload = function(event) {
			var img = document.createElement("img");
			img.setAttribute("src", event.target.result);
			document.querySelector("div.location-img").append(img);
			};
		console.log(image);
		reader.readAsDataURL(image);
		}
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



<div class="section-top-border">
	<div class="row">
		<div class="col-lg-6 col-md-6">
			<h3 class="mb-30">매물등록</h3>
			
			<form id="form" name="form" method="post">
			
				<input type="button" class=" genric-btn primary " onClick="goPopup();" value="주소검색"/>
				
				<div id="list"></div>
				
				<div id="callBackDiv">
				
					<div class="mt-10">
						<input type="text" readonly="readonly" id="roadFullAddr" name="roadFullAddr" placeholder="도로명주소(전체)" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="roadAddrPart1" name="roadAddrPart1" placeholder="도로명주소" class="single-input">
					</div>
					<div class="mt-10">
						<input type="text" id="addrDetail" name="addrDetail" placeholder="고객입력 상세주소" class="single-input">
					</div>
					<div class="mt-10">
						<input type="text" readonly="readonly" id="roadAddrPart2" name="roadAddrPart2" placeholder="참고주소" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="engAddr" name="engAddr" placeholder="영문 도로명주소" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="jibunAddr" name="jibunAddr" placeholder="지번" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="zipNo" name="zipNo" placeholder="우편번호" class="single-input">
					</div>
					<div class="mt-10">
						<input type="text" readonly="readonly" id="admCd" name="admCd" placeholder="행정구역코드" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="rnMgtSn" name="rnMgtSn" placeholder="도로명코드" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="bdMgtSn" name="bdMgtSn" placeholder="건물관리번호" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="detBdNmList" name="detBdNmList" placeholder="상세건물명" class="single-input">
					</div>
					<div class="mt-10">
						<input type="text" readonly="readonly" id="bdNm" name="bdNm" placeholder="건물명" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="bdKdcd" name="bdKdcd" placeholder="공동주택여부" class="single-input">
					</div>
					<div class="mt-10">
						<input type="text" readonly="readonly" id="siNm" name="siNm" placeholder="시도명" class="single-input">
					</div>
					<div class="mt-10">
						<input type="text" readonly="readonly" id="sggNm" name="sggNm" placeholder="시군구명" class="single-input">
					</div>
					<div class="mt-10">
						<input type="text" readonly="readonly" id="emdNm" name="emdNm" placeholder="읍면동명" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="liNm" name="liNm" placeholder="법정리명" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="rn" name="rn" placeholder="도로명" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="udrtYn" name="udrtYn" placeholder="지하여부" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="buldMnnm" name="buldMnnm" placeholder="건물본번" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="buldSlno" name="buldSlno" placeholder="건물부번" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="mtYn" name="mtYn" placeholder="산여부" class="single-input">
					</div>
					<div class="mt-10">
						<input type="text" readonly="readonly" id="lnbrMnnm" name="lnbrMnnm" placeholder="지번본번(번지)" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="lnbrSlno" name="lnbrSlno" placeholder="지번부번(호)" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="emdNo" name="emdNo" placeholder="읍면동일련번호" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="entX" name="entX" placeholder="X 좌표" class="single-input" value="">
					</div>
					<div class="mt-10">
						<input type="hidden" id="entY" name="entY" placeholder="Y 좌표 " class="single-input" value="">
					</div>
					
				</div>
				<br>
				<br>
				<div>
					<div class="mt-10">
						<input type="text" id="dealAmount" name="dealAmount" placeholder="거래금액" onfocus="this.placeholder = ''" 
						onblur="this.placeholder = '거래금액'" class="single-input-primary">
					</div>
					<div class="mt-10">
						<input type="text" id="buildYear" name="buildYear" placeholder="건설년도" onfocus="this.placeholder = ''" 
						onblur="this.placeholder = '건설년도'" class="single-input-primary">
					</div>
					<div class="mt-10">
						<input type="text" id="area" name="area" placeholder="평수" onfocus="this.placeholder = ''" 
						onblur="this.placeholder = 'Primary color'" class="single-input-primary">
					</div>
					<div class="mt-10">
						<input type="text" id="floor" name="floor" placeholder="층" onfocus="this.placeholder = ''" 
						onblur="this.placeholder = 'Primary color'" class="single-input-primary">
					</div>
				</div>
				
				<div class="mt-10" align="center">
				<button type="button" id="registerBtn" name="registerBtn" class="genric-btn primary">매물등록</button>
				</div>
			</form>
				
				
		</div>
		<div class="col-lg-6 col-md-6">
			<div id="map" style="width: 100%; height: 500px; margin: auto;"></div>
			
			<h3>사진등록</h3>
			<form method="POST" enctype="multipart/form-data" id="fileUploadForm">
				<input type="hidden" name="extraField"/>
		<!-- 	<input type="file" name="files"/><br/><br/> -->
		<!-- 	<input type="file" name="files"/><br/><br/> -->
				<input type="file" id="image" name="files" accept="image/*" onchange="setThumbnail(event);" multiple/>
				<input type="submit" value="Submit" id="btnSubmit"/>
			</form>
			<pre>
				<span id="result"></span>
			</pre>
			<div class="popular-location section-padding30">
				<div class="container">
					<div class="row" >
						<div class="col-lg-4 col-md-6 col-sm-6">
							<div class="single-location mb-30">
								<div class="location-img"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</div>
</div>
 


<!--  
<form name="form" id="form" method="post">

	<input type="button" onClick="goPopup();" value="팝업_domainChk"/>
	<div id="list"></div>
	<div id="callBackDiv">
		<table>
			<tr><td>건물명        		</td><td><input type="text"  style="width:500px;" id="bdNm"  name="bdNm" /></td></tr>
			<tr><td>상세건물명        		</td><td><input type="text"  style="width:500px;" id="AptName"  name="AptName" /></td></tr>
			<tr><td>시도명        		</td><td><input type="text"  style="width:500px;" id="siNm"  name="siNm" /></td></tr>
			<tr><td>시군구명        	</td><td><input type="text"  style="width:500px;" id="sggNm"  name="sggNm" /></td></tr>
			<tr><td>읍면동명        	</td><td><input type="text"  style="width:500px;" id="dong"  name="dong" /></td></tr>
			<tr><td>지번본번(번지)     </td><td><input type="text"  style="width:500px;" id="jibun"  name="jibun" /></td></tr>
			<tr><td>X 좌표       		</td><td><input type="text"  style="width:500px;" id="entX"  name="entX" /></td></tr>
			<tr><td>Y 좌표       		</td><td><input type="text"  style="width:500px;" id="entY"  name="entY" /></td></tr>
		</table>
	</div>

	<div>
		<label for="dealAmount">거래금액</label>
		<input type="text" id="dealAmount" name="dealAmount">
		<label for="buildYear">건설년도</label>
		<input type="text" id="buildYear" name="buildYear">
		<label for="area">면적</label>
		<input type="text" id="area" name="area">
		<label for="floor">층</label>
		<input type="text" id="floor" name="floor">
	</div>
</form>


<div align="center">
	<div class="col-lg-12">
		
	</div>
</div>
<div id="image_container"></div>
-->

       



<%@ include file="/WEB-INF/views/template/footer.jsp" %>