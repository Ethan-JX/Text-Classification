/**
 * Created by Ethan on 2017/4/4.
 */
var trainData ={
    loadData:function () {
        $.ajax({
            type: "POST",
            url: basePath+"/trainData/loadTrainDataList",
            success: function(msg){
                var trainDataList = msg.trainDataList;
                var $trainDataListTbody = $("#trainDataListTbody");
                $trainDataListTbody.empty();
                if (trainDataList==null||trainDataList.length==0){
                    $trainDataListTbody.append('<tr><td colspan="5" align="center">数据为空</td></tr>');
                }else {
                    for (var i=0;i<trainDataList.length;i++){
                        var stringBuffer = new StringBuffer();
                        stringBuffer.append('<tr>')
                            .append('<td>'+trainDataList[i].trainDataName+'</td>')
                            .append('<td>'+trainDataList[i].trainDataDesc+'</td>')
                            .append('<td>'+trainDataList[i].sourceFilePath+'</td>');
                        if(trainDataList[i].trainDataStatus==0){
                            stringBuffer.append('<td><span class="red">未预处理</span></td>');
                            stringBuffer.append('<td><a href="#" onclick="trainData.initTrainData(\''+trainDataList[i].trainDataID+'\')" class="btn-tools btn-success" title="预处理"><i class="glyphicon glyphicon-pencil"></i></a>');
                        }else {
                            stringBuffer.append('<td><span class="green">已预处理</span></td>');
                            stringBuffer.append('<td><a href="#" onclick="trainData.getTextClass(\''+trainDataList[i].trainDataID+'\')" class="btn-tools btn-success" title="查看类别"><i class="glyphicon glyphicon-eye-open"></i></a>');
                        }
                        stringBuffer.append('<a href="#" onclick="trainData.openEditOrNewtrainDataPage(\'编辑\',\''+trainDataList[i].trainDataID+'\')" class="btn-tools btn-info" title="编辑"><i class="glyphicon glyphicon-edit"></i></a>');
                        stringBuffer.append('<a href="#" onclick="trainData.deleteTrainData(\''+trainDataList[i].trainDataID+'\')" class="btn-tools btn-danger" title="删除"><i class="glyphicon glyphicon-trash"></i></a></td>');
                        stringBuffer.append('</tr>');
                        $trainDataListTbody.append(stringBuffer.toString());
                    }
                }

            }
        });
    },
    getTextClass:function (id) {
        layer.open({
            type: 2,
            title: "文本类别",
            shadeClose: false,
            shade: 0.8,
            // area: ['400px', '200px'],
            content: basePath+"/trainData/toShowTextClassPage?trainDataId="+id,//iframe的url
        });
    },
    deleteTrainData:function (id) {
        layer.confirm('您确定删除数据？', {
            btn: ['是', '否']
            ,yes: function(index, layero){
                layer.close(index);
                var loadingIndex = layer.load(1, {
                    shade: [0.5,'#000'] //0.1透明度的白色背景
                });
                $.ajax({
                    type: "POST",
                    url: basePath+"/trainData/deleteTrainData",
                    data:{"trainDataId":id},
                    success: function(msg){
                        if (msg.code != 1) {
                            layer.alert(msg.msg);
                        }else {
                            layer.alert("删除成功");
                            trainData.loadData();
                        }
                        layer.close(loadingIndex);
                    }
                })
            }
        });
    },
    openEditOrNewtrainDataPage:function (title,id) {
        var ajaxPath = basePath+'/trainData/toAddOrEditTrainDataPage';
        if(id != undefined){
            ajaxPath = ajaxPath+"?trainDataId="+id;
        }
        layer.open({
            type: 2,
            title: title,
            shadeClose: false,
            shade: 0.8,
            area: ['400px', '300px'],
            content: ajaxPath,//iframe的url
            btn: ['保存', '取消'],
            yes: function (index, layero) {
                var iframeBody = layer.getChildFrame('body', index);
                trainData.editOrNewtrainData(index,iframeBody);
            }
        });
    },
    editOrNewtrainData:function (index,iframeBody) {
        var trainDataId = iframeBody.find("#trainDataId").val();
        var trainDataName = iframeBody.find("#trainDataName").val();
        var sourceFilePath = iframeBody.find("#sourceFilePath").val();
        var trainDataDesc = iframeBody.find("#trainDataDesc").val();
        if(sourceFilePath==""){
            layer.alert("数据路径不能为空");
            return;
        }
        layer.close(index);
        var loadingIndex = layer.load(1, {
            shade: [0.5,'#000'] //0.1透明度的白色背景
        });
        $.ajax({
            type: "POST",
            url: basePath+"/trainData/editOrNewtrainData",
            data:{
                trainDataName:trainDataName,
                trainDataID:trainDataId,
                sourceFilePath:sourceFilePath,
                trainDataDesc:trainDataDesc,
            },
            success: function(msg){
                if (msg.code != 1) {
                    layer.alert(msg.msg);
                }else {
                    layer.alert("保存成功");
                    trainData.loadData();
                }
                layer.close(loadingIndex);
            }
        })
    },
    initTrainData:function (id) {
        layer.confirm('您确定预处理数据？', {
            btn: ['是', '否']
            ,yes: function(index, layero){
                layer.close(index);
                var loadingIndex = layer.load(1, {
                    shade: [0.5,'#000'] //0.1透明度的白色背景
                });
                $.ajax({
                    type: "POST",
                    url: basePath+"/trainData/initTrainData",
                    data:{"trainDataId":id},
                    success: function(msg){
                        if (msg.code != 1) {
                            layer.alert(msg.msg);
                        }else {
                            layer.alert("预处理成功");
                            trainData.loadData();
                        }
                        layer.close(loadingIndex);
                    }
                })
            }
        });
    },
}
$(function () {
    trainData.loadData();
})