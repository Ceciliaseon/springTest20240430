<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../layout/header.jsp" />
<div class="container-md">
	<h1>Board Register Page</h1>
	<form action="/board/insert" method="post" enctype="multipart/form-data">
		<div class="mb-3">
		  <label for="t" class="form-label">title</label>
		  <input type="text" class="form-control" name="title" id="t" placeholder="Title...">
		</div>		
		<div class="mb-3">
		  <label for="w" class="form-label">writer</label>s
		  <input type="text" class="form-control" name="writer" value="${ses.id }" id="w" placeholder="writer...">
		</div>		
		<div class="mb-3">
		  <label for="c" class="form-label">content</label>
		  <textarea class="form-control" id="c" name="content" aria-label="With textarea"></textarea>
		</div>
			
		<!-- file 입력라인 추가 -->
		<div class="mb-3">
		  <label for="file" class="form-label">files...</label>
		  <input type="file" class="form-control" name="files" id="file" multiple="multiple" style="display: none"> <br>
		  <button type="button" class="btn btn-dark" id="trigger">FileUpload</button>
		</div>	
		
		<!-- 파일 목록 표시라인 -->
		<div class="mb-3" id="fileZone">
		
		</div>
		<button type="submit" class="btn btn-success" id="regBtn">등록</button>
	</form>
</div>

<script type="text/javascript" src="/resources/js/boardRegister.js"></script>

<jsp:include page="../layout/footer.jsp" />
