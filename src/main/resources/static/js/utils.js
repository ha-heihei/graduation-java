var post=function(url,data,callBack){
    $.ajax({
        type:"post",
        url:url,
        data:data,
        processData:false,   //  告诉jquery不要处理发送的数据
        contentType:false,   // 告诉jquery不要设置content-Type请求头
        success:callBack
    })
};


var toast=function (icon,title,msg) {
    Swal.fire({
        toast: true,
        position: 'top-end',
        icon: icon,
        title: title,
        showCloseButton:false,
        text: msg,
        timer: 3000,
        timerProgressBar: true,
        onOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer);
            toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
    });
};


function DownLoadFile(options) {
    var config = $.extend(true, { method: 'post' }, options);
    var $iframe = $('<iframe id="down-file-iframe" />');
    var $form = $('<form target="down-file-iframe" method="' + config.method + '" />');
    $form.attr('action', config.url);
    for (var key in config.data) {
        $form.append('<input type="hidden" name="' + key + '" value="' + config.data[key] + '" />');
    }
    $iframe.append($form);
    $(document.body).append($iframe);
    $form[0].submit();
    console.log($form.serialize());
    $iframe.remove();
}