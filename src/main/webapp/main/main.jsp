<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>持明法州后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>

</head>
<body>
<!--顶部导航-->
<nav class="navbar navbar-default navbar-inverse">
    <div class="container-fluid">
        <!-- 导航条标题 -->
        <div class="navbar-header">
            <span class="navbar-brand">应学App后台管理系统</span>
        </div>
        <!-- 导航条内容 -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎：<span class="text text-primary">${sessionScope.admin.username}</span></a></li>
                <li><a href="#">退出：<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span></a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<!--栅格系统-->
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2">
            <div class="panel-group" id="accordion" role="tablist" align="center" aria-multiselectable="true">
                <div class="panel panel-danger">
                    <div class="panel-heading " role="tab" id="headingOne">
                        <h4 class="panel-title text-center">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                               aria-expanded="true" aria-controls="collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse " role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a class="btn btn-danger"
                                       href="javascript:$('#mainId').load('${path}/user/user.jsp')">用户展示</a></li>
                                <li><a class="btn btn-danger"
                                       href="javascript:$('#mainId').load('${path}/echarts-goeasy/echarts1.jsp')">用户统计</a>
                                </li>
                                <li><a class="btn btn-danger"
                                       href="javascript:$('#mainId').load('${path}/echarts-goeasy/echarts-map1.jsp')">用户分布</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-info">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title text-center">
                            <a class="collapsed " role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                分类管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <div class="list-group">
                                <ul class="nav nav-pills nav-stacked">
                                    <li><a class="btn btn-info"
                                           href="javascript:$('#mainId').load('${path}/category/category.jsp')">分类展示</a>
                                    </li>

                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="panel panel-warning">
                    <div class="panel-heading" role="tab" id="headingThree">
                        <h4 class="panel-title text-center">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                视频管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingThree">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a class="btn btn-warning"
                                       href="javascript:$('#mainId').load('${path}/video/video.jsp')">视频展示</a></li>
                                <li><a class="btn btn-warning"
                                       href="javascript:$('#mainId').load('${path}/video/findVideo.jsp')">视频搜索</a></li>

                            </ul>
                        </div>
                    </div>
                </div>

                <div class="panel panel-success">
                    <div class="panel-heading" role="tab" id="headingFore">
                        <h4 class="panel-title text-center">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFore" aria-expanded="false" aria-controls="collapseFore">
                                日志管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFore" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingFore">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a class="btn btn-success"
                                       href="javascript:$('#mainId').load('${path}/log/log.jsp')">查看日志</a></li>

                            </ul>
                        </div>
                    </div>
                </div>

                <div class="panel panel-primary">
                    <div class="panel-heading" role="tab" id="headingFive">
                        <h4 class="panel-title text-center">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                                反馈管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingFive">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a class="btn btn-primary">用户提示</a></li>
                                <li><a class="btn btn-primary">用户统计</a></li>
                                <li><a class="btn btn-primary">用户分析</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

            </div>


        </div>
        <div class="col-md-10" id="mainId">
            <!--巨幕开始-->
            <div class="jumbotron">
                <h1 class="text-center">欢迎来到应学 APP后台管理系统</h1>
            </div>
            <!--右边轮播图部分-->
            <div>
                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                    <!-- Indicators -->
                    <ol class="carousel-indicators">
                        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                    </ol>

                    <!-- Wrapper for slides -->
                    <div class="carousel-inner" role="listbox" align="center">
                        <div class="item active " align="center">
                            <%--
                                                        <img src="https://yingx-huangy.oss-cn-beijing.aliyuncs.com/photo/1.jpg" alt="...">
                            --%>
                            <%--controls 可操作可播放视频  poster 封面 --%>
                            <video controls poster="https://yingx-huangy.oss-cn-beijing.aliyuncs.com/photo/1.jpg"
                                   width="400px"
                                   src="https://yingx-huangy.oss-cn-beijing.aliyuncs.com/video/1.mp4"></video>
                            <div class="carousel-caption">
                                ...
                            </div>
                        </div>
                        <div class="item">
                            <img src="${path}/bootstrap/img/pic2.jpg" alt="...">
                            <div class="carousel-caption">
                                ...
                            </div>
                        </div>
                        <div class="item">
                            <img src="${path}/bootstrap/img/pic3.jpg" alt="...">
                            <div class="carousel-caption">
                                ...
                            </div>
                        </div>
                        ...
                    </div>

                    <!-- Controls -->
                    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
        </div>

    </div>
    <!--页脚-->
    <div class="panel panel-footer  text-center">© 百知教育</div>
</div>
<!--左边手风琴部分-->
<!--巨幕开始-->
<!--右边轮播图部分-->
<!--页脚-->
<!--栅格系统-->

</body>
</html>
