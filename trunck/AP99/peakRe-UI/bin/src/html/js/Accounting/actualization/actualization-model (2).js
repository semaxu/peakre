(function (context) {
    var $page = $pt.getService(context, '$page');

    var model = jsface.Class({
        createBaseModel: function () {
            return {
                condition: {
                    PageIndex: 1,
                    CountPerPage: 10
                },
                cachedCriteria: {
                    countPerPage: 10,
                    url: $ri.getRestfulURL("action.accounting.actualization") + "/page"
                },
                resultTable: []
            };
        }
    });

    $page.model = new model();

}(typeof window !== "undefined" ? window : this));