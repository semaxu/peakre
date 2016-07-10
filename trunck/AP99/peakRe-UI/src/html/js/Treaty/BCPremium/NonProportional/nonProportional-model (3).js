/*
 create by elvira.du 10/15/2015
 */
(function(context){
    var $page = $pt.getService(context,"$page");
    $page.model = {};
    $page.model.basic = {
        supiDefined : null,
        supiRevisionDefine : null,
        note : "Note:Revised SUPI is for information only, Change Deposit Premium, if needed, to have accounting effect.",
        adjustSupiDefine : null,
        premiumType : null,
        installments : null
    };
    $page.model.result = {
        estimatedSUPI : null,
        revisedSUPI : null,
        adjustedSUPI : null,
        floatPremium : null,
        fixedRate : null,
        swingRate : null,
        minimumPremium : null,
        depositPremium :null,
        depositSchedule : null
    };

    $page.model.estimatedSUPITemplate = {
        currency : null,
        amount : null
    };

    $page.model.revisedSUPITemplate = {
        dateOfRevision : null,
        currency : null,
        amount : null
    };

    $page.model.adjustedSUPITemplate = {
        dateOfAdjustment : null,
        currency : null,
        amount : null
    };

    $page.model.floatPremiumTemplate = {
        currency : null,
        percent : null,
        ourShare : null
    };

    $page.model.fixedRateTemplate = {
        currency : null,
        premiumRate : 0,
        percent : null,
        ourShare : null
    };

    $page.model.swingRateTemplate = {
        currency : null,
        lrFrom : 0,
        lrTo : 0,
        prFrom : 0,
        prTo : 0,
        provR : 0,
        provAMT : null
    };

    $page.model.minimumPremiumTemplate = {
        currency : null,
        definedIn : null,
        percent : null,
        ourShare : null
    };

    $page.model.depositPremiumTemplate = {
        currency : null,
        definedIn : null,
        percent : null,
        ourShare : null
    };

    $page.model.depositScheduleTemplate = {
        instalNo : null,
        dueDate : null,
        currency : null,
        amount : null
    }

    $page.model.all = $.extend(true, {}, $page.model.basic, $page.model.result);
}(typeof window !== "undefined" ? window : this));