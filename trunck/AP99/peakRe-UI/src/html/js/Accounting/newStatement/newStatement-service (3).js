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
    service.create = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.accounting.statement") + "/createSoaLayer";
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.validateCurrentSoa = function (validateCondition, quiet, async, done, fail) {

        var ContractCode = validateCondition.ContractCode;
        var UWYear = validateCondition.UwYear;
        var CendantYear = validateCondition.CedentYear;
        var CendantQuarter = validateCondition.CedentQuarter;
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/validateSoa/'+ContractCode+'/'+UWYear+'/'+CendantYear+'/'+CendantQuarter;
        $pt.doGet(url, validateCondition, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };


    service.validateCurrentPTFSoa = function (validateCondition, quiet, async, done, fail) {

        var ContractCode = validateCondition.ContractCode;
        var UWYear = validateCondition.UwYear;
        var CendantYear = validateCondition.CedentYear;
        var CendantQuarter = validateCondition.CedentQuarter;
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/validatePTFSoa/'+ContractCode+'/'+UWYear+'/'+CendantYear+'/'+CendantQuarter;
        $pt.doGet(url, validateCondition, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

}(this));