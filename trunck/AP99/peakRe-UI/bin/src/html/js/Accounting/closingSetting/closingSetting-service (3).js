/**
 * Created by xiaoyu.yang on 12/23/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');

    service.load = function (done) {
        var url = $ri.getRestfulURL("action.accounting.closing") + "/load";
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done
        });
    };

    service.loadTable = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.accounting.closing") + "/page";
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.setCutOffDate = function (model, done, fail) {
        var url = $ri.getRestfulURL("action.accounting.closing") + "/setCutOffDate";
        $pt.doGet(url, model, {
            quiet: false,
            async: false,
            done: done,
            fail: fail
        });
    };

    service.startClosing = function (fnQuarter, done, fail) {
        var url = $ri.getRestfulURL("action.accounting.closing") + "/startClosing/" + fnQuarter;
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done,
            fail: fail
        });
    };

    service.endClosing = function (fnQuarter, done, fail) {
        var url = $ri.getRestfulURL("action.accounting.closing") + "/endClosing/" + fnQuarter;
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done,
            fail: fail
        });
    };

    service.runBatch = function (done, fail) {
        var url = $ri.getRestfulURL("action.accounting.estimate") + "/cutoffBatch";
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done,
            fail: fail
        });
    };

}(this));