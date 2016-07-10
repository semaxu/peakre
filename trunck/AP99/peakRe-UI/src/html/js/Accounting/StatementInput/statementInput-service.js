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

    service.saveAndUpdate = function (model, quiet, async, done, fail) {
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/saveAndUpdateSoa';
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.load = function (model, quiet, async, done, fail) {
        var SoaIdRead = model.SoaIdRead;
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/loadSoa/'+SoaIdRead;
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.findcontractser = function (model, quiet, async, done, fail) {
        var contractCode = model.ContractCode;
        var uwYear =model.UnderWritingYear;
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/contractStructure/'+contractCode+'/'+uwYear+'/true';
        $pt.doGet(url,null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };


    service.saveContractser= function (model, quiet, async, done, fail) {
        var treeArray =  model.treeArray;
        var contractId =model.ContractCode;
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/contractStructure/'+treeArray+'/'+contractId;
        $pt.doPost(url,null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.submit = function (model, quiet, async, done, fail) {
        var SoaIdRead = model.SoaId;
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/submitSoa';
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.PTFSubmit = function (model, quiet, async, done, fail) {
        var SoaIdRead = model.SoaId;
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/PTFSubmitSoa';
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.ignoringSubmit = function (model, quiet, async, done, fail) {
        var SoaIdRead = model.SoaId;
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/ignoringSubmit';
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };


    service.saveAndReverse = function (model, quiet, async, done, fail) {
        var SoaIdRead = model.SoaId;
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/submitAndReverseSoa';
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.ignoringSaveAndReverse = function (model, quiet, async, done, fail) {
        var SoaIdRead = model.SoaId;
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/ignoringSaveAndReverse';
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };




    service.beforeReversal = function (model, quiet, async, done, fail) {
        var SoaIdRead = model.SoaId;
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/validateSoaReversal';
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };


    service.validCendentYearAndQuarter = function (model, quiet, async, done, fail) {
        var ContractCode = model.ContractCode;
        var UWYear = model.UwYear;
        var CendantYear = model.CedentYear;
        var CendantQuarter = model.CedentQuarter;
        var Cedent = model.Cedent;
        var Broker = model.Broke;
        if (model.Cedent==undefined|| model.Cedent == null|| model.Cedent == '') {
            Cedent = "0"
        }
        if (model.Broke==undefined||  model.Broke == null|| model.Broke == '') {
            Broker = "0"
        }
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/validCendentYearAndQuarter/'+ContractCode+'/'+UWYear+'/'+CendantYear+'/'+CendantQuarter+'/'+Cedent+'/'+Broker;
        $pt.doPost(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.AMLCheckService = function (model, quiet, async, done, fail) {
        var Cedent = model.Cedent;
        var Broker = model.Broke;
        if (model.Cedent==undefined|| model.Cedent == null|| model.Cedent == '') {
            Cedent = "0"
        }
        if (model.Broke==undefined||  model.Broke == null|| model.Broke == '') {
            Broker = "0"
        }
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/AMLCheck/'+Cedent+'/'+Broker;
        $pt.doPost(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.queryEntryItem = function (entryCode, quiet, async, done, fail) {
        var statementType = $page.controller.model.get('StatementType');
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/queryEntryItem/'+entryCode+'/'+statementType;
        $pt.doGet(url,null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.validateCurrentPTFSoa = function (validateCondition, quiet, async, done, fail) {

        var ContractCode = validateCondition.get("ContractCode");
        var UWYear = validateCondition.get("UwYear");
        var CendantYear = validateCondition.get("CedentYear");
        var CendantQuarter = validateCondition.get("CedentQuarter");
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/validatePTFSoa/'+ContractCode+'/'+UWYear+'/'+CendantYear+'/'+CendantQuarter;
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };




}(this));