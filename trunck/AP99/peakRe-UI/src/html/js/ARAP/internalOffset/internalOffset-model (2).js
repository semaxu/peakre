(function (context) {
    var $page = $pt.getService(context, '$page');

    var model = jsface.Class({
        searchResult : function(){
            return {}
        },
        createModel:function(){
            return{

            }
        },
        userCodes:null
    });
    $page.model = new model();

}(typeof window !== "undefined" ? window : this));