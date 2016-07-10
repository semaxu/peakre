(function (context) {
    var $page = $pt.getService(context, '$page');
    var model = jsface.Class({
        createModel: function(){
            return {
                DateOfLossFrom:$pt.newDate,
                DateOfLossTo:$pt.newDate,
                DateOfCreation:$pt.newSystemDate
            }
        }
    });
    $page.model = new model();
}(typeof window !== "undefined" ? window : this));
