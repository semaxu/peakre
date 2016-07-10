/**
 * Created by share.zhang on 2/24/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.validator = $pt.createModelValidator({
        documentName:{required:true,},
        documentType:{required:true},
        uploadFile:{required:true}

    });
}(typeof window !== "undefined" ? window : this));
