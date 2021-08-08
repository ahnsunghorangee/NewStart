<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script type="text/javascript">

function modify1(){

	$("#memberform").attr("action", "${root}/member/modify/${userinfo.userid}").submit();
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
		<form id="memberform" method="get" action="">
		
			<div class="form-group" align="left">
				<label for="userid">아이디</label>
				<input type="text" readonly="readonly" class="form-control" id="userid" name="userid" placeholder=""value="${userinfo.userid }">
			</div>
			<div class="form-group" align="left">
				<label for="username">이름</label>
				<input type="text" class="form-control" id="username" name="username" placeholder="" value="${userinfo.username }">
			</div>
			<div class="form-group" align="left">
				<label for="userpwd">비밀번호</label>
				<input type="password" class="form-control" id="userpwd" name="userpwd" placeholder=""value="${userinfo.userpwd }">
			</div>
			<div class="form-group" align="left">
				<label for="email">이메일</label><br>
				<div id="email" class="custom-control-inline">
				<input type="text" class="form-control" id="email" name="email" placeholder="" size="25"value="${userinfo.email }"> @
				<select class="form-control" id="emaildomain" name="emaildomain">
					<option value="naver.com">naver.com</option>
					<option value="google.com">google.com</option>
					<option value="daum.net">daum.net</option>
					<option value="nate.com">nate.com</option>
					<option value="hanmail.net">hanmail.net</option>
				</select>
				</div>
			</div>
			<div class="form-group" align="left">
				<label for="">주소</label><br>
				<div id="addressdiv" class="custom-control-inline">
					<input type="text" class="form-control" id="zipcode" name="zipcode" placeholder="우편번호" size="7" maxlength="5" readonly="readonly">
					<!--<button type="button" class="btn btn-primary" onclick="javascript:">우편번호</button>-->
				</div>
				<input type="text" class="form-control" id="address" name="address" placeholder="" value="${userinfo.address }">
				<input type="text" class="form-control" id="address_detail" name="address_detail" placeholder="">
			</div>
			
			<div class="form-group" align="center">
				<button type="button" class="btn btn-primary" id="modifyBtn" onclick="javascript:modify1()">수정완료</button>
				<button type="reset" class="btn btn-warning" id="cancleBtn" onclick="location.href='${root}/member/confirm'">취소</button>
			</div>
		</form>
		
		
		
	</div>
</div>

<div id="zipModal" class="modal fade" role="dialog">
<h5 class="modal-title" id="myModalLabel">우편번호검색</h5>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header text-center">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>    
            <div class="modal-body text-center">
            	<form id = "zip_codeForm">
            		<div align="center">
            			<label>도로명 주소검색</label>
            		</div>
					<div class="input-group" align="left">
						<input type="text" class="form-control" id="doro" name="doro" placeholder="검색 할 도로명 입력(예: 대왕판교로, 학하서로)">
						<span class="input-group-btn">
						<input type="submit" class="btn btn-warning" value="검색" id="searchBtn">
						</span>
					</div>
                </form>
                <div style="width:100%; height:200px; overflow:auto">
                	<table class = "table text-center">
                		<thead>
                		<tr>
                			<th style="width:150px;">우편번호</th>
                			<th style="width:600px;">주소</th>
                		</tr>
                		</thead>
                		<tbody id="zip_codeList"></tbody>
                	</table>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>