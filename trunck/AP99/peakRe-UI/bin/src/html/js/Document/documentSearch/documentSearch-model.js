(function (context) {
    var $page = $pt.getService(context, '$page');

    var model = jsface.Class({
        createModel: function(){
            return {
                condition: this.condition(),
                searchResult:this.searchResult(),
                cachedCriteria:this.cachedCriteria(),
                lastCriteria:this.lastCriteria()
            }
        },
        condition: function(){
            return {
                CountPerPage: 10,
                PageIndex: 1
            }
        },
        searchResult : function(){
            return {}
        },
        cachedCriteria : function(){
            return {
                countPerPage: 10,
                url: $ri.getRestfulURL("action.public.document")+ "/queryDocument"
            }
        },
        lastCriteria: function(){

        }
    });
    $page.model = new model();
}(typeof window !== "undefined" ? window : this));
