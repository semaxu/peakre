(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.validator = $pt.createModelValidator({
        BusinessPartnerCategory:{required:true},
        BusinessPartnerId:{required:true},
        Status:{required:true},
        Country:{required:true}
    });
}(typeof window !== "undefined" ? window : this));