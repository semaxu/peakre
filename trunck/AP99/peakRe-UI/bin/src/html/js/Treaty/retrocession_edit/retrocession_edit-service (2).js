/**
 * Created by Weiping.Wang on 01/16/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');

    service.saveRetrocession = function (model, quiet, async, done, fail) {
        var param = model;
        delete param.treeArray;
        var url = $ri.getRestfulURL("action.contract.contractRetrocession");
        $pt.doPost(url + '/saveRetrocession', param, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadRetrocession = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractRetrocession");
        $pt.doGet(url + '/loadAssumedRetrocession', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    
    service.loadRetrocessionForLog = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractRetrocession");
        $pt.doGet(url + '/loadAssumedRetrocessionForLog', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.generateRetrocession = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractRetrocession");
        $pt.doGet(url + '/generateRetrocession', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.findContractSer = function (model, quiet, async, done, fail) {
        var contractCode = model.ContractCode;
        var UWyear =model.UnderWritingYear;
        var url = $ri.getRestfulURL("action.contract.contractRetrocession");
        $pt.doGet(url+'/getRetroContractStructure/'+contractCode+'/'+UWyear,null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

}(this));