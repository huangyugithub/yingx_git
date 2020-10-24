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
        $("#userTable").jqGrid({
            url: "${path}/user/queryUserByPage", //发送请求，传递参数 page rows
            editurl: "${path}/user/edit",        //增删改路径 传参oper = add | edit | del  针对一行数据
            //cellurl:"${path}/emp/edit",		  //针对单元格的修改
            //cellEdit:true,                      //单元格编辑功能
            mtype: "POST",
            datatype: "json",                   //返回数据类型  拿到的返回值map集合？page页码   rows当前页的数据  total总页数    records总条数
            rowNum: 2,                           //每页展示多少条数据
            rowList: [2, 3, 5, 10, 20],              //动态设置每页展示多少条
            pager: "#userPage",                  //表下分页栏的位置
            styleUI: "Bootstrap",                //集成bootstrap
            height: "auto",
            autowidth: true,                     //充满宽度 自适应宽度
            viewrecords: true,                   //展示总条数
            colNames: ['id', '用户名', '手机号', '微信', '头像', '签名', '状态', '注册时间'],
            colModel: [
                {name: 'id', width: 55, align: "center"},
                {name: 'username', width: 90, align: "center", editable: true},
                {name: 'phone', width: 100, align: "center", editable: true},
                {name: 'wechat', align: "center", editable: true},
                {
                    name: 'headImg', align: "center", editable: true, edittype: "file",
                    formatter: function (cellvalue, options, rowObject) {
                        //三个参数  列的值 ，操作 ，行对象
                        console.log(cellvalue);
                        return "<img width='120px' height='80px' src='" + cellvalue + "'/>"
                    }
                },

                {name: 'sign', align: "center", editable: true},
                {
                    name: 'status', width: 150, align: "center",
                    formatter: function (cellvalue, options, rowObject) {
                        //三个参数  列的值 ，操作 ，行对象
                        if (cellvalue == "正常") {
                            console.log("列的值： " + cellvalue);
                            console.log("行对象： " + rowObject);
                            console.log("行id： " + rowObject.id);
                            return "<a class='btn btn-success' onclick=\"updateStatus('" + rowObject.id + "','" + cellvalue + "')\">正常</a>";

                        } else {
                            return "<a class='btn btn-danger' onclick=\"updateStatus('" + rowObject.id + "','" + cellvalue + "')\">冻结</a>";
                        }
                    }
                },

                {name: 'createDate', align: "center"},
            ]
        });

        $("#userTable").jqGrid('navGrid', '#userPage', {edit: false, add: true, del: false},
            {}, //操作修改之后的额外操作
            {
                //操作添加之后的额外操作
                //关闭文本框
                closeAfterAdd: true,
                //手动文件上传
                afterSubmit: function (response) {
                    //添加用户后 返回一个map集合， 训的是 "id"=id   拿到当前新添加用户的id
                    var id = response.responseJSON.id;
                    console.log(id);
                    //异步文件上传
                    $.ajaxFileUpload({
                        url: "${path}/user/uploadFile",
                        type: "POST",
                        dataType: "text",
                        fileElementId: "headImg", //上传的文件域id
                        data: {id: id},
                        success: function () {
                            //刷新表单
                            $("#userTable").trigger("reloadGrid");
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
            {}
        );
    })

    //点击按钮 修改状态
    function updateStatus(id, status) {
        console.log("zt   " + status);
        console.log(id + "----" + status);
        $.ajax({
            url: "${path}/user/updateStatus",
            type: "POST",
            data: {id: id, status: status},
            //dataType: "json",
            success: function () {
                //刷新表单  刷新当前页的表单数据
                $("#userTable").trigger("reloadGrid");
            }
        })
    }

    //给手机发送验证码
    function sendCode() {
        var phone = $("#phone").val();
        //alert(phone);
        $.ajax({
            url: "${path}/user/sendCode",
            type: "POST",
            data: {phone, phone},
            success: function () {
                //刷新表单

            }
        })
    }

    function booleanCode() {
        var code = $("#code").val();
        $.ajax({
            url: "${path}/user/booleanCode",
            type: "POST",
            data: {code, code},
            datatype: "json",
            success: function (data) {
                alert(data);
            }
        })
    }

    //批量导出用户数据
    function easyPoiOut() {
        $.ajax({
            url: "${path}/user/easyPoiOut",
            type: "POST",
            dataType: "text",
            success: function (data) {
                alert(data);
            }
        })
    }

    //批量导入用户数据
    function easyPoiIn() {
        $.ajax({
            url: "${path}/user/easyPoiIn",
            type: "POST",
            dataType: "text",
            success: function (data) {
                alert(data);
            }

        })
    }
</script>

<%--初始化面板--%>
<div class="panel panel-info">
    <%--面板头部--%>
    <div class="panel panel-heading">
        <h2>用户信息</h2>
    </div>
    <%--标签页--%>
    <div class="nav nav-tabs">
        <li class="active"><a>用户信息</a></li>
    </div>
    <%--面板体--%>
    <div class="panel panel-body">
        <%--按钮--%>
        <a onclick="easyPoiOut()" class="btn btn-success">批量导出用户数据</a>
        <a onclick="easyPoiIn()" class="btn btn-warning">批量导入用户数据</a>
        <%--输入框--%>
        <div class="input-group">
            <input id="phone" type="text" class="form-control" placeholder="请填写手机号" aria-describedby="basic-addon2">
            <span class="input-group-addon" onclick="sendCode()" id="basic-addon2">获取验证码</span>
        </div>
        <%--输入框--%>
        <div class="input-group">
            <input id="code" type="text" class="form-control" placeholder="验证码" aria-describedby="basic-addon3">
            <span class="input-group-addon" onclick="booleanCode()" id="basic-addon3">发送验证码</span>
        </div>

    </div>


    <table id="userTable"></table>
    <div id="userPage"></div>

</div>