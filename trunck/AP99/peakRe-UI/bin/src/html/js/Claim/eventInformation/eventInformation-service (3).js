/**
 * Created by yujie.zhang on 1/27/2016.
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
    service.loadEvent = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.event");

        $pt.doGet(url+"/eventInfo", model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.loadRContractSection = function (eventId, quiet, async, done, fail) {
        //console.log("claimId = " + claimId);
        var url = $ri.getRestfulURL("action.claim.contractSection");
        console.log("url = " + url);
        $pt.doGet(url+'/'+eventId+'/2', {}, {
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
        console.log("url = " + url);
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
        console.log("url = " + url);
        console.log(model);
        //'http://localhost:8080/peakre/restlet/v1/claim/claimsection
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
        console.log("businessDirection = " + businessDirection);
        $pt.doPost(url+'/'+treeArray+'/'+refType+'/'+refId+'/'+businessDirection, null, {
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
    service.saveEventInfo = function (model, quiet, async, done, fail) {
        console.debug("saveClaimInfo in service js....");
        var url = $ri.getRestfulURL("action.claim.event");
        console.log("url = " + url);
        console.log(model);

        $pt.doPut(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.removeClaims = function (model, quiet, async, done, fail){
        var url = $ri.getRestfulURL("action.claim.event");
        console.log(model);
        $pt.doPost(url+'/remove',model,{
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.checkCode = function (model, quiet, async, done, fail){
        var url = $ri.getRestfulURL("action.claim.event");
        $pt.doGet(url+'/eventCode',null,{//checkCode
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.submitEventInfo = function (model, quiet, async, done, fail) {
        console.debug("saveClaimInfo in service js....");
        var url = $ri.getRestfulURL("action.claim.event");
        //http://localhost:8080/peakre/restlet/v1/claim/claimInfo
        $pt.doPost(url+'/submit', model, {
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

    service.loadRelatedClaim = function (model, quiet, async, done, fail) {
        //console.log("claimId = " + claimId);
        var url = $ri.getRestfulURL("action.claim.event");

        $pt.doGet(url+"/relateClaim", model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.getClaimRecordsService = function (model, quiet, async, done, fail) {
        //console.log("claimId = " + claimId);
        var url = $ri.getRestfulURL("action.claim.event");

        $pt.doPost(url+"/queryClaimSumary", model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadClaimMessage = function (refId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.event");
        $pt.doGet(url+'/message/'+refId, null, {
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
    service.showMessageBoxService = function (refId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.event");
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
    service.loadEventAmountMessage = function (refId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.event");
        $pt.doGet(url+'/amountMessage/'+refId, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.exportService = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.common.excel");
        $pt.doPost(url+'/create/10', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.exportDetailService = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.common.excel");
        $pt.doPost(url+'/create/18', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.AMLCheckService = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.event");
        $pt.doPost(url+'/AMLCHECK', model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
}(this));
