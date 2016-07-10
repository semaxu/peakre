/**
 * Created by xinxin.yue on 2/22/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.validator = $pt.createModelValidator({
        financialYear:{required:true},
        financialQuarter:{required:true}
    });
}(typeof window !== "undefined" ? window : this));
