/**
 * Created by dominic.han on 1/19/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');
    /**
     * client search service
     * @param model {{user: string, password: string}
     * @param done {function}
     */
    service.save = function (model, quiet, async, done, fail) {
        console.log(model);
        var url = $ri.getRestfulURL("action.contract.contractBusiness")+'/saveCommSliding';
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.load = function (model, done, fail) {
        console.log(model);
        var url = $ri.getRestfulURL("action.contract.contractBusiness")+'/loadCommSliding/'+model.DeductionsId;
        $pt.doGet(url, null, {
            done: done,
            fail: fail
        });
    };

    service.loadCommSlidingForLog = function (model, done, fail) {
        console.log(model);
        var url = $ri.getRestfulURL("action.contract.contractBusiness")+'/loadCommSlidingForLog/'+model.DeductionsId+'/'+model.OperateId;
        $pt.doGet(url, null, {
            done: done,
            fail: fail
        });
    };
}(this));