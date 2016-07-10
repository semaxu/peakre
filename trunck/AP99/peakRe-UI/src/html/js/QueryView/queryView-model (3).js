(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.linkedContractModel = $pt.createModel({

    });
    var model = jsface.Class({
          createModel: function (){
              return {
                      contractCodes: [
                      ],
                      sectionCodes: [
                      ],
                  tableColumns: [
                      //{text: "2015Q4", value: "cedentQ1"}
                  ],
                  contractTable:[

                  ],
                  sectionTables:[

                  ]
              }
          }
        }
    )
    //$page.model = {
    //    contractType:1,
    //    contractNature : 1,
    //    contractCategory:1,
    //    fronting:'N',
    //    depositAccounting:'N',
    //    accountingBasis:1,
    //    earningPattern:1,
    //    EntryItems : [
    //
    //    ],
    //    statementTable: [
    //      ],
    //    claimTable:[
    //    ],
    //    sectionTable : [
    //      ],
    //    logTable : [
    //       // {contractStatus: "Contract Input", updateTime: "2015-12-12", updateBy: "Elvira", remarks: ""},
    //       // {contractStatus: "UWR Review", updateTime: "2015-12-12", updateBy: "Elvira", remarks: "from Contract Input to UWR Review"},
    //    ],
    //    EndorsementList:[
    //        //{endorsementID:"10091",creationDate:"01/01/2016",effectiveDate:"02/01/2016",createdBy:"UW001",description:"AAA"}
    //    ],

    //};
    $page.model = new model();
}(typeof window !== "undefined" ? window : this));
