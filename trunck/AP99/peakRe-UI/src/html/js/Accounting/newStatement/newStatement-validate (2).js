/**
 * Created by xinxin.yue on 2/22/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.validator = $pt.createModelValidator({
        condition_ContractCode:{required:true},
        condition_UwYear:{required:true},
        condition_CedentYear:{required:true},
        condition_CedentQuarter:{required:true},
        condition_StatementType:{required:true},
        condition_CedentPeriod:{required:true}

    });
}(typeof window !== "undefined" ? window : this));
