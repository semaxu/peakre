/**
 * Created by share.zhang on 2/23/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.validator = $pt.createModelValidator({
        ClaimNo:{required:true,},
        CauseOfLoss:{required:true},
        DateOfLossFrom:{required:true},
        DateOfLossTo:{required:true},
        DateOfReport:{required:true},
        //"ReserveList": {
        //    "table":{
        //        "ReserveType":{
        //            "required":true
        //        }
                //"ReserveType":{
                //    "required":true
                //}
        //    }
        //}
    });
}(typeof window !== "undefined" ? window : this));
