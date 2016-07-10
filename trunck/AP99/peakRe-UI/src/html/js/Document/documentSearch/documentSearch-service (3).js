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
    service.query = function (url,model, quiet, async, done, fail) {
     //   var url = $ri.getRestfulURL("action.public.document") + "/queryDocument";
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.fileUpload = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.public.document") + "/create/"+$page.model.DocumentId+"/"+model.DocumentType+"/"+model.Remarks;
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.fileRemove = function (DocumentId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.public.document") + "/remove/"+DocumentId;
        $pt.doGet(url, {}, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.loadDocumentTypeSection = function (businessType, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.public.document")+"/loadCode";
        $pt.doGet(url+"/"+businessType, {}, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.loadAllUserCodes = function (claimId, quiet, async, done, fail) {
        //console.log("claimId = " + claimId);
        var url = $ri.getRestfulURL("action.common.codeTable");
        console.log("url = " + url);
        $pt.doGet(url+'/getAllUser/CLAIM', {}, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.loadCurrentUserCodes = function (claimId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.common.codeTable");
        $pt.doGet(url+'/getCurrentUser', {}, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
}(this));