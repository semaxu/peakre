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
    service.query = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.public.document") + "/queryDocument";
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.fileUpload = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.public.document") + "/saveDocumentInfo";
        console.log(model.getCurrentModel());
        $pt.doPost(url, model.getCurrentModel(), {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.upload = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.public.file") + "/upload/1/1";
        console.log(model.getCurrentModel());
        $pt.doPost(url, model.getCurrentModel(), {
            quiet: quiet,
            async: async,
            contentType: "multipart/form-data",
            done: done,
            fail: fail
        });
    };
}(this));