/**
 * Created by yujie.zhang on 1/7/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');
    /**
     * client search service
     * @param url,model
     * @param quiet {boolean}
     * @param async {boolean}
     * @param done {function}
     * @param fail {function|{}}
     */
    //service.claimQuery = function (model, quiet, async, done, fail) {
    //    var url = $ri.getRestfulURL("action.claim.query");
    //
    //    $pt.doPost(url+'/claimquery', model, {
    //        quiet: quiet,
    //        async: async,
    //        done: done,
    //        fail: fail
    //    });
    //};
    service.claimQuery = function (url,model, quiet, async, done, fail) {
        //var url = $ri.getRestfulURL("action.claim.query");

        $pt.doPost(url, model, {
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

    service.loadClaim = function (claimId, quiet, async, done, fail) {
        //console.log("claimId = " + claimId);
        var url = $ri.getRestfulURL("action.claim.info");
        console.log("url = " + url);
        //'http://localhost:8080/peakre/restlet/v1/claim/claimInfo/'
        $pt.doGet(url+'/'+claimId, {}, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
//loadCurrentUserCodes
    service.loadCurrentUserCodes = function (claimId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.common.codeTable");
        $pt.doGet(url+'/getCurrentUser', {}, {
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
    service.loadCountryService = function (claimId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.common.codeTable");
        $pt.doGet(url+'/country', {}, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };


    service.loadXMLService = function (claimId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.claim.info");
        $pt.doGet(url+'/loadClaimModel/'+claimId, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
}(this));
