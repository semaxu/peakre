/**
 * Created by brad.wu on 9/6/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');
    var queryCollectionUrl = $ri.getRestfulURL("action.arap.exchangeRate.queryExchangeRate");
    service.searchExchangeRate = function (model, quiet, async, done, fail) {
        $pt.doPost(queryCollectionUrl, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    var saveExchangeRateUrl = $ri.getRestfulURL("action.arap.exchangeRate.saveExchangeRate");
    service.saveExchangeRate = function (model, quiet, async, done, fail) {
        $pt.doPost(saveExchangeRateUrl, model, {
         quiet: quiet,
         async: async,
         done: done,
         fail: fail
         });
    };
    var invalidExchangeRateUrl = $ri.getRestfulURL("action.arap.exchangeRate.invalidExchangeRate");
    service.invalidExchangeRate = function (model, quiet, async, done, fail) {
        $pt.doPost(invalidExchangeRateUrl, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    var deleteExchangeRateUrl = $ri.getRestfulURL("action.arap.exchangeRate.deleteExchangeRate");
    service.deleteExchangeRate = function (model, quiet, async, done, fail) {
        $pt.doPost(deleteExchangeRateUrl, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    var updateExchangeRateUrl = $ri.getRestfulURL("action.arap.exchangeRate.updateExchangeRate");
    service.updateExchangeRate = function (model, quiet, async, done, fail) {
        $pt.doPost(updateExchangeRateUrl, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
}(this));