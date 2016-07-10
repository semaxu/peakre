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


    service.load = function (model, quiet, async, done, fail) {
        var FNYear = model.FNYear;
        var FNQuarter = model.FNQuarter;
        var url =  $ri.getRestfulURL("action.accounting.closing")+'/exceptionContractLoad/'+FNYear+'/'+FNQuarter;
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.review = function (model, quiet, async, done, fail) {
        var url =  $ri.getRestfulURL("action.accounting.closing")+'/exceptionContractReview';
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

}(this));