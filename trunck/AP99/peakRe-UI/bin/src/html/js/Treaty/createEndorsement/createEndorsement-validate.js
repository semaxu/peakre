/**
 * Created by xinxin.yue on 4/12/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.validator = $pt.createModelValidator({
        EffDate:{required:true},
    });
}(typeof window !== "undefined" ? window : this));