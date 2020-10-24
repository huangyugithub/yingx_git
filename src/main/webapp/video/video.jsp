<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script>
    $(function () {
        $("#videoTable").jqGrid({
            url: "${path}/video/queryVideoByPage", //发送请求，传递参数 page rows
            editurl: "${path}/video/edit?adminId=" +${sessionScope.admin.id},        //增删改路径 传参oper = add | edit | del  针对一行数据
            //cellurl:"${path}/emp/edit",		  //针对单元格的修改
            //cellEdit:true,                      //单元格编辑功能
            mtype: "POST",
            datatype: "json",                   //返回数据类型  拿到的返回值map集合？page页码   rows当前页的数据  total总页数    records总条数
            rowNum: 2,                           //每页展示多少条数据
            rowList: [2, 3, 5, 10, 20],              //动态设置每页展示多少条
            pager: "#videoPage",                  //表下分页栏的位置
            styleUI: "Bootstrap",                //集成bootstrap
            height: "auto",
            autowidth: true,                     //充满宽度 自适应宽度
            viewrecords: true,                   //展示总条数
            colNames: ['id', '名称', '视频', '上传时间', '描述',/*'所属类别',*/'类别id', '用户id'],
            colModel: [
                {name: 'id', width: 55, align: "center"},
                {name: 'title', width: 90, align: "center", editable: true},
                {
                    name: 'path', width: 180, align: "center", editable: true, edittype: "file",
                    formatter: function (cellvalue, options, rowObject) {
                        //三个参数  列的值 ，操作 ，行对象
                        console.log(cellvalue);
                        return "<video controls poster='" + rowObject.cover + "' width='400px' height='120px' src='" + cellvalue + "'/>"
                    }
                },
                {name: 'publishDate', width: 80, align: "center"},
                {name: 'biref', width: 100, align: "center", editable: true},

                /*{name : 'categoryName',align:"center" ,editable:true},*/
                {name: 'categoryId', width: 80, align: "center"},

                {name: 'userId', align: "center"},
            ]
        })
        $("#videoTable").jqGrid('navGrid', '#videoPage', {edit: false, add: true, del: true},
            {/*操作修改之后的额外操作*/},
            {/*操作添加之后的额外操作*/
                //关闭文本框
                closeAfterAdd: true,
                //手动文件上传
                afterSubmit: function (response) {
                    //添加用户后 返回一个map集合， 训的是 "id"=id   拿到当前新添加用户的id
                    var id = response.responseJSON.id;
                    console.log(id);
                    //异步文件上传
                    $.ajaxFileUpload({
                        url: "${path}/video/uploadVideo",
                        type: "POST",
                        dataType: "text",
                        fileElementId: "path", //上传的文件域id
                        data: {id: id},
                        success: function () {
                            //刷新表单
                            $("#videoTable").trigger("reloadGrid");
                            //return null;
                        },
                        error: function () {
                            alert("上传出错？");
                        }
                    });
                    //必须要有返回值
                    return "123";
                }
            },
            {/*删除之后的操作*/}
        )
    })
</script>

<%--初始化面板--%>
<div class="panel panel-warning">
    <%--面板头部--%>
    <div class="panel panel-heading">
        <h2>视频信息</h2>
    </div>
    <%--标签页--%>
    <div class="nav nav-tabs">
        <li class="active"><a>视频信息</a></li>
    </div>
    <%--面板体--%>
    <table id="videoTable"></table>
    <div id="videoPage"></div>

</div>