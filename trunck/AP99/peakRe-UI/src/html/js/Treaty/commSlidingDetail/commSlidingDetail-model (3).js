/**
 * Created by gang.wang on 10/14/2015.
 */
(function(context) {
    var $page = $pt.getService(context, '$page');
    $page.model = {
        LossIncludeIBNR:0,
        DeductionsCarried: [
            {Name: "Profit",Yn:"0"},
            {Name: "Loss",Yn:"0"}
        ]

    };
}(typeof window !== "undefined" ? window : this));
