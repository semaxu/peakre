/**
 * Created by elvira.du on 3/10/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');
    /**
     * client search service
     * @param model {{user: string, password: string}
     * @param done {function}
     */
    service.save = function (model, done) {
        var url = $ri.getRestfulURL("action.contract.pricingEstimate")+'/savePricingEstimate';
        $pt.doPost(url, model, {
            done: done
        });
    };

    service.load = function (contCompId, done) {
        var url = $ri.getRestfulURL("action.contract.pricingEstimate")+'/loadPricingEstimate/'+contCompId;
        $pt.doGet(url, null, {
            done: done
        });
    };

    service.loadPricingEstimateForLog = function (contCompId, operateId, done) {
        var url = $ri.getRestfulURL("action.contract.pricingEstimate")+'/loadPricingEstimateForLog/'+contCompId+'/'+operateId;
        $pt.doGet(url, null, {
            done: done
        });
    };

    service.loadAllUserCodes = function (claimId, quiet, async, done, fail) {
        var url = $ri.getRestfulURL("action.common.codeTable");
        console.log("url = " + url);
        $pt.doGet(url+'/getAllUser/CONTRACT', {}, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
}(this));