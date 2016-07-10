/**
 * Created by share.zhang on 2/25/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.validator = $pt.createModelValidator({
        EventCode:{required:true},
        CauseOfLoss:{required:true},
        DateOfLossFrom:{required:true},
        DateOfLossTo:{required:true},
        //DateOfReceipt:{required:true}

    });
}(typeof window !== "undefined" ? window : this));