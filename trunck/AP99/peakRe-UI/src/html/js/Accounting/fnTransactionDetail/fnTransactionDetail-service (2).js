/**
 * Created by xiaoyu.yang on 12/23/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');

    service.load = function (contractCode, uwYear, done, fail) {
        var url = $ri.getRestfulURL("action.accounting.estimate") + "/loadContractStructure/" + contractCode + "/" +uwYear;
        $pt.doGet(url, null, {
            quiet: false,
            async: false,
            done: done,
            fail: fail
        });
    };

}(this));