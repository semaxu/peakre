(function (context) {
    var $page = $pt.getService(context, '$page');
    var model = jsface.Class({
        createBaseModel: function () {
            return {
                ContractCode: null,
                FinancialYear: null,
                Cedent: null,
                SoaIdRead: null,
                UwYear: null,
                Broke: null,
                ReceivedDate: null,
                DueDate: null,
                AccountLevel: "1",
                BizType: null,
                CedentYear: null,
                CedentQuarter: null,
                //ReversalFlag:true,
                SoaText:null
            };
        },
        createEntryItemTemplate : function (){
            return {Percentage:1}
        },
        createPanelsModel: function(){
            return{
                Currencies: [{CurrencyType: "2"}, {CurrencyType: "+"}]
            }
        }

    });
    $page.model = new model();

}(typeof window !== "undefined" ? window : this));