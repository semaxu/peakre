(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.model = {
        supiSection:[
            {
                sectionName:"Section 1"
            },{
                sectionName:"Section 2"
            }
        ]
    };

    //extends model
    //$page.model.supiSection = {
    //    supiSection:[
    //        {
    //            sectionName:"Section 1"
    //
    //        },{
    //            sectionName:"Section 2"
    //        }
    //    ]
    //};
    //
    //$page.model.all = $.extend(false, {}, $page.model.supiSection);

}(typeof window !== "undefined" ? window : this));
