(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.model = {
    		Condition: {
                PageIndex: 1,
                CountPerPage: 10
            },
            cachedCriteria:{
            	countPerPage: 10,
                url:$ri.getRestfulURL("action.arap.transactionQuery.queryTransactionQuery")
            },
            SearchResult : [

            ]
        };
}(typeof window !== "undefined" ? window : this));
