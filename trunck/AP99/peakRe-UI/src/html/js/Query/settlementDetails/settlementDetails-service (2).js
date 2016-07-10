/**
 * Created by ammon.zhou on 4/8/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');
    /**
     * user search service
     * @param model
     * @param quiet {boolean}
     * @param async {boolean}
     * @param done {function}
     * @param fail {function|{}}
     */
    var loadUrl = $ri.getRestfulURL("action.arap.credit.queryTransactionHistory");
    service.load = function (FeeIdArray, quiet, async, done, fail) {
        loadUrl = loadUrl +"/"+FeeIdArray;
        $pt.doPost(loadUrl, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
}(this));