(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.model = {
        Quarter:null,
        ClosingDate:null,
        ClosingStatus:null,
        cachedCriteria:{

        },
        closingHistory:[

        ]
    };
}(typeof window !== "undefined" ? window : this));
