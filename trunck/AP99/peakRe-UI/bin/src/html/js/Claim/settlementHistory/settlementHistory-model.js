/**
 * Created by gang.wang on 10/20/2015.
 */
(function(context){
    var $page = $pt.getService(context,"$page");

    var model = jsface.Class({
        condition: function(){
            return {
                SettlementType : null,
                ContractCode : null,
                UnderwritingYear : null,
                ContractSection : null,
                RefId:null,
                BusinessDirection:null
            }
        },
        settlementHisData : function(){
            return {}
        },
        summaryData : function(){
            return {}
        }
    });
    $page.model = new model();

}(typeof window !== "undefined" ? window : this));