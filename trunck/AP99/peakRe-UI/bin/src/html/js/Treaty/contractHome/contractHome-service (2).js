/**
*Autohor : weiping.wang
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
    service.save = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/saveContract";
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadContractInfo = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/loadContractInfo";
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadContractInfoForLog = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/loadContractInfoForLog";
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    //
    //service.loadLink = function (contractCode,linkName, done) {
    //    var url = $ri.getRestfulURL("action.contract.contractStructure") + "/getLinkList/" + contractCode+"/"+linkName;
    //
    //    $pt.doGet(url, null, {
    //        done: done
    //    })
    //};

    service.isPricingEstimated = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/isPricingEstimated";
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.AMLCheckService = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/AMLCheck";
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };



    service.getNonRetroList= function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome")+"/getNonRetroList";
        $pt.doGet(url,model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.getContractCode = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractStructure") + "/getContractCode";
        $pt.doGet(url, null, {
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

    service.loadLinkTree = function (initValues, done) {
        var url = $ri.getRestfulURL("action.contract.contractStructure") + "/getLinkStructure/" + initValues.contractId + "/" + initValues.linkName+"/"+initValues.uwYear;
        $pt.doGet(url, null, {
            done: done
        })
    };
    service.loadBusiness = function (model, done) {
        var url = $ri.getRestfulURL("action.contract.contractBusiness") + "/loadBusiness/" + model.ContractNature + "/" + model.ContCompId;
        $pt.doGet(url, null, {
            done: done
        })
    };
    service.queryDocument = function (model, done) {
        var url = $ri.getRestfulURL("action.public.document") + "/queryDocument";
        $pt.doPost(url, model, {
            done: done,
            async: false,
        });
    };
    service.export = function(model,done,fail){
        var url = $ri.getRestfulURL("action.common.excel") + "/create/";
        if(model.ContractNature == 1){
            url = url + "13";
        }else if(model.ContractNature == 2){
            url = url + "14";
        }
        $pt.doPost(url, model, {
            done: done,
            async: false,
            fail: fail
        });
    };
    service.print = function(model,done){
        var url = $ri.getRestfulURL("action.common.print") + "/requestFilePath/" + model.DocumentId;
        $pt.doGet(url, null, {
            done: done,
            async: false,
        });
    };
    service.requestFilePath = function(model,done,fail){
        var url = $ri.getRestfulURL("action.common.print") + "/requestFilePathByBizType/";
        if(model.ContractNature == 1){
            url = url + "13";
        }else if(model.ContractNature == 2){
            url = url + "14";
        }
        $pt.doPost(url, model, {
            done: done,
            async: false,
            fail: fail
        });
    };
    service.pdfSummary = function(model,done,fail){
        var url = $ri.getRestfulURL("action.public.file") + "/printURL/";
        if(model.ContractNature == 1){
            url = url + "14";
        }else if(model.ContractNature == 2){
            url = url + "13";
        }
        $pt.doPost(url, model, {
            done: done,
            async: false,
            fail: fail
        });
    };
    service.mailForEndo = function(model,done,fail){
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/sendMail1";
        $pt.doPost(url, model, {
            done: done,
            async: false,
            fail: fail
        });
    };

    service.loadCountryService = function (claimId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.common.codeTable");
        $pt.doGet(url+'/country', {}, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.loadPartner = function(model,done){
      var url = $ri.getRestfulURL("action.public.partner") + "/loadByBusinessPartnerId";
      $pt.doGet(url, model, {
          quiet: false,
          async: false,
          done: done
      });
    };

    service.categoryChangeService = function (ContCompId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/CategoryChange/"+ContCompId;
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
}(typeof window !== "undefined" ? window : this));
