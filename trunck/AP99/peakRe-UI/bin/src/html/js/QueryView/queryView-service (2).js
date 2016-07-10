/**
 *Autohor : ammon.zhou
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

    service.loadContractInfo = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/loadContractInfo";
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    //Edit Icon
    service.save = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/saveContract";
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.submit = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/submitContractInfo";
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.submitSpecial = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/submitContractInfoWithSpecial";
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadEndorsementList = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractEndorsement") + "/loadEndorsementList";
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.queryStatement = function (contractId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.accounting.statementOfAccounting.viewStatement");
        url = url + "/"+contractId;
        $pt.doPost(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadClaimList = function (contractId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.query");
        url = url + "/claimInfo/"+contractId;
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    //hyperlink of claim number
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

    service.selectCollection = function (model, quiet, async, done, fail) {
        $pt.doPost($ri.getRestfulURL("action.arap.credit.queryCredit"), model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.load = function (contractCode, uwYear, done, fail) {
        var url = $ri.getRestfulURL("action.accounting.estimate") + "/loadContractStructure/" + contractCode + "/" +uwYear;
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done,
            fail: fail
        });
    };

    //FN View
    service.loadFnPage = function (conCompId, done, fail) {
        var url = $ri.getRestfulURL("action.accounting.estimate") + "/loadFnViews/" + conCompId;
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done,
            fail: fail
        });
    };

    service.recalculateForecast = function (itemId, done, fail) {
        var url = $ri.getRestfulURL("action.accounting.estimate") + "/transDetail/" + itemId;
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done,
            fail: fail
        });
    };

}(typeof window !== "undefined" ? window : this));
