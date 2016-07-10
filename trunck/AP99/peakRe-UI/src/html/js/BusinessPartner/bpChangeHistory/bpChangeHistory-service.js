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
        var url = $ri.getRestfulURL("action.public.partner") + "/queryHistory";
        $pt.doPost(url, model, {
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
}(this));