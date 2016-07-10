/**
 * Created by elvira.du on 11/09/2015
 */
(function(context){
    var $page = $pt.getService(context,"$page");
    $page.model = {
    };
    $page.model.basic = {
        epiType : null,
        epiDefinedIn : null,
        epiRevisionType : null,
        epiRevisionDefinedIn : null
    };
    $page.model.result = {
        epiList : null,
        epiRevision : null,
        terrorismPremium : null
    };

    $page.model.epiListTemplate = {
        currency : null,
        amountPercent : 0,
        amountInShare : null
    };

    $page.model.epiRevisionTemplate = {
        date : null,
        by : null,
        currency : null,
        amountPercent : 0,
        amountInShare : null
    };

    $page.model.terrorismPremium = {
        currency : null,
        amountPercent : 0,
        amountInShare : null
    };

    $page.model.all = $.extend(true, {}, $page.model.basic, $page.model.result);
}(typeof window !== "undefined" ? window : this));