/**
 * Created by xiaoyu.yang on 12/23/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    var validator = {};

    if ($page.controller.isManageClient) {
        validator.idNumber = {required: true};
        validator.name = {required: true};
    }

    $page.validator = validator;
}(this));