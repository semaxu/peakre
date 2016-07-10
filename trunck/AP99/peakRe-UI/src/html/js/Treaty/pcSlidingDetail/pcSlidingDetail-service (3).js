/**
 * Created by dominic.han on 1/18/2016.
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
        var url = $ri.getRestfulURL("action.contract.contractBusiness")+'/savePcSliding';
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.load = function (model, done, fail) {
        console.log(model);
        var url = $ri.getRestfulURL("action.contract.contractBusiness")+'/loadPcSliding/'+model.DeductionsId;
        $pt.doGet(url, null, {
            done: done,
            fail: fail
        });
    };

    service.loadPcSlidingForLog = function (model, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractBusiness")+'/loadPcSlidingForLog/'+model.DeductionsId+'/'+model.OperateId;
        $pt.doGet(url, null, {
            done: done,
            fail: fail
        });
    };
}(this));