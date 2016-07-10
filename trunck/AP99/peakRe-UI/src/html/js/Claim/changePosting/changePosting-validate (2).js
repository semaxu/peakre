/**
 * Created by share.zhang on 2/26/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.validator = $pt.createModelValidator({
        SettlementId:{required:true},

    });
}(typeof window !== "undefined" ? window : this));