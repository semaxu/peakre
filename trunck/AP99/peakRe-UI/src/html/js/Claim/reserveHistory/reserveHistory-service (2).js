/**
 * Created by yujie.zhang on 1/19/2016.
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
    service.loadReserve = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.reserve");

        $pt.doPost(url+'/reserveHistory', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadContractSer = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.reserve");

        $pt.doGet(url+'/select', model, {
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
