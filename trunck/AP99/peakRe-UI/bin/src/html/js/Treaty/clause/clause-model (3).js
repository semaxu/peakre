/**
 * Created by gang.wang on 10/20/2015.
 */
(function(context){
    var $page = $pt.getService(context,"$page");
    $page.clauseModel = {
        clauses:[
            {
                id:"1",
                text:"Clause 01",
                children:[
                    {
                        id:"11",
                        text:"Clause 001"
                    },{
                        id:"12",
                        text:"Clause 002"
                    },{
                        id:"13",
                        text:"Clause 003"
                    }
                ]
            },
            {id:"2", text:"Clause 02"},
            {id:"3", text:"Clause 03"},
            {id:"4", text:"Clause 04"}
        ]
    };
    $page.requiredClauseModel = $pt.createModel($.extend(true, {}, $page.clauseModel));
    $page.recommendClauseModel = $pt.createModel($.extend(true, {}, $page.clauseModel));
    $page.countryDialogLayout = {
        clauses: {
            comp: {
                type: $pt.ComponentConstants.Tree,
                check: "selected",
                hierarchyCheck: true,
                inactiveSlibing: false,
                root: false
            }
        }
    };
    $page.model={
    };
}(typeof window !== "undefined" ? window : this));
