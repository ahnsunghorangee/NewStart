<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script type="text/javascript" src="${root}/static/assets/js/httpRequest.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#registerBtn").click(function() {
		
		if($("#username").val() == "") {
			alert("이름 입력!!!");
			return;
		} else if($("#userid").val() == "") {
			alert("아이디 입력!!!");
			return;
		} else if($("#userpwd").val() == "") {
			alert("비밀번호 입력!!!");
			return;
		} else if($("#userpwd").val() != $("#pwdcheck").val()) {
			alert("비밀번호 확인!!!");
			return;
		} else {
			alert("이메일 인증 후 사용하세요.");
			$("#form").attr("action", "${root}/member/join").submit();
		}
	});
	 
	$('#zipcode').focusin(function() {
		$('#zipModal').modal();
	});
});

function openidcheck(){
	window.open("","","top=200, left=300, width=400, height=180, menubar=no, status=no, toolbar=no, location=no, scrollbars=no");
}
//https://www.juso.go.kr/addrlink/devAddrLinkRequestGuide.do?menu=coordApi : 주소검색 위도경도 api 주소
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
		
}


var result;
var flag = 0;

function idcheck() {
	var sid = document.getElementById("userid").value;
	result = document.getElementById("idresult");
	if(sid.length < 4 || sid.length > 16) {
		result.innerHTML = "<font color='red'>아이디는 4자이상 16자이하입니다.</font>";
	} else {
		var param = "sid=" + sid;
		sendRequest("${root}/member/idcheck.test", param, resultview, "GET");
	}
}

function resultview() {
	if(httpRequest.readyState == 4) {
		if(httpRequest.status == 200) {
			
			var txt = httpRequest.responseText;//1,java2   0,dfdfeer
			var resultTxt = txt.split(",");
			var color = "pink";
			var yn = "불가능";
			if(resultTxt[0] == 0) {
				color = "cyan";
				yn = "가능";
				flag = 1;
			} else {
				flag = 0;
			}
			result.innerHTML = "<font color=\"" + color + "\">" + resultTxt[1] + "는 사용 " + yn + "합니다.</font>";
			
		}
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


	<div class="container" align="center">
		<div class="col-lg-6" align="center">
			
			<form id="form" name="form" method="post">
			
				
				
				<div id="list"></div>
				
				

				<div class="form-group" align="left">
					<label for="name">이름</label>
					<input type="text" class="single-input" id="username" name="username" placeholder="">
				</div>
				<div class="form-group" align="left">
					<label for="">아이디</label>
					<input type="text" class="single-input" id="userid" name="userid" placeholder="" onkeyup="javascript:idcheck();">
					<div id="idresult"></div>
				</div>
				<div class="form-group" align="left">
					<label for="">비밀번호</label>
					<input type="password" class="single-input" id="userpwd" name="userpwd" placeholder="">
				</div>
				<div class="form-group" align="left">
					<label for="">비밀번호재입력</label>
					<input type="password" class="single-input" id="pwdcheck" name="pwdcheck" placeholder="">
				</div>
				<div class="form-group" align="left">
					<label for="email">이메일</label><br>
					<div id="email" class="custom-control-inline">
						<input type="text" class="form-control" id="email" name="email" placeholder="" size="25" value=""> @
						<select class="form-control" id="emaildomain" name="emaildomain">
							<option value="naver.com">naver.com</option>
							<option value="google.com">google.com</option>
							<option value="daum.net">daum.net</option>
							<option value="nate.com">nate.com</option>
							<option value="hanmail.net">hanmail.net</option>
						</select>
					</div>
				</div>
				<br>
				
				<input type="button" class=" genric-btn primary " onClick="goPopup();" value="주소검색"/>
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
						<input type="hidden" readonly="readonly" id="roadAddrPart2" name="roadAddrPart2" placeholder="참고주소" class="single-input">
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
						<input type="hidden" readonly="readonly" id="admCd" name="admCd" placeholder="행정구역코드" class="single-input">
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
						<input type="hidden" readonly="readonly" id="bdNm" name="bdNm" placeholder="건물명" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" id="bdKdcd" name="bdKdcd" placeholder="공동주택여부" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" readonly="readonly" id="siNm" name="siNm" placeholder="시도명" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" readonly="readonly" id="sggNm" name="sggNm" placeholder="시군구명" class="single-input">
					</div>
					<div class="mt-10">
						<input type="hidden" readonly="readonly" id="emdNm" name="emdNm" placeholder="읍면동명" class="single-input">
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
						<input type="hidden" readonly="readonly" id="lnbrMnnm" name="lnbrMnnm" placeholder="지번본번(번지)" class="single-input">
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
				
				<div class="form-group" align="center">
					<button type="button" class="genric-btn success circle arrow" id="registerBtn">회원가입</button>
					<button type="reset" class="genric-btn warning circle arrow">초기화</button>
				</div>
			
			
			
				<!--  <div class="form-group" align="left">
					<label for="tel">전화번호</label>
					<div id="tel" class="custom-control-inline">
					<select class="form-control" id="tel1" name="tel1">
						<option value="010">010</option>
						<option value="02">02</option>
						<option value="031">031</option>
						<option value="032">032</option>
						<option value="041">041</option>
						<option value="051">051</option>
						<option value="061">061</option>
					</select> _
					<input type="text" class="form-control" id="tel2" name="tel2" placeholder=""> _
					<input type="text" class="form-control" id="tel3" name="tel3" placeholder="">
					</div>
				</div> -->
				
				
			</form>
		</div>
	</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>