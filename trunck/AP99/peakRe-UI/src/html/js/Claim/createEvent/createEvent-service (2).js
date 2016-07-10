/**
 * Created by yujie.zhang on 1/13/2016.
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
    service.createEvent = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.event");
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.checkCode = function (model, quiet, async, done, fail){
        var url = $ri.getRestfulURL("action.claim.event");
        $pt.doGet(url+'/checkCode',null,{
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };



}(this));