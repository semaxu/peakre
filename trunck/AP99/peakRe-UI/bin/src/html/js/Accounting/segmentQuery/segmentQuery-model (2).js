(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.model = {
        condition: {
            PageIndex: 1,
            CountPerPage: 10
        },
        cachedCriteria: {
            countPerPage: 10,
            url: $ri.getRestfulURL("action.accounting.ibnr") + "/page"
        },
        segmentTable: []
    };
}(typeof window !== "undefined" ? window : this));
