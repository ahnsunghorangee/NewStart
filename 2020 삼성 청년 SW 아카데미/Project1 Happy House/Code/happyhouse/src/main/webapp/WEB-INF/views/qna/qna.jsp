<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>SSAFY</title>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="manifest" href="site.webmanifest">
<link rel="shortcut icon" type="image/x-icon" href="${root}/static/assets/img/favicon.ico">

<!-- CSS here -->
<link rel="stylesheet" href="${root}/static/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="${root}/static/assets/css/owl.carousel.min.css">
<link rel="stylesheet" href="${root}/static/assets/css/slicknav.css">
<link rel="stylesheet" href="${root}/static/assets/css/flaticon.css">
<link rel="stylesheet" href="${root}/static/assets/css/animate.min.css">
<link rel="stylesheet" href="${root}/static/assets/css/magnific-popup.css">
<link rel="stylesheet" href="${root}/static/assets/css/fontawesome-all.min.css">
<link rel="stylesheet" href="${root}/static/assets/css/themify-icons.css">
<link rel="stylesheet" href="${root}/static/assets/css/slick.css">
<link rel="stylesheet" href="${root}/static/assets/css/nice-select.css">
<link rel="stylesheet" href="${root}/static/assets/css/style.css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>



<link href=${root}/static/vue/js/app.5aee07c8.js rel=preload as=script>
<link href=${root}/static/vue/js/chunk-vendors.39500b71.js rel=preload as=script>


</head>
<body>

<!-- Preloader Start -->
<div id="preloader-active">
    <div class="preloader d-flex align-items-center justify-content-center">
        <div class="preloader-inner position-relative">
            <div class="preloader-circle"></div>
            <div class="preloader-img pere-text">
                <img src="${root}/static/assets/img/logo/loder.jpg" alt="">
            </div>
        </div>
    </div>
</div>


<!-- Preloader Start -->
<header>
  <!-- Header Start -->
  <div class="header-area header-transparent">
       <div class="main-header">
          <div class="header-bottom  header-sticky">
               <div class="container-fluid">
                   <div class="row align-items-center">
                       <!-- Logo -->
                       <div class="col-xl-2 col-lg-2 col-md-1">
                           <div class="logo">
                             <img src="${root}/static/assets/img/logo/logo.png" alt="">
                           </div>
                       </div>
                       <div class="col-xl-10 col-lg-10 col-md-8">
                           <!-- Main-menu -->
                           <div class="main-menu f-right d-none d-lg-block">
                               <nav>
                                   <ul id="navigation" style="float:right;">                                                                                                                                     
                                       <li><a href="${root}/">Home</a></li>

                                       <li><a href="${root}/notice/go">공지사항</a></li>
                                       <li><a href="${root}/qna/go">QnA</a></li>
                                       
                                       
                                       <c:if test="${userinfo == null}">
											<li class="login"><a href="${root}/login/go">
	                                           	<i class="ti-user"></i> Sign in or Register</a>
	                                       	</li>
										</c:if>
										
										<c:if test="${userinfo != null}">
											<c:choose>
											<c:when test="${userinfo.userid eq 'admin'}">
												<li><a href="${root}/sale/go">매물등록</a></li>
												<li><a href="${root}/member/go">회원검색</a></li>
												<li><a href="${root}/favorite/go">관심목록</a></li>
												<li class="add-list"><a href="${root}/member/confirm"><i class="ti-plus"></i>${userinfo.username}</a></li>
				                                <li><a href="${root}/member/logout">로그아웃</a></li>
											</c:when>
											<c:otherwise>
												<li><a href="${root}/sale/go">매물등록</a></li>
												<li><a href="${root}/favorite/go">관심목록</a></li>
												<li class="add-list"><a href="${root}/member/confirm"><i class="ti-plus"></i>${userinfo.username}</a></li>
				                                <li><a href="${root}/member/logout">로그아웃</a></li>
											</c:otherwise>
											</c:choose>
										</c:if>
                                   </ul>
                               </nav>
                           </div>
                       </div>
                       
                       <!-- Mobile Menu -->
                        <div class="col-12">
                            <div class="mobile_menu d-block d-lg-none"></div>
                        </div>
                    </div>
                </div>
           </div>
        </div>
   </div>
</header>
<!-- Header End -->

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











	<noscript>
		<strong>We're sorry but vue-board doesn't work properly
			without JavaScript enabled. Please enable it to continue.</strong>
	</noscript>
	<div id=app class=container></div>
	<script src=${root}/static/vue/js/chunk-vendors.39500b71.js></script>
	<script src=${root}/static/vue/js/app.5aee07c8.js></script>


<%@ include file="/WEB-INF/views/template/footer.jsp" %>