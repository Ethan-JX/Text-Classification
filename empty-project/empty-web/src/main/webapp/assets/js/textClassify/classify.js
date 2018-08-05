/**
 * Created by Ethan on 2017/4/11.
 */

function classifyText() {
    var loadingIndex = layer.load(1, {
        shade: [0.5,'#000'] //0.1透明度的白色背景
    });
    var text = $("#text").val();
    var modelId = $("#model").val();
    if(modelId==null){
        layer.alert("无模型无法进行分类!");
        layer.close(loadingIndex);
        return;
    }
    $.ajax({
        type: "POST",
        url: basePath+"/textClassify/classify",
        data:{
            "modelId":modelId,
            "text":text},
        success: function(msg){
            if (msg.code != 1) {
                layer.alert(msg.msg);
            }else {
                layer.alert(msg.textClassName);
            }
            layer.close(loadingIndex);
        }
    });
}