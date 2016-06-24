<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>服务统计</title>
    <style type="text/css">
        body{margin:0; padding:0; font-size:12px; font-family:"宋体"; color:#000000; background-color:#f4f4f4;}
        a {
            text-decoration: none;
        }
        table {
            border: 1px;
            border-style: solid;
            border-color: gray;

            #font-family: "微软雅黑";
            text-align: center;

            margin: 0px;
            padding: 0px;
            border-collapse: collapse;
            padding: 0px;
        }
        tr:first-child {
            background-color: #AEC7E1;
        }
        tr {
            cursor: default;
        }
        td {
            #width: 100px;
            height:25px;
            border: 1px;
            border-style: solid;
            border-color: gray;
        }

    </style>
</head>
<body>
<br>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<c:if test="${type=='2'}">
        APPID：<select name = "appId" id = "appId">
					<c:forEach items="${appIdList}" var="data">
			             <option value="${data.appId}">${data.appId}</option>
			     	</c:forEach>
				</select>
    </c:if>
    <c:if test="${type=='1'}">
    	APPID列表：<input type="text" name = "appId" id = "appId" />
    </c:if>
	&nbsp;&nbsp;&nbsp;&nbsp;
	时间:<input id="time" class="Wdate" type="text" onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" onclick="search()" value="查看统计数据" />
<br/>
<br/>
<div id="main" style="height:500px;border:1px solid #ccc;padding:10px;"></div>

<!--Step:2 Import echarts.js-->
<!--Step:2 引入echarts.js-->
<script src="../js/echarts/build/dist/echarts.js"></script>
<script type="text/javascript" src="../js/jquery-1.2.6.pack.js"></script>
<script src="../js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

<script type="text/javascript">

    function search(){
        var time = $("#time").val();
        if(time == ''){
            alert("请选择时间!");
            return false;
        }

        // Step:3 conifg ECharts's path, link to echarts.js from current page.
        // Step:3 为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
        var myChart;
        var option;
        require.config({
            paths: {
                echarts: '../js/echarts/build/dist'
            }
        });

        // Step:4 require echarts and use it in the callback.
        // Step:4 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
        require(
                [
                    'echarts',
                    'echarts/chart/bar',
                    'echarts/chart/line',
                    'echarts/chart/map'
                ],
                function (ec) {
                    //--- 折柱 ---
                    myChart = ec.init(document.getElementById('main'));

                    option={
                        title : {
                            text: '消息量',
                            subtext: '消息量'
                        },
                        tooltip : {
                            trigger: 'axis'
                        },
                        legend: {
                            data:[]
                        },
                        toolbox: {
                            show : true,
                            feature : {
                                mark : {show: true},
                                dataView : {show: true, readOnly: false},
                                magicType : {show: true, type: ['line', 'bar']},
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        calculable : true,
                        xAxis : [
                            {
                                type : 'category',
                                boundaryGap : false,
                                data : []
                            }
                        ],
                        yAxis : [
                            {
                                type : 'value',
                                axisLabel : {
                                    formatter: '{value} '
                                }
                            }
                        ],
                        series : []
                    };

                    //采用ajax异步请求数据
                    $.ajax({
                        type:'post',
                        url:"getData.do?time="+time,
                        dataType:'json',
                        success:function(result){
                            if(result){
                                //将返回的category和series对象赋值给options对象内的category和series
                                option.xAxis[0].data = result.axis;
                                option.legend.data = result.legend;
                                var series_arr=result.series;
                                for(var i=0;i<series_arr.length;i++){
                                    option.series[i] = result.series[i];
                                }
                                myChart.hideLoading();
                                myChart.setOption(option);
                            }
                        },
                        error:function(errMsg){
                            console.error("加载数据失败")
                        }
                    });
                }
        );
    }

    $(document).ready(function() {
		
    });

</script>
</body>
</html>