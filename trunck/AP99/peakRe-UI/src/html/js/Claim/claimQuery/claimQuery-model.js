(function (context) {
    var $page = $pt.getService(context, '$page');

    var model = jsface.Class({
        condition: function(){
            return {
                //DateOfLossFrom:$pt.newSystemDate,
                //DateOfLossTo:$pt.newSystemDate,
                //TaskOwner:Cookies.get($page.constants.USER_COOKIE_NAME),
                ClaimBranch:'1',
                PageIndex: 1,
                CountPerPage: 10

            }
        },
        searchResult : function(){
            return {}
        },
        cachedCriteria : function(){
            return {
                countPerPage: 10,
                url: $ri.getRestfulURL("action.claim.query")+ "/claimquery"
            }
        },
        lastCriteria: function(){

        },
        createModel:function(){
            return{
                condition:this.condition(),
                cachedCriteria:this.cachedCriteria(),
                lastCriteria:this.lastCriteria(),

            }
        },
        eventCodes : null,
        userCodes:null,
    });
    $page.model = new model();

    }(typeof window !== "undefined" ? window : this));
