(function (context) {
    var $page = $pt.getService(context, '$page');

    var model = jsface.Class({
        createBaseModel: function () {
            return {
                condition: {
                    PageIndex: 1,
                    CountPerPage: 10,
                    //LatestStatus: 4 || 5,
                },
                cachedCriteria: {
                    countPerPage: 10,
                    //LatestStatus: 4 || 5,
                    url: $ri.getRestfulURL("action.contract.contractHome") + "/page"
                },
                queryResult: []
            };
        }
    });

    $page.model = new model();

}(typeof window !== "undefined" ? window : this));