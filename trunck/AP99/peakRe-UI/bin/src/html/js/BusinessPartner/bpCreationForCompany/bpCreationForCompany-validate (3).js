(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.validator = $pt.createModelValidator({
        BusinessPartnerCategory:{required:true},
        BusinessPartnerId:{required:true},
        TradingName:{required:true},
        Status:{required:true},
        RegistrationName:{required:true},
        ShortName:{required:true},
        Country:{required:true},
        RoleSelectIds:{required:true}
    });
}(typeof window !== "undefined" ? window : this));