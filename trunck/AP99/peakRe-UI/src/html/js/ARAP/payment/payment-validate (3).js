/**
 * Created by xiaoyu.yang on 12/23/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.validator = $pt.createModelValidator({
    	TotalAmount:$helper.validateAmountLength(false, 2, 16),
        PaymentCurrency:{
            "required":true
        },
        PaymentDate:{
            "required":true
        },
        ValueDate:{
            "required":true
        },
        PaymentMethod:{
            "required":true
        },
        PaidAccount:{
            "required":true
        }
    });
}(this));


