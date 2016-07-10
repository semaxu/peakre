/**
 * Created by elvira.du on 11/09/2015
 */
(function(context){
    var $page = $pt.getService(context,"$page");
    $page.model = {};
    $page.model.basic = {
        limitType : null,
        limitBaseOn : null
    };
    $page.model.result = {
        qs1 : null,
        qs2 : null
    };

    $page.model.qs1Template = {
        currency : null,
        retention : null,
        liability : null,
        liabilityOurShare : null,
        remark : null
    };

    $page.model.qs2Template = {
        currency : null,
        event : null,
        liability : null,
        liabilityOurShare : null,
        remark : null
    };

    $page.model.sp = {
        retention : null,
        lineNumber : null,
        capacity : null,
        eq : null,
        ws : null,
        fl : null,
        ot : null,
        terrorism : null
    };

    $page.model.all = $.extend(true, {}, $page.model.basic, $page.model.result,$page.model.sp);
}(typeof window !== "undefined" ? window : this));