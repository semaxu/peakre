/**
 * Created by yujie.zhang on 12/30/2015.
 */
//(function (context) {
//    var $page = $pt.getService(context, '$page');
//
//    var validator = {};

    //if ($page.controller.isManageClient) {
    //    validator.idNumber = {required: true};
    //    validator.name = {required: true};
    //}

    //$page.validator = validator;
//}(this));

(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.validator = $pt.createModelValidator({
        CauseOfLoss:{required:true},
        DateOfReport:{required:true},
        DateOfLossFrom:{required:true},
        DateOfLossTo:{required:true}
    });
}(typeof window !== "undefined" ? window : this));
