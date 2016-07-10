(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.model = {

    };

    $page.model.section = {
        epiSection:[
            {
                sectionName:"Section 1"
            },{
                sectionName:"Section 2"
            }
        ]
    };

    $page.model.all = $.extend(false, {}, $page.model.section);


}(typeof window !== "undefined" ? window : this));
