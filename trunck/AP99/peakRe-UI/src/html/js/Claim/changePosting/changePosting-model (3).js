(function (context) {
    var $page = $pt.getService(context, '$page');



    var model = jsface.Class({
        condition: function(){
            return {
                SettlementNo : null,
            }
        },
        resultTable : function(){
            return {}
        }

    });
    $page.model = new model();
}(typeof window !== "undefined" ? window : this));
