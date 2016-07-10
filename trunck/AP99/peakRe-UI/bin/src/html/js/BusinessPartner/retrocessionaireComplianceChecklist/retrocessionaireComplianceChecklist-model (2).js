(function (context) {
    var $page = $pt.getService(context, '$page');

    var model = jsface.Class({
        createModel: function(){
            return {
            }
        }
    });
    $page.model = new model();

    $page.ApprovedOrDeclined = $pt.createCodeTable([
        {
            id: "1",
            text: "Approved"
        }, {
            id: "2",
            text: "Declined"
        }
    ]);


}(typeof window !== "undefined" ? window : this));
