(function (context) {
    var $page = $pt.getService(context, '$page');
    //$page.model = {
    //    searchResult : [
    //        {eventCode: "EA001", dataOfLossFrom: "13/09/2014", to:"13/09/2014", causeOfLoss: "Fire", lossDescription: ""},
    //        {eventCode: "EA002", dataOfLossFrom: "13/09/2014", to:"13/09/2014", causeOfLoss: "Fire", lossDescription: ""},
    //        {eventCode: "EA003", dataOfLossFrom: "13/09/2014", to:"13/09/2014", causeOfLoss: "Fire", lossDescription: ""}
    //    ]
    //};

    var model = jsface.Class({
        //condition: function(){
        //    return {
        //        EventCode:null,
        //        //ClaimBranch:null,
        //        CauseOfLoss:null,
        //        ContractID:null,
        //        //UnderwritingYear:null,
        //        CedantName:null,
        //       // CedantCountry:null,
        //       // CedantReference:null,
        //       // CrokerReferece:null,
        //        DateOfLossFrom:null,
        //        DateOfLossTo:null,
        //        //Status:null,
        //        TaskOwner:null,
        //        LossDescription:null,
        //    }
        //},
        //searchResult : function(){
        //    return {}
        //},
        //cachedCriteria : function(){
        //    return {}
        //}

        condition: function(){
            return {
                //DateOfLossFrom:$pt.newSystemDate,
                //DateOfLossTo:$pt.newSystemDate,
               // TaskOwner:Cookies.get($page.constants.USER_COOKIE_NAME),
                PageIndex: 1,
                CountPerPage: 10

            }
        },
        searchResult : function(){
            return {}
        },
        cachedCriteria : function(){
            return {
                countPerPage: 10,
                url: $ri.getRestfulURL("action.claim.query")+ "/eventQuery"
            }
        },
        lastCriteria: function(){

        },
        createModel:function(){
            return{
                condition:this.condition(),
                cachedCriteria:this.cachedCriteria(),
                lastCriteria:this.lastCriteria()
            }
        }
    });
    $page.model = new model();
}(typeof window !== "undefined" ? window : this));
