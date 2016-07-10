/**
 * Created by ammon.zhou on 1/19/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');
    var querySuspenseUrl = $ri.getRestfulURL("action.arap.offset.querySuspense");
    service.searchSuspense = function (model, quiet, async, done, fail) {
        console.info("-----search offset suspense-----");
        $pt.doPost(querySuspenseUrl, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    var queryCreditDebitUrl = $ri.getRestfulURL("action.arap.offset.queryCreditDebit");
    service.searchCreditDebit = function (model, quiet, async, done, fail) {
        console.info("-----search offset credit & debit-----");
        $pt.doPost(queryCreditDebitUrl, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    var submitOffsetUrl = $ri.getRestfulURL("action.arap.offset.submitOffset");
    service.submitOffset = function (model, quiet, async, done, fail) {
        console.info("-----submit offset -----");
        $pt.doPost(submitOffsetUrl, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.viewOffset = function (transId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.arap.offset.viewOffset");
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
