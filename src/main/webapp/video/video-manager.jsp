<%-- 
    Document   : video-manager
    Created on : 2014-10-17, 17:12:47
    Author     : 蔡迪旻
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:h="http://java.sun.com/jsf/html" xmlns:f="http://java.sun.com/jsf/core">
    <head>
        <link rel="stylesheet" href="/resources/css/bootstrap-datetimepicker.min.css">
        <link rel="stylesheet" href="/resources/css/video-info.css">
    </head>
    <body>
        <div class="table-counter text-primary">
            每页显示<input id="size" class="input-table-counter" type="text">行
            <a id="filter" class="btn btn-primary btn-sm pull-right" href="/video/query"><i class="icon-filter"></i> 过滤</a>
        </div>
        <table class="table table-hover table-striped table-responsive" frame="below">
            <thead>
                <tr>
                    <th class="table-col-file">编号</th>
                    <th class="table-col-title">标题</th>
                    <th class="table-col-index">序号</th>
                    <th class="table-col-duration">时长</th>
                    <th class="table-col-date">拍摄日期</th>
                    <th class="table-col-time">发布/更新 时间</th>
                    <th class="table-col-column">栏目</th>
                    <th class="table-col-id">荐</th>
                    <th class="table-col-status">状态</th>
                    <th class="table-col-opera">操作</th>
                </tr>
            </thead>
            <tbody id="table-content">
            </tbody>
        </table>
        <div><ul id="pagination" class="pagination">
            </ul>
            <div class="table-counter text-primary pull-right">
                共 <span id="count"></span> 页，跳转到第<input id="currentPage" class="input-table-counter text-center" type="text"> 页
            </div></div>
    </div>
    <div id="amodal" class="modal" role="dialog" aria-labelledby="title" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <span id="title" class="modal-title"></span>
                </div>
                <div class="modal-body form-horizontal">
                    <div class="form-group">
                        <label for="itemId" class="col-sm-2 control-label">记录标识：</label>
                        <div class="col-sm-2">
                            <p id="itemId" class="text-muted form-control-static"></p>
                        </div>
                        <label for="file" class="col-sm-2 control-label">视频编号：</label>
                        <div class="col-sm-2">
                            <p id="file" class="text-muted form-control-static"></p>
                        </div>
                        <label for="dur" class="col-sm-2 control-label">播放时长：</label>
                        <div class="col-sm-2">
                            <p id="dur" class="text-muted form-control-static"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sort" class="col-sm-2 control-label">发布序号：</label>
                        <div class="col-sm-2">
                            <p id="sort"></p>
                        </div>
                        <label for="type" class="col-sm-2 control-label">所属栏目：</label>
                        <div class="col-sm-2">
                            <p id="type"></p>
                        </div>
                        <label for="grade" class="col-sm-2 control-label">分级信息：</label>
                        <div class="col-sm-1">
                            <p id="grade"></p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="recommend" class="col-sm-2 control-label">推荐度：</label>
                        <div class="col-sm-2">
                            <p id="recommend"></p>
                        </div>
                        <label for="shoot" class="col-sm-2 control-label">拍摄日期：</label>
                        <div class="col-sm-2">
                            <p id="shoot"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="status" class="col-sm-2 control-label">当前状态：</label>
                        <div class="col-sm-2">
                            <p id="status" class="text-muted form-control-static"></p>
                        </div>
                        <label for="pub" class="col-sm-2 control-label">发布时间：</label>
                        <div class="col-sm-4">
                            <p id="pub" class="text-muted form-control-static"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="tag" class="col-sm-2 control-label">标签：</label>
                        <div class="col-sm-9">
                            <p id="tag" class="text-muted form-control-static"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="desc" class="col-sm-2 control-label">视频描述：</label>
                        <div class="col-sm-9">
                            <p id="desc"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="note" class="col-sm-2 control-label">备注：</label>
                        <div class="col-sm-9">
                            <p id="note"></p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="ext" class="col-sm-2 control-label">文件信息：</label>
                        <div class="col-sm-3">
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <td>清晰度</td>
                                        <td>文件大小</td>
                                    </tr>
                                </thead>
                                <tbody id="ext"></tbody>
                            </table>
                        </div>
                        <div id="help">
                            <label class="col-sm-2 control-label">填写提示：</label>
                            <div class="col-sm-4">
                                <p class="form-control-static text-warning">推荐度为0即为不推荐，推荐度高的视频排列会更靠前。</p>
                                <p class="form-control-static text-warning">同理，发布序号为0即为不排序。</p>
                                <p class="form-control-static text-info">您只能任意修改一部分信息。其它不能修改的必须通过特定的工作流程才会被更新。如：状态、更新时间等。</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="footer" class="modal-footer"></div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <script src="/resources/js/bootstrap-datetimepicker.min.js"></script>
    <script src="/resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript">var count = ${count}</script>
    <script src="/resources/js/video-manager.js"></script>
</body>
</html>
