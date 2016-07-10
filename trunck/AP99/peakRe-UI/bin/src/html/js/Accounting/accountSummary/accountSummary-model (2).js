(function (context) {
    var $page = $pt.getService(context, '$page');

    var model = jsface.Class({
        createBaseModel: function () {
            return {
                contractCode: null,
                uwYear: null,
                contractCodes: [],
                sectionCodes: []
            };
        }
    });

    $page.model = new model();

}(typeof window !== "undefined" ? window : this));
