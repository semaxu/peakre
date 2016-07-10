/**
 * Created by elvira.du on 11/09/2015
 */
(function(context){
    var $page = $pt.getService(context,"$page");
    $page.model = {};
    $page.model.basic = {
        participationBase : null,
        minimumRatio : 0,
        maxinumRatio : 0,
        cedentParticipation : 0,
        firstCalc : null,
        subseqCalc : null,
        ssTable : null
    };
       $page.model.all = $.extend(true, {}, $page.model.basic);
}(typeof window !== "undefined" ? window : this));