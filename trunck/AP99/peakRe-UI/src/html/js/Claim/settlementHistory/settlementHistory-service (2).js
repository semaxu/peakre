/**
 * Created by yujie.zhang on 1/25/2016.
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
    service.loadSettlementService = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.settlement");

        $pt.doPost(url+'/settlementHistory', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadContractSer = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.settlement");

        $pt.doGet(url+'/select', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.getDetailSer = function (SettleDetailId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.settlement");

        $pt.doGet(url+'/settlementDetail/'+SettleDetailId, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadAllUserCodes = function (claimId, quiet, async, done, fail) {
        //console.log("claimId = " + claimId);
        var url = $ri.getRestfulURL("action.common.codeTable");
        console.log("url = " + url);
        $pt.doGet(url+'/getAllUser/CLAIM', {}, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
}(this));