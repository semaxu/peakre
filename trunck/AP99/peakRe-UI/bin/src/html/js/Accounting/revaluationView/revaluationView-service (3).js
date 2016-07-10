/**
 * Created by Sema.Xu on 12/23/2015.
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

    //
    service.detailView = function (model, quiet, async, done, fail) {
        var url =  $ri.getRestfulURL("action.accounting.closing")+'/revaluationDetailSeach';
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });

        //var url =  $ri.getRestfulURL("action.common.excel")+'/create/8';
        //$pt.doGet(url, null, {
        //    quiet: quiet,
        //    async: async,
        //    done: done,
        //    fail: fail
        //});
    };

    service.view = function (model, quiet, async, done, fail) {
        var url =  $ri.getRestfulURL("action.accounting.closing")+'/revaluateResultView';
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.exportService = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.common.excel");
        $pt.doPost(url+'/create/9', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };




}(this));