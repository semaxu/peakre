/**
 * Created by yujie.zhang on 2/4/2016.
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
    //service.eventQuery = function (model, quiet, async, done, fail) {
    //    var url = $ri.getRestfulURL("action.claim.query");
    //
    //    $pt.doPost(url+'/eventQuery', model, {
    //        quiet: quiet,
    //        async: async,
    //        done: done,
    //        fail: fail
    //    });
    //};

    service.eventQuery = function (url,model, quiet, async, done, fail) {

        $pt.doPost(url,model, {
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
    service.loadXMLService = function (eventId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.event");
        console.log(url+"=========="+eventId);
        $pt.doGet(url+'/loadEventModel/'+eventId, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    //loadAllUserCodes
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