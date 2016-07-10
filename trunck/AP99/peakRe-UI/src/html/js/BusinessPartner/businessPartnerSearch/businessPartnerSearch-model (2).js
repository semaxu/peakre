(function (context) {
    var $page = $pt.getService(context, '$page');


    $page.partnerRole = $pt.createCodeTable([
        {id:"1",text:"Cedent"},
        {id:"2",text:"Broker"},
        {id:"3", text:"Insured"},
        {id:"4", text:"Retrocessionaire"},
        {id:"5", text:"MGA(Managing General Agent)"}
    ]);
    $page.BusinessPartnerCategory = $pt.createCodeTable([
        {
            id: "1",
            text: "Individual"
        }, {
            id: "2",
            text: "Organization"
        }
    ]);
    $page.Status = $pt.createCodeTable([
        {
            id: "1",
            text: "Active"
        }, {
            id: "2",
            text: "Inactive"
        }
    ]);
    var model = jsface.Class({
        createModel: function(){
            return {
                condition: {
                    CountPerPage: 10,
                    PageIndex: 1
                },
                results: [],
                lastCriteria: {}
            }
        }

    });
    $page.model = new model();


}(typeof window !== "undefined" ? window : this));
