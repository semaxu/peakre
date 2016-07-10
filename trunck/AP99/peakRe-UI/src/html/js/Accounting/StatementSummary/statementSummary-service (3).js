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


    service.loadSummary = function (model, quiet, async, done, fail) {
        var SoaIdRead = model.SoaIdRead;
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/loadSoaSummary/'+SoaIdRead;
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };











}(this));