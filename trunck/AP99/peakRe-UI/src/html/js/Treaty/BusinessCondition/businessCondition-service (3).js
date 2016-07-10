/**
 * Created by elvira.du on 12/29/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');
    /**
     * save business condition info
     * @param model
     * @param done
     * @param fail
     */
    service.save = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractBusiness")+'/save';
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    /**
     * load business condition info
     * @param model
     * @param done
     */
    service.load = function(model, done){
        var url =  $ri.getRestfulURL("action.contract.contractBusiness")+'/load/'+model.ContCompId +"/"+model.ContractNature;
        $pt.doGet(url, null, {
            done: done
        });
    };
    //
    //service.recalculate = function(model, done){
    //    var url =  $ri.getRestfulURL("action.contract.contractBusiness")+'/recalculate';
    //    $pt.doPost(url, model, {
    //        done: done
    //    });
    //}
}(this));