(function (context) {
    var $page = $pt.getService(context, '$page');
    var model = jsface.Class({
        createModel: function () {
            return {
                ExceptionContractList: [],
                //condition: {
                //    CountPerPage: 10,
                //    PageIndex: 1
                //},
               // cachedCriteria:{},
            };
        },
        createexceptionModel: function(){
            return{

            }
        }
    });
    $page.model = new model();
}(typeof window !== "undefined" ? window : this));
