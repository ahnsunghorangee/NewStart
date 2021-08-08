<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<c:if test="${userinfo == null}">
	<c:redirect url="/user.do"/>
</c:if> 

	
		<script type="text/javascript">
		/* $(document).ready(function(){
			$('#modifyBtn').click(function() {
				if($('#subject').val() == '') {
					alert("제목 입력!!!!");
					return;
				} else if($('#content').val() == '') {
					alert("내용 입력!!!!");
					return;
				} else {
					$('#modifyform').attr('action', '${root}/notice/modify').submit();
				}
			});
		}); */
		function writeArticle() {
			if(document.getElementById("subject").value == "") {
				alert("제목 입력!!!!");
				return;
			} else if(document.getElementById("content").value == "") {
				alert("내용 입력!!!!");
				return;
			} else {
			  	document.getElementById("modifyform").action = "${root}/notice/modify";
			  	document.getElementById("modifyform").submit();
			}
		} 
		
		function deleteArticle(){
			if(confirm("정말 삭제하시겠습니까?")) {
				document.getElementById("modifyform").action = "${root}/notice/delete";
			  	document.getElementById("modifyform").submit();
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
				<form id="modifyform" method="post" action="">
				<input type="hidden" name="articleno" id="articleno" value="${notice.articleno}">
					<div class="form-group" align="left">
						<label for="subject">제목:</label>
						<input type="text" class="form-control" id="subject" name="subject" value="${notice.subject}">
					</div>
					<div class="form-group" align="left">
						<label for="content">내용:</label>
						<textarea class="form-control" rows="15" id="content" name="content">${notice.content}</textarea>
					</div>
					<c:if test="${userinfo.userid eq 'admin'}"> 
						<button type="button" class="btn btn-primary" onclick="javascript:writeArticle();">글수정</button>
						<button type="button" class="btn btn-primary" onclick="javascript:deleteArticle();">삭제</button>
						<button type="button" class="btn btn-warning" onclick="javascript:listNotice()">취소</button>
					</c:if> 
				</form>
			</div>
		</div>
<%@ include file="/WEB-INF/views/template/footer.jsp" %>