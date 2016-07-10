/**
 * Created by gang.wang on 10/14/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    
    var model = jsface.Class({
        reserveArrayTemplate:function(){
            return{
                "AmountOc":"",
                //"OrgAmountOc": 0,
                //"AmountUsd": 0,
               // "OrgAmountUsd": 0,
                // "BrokerRefer": "broker1",
                // "CedentRefer": "CedentA",
                // "InsertBy": -11,
                // "InsertTime": "2015/12/30 11:22:16",
                // "OriginalCurrency": "1",
                "PostingFlag": "1",
                "OrgPostingFlag": "0",
                // "ReserveId": 1636533,
                // "ReserveType": "1",
                // "SectionId": "1",
                "RefType": "1",
                "Status": "0"
            };
        },
        settlementArrayTemplate:function(){
            return {
                "SettlementName":"",
                "DateOfReceipt": $pt.newSystemDate,
                "Status": "1",
                "RefType": "1"
                //"SettlementItemList": [
                //    {
                //        "AmountOc": 0,
                //        "AmountUsd": 0,
                //        // "BrokerRefer": "",
                //        // "CedentRefer": "",
                //        // "OriginalCurrency": "2",
                //        "PostingFlag": "1"
                //        // "SectionId": 1,
                //        // "SettlementType": "1"
                //    }
                //]
            };
        },
        settlementDetailTemplate:function(){
            return {
                "AmountOc": "",
                //"AmountUsd": 0,
                // "BrokerRefer": "",
                // "CedentRefer": "",
                // "OriginalCurrency": "2",
                "PostingFlag": "1",
                "OrgPostingFlag":"0"
                // "SectionId": 1,
                // "SettlementType": "1"
              }
        },
        createModel :function() {
          return {
            // ReserveSummary:[
            //   {"CurrencyType":"1", "LossTotal":100, "ACRTotal":200, "RIPTotal":300, "TaxTotal": 400, "OthersTotal": 500},
            //   {"CurrencyType":"2", "LossTotal":100, "ACRTotal":200, "RIPTotal":300, "TaxTotal": 400, "OthersTotal": 500},
            //   {"CurrencyType":"3", "LossTotal":100, "ACRTotal":200, "RIPTotal":300, "TaxTotal": 400, "OthersTotal": 500}
            //
            // ],
            // SettlementSummary:[
            //   {"CurrencyType":"1", "GrossTotal":500, "NetTotal":200},
            //   {"CurrencyType":"2", "GrossTotal":200, "NetTotal":100},
            // ],
            // ReserveUsdEquivalent : 1500,
          }
        },
        eventCodes : null,
        contractSelection : null,
        rcontractSelection:null
    });
    $page.model = new model();

    // $page.model.all = $.extend(true, {}, $page.model.base, $page.model.settlementArrayTemplate);
}(typeof window !== "undefined" ? window : this));
