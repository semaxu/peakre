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
    service.execute = function (model, quiet, async, done, fail) {
        var url =  $ri.getRestfulURL("action.accounting.closing")+'/revaluateJobExecute';
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    //service.execute = function (model, quiet, async, done, fail) {
    //    var url = $ri.getRestfulURL("action.common.excel");
    //    $pt.doPost(url+'/create/11', model, {
    //        quiet: quiet,
    //        async: async,
    //        done: done,
    //        fail: fail
    //    });
    //};

    service.search = function (model, quiet, async, done, fail) {
        var FNYear = model.financialYear;
        var FNQuarter = model.financialQuarter;
        var status = model.status;
        var url =  $ri.getRestfulURL("action.accounting.closing")+'/revaluationSearch/'+FNYear+'/'+FNQuarter+'/'+status;
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };


    service.exportService = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.common.excel");
        $pt.doPost(url+'/create/11', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.validBefore = function (model, quiet, async, done, fail) {
        var url =  $ri.getRestfulURL("action.accounting.closing")+'/validBefore';
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };



}(this));