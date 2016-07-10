/**
 * Created by gang.wang on 10/20/2015.
 */
(function(context){
    var $page = $pt.getService(context,"$page");

    var model = jsface.Class({
        condition: function(){
            return {
                ReserveType : null,
                ContractCode : null,
                UnderwritingYear : null,
                ContractSection : null,
                RefId:null,
                BusinessDirection:null
            }
        },
        reserveHisData : function(){
            return {}
        },
        lastReserveData : function(){
            return {}
        }
    });
    $page.model = new model();
}(typeof window !== "undefined" ? window : this));