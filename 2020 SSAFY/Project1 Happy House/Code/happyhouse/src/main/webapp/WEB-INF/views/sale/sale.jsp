<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<script src="https://unpkg.com/@google/markerclustererplus@4.0.1/dist/markerclustererplus.min.js"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCMXtnv5FOhNVw0q66dGzHxXIqFS_UUY3A&callback=initMap"></script>
<script src="${root}/static/proj4js/proj4js-combined.js"></script>


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




<%@ include file="/WEB-INF/views/template/footer.jsp" %>