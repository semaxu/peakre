(function (context) {
    var $page = $pt.getService(context, '$page');

    var model = jsface.Class({
        createBaseModel: function () {
            return {
                ContCompId: null
            };
        }
    });

    $page.model = new model();

}(typeof window !== "undefined" ? window : this));