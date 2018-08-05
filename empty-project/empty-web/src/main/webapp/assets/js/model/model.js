/**
 * Created by Ethan on 2017/4/7.
 */
var model ={
    loadData:function () {
        $.ajax({
            type: "POST",
            url: basePath+"/model/loadModelList",
            success: function(msg){
                var modelList = msg.modelList;
                var $modelListTbody = $("#modelListTbody");
                $modelListTbody.empty();
                if (modelList==null||modelList.length==0){
                    $modelListTbody.append('<tr><td colspan="9" align="center">数据为空</td></tr>');
                }else {
                    for (var i=0;i<modelList.length;i++){
                        var stringBuffer = new StringBuffer();
                        stringBuffer.append('<tr>')
                            .append('<td>'+modelList[i].modelName+'</td>')
                            .append('<td>'+modelList[i].modelDesc+'</td>')
                            .append('<td>'+modelList[i].algorithm+'</td>')
                            .append('<td>'+modelList[i].trainData.trainDataName+'</td>')
                            .append('<td><span class="green">'+modelList[i].accuracyRate.substring(0,modelList[i].accuracyRate.indexOf(".") + 3)+'%</span></td>')
                            .append('<td><span class="green">'+modelList[i].precisionRate.substring(0,modelList[i].precisionRate.indexOf(".") + 3)+'%</span></td>')
                            .append('<td><span class="green">'+modelList[i].recallRate.substring(0,modelList[i].recallRate.indexOf(".") + 3)+'%</span></td>')
                            .append('<td><span class="green">'+modelList[i].f1.substring(0,modelList[i].recallRate.indexOf(".") + 5)+'</span></td>');
                        stringBuffer.append('<td><a href="#" onclick="model.openEditOrNewModelPage(\'编辑\',\''+modelList[i].modelID+'\')" class="btn-tools btn-info" title="编辑"><i class="glyphicon glyphicon-edit"></i></a>');
                        stringBuffer.append('<a href="#" onclick="model.deleteModel(\''+modelList[i].modelID+'\')" class="btn-tools btn-danger" title="删除"><i class="glyphicon glyphicon-trash"></i></a></td>');
                        stringBuffer.append('</tr>');
                        $modelListTbody.append(stringBuffer.toString());
                    }
                }

            }
        });
    },
    deleteModel:function (id) {
        layer.confirm('您确定删除数据？', {
            btn: ['是', '否']
            ,yes: function(index, layero){
                layer.close(index);
                var loadingIndex = layer.load(1, {
                    shade: [0.5,'#000'] //0.1透明度的白色背景
                });
                $.ajax({
                    type: "POST",
                    url: basePath+"/model/deleteModel",
                    data:{"modelId":id},
                    success: function(msg){
                        if (msg.code != 1) {
                            layer.alert(msg.msg);
                        }else {
                            layer.alert("删除成功");
                            model.loadData();
                        }
                        layer.close(loadingIndex);
                    }
                })
            }
        });
    },
    openEditOrNewModelPage:function (title,id) {
        var ajaxPath = basePath+'/model/toAddOrEditModelPage';
        if(id != undefined){
            ajaxPath = ajaxPath+"?modelId="+id;
        }
        layer.open({
            type: 2,
            title: title,
            shadeClose: false,
            shade: 0.8,
            area: ['400px', '350px'],
            content: ajaxPath,//iframe的url
            btn: ['保存', '取消'],
            yes: function (index, layero) {
                var iframeBody = layer.getChildFrame('body', index);
                model.editOrNewModel(index,iframeBody);
            }
        });
    },
    editOrNewModel:function (index,iframeBody) {
        var modelID = iframeBody.find("#modelID").val();
        var modelName = iframeBody.find("#modelName").val();
        var trainDataId = iframeBody.find("#trainDataId").val();
        var algorithm = iframeBody.find("#algorithm").val();
        var modelDesc = iframeBody.find("#modelDesc").val();
        layer.close(index);
        var loadingIndex = layer.load(1, {
            shade: [0.5,'#000'] //0.1透明度的白色背景
        });
        $.ajax({
            type: "POST",
            url: basePath+"/model/editOrNewModel",
            data:{
                modelID:modelID,
                modelName:modelName,
                trainDataId:trainDataId,
                algorithm:algorithm,
                modelDesc:modelDesc,
            },
            success: function(msg){
                if (msg.code != 1) {
                    layer.alert(msg.msg);
                }else {
                    layer.alert("保存成功");
                    model.loadData();
                }
                layer.close(loadingIndex);
            }
        })
    }
}
$(function () {
    model.loadData();
})