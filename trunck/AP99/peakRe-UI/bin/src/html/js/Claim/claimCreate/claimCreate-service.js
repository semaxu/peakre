/**
 * Created by yujie.zhang on 12/30/2015.
 */

(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');
    /**
     * client search service
     * @param model {{user: string, password: string}}
     * @param quiet {boolean}
     * @param async {boolean}
     * @param done {function}
     * @param fail {function|{}}
     */
    service.createclaim = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.info");

        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.checkEventDate = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.event");

        $pt.doGet(url+"/eventDate", model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadEventCodes = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.event");

        $pt.doGet(url+"/eventCode", model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
}(this));