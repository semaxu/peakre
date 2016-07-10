(function (context) {
    var $page = $pt.getService(context, '$page');

    var model = jsface.Class({
        createModel: function(){
            return {

            }
        }

    });
    $page.model = new model();

}(typeof window !== "undefined" ? window : this));
