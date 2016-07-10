/**
 * Created by xiaoyu.yang on 12/23/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');

    service.load = function (segmentId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.accounting.ibnr") + "/load/" + segmentId;
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadTable = function (segmentId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.accounting.ibnr") + "/loadIbnr/" + segmentId;
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.update = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.accounting.ibnr") + "/update";
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
}(this));