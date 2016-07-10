/**
 * Created by Sema.Xu on 12/23/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');

    service.query = function (model, quiet, async, done, fail) {
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/search';
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadXMLService = function (soaId, quiet, async, done, fail) {
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/loadSoaModel/'+soaId;
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };


}(this));