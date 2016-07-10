/**
 * Created by share.zhang on 2/24/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.validator = $pt.createModelValidator({
        contractID:{required:true},
        underWritingYear:{required:true}
    });
}(typeof window !== "undefined" ? window : this));
