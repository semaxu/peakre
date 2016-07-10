/**
 * Created by Weiping.Wang on 01/16/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');

    service.loadCoveredRetrocession = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractRetrocession");
        $pt.doGet(url + '/loadCoveredRetrocession', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadCoveredRetrocessionForLog = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractRetrocession");
        $pt.doGet(url + '/loadCoveredRetrocessionForLog', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

}(this));