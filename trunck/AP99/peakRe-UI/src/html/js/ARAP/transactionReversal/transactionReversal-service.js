/**
 * Created by ammon.zhou on 1/7/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');
    service.searchTransactionReversal = function (url, model, async, done) {
        $pt.doPost(url, model, {
            quiet: false,
            async: async,
            done: done
        });
    };
    var submitTransactionUrl = $ri.getRestfulURL("action.arap.transactionReversal.submitTransactionReversal");
    service.submitTransactionReversal = function (model, quiet, async, done, fail) {
        console.info("-----submitTransactionReversal-----");
        $pt.doPost(submitTransactionUrl, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.loadCurrentUserCodes = function (claimId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.common.codeTable");
        $pt.doGet(url+'/getCurrentUser', {}, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
}(this));
