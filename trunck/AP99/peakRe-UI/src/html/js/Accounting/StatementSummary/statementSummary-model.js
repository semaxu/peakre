(function (context) {
    var $page = $pt.getService(context, '$page');
    var model = jsface.Class({
        createTempModel: function () {
            return {
                SoaID:null
            };
        }

    });



    $page.model = new model();



}(typeof window !== "undefined" ? window : this));