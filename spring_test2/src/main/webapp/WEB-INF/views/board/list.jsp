<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../layout/header.jsp" />

<div class="container-md">
	<h1>Board List Page</h1>
	<!-- 검색라인  -->
	<form action="/board/list" method="get">
		<div class="input-group">
		  <input type="text" name="keyword" class="form-control" 
		  value="${ph.pgvo.keyword }" placeholder="Search..." aria-label="Username">
		  <input type="hidden" name="pageNo" value="1">
		  <input type="hidden" name="qty" value="10">
		  
		  <select class="form-select" name="type" id="inputGroupSelect04" aria-label="Example select with button addon">
		    <option ${ph.pgvo.type == null ? 'selected' : ''}> Choose...</option>
		    <option value="t" ${ph.pgvo.type eq 't' ? 'selected' : ''}>Title</option>
		    <option value="w" ${ph.pgvo.type eq 'w' ? 'selected' : ''}>Writer</option>
		    <option value="c" ${ph.pgvo.type eq 'c' ? 'selected' : ''}>Content</option>
		    <option value="tc" ${ph.pgvo.type eq 'tc' ? 'selected' : ''}>Title & Content</option>
		    <option value="wc" ${ph.pgvo.type eq 'wc' ? 'selected' : ''}>Writer & Content</option>
		    <option value="tw" ${ph.pgvo.type eq 'tw' ? 'selected' : ''}>Title & Writer</option>
		    <option value="twc" ${ph.pgvo.type eq 'twc' ? 'selected' : ''}>All</option>
		  </select>
		  
		  <button class="btn btn-primary" type="submit">
		  	Search		  
			  <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
			    ${ph.totalCount }
			    <span class="visually-hidden">unread messages</span>
			  </span>
		  </button>
		</div>
		<br>
	</form>
	<table class="table table-dark table-striped-columns">
		<thead>
			<tr>
				<th>#</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
				<th>첨부파일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="bvo" >
				<tr>
					<td>${bvo.bno }</td>
					<td><a href="/board/detail?bno=${bvo.bno }"> ${bvo.title } [<c:out value="${bvo.cmtQty }" />]</a></td>
					<td>${bvo.writer }</td>
					<td>${bvo.regDate }</td>
					<td>${bvo.readCount }</td>
					<td>${bvo.hasFile }</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!--  <li class="page-item disabled"> -->
	<!-- 페이지네이션 라인 -->
	<nav aria-label="Page navigation example">
	  <ul class="pagination justify-content-center">
	    <li class="page-item ${ph.prev eq false ? 'disabled' : '' }">
	      <a class="page-link " href="/board/list?pageNo=${ph.startPage-1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	      </a>
	    </li>

<%-- 	  	<c:if test="${ph.prev }">
	    <li class="page-item">
	      <a class="page-link" href="/board/list?pageNo=${ph.startPage-1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	      </a>
	    </li>
	    </c:if> --%>
	    
	    <c:forEach begin="${ph.startPage }" end="${ph.endPage }" var="i">
	    	<li class="page-item ${ph.pgvo.pageNo eq i ? 'active' : ''}">
	    		<a class="page-link" href="/board/list?pageNo=${i }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}">${i }</a>
	    	</li>
	    </c:forEach>
	    
	    <li class="page-item ${ph.next eq false ? 'disabled' : '' }">
	      <a class="page-link" href="/board/list?pageNo=${ph.endPage+1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Next">
	        <span aria-hidden="true">&raquo;</span>
	      </a>
	    </li>
	  </ul>
	</nav>
</div>


<jsp:include page="../layout/footer.jsp" />
