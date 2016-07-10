/**
 * Created by weiping.wang on 01/05/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');

    //service.query = function (model, quiet, async, done, fail) {
    //    var url = $ri.getRestfulURL("action.contract.contractHome") + "/page";
    service.query = function (url, model, quiet, async, done, fail) {
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.copyOrRenewContract = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/copyOrRenewContract";
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.getRenewalStatus = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/getRenewalStatus";
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

}(this));