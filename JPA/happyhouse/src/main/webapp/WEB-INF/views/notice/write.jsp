<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>



	  	<script type="text/javascript">
	  		
  		function writeNotice() {
			if(document.getElementById("subject").value == "") {
				alert("제목 입력!!!!");
				return;
			} else if(document.getElementById("content").value == "") {
				alert("내용 입력!!!!");
				return;
			} else {	
	  			document.getElementById("writeform").action = "${root}/notice/write";
	  			document.getElementById("writeform").submit();
			}
  		}
  		
		function listNotice() {
			location.href="${root}/notice/list";
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
			<div class="col-lg-8" align="center">
				<br>
				<form id="writeform" method="post" action="">
				<input type="hidden" name="act" id="act" value="write">
					<div class="form-group" align="left">
						<label for="subject">제목:</label>
						<input type="text" class="form-control" id="subject" name="subject">
					</div>
					<div class="form-group" align="left">
						<label for="content">내용:</label>
						<textarea class="form-control" rows="15" id="content" name="content"></textarea>
					</div>
					<button type="button" class="btn btn-primary" onclick="javascript:writeNotice();">글작성</button>
					<button type="button" class="btn btn-warning" onclick="javascript:listNotice()">취소</button>
				</form>
			</div>
		</div>
<%@ include file="/WEB-INF/views/template/footer.jsp" %>
