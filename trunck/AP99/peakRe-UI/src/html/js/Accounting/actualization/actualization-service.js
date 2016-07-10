/**
 * Created by xiaoyu.yang on 12/23/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');

    service.query = function (url, model, async, done) {
        $pt.doPost(url, model, {
            quiet: false,
            async: async,
            done: done
        });
    };

    service.actualize = function (contractIdStr, done, fail) {
        var url = $ri.getRestfulURL("action.accounting.actualization") + "/actualize/" + contractIdStr;
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done,
            fail: fail
        });
    };

}(this));