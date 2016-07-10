/**
 * Created by Weiping.Wang on 01/16/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');

    service.loadEndorsementList = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractEndorsement") + '/loadEndorsementList';
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.deleteEndorsement = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractEndorsement") + '/deleteEndorsement';
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

}(this));