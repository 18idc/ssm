<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/themes/jquery.insdep-extend.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/echarts/echarts.min.js"></script>
</head>
<body>

<table id="dg" title="用户管理" class="easyui-datagrid" style="width:100%;height:600px"
       url="${pageContext.request.contextPath}/user/list"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" singleSelect="true">
    <thead>
    <tr>
        <th field="username" width="50">用户名</th>
        <th field="password" width="50">密码</th>
        <th field="phone" width="50">电话</th>
        <th field="email" width="50">邮箱</th>
        <th field="sex" width="50">性别</th>
        <th field="birthday" width="50">生日</th>
    </tr>
    </thead>
</table>
<div id="toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加用户</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
       onclick="editUser()">编辑用户</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除用户</a>

    关键字：<input class="easyui-textbox" type="text" name="key" style="width:150px"/>
    <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchkey()">查询</a>

</div>

<div id="dlg" class="easyui-dialog" style="width:400px"
     closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post" novalidate style="margin:0;padding:20px 50px">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">用户信息</div>
        <div style="margin-bottom:10px">
            <input name="username" class="easyui-textbox" required="true"  label="用户名:" style="width:100%">
        </div>
        <div style="margin-bottom:10px">
            <input name="password" class="easyui-textbox" required="true"  label="密码:" style="width:100%">
        </div>
        <div style="margin-bottom:10px">
            <input name="phone" class="easyui-textbox" required="true" validType="phone"   label="电话:" style="width:100%">
        </div>
        <div style="margin-bottom:10px">
            <input name="email" class="easyui-textbox" required="true" validType="email"   label="邮箱:"
                   style="width:100%">
        </div>
        <div style="margin-bottom:10px">
            <select class="easyui-combobox" editable="false" required="true"  label="性别:" name="sex" style="width: 100%">
                <option value="男">男</option>
                <option value="女">女</option>
            </select>
        </div>
        <div style="margin-bottom:10px">
            <input name="birthday" editable="false" class="easyui-datetimebox" data-options="required:true,showSeconds:true" label="生日:"
                   style="width:100%"/>
        </div>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
</div>
<script type="text/javascript">
    var url;

    function newUser() {
        $('#dlg').dialog('open').dialog('center').dialog('setTitle', '添加用户');
        $('#fm').form('clear');
        url = '${pageContext.request.contextPath}/user/addUpdate';
    }

    function editUser() {
        var row = $('#dg').datagrid('getSelected');
        if (row) {
            $('#dlg').dialog('open').dialog('center').dialog('setTitle', '编辑用户');
            $('#fm').form('load', row);
            url = '${pageContext.request.contextPath}/user/addUpdate?id=' + row.id;
        }
    }

    function saveUser() {
        $('#fm').form('submit', {
            url: url,
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (result) {
                var result = eval('(' + result + ')');
                if (result.success) {
                    $.messager.alert('信息', result.message, "info", function () {
                        $('#dlg').dialog('close');        // close the dialog
                        $('#dg').datagrid('reload');    // reload the user data
                        $("#shuaxin").click();
                    });
                } else {
                    $.messager.alert('信息', result.message, "error", function () {
                    });
                }
            }
        });
    }
    
    function searchkey() {
        $('#dg').datagrid('load', {
            key: $('input[name="key"]').val(),
        });
    }

    function destroyUser() {
        var row = $('#dg').datagrid('getSelected');
        if (row) {
            $.messager.confirm('删除用户', '确定要删除该用户吗?', function (r) {
                if (r) {
                    $.get('${pageContext.request.contextPath}/user/del', {id: row.id}, function (result) {
                        if (result.success) {
                            $.messager.alert('信息', result.message, "info", function () {
                                $('#dg').datagrid('reload');    // reload the user data
                            });
                        } else {
                            $.messager.alert('信息', result.message, "error", function () {
                                $('#dg').datagrid('reload');    // reload the user data
                            });
                        }
                        $("#shuaxin").click();
                    }, 'json');
                }
            });
        }
    }
</script>
<hr>
<a class="easyui-linkbutton" iconCls="icon-search"  href="${pageContext.request.contextPath}/admin">一对一</a>
<a class="easyui-linkbutton" iconCls="icon-search"  href="#">一对多</a>
<a class="easyui-linkbutton" iconCls="icon-search"  href="#">多对多</a>
<hr>
<button id="shuaxin" class="easyui-linkbutton" iconCls="icon-reload">获取统计</button>
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    $("#shuaxin").click(function () {
        var myChart = echarts.init(document.getElementById('main'));
        $.get('${pageContext.request.contextPath}/user/sex').done(function (datas) {
            var options = {
                title: {
                    text: '性别',
                    subtext: '性别统计',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['男', '女']
                },
                series: [
                    {
                        name: '性别',
                        type: 'pie',
                        radius: '50%',

                        data: datas,
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            myChart.setOption(options,true,true);
        });
    });

    $(function () {
        $("#shuaxin").click();
    });

</script>


</body>
</html>