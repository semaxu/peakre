(function (context) {
    var $page = $pt.getService(context, '$page');

    var model = jsface.Class({
        createBaseModel: function () {
            return {
                item: null,
                quarterSeq: null,
                status: null,
                mainCurrency: null,
                accountTable: []
            };
        }
    });

    $page.model = new model();

}(typeof window !== "undefined" ? window : this));

