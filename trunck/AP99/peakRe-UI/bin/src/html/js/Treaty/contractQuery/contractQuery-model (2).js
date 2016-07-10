(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.columnData = [];

    $page.model = {
        operateType : "",
        condition: {
            PageIndex: 1,
            CountPerPage: 10
        },
        cachedCriteria:{
            countPerPage: 10,
            url: $ri.getRestfulURL("action.contract.contractHome") + "/page"
        },

        resultTable: []
    };

}(typeof window !== "undefined" ? window : this));
