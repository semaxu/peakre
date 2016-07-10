(function (context) {
    var $page = $pt.getService(context, '$page');
    //$page.model = {
    //    ClaimNo:null,
    //    ClaimId:null,
    //    EventCode:null,
    //    CauseOfLoss:null,
    //    DateOfReport:null,
    //    DateOfLossFrom:null,
    //    DateOfLossTo:null,
    //    DateOfCreation:null,
    //    LossDescription:null,
    //    Remark:null
    //};
    var model = jsface.Class({
        createModel: function () {
            //alert($pt.newSystemDate);
            return {
                ClaimNo: null,
                ClaimId: null,
                EventId: "",
                CauseOfLoss: "",
                ClaimBranch: "1",
                DateOfReport: $pt.newDate,
                DateOfLossFrom: $pt.newDate,
                DateOfLossTo: $pt.newDate,
                DateOfCreation: $pt.newSystemDate,
                LossDescription: "",
                Remark: null
            }
        },
        eventCodes: null
    });
    $page.model = new model();

}(typeof window !== "undefined" ? window : this));
