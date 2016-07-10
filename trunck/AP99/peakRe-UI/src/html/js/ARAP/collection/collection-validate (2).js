/**
 * Created by xiaoyu.yang on 12/23/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.validate = $pt.createModelValidator({
        Payer:{
            "required":true
        },
        CollectionDate:{
            "required":true
        },
        PaymentMethod:{
            "required":true
        },
        Bank:{
            "required":true
        },
        CollectionCurrency:{
            "required":true
        },
        CollectToBankAccount:{
            "required":true
        },
        ValueDate:{
            "required":true
        },
        BankCharge:$helper.validateAmountLength(false, 2, 16),
        NetAmount:$helper.validateAmountLength(true, 2, 16)
    });
}(this));