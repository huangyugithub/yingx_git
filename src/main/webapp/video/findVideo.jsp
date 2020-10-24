<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script>

</script>

<%--初始化面板--%>
<div class="panel panel-warning">
    <%--面板头部--%>
    <div class="panel panel-heading">
        <h2>搜索视频</h2>
    </div>
    <%--标签页--%>
    <div class="nav nav-tabs">
        <li class="active"><a>视频信息</a></li>
    </div>
    <%--面板体--%>
    <table id="videoTable"></table>
    <div id="videoPage"></div>

</div>