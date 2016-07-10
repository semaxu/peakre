(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.model = {
        searchResult:[
            {batchJobType:"Quarterly Closing",runID:"321",processingDate:"10/22/2015",startingTime:"9:00AM",finishTime:"9:10AM",operator:"System",batchJobStatus:"Completed"}
        ]
    };
}(typeof window !== "undefined" ? window : this));
