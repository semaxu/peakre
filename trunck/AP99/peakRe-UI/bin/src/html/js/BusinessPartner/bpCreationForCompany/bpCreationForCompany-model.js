(function (context) {
    var $page = $pt.getService(context, '$page');

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
    $page.DebitOrCredit = $pt.createCodeTable([
        {
            id: "1",
            text: "Debit"
        }, {
            id: "2",
            text: "Credit"
        }, {
            id: "3",
            text: "Debit/Credit"
        }
    ]);
    $page.partnerRole = $pt.createCodeTable([
        {id:"1",text:"Cedent"},
        {id:"2",text:"Broker"},
        {id:"3", text:"Insured"},
        {id:"4", text:"Retrocessionaire"},
        {id:"5", text:"MGA(Managing General Agent)"}
    ]);


    var model = jsface.Class({
        createModel: function(){
            return {

                BusinessPartnerCategory:"2"
            }
        },
        getRelationshipTable: function () {
            return {
                RelationshipType: null,
                RelatedPartnerId: null
            };
        },
        getBankAccountTable: function () {
            return {
                AccountHolderType: null,
                AccountHolderName: null,
                BankCode: null,
                AccountNumber: null,
                DebitCredit: null,
                AccountStatus:'1'
            };
        },
        getContactTable:function(){
            return{
                ContactPerson: null,
                ContactTitle: null,
                EmailAddress: null,
                TelephoneNumber:null,
                Remark:null
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
