/**
 * Created by Weiping.Wang on 01/16/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');

    service.loadEndorsement = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractEndorsement") + '/loadEndorsement';
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.saveEndorsement = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractEndorsement") + '/saveEndorsement';
        $pt.doPost(url, model, {
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


}(this));