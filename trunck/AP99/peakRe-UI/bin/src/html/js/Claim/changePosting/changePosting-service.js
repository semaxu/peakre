/**
 * Created by yujie.zhang on 1/21/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');
    /**
     * client search service
     * @param claimId
     * @param quiet {boolean}
     * @param async {boolean}
     * @param done {function}
     * @param fail {function|{}}
     */
    service.loadSettlementNoListSer = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.settlement");
        var refId=model.RefId;
        var businessDirection=model.BusinessDirection;

        $pt.doPost(url+'/settlementNoList/'+refId+'/'+businessDirection, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.loadContractSection = function (model, quiet, async, done, fail) {
        //console.log("claimId = " + claimId);
        var refId=model.RefId;
        var businessDirection=model.BusinessDirection;
        var refType = model.RefType;

        var url = $ri.getRestfulURL("action.claim.contractSection");
        console.log("url = " + url);

        $pt.doGet(url+'/'+refId+'/'+businessDirection, {}, {

            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    //loadSettlement
    service.loadSettlement = function (model, quiet, async, done, fail) {
        //console.log("claimId = " + claimId);

        var settlementId=model.SettlementId;

        var url = $ri.getRestfulURL("action.claim.settlement");
        console.log("url = " + url);
        $pt.doGet(url+'/settlementItemList/'+settlementId, {}, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.submitSettlement= function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.settlement");
        $pt.doPost(url+'/changePostFlag', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

}(this));
