/**
 * Created by xiaoyu.yang on 12/23/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');

    service.load = function (level, conCompId, done) {
        var url = $ri.getRestfulURL("action.accounting.estimate") + "/loadUwViews/" + level + "/" + conCompId;
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done,
            fail: null
        });
    };

    service.search = function (level, conCompId, currency, date, done) {
        var url = $ri.getRestfulURL("action.accounting.estimate") + "/loadUwViews/" + level + "/" + conCompId + "/" + currency + "/" + date;
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done,
            fail: null
        });
    };

    service.recalculateForecast = function (level, conCompId, done) {
        var url = $ri.getRestfulURL("action.accounting.estimate") + "/recalculateForecast/" + level + "/" + conCompId;
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done,
            fail: null
        });
    };

    service.loadQuarterNeedPostE = function (conCompId, done) {
        var url = $ri.getRestfulURL("action.accounting.estimate") + "/loadQuarterNeedPostE/" + conCompId;
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done,
            fail: null
        });
    };

    service.postEstimation = function (conCompId, estimateIds, done) {
        var url = $ri.getRestfulURL("action.accounting.estimate") + "/postEstimation/" + conCompId + "/" + estimateIds;
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done,
            fail: null
        });
    };

    service.adjustEstimation = function (conCompId, estimateItems, done) {
        var url = $ri.getRestfulURL("action.accounting.estimate") + "/adjustEstimation/" + conCompId + "/" + estimateItems;
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done,
            fail: null
        });
    };

    service.adjustUprDac = function (conCompId, actualItems, done) {
        var url = $ri.getRestfulURL("action.accounting.estimate") + "/adjustUprDac/" + conCompId + "/" + actualItems;
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done,
            fail: null
        });
    };

    service.exportService = function (model, quiet, async, done) {
        var url = $ri.getRestfulURL("action.common.excel") + '/create/12';
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: null
        });
    };

}(this));