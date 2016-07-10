/**
 * Created by weiping.wang on 01/05/2016.
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
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/saveSection";
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

    service.exit = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/getContCompId";
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadSectionInfo = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/loadSectionInfo";
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.loadSectionInfoForLog = function (model, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.contract.contractHome") + "/loadSectionInfoForLog";
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

}(this));