/**
 * Created by yujie.zhang on 12/30/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');
    /**
     * client search service
     * @param claimId
     * @param quiet {boolean}
     * @param async {boolean}
     * @param done {function}
     * @param fail {function|{}}
     */
    service.loadClaim = function (claimId, quiet, async, done, fail) {
        //console.log("claimId = " + claimId);
        var url = $ri.getRestfulURL("action.claim.info");
        //'http://localhost:8080/peakre/restlet/v1/claim/claimInfo/'
        $pt.doGet(url+'/'+claimId, {}, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadFContractSection = function (refId, quiet, async, done, fail) {
        //console.log("claimId = " + claimId);
        var url = $ri.getRestfulURL("action.claim.contractSection");
        $pt.doGet(url+"/"+refId+'/1', {}, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadRContractSection = function (refId, quiet, async, done, fail) {
        //console.log("claimId = " + claimId);
        var url = $ri.getRestfulURL("action.claim.contractSection");
        $pt.doGet(url+"/"+refId+'/2', {}, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.checkEventDate = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.event");

        $pt.doGet(url+"/eventDate", model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };


    service.getContractDate= function (contCompId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.info");

        $pt.doGet(url+"/contractDate/"+contCompId, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.findContractSer = function (model, quiet, async, done, fail) {
        var contractID = model.ContractID;
        var UWyear =model.UnderWritingYear;
        var url = $ri.getRestfulURL("action.contract.contractStructure");
        //'http://localhost:8080/peakre/restlet/v1/contract/contractStructure
        $pt.doGet(url+'/getContractStructure/'+contractID+'/'+UWyear+'/false',null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.checkSectionSer= function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.contractSection");
        $pt.doGet(url+'/check',model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.saveContractSer= function (model, quiet, async, done, fail) {
        var treeArray =  model.treeArray;
        var refType = model.refType;
        var refId =model.refId;
        var businessDirection= model.businessDirection;

        var url = $ri.getRestfulURL("action.claim.contractSection");
        //http://localhost:8080/peakre/restlet/v1/claim/claimsection

        $pt.doPost(url+'/'+treeArray+'/'+refType+'/'+refId+'/'+businessDirection, null, {

            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.saveClaimInfo = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.info");
        $pt.doPut(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.submitClaimInfo = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.info");
        $pt.doPost(url+'/submit', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.getSettlementSer = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.settlement");
        $pt.doGet(url+'/settlementName', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.getReserve = function (model, quiet, async, done, fail) {
        var claimId = model.claimId;
        var url = $ri.getRestfulURL("action.claim.info");
        $pt.doGet(url+'/reserve/'+claimId,null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.getPosting = function (sectionId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.info");
        $pt.doGet(url+'/posting/'+sectionId,null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };


    service.checkClaimService = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.info");
        $pt.doPost(url+'/checkClose', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.closeClaimService = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.info");
        $pt.doPost(url+'/close', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.reopenClaimService = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.info");
        $pt.doPost(url+'/open', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.loadEventCodes = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.event");

        $pt.doGet(url+"/eventCode", model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadClaimMessage = function (refId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.info");
        $pt.doGet(url+'/message/'+refId, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.showMessageBoxService = function (refId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.info");
        $pt.doGet(url+'/message/'+refId, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.getAllClaimMessageService = function (refId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.message");
        $pt.doGet(url+'/'+refId, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.submitClaimMessageService = function (params, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.message");
        var array = params.treeArray;
        var refId = params.refId;
        $pt.doPost(url+'/'+array+'/'+refId, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadClaimAmountMessage = function (refId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.info");
        $pt.doGet(url+'/amountMessage/'+refId, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.exportService = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.common.excel");
        $pt.doPost(url+'/create/9', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.openExcelService = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.common.file");
        $pt.doGet(url+'/export/'+model, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.AMLCheckService = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.info");
        $pt.doPost(url+'/AMLCHECK', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
}(this));
