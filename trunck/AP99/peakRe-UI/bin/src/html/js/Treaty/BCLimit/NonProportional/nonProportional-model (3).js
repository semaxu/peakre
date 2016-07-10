/*
 create by elvira.du 10/15/2015
 */
(function(context){
    var $page = $pt.getService(context,"$page");
    $page.model = {};
    $page.model.basic = {
        limitType : null,
        unlimited : false,
        amountType : '1'
    };
    $page.model.result = {
        regular1 : null,
        regular2 : null,
        stopLoss : null
    };

    $page.model.regular1Template = {
        currency : null,
        deductible : null,
        layer : null,
        aal : null,
        aad : null,
        layerInShare : null,
        aalInShare : null,
        remark : null
    };

    $page.model.regular2Template = {
        currency : null,
        event : null,
        limit : null,
        limitInShare : null,
        remark : null
    };

    $page.model.stopLossTemplate = {
        currency : null,
        layer : null,
        deductible : null,
        eventEQ : null,
        eventWS : null,
        eventFL : null,
        eventOther : null,
        terrorism : null
    };

    $page.model.all = $.extend(true, {}, $page.model.basic, $page.model.result);
}(typeof window !== "undefined" ? window : this));