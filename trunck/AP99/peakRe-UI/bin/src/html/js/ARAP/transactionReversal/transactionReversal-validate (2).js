/**
 * Created by ammon.zhou on 1/11/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.validator = $pt.createModelValidator({
        ReversalReason:{
            "required":true
        },
        RequestedBy:{
            "required":true
        }
    });
}(typeof window !== "undefined" ? window : this));

