/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.87
 * Generated at: 2024-05-01 08:17:40 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.views.board;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class detail_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("jar:file:/C:/Users/mangs/Desktop/mangseon/_spring_workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/spring_test2/WEB-INF/lib/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1153352682000L));
    _jspx_dependants.put("/WEB-INF/lib/jstl-1.2.jar", Long.valueOf(1713837828499L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.LinkedHashSet<>(3);
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP들은 오직 GET, POST 또는 HEAD 메소드만을 허용합니다. Jasper는 OPTIONS 메소드 또한 허용합니다.");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("    \r\n");
      out.write("    \r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "../layout/header.jsp", out, false);
      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"container-md\">\r\n");
      out.write("	<h1>Board Detail Page</h1>\r\n");
      out.write("	");
      out.write("	\r\n");
      out.write("		<div class=\"mb-3\">\r\n");
      out.write("		  <label for=\"n\" class=\"form-label\">bno</label>\r\n");
      out.write("		  <input type=\"text\" class=\"form-control\" name=\"bno\" id=\"n\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bvo.bno }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" placeholder=\"bno...\" readonly=\"readonly\">\r\n");
      out.write("		</div>\r\n");
      out.write("		<div class=\"mb-3\">\r\n");
      out.write("		  <label for=\"t\" class=\"form-label\">title</label>\r\n");
      out.write("		  <input type=\"text\" class=\"form-control\" name=\"title\" id=\"t\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bvo.title }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" placeholder=\"Title...\" readonly=\"readonly\">\r\n");
      out.write("		</div>		\r\n");
      out.write("		<div class=\"mb-3\">\r\n");
      out.write("		  <label for=\"w\" class=\"form-label\">writer</label>\r\n");
      out.write("		  <input type=\"text\" class=\"form-control\" name=\"writer\" id=\"w\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bvo.writer }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" placeholder=\"writer...\" readonly=\"readonly\">\r\n");
      out.write("		</div>	\r\n");
      out.write("		<div class=\"mb-3\">\r\n");
      out.write("		  <label for=\"r\" class=\"form-label\">regDate</label>\r\n");
      out.write("		  <input type=\"text\" class=\"form-control\" name=\"regDate\" id=\"r\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bvo.regDate }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" placeholder=\"regDate...\" readonly=\"readonly\"> \r\n");
      out.write("		</div>		\r\n");
      out.write("		<div class=\"mb-3\">\r\n");
      out.write("		  <label for=\"c\" class=\"form-label\">content</label>\r\n");
      out.write("		  <textarea class=\"form-control\" id=\"c\" name=\"content\" aria-label=\"With textarea\" readonly=\"readonly\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bvo.content }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("</textarea>\r\n");
      out.write("		</div>	\r\n");
      out.write("		\r\n");
      out.write("		<!-- file upload 표시라인 -->\r\n");
      out.write("		");
      out.write("\r\n");
      out.write("			<!-- 파일 갯수만큼 li 반복하여 파일 표시 / 타입이 1인 경우만 -->\r\n");
      out.write("			<!-- li > div > img -->\r\n");
      out.write("			<!--    > div > 파일이름, 작성일, span size -->\r\n");
      out.write("			\r\n");
      out.write("			<!-- c:when : 파일타입이 1인 경우 -->\r\n");
      out.write("			<!-- c:otherwise : 파일타입이 0인경우 아이콘 모양 하나 가져와서 넣기 -->\r\n");
      out.write("			");
      out.write("\r\n");
      out.write("		\r\n");
      out.write("		<br>\r\n");
      out.write("		\r\n");
      out.write("		<!-- Comment line -->\r\n");
      out.write("		<!-- 댓글등록라인 -->\r\n");
      out.write("		");
      out.write("\r\n");
      out.write("		<br>\r\n");
      out.write("		<a href=\"/board/modify?bno=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bvo.bno }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\"><button type=\"button\" class=\"btn btn-success\">수정</button></a>\r\n");
      out.write("		<a href=\"/board/remove?bno=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bvo.bno }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\"><button type=\"button\" class=\"btn btn-danger\">삭제</button></a>		\r\n");
      out.write("		<a href=\"/board/list\"><button type=\"button\" class=\"btn btn-warning\">list</button></a>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<!-- <script type=\"text/javascript\">\n");
      out.write("	const bnoVal = `");
      if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
        return;
      out.write("`\r\n");
      out.write("	const id = `");
      if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
        return;
      out.write("`;\r\n");
      out.write("	console.log(bnoVal);\r\n");
      out.write("	console.log(id);\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"/resources/js/board.detail.comment.js\" ></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("	spreadCommentList(bnoVal);\n");
      out.write("</script> -->\r\n");
      out.write("\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "../layout/footer.jsp", out, false);
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fout_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:out
    org.apache.taglibs.standard.tag.rt.core.OutTag _jspx_th_c_005fout_005f0 = (org.apache.taglibs.standard.tag.rt.core.OutTag) _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.OutTag.class);
    boolean _jspx_th_c_005fout_005f0_reused = false;
    try {
      _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fout_005f0.setParent(null);
      // /WEB-INF/views/board/detail.jsp(97,17) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fout_005f0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bvo.bno}", java.lang.Object.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
      if (_jspx_th_c_005fout_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
      _jspx_th_c_005fout_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fout_005f0, _jsp_getInstanceManager(), _jspx_th_c_005fout_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fout_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:out
    org.apache.taglibs.standard.tag.rt.core.OutTag _jspx_th_c_005fout_005f1 = (org.apache.taglibs.standard.tag.rt.core.OutTag) _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.OutTag.class);
    boolean _jspx_th_c_005fout_005f1_reused = false;
    try {
      _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
      _jspx_th_c_005fout_005f1.setParent(null);
      // /WEB-INF/views/board/detail.jsp(98,13) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fout_005f1.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ses.id}", java.lang.Object.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
      if (_jspx_th_c_005fout_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
      _jspx_th_c_005fout_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fout_005f1, _jsp_getInstanceManager(), _jspx_th_c_005fout_005f1_reused);
    }
    return false;
  }
}
