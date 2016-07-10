/**
 * Created by brad.wu on 9/6/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');
    service.selectCollection = function (model, quiet, async, done, fail) {
        $pt.doPost($ri.getRestfulURL("action.arap.collection.queryCollection"), model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.saveCollection = function (model, quiet, async, done, fail) {
        $pt.doPost($ri.getRestfulURL("action.arap.collection.saveCollection"), model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.calGainLossInUSD = function (model, quiet, async, done, fail) {
        $pt.doPost($ri.getRestfulURL("action.arap.collection.calGainLossInUSD"), model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.queryBankAccountByCurrency = function (currency, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.arap.bankAccount.queryBankAccountByCurrency");
        url = url + "/"+currency +"/1";
        $pt.doGet(url, {}, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.viewCollection = function (transId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.arap.collection.viewCollection");
        url = url + "/"+transId;
        $pt.doGet(url, {}, {
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