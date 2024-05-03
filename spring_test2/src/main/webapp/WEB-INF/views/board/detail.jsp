<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<jsp:include page="../layout/header.jsp" />

<div class="container-md">
	<h1>Board Detail Page</h1>
	<%-- <c:set value="${bdto.bvo }" var="bvo" /> --%>	
		<div class="mb-3">
		  <label for="n" class="form-label">bno</label>
		  <input type="text" class="form-control" name="bno" id="n" value="${bvo.bno }" placeholder="bno..." readonly="readonly">
		</div>
		<div class="mb-3">
		  <label for="t" class="form-label">title</label>
		  <input type="text" class="form-control" name="title" id="t" value="${bvo.title }" placeholder="Title..." readonly="readonly">
		</div>		
		<div class="mb-3">
		  <label for="w" class="form-label">writer</label>
		  <input type="text" class="form-control" name="writer" id="w" value="${bvo.writer }" placeholder="writer..." readonly="readonly">
		</div>	
		<div class="mb-3">
		  <label for="r" class="form-label">regDate</label>
		  <input type="text" class="form-control" name="regDate" id="r" value="${bvo.regDate }" placeholder="regDate..." readonly="readonly"> 
		</div>		
		<div class="mb-3">
		  <label for="c" class="form-label">content</label>
		  <textarea class="form-control" id="c" name="content" aria-label="With textarea" readonly="readonly">${bvo.content }</textarea>
		</div>	
		
		<!-- file upload 표시라인 -->
		<%-- <c:set value="${bdto.flist }" var="flist" />	
		<div class="mb-3">
			<ul class="list-group list-group-flush"> --%>
			<!-- 파일 갯수만큼 li 반복하여 파일 표시 / 타입이 1인 경우만 -->
			<!-- li > div > img -->
			<!--    > div > 파일이름, 작성일, span size -->
			
			<!-- c:when : 파일타입이 1인 경우 -->
			<!-- c:otherwise : 파일타입이 0인경우 아이콘 모양 하나 가져와서 넣기 -->
			<%-- <c:forEach items="${flist }" var="fvo">			
  				<li class="list-group-item"> 
  					<c:choose>
  						<c:when test="${fvo.file_type > 0 }">
		  					<div>
		  						<img alt="" src="/upload/${fvo.save_dir }/${fvo.uuid}_${fvo.file_name}">
		  					</div>					
  						</c:when>
  						<c:otherwise>
  							<div></div>
  						</c:otherwise>
  					</c:choose>
  					<div>
  						<div>${fvo.file_name }</div>
  						${fvo.reg_Date }
	  					<span class="badge rounded-pill text-bg-warning"> ${fvo.file_size }Byte</span>
  					</div>
  				</li>
			</c:forEach>
  			</ul>
		</div> --%>
		
		<br>
		
		<!-- Comment line -->
		<!-- 댓글등록라인 -->
		 <div class="input-group mb-3">
		  <span class="input-group-text" id="cmtWriter">Tester</span>
		  <input type="text" id="cmtText" class="form-control" placeholder="Add Comment..." aria-label="Username" aria-describedby="basic-addon1">
		  <button type="button" class="btn btn-secondary" id="cmtAddBtn">등록</button>
		</div>
		
		
		<!-- 댓글출력라인 -->
		<ul class="list-group list-group-flush" id="cmtListArea">
  			<li class="list-group-item">
  				<div class="input-group mb-3">
  					<div class="fw-bold">Writer</div>
  					content
  				</div>
  				<span class="badge rounded-pill text-bg-warning">regDate</span>
  			</li>
  		</ul>
  		
  		<!-- 댓글 더보기 커븐 -->
		<div>
			<button type="button" id="moreBtn" data-page="1" class="btn btn-dark" style="visibility:hidden">더보기+</button>
		</div>
		
		<!-- 모달찰 라인 -->
		<div class="modal" id="myModal" tabindex="-1">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">Writer</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
		      	<!-- 모달을 띄울때만 id 값을 가지고 오기 때문에 id 를 사용할 수 있음 -->
		        <input type="text" class="form-control" id="cmtTextMod"> <!-- content -->
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
		        <button type="button" class="btn btn-primary" id="cmtModBtn">Modify</button>
		      </div>
		    </div>
		  </div>
		</div>
			
		<br><hr>
		<a href="/board/modify?bno=${bvo.bno }"><button type="button" class="btn btn-success">수정</button></a>
		<a href="/board/remove?bno=${bvo.bno }"><button type="button" class="btn btn-danger">삭제</button></a>		
		<a href="/board/list"><button type="button" class="btn btn-warning">list</button></a>
</div>

<script type="text/javascript">
	const bnoVal = `<c:out value="${bvo.bno}" />`
/* 	const id = `<c:out value="${ses.id}" />`; */
	console.log(bnoVal);
/* 	console.log(id); */
</script>

<script type="text/javascript" src="/re/js/boardDetailComment.js" ></script>

<!-- 작성한 댓글 맨 바닥에 뿌리는 라인  -->
<script type="text/javascript">
	spreadCommentList(bnoVal);
</script>

<jsp:include page="../layout/footer.jsp" />