<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<style>
    .ui-th-column {
        text-align: center;
    }
</style>
<script>
    $(function () {
        $("#logTable").jqGrid({
            url: "${path}/log/queryLogByPage", //发送请求，传递参数 page rows
            mtype: "POST",
            datatype: "json",                   //返回数据类型  拿到的返回值map集合？page页码   rows当前页的数据  total总页数    records总条数
            rowNum: 2,                           //每页展示多少条数据
            rowList: [2, 5, 10, 15, 20],              //动态设置每页展示多少条
            pager: "#logPage",                  //表下分页栏的位置
            styleUI: "Bootstrap",                //集成bootstrap
            height: "auto",
            autowidth: true,                     //充满宽度 自适应宽度
            viewrecords: true,                   //展示总条数
            colNames: ['id', '用户名', '操作', '时间', '状态'],
            colModel: [
                {name: 'id', width: 55, align: "center"},
                {name: 'name', width: 90, align: "center"},
                {name: 'operation', width: 100, align: "center"},
                {name: 'date', align: "center"},
                {name: 'status', align: "center"},
            ]
        });

        $("#logTable").jqGrid('navGrid', '#logPage', {edit: false, add: false, del: false},
            {}, {}, {}
        );
    })

</script>

<%--初始化面板--%>
<div class="panel panel-info">
    <%--面板头部--%>
    <div class="panel panel-heading">
        <h2>日志信息</h2>
    </div>
    <%--标签页--%>
    <div class="nav nav-tabs">
        <li class="active"><a>日志信息</a></li>
    </div>

    <table id="logTable"></table>
    <div id="logPage"></div>

</div>