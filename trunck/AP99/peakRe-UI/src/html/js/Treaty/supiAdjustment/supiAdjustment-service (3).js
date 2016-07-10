/**
 * Created by elvira.du on 3/10/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');

    service.save = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.adjustment") + '/saveSUPIAdjustment';
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.load = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.adjustment") + '/loadSUPIAdjustment';
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadContractInfo = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/loadContractInfo";
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadAllUserCodes = function (claimId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.common.codeTable");
        console.log("url = " + url);
        $pt.doGet(url+'/getAllUser/CONTRACT', {}, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
}(this));