/**
 * Created by xiaoyu.yang on 12/23/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');

    service.save = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.accounting.ibnr") + "/create";
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.load = function (done) {
        var url = $ri.getRestfulURL("action.accounting.ibnr") + "/generateCode";
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done,
            fail: null
        });
    };

}(this));