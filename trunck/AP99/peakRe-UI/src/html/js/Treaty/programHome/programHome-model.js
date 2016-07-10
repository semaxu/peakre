(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.linkedProgramModel = $pt.createModel({
        programTree : [
            {
                id : 1,
                text : "Program 1 Name",
                selected : true,
                children : [
                    {
                        id : 2,
                        text : "Section 1 Name",
                        children : [
                            {
                                id : 3,
                                text : "Sub-section 1 Name"
                            }, {
                                id : 4,
                                text : "Sub-section 2 Name"
                            }
                        ]
                    }, {
                        id : 5,
                        "text": "Section 2 Name"
                    }, {
                        id : 6,
                        "text": "Section 3 Name"
                    }
                ]
            }, {
                id: 7,
                text : "Program 2 Name"
            }, {
                id : 8,
                text : "Program 3 Name"
            }
        ]
    });

    $page.classModel = $pt.createModel({
        classes: [
            {
                id: "1",
                text: "Property"
            }, {
                id: "2",
                text: "Engineering"
            }, {
                id: "3",
                text: "Casualty"
            }, {
                id: "4",
                text: "Motor"
            }, {
                id: "5",
                text: "Marine"
            }, {
                id: "6",
                text: "Credit and Bonds"
            }, {
                id: "7",
                text: "Aviation/Space"
            }, {
                id: "8",
                text: "Agriculture"
            }, {
                id: "9",
                text: "Financial Lines"
            }, {
                id: "10",
                text: "Miscellaneous Accident"
            }
        ]
    });

    $page.subClassModel = $pt.createModel({
        subClasses: [
            {
                id: "1",
                text: "Fire, FLEXA"
            }, {
                id: "2",
                text: "Property All Risks"
            }, {
                id: "3",
                text: "Business Interruption"
            }, {
                id: "4",
                text: "Natural Catastrophe"
            }, {
                id: "5",
                text: "Contractors'/Erection All Risks"
            }, {
                id: "6",
                text: "General Third Party Liability"
            }, {
                id: "7",
                text: "Products Liability"
            }, {
                id: "8",
                text: "Workmen's Compensation/Employers' Liability"
            }
        ]
    });

    $page.currencyModel = $pt.createModel({
        currencys: [
            {
                id: "1",
                text: "USD"
            }, {
                id: "2",
                text: "HKD"
            }
        ]
    });

    $page.uwAreaModel = $pt.createModel({
        uwAreas: [
            {
                id: "1",
                text: "Asia",
                children: [
                    {
                        id: "11",
                        text: "China"
                    }, {
                        id: "12",
                        text: "HongKong"
                    }, {
                        id: "13",
                        text: "Korea"
                    }
                ]
            },
            {id: "2", text: "America"},
            {id: "3", text: "Europ"},
            {id: "4", text: "Africa"}
        ]
    });

    $page.coveredAreaModel = $pt.createModel({
        coveredAreas: [
            {
                id: "1",
                text: "WorldWide"
            }, {
                id: "2",
                text: "National"
            }
        ]
    });

    $page.countryModel = {
      countries:[
        {
          id:"1",
          text:"Asia",
          children:[
            {
              id:"11",
              text:"China"
            },{
              id:"12",
              text:"HongKong"
            },{
              id:"13",
              text:"Korea"
            }
          ]
        },
        {id:"2", text:"America"},
        {id:"3", text:"Europ"},
        {id:"4", text:"Africa"}
      ]
    };

    $page.uwCountryModel = $pt.createModel($.extend(true, {}, $page.countryModel));
    $page.coveredCountryModel = $pt.createModel($.extend(true, {}, $page.countryModel));
    $page.perilCountryModel = $pt.createModel($.extend(true, {}, $page.countryModel));

    $page.model = {
        programNature : null,
        area:"Area : ",
        class: "Class : ",
        subClass: "Sub-class : ",
        currency: "Currency : ",
        uwArea: "UW Area : ",
        coveredArea: "Covered Area : ",
        sectionTable : [
            {sectionNo: "1", sectionType: "QS", sectionName: "Treaty QS 2015", mainCurrency: "2", ourShare: "20%", premium: "200000.00", deductions: "5000.00"},
            {sectionNo: "2", sectionType: "Surplus", sectionName: "Treaty surplus 2015", mainCurrency: "1", ourShare: "20%", premium: "500000.00", deductions: "6000.00"}
        ],
        logTable : [
            {programStatus: "Signed", updateTime: "2015-11-11", updateBy: "Faker", remarks: ""},
            {programStatus: "UWR Review", updateTime: "2015-11-11", updateBy: "Faker", remarks: "from Signed to UWR Review"},
        ],
        claimLimitDataUI:[
            {cateId:1,percentage:0,amount:0},
            {cateId:2,percentage:0,amount:0},
            {cateId:3,percentage:0,amount:0},
            {cateId:4,percentage:0,amount:0},
            {cateId:5,percentage:0,amount:0},
            {cateId:6,percentage:0,amount:0},
            {cateId:7,percentage:0,amount:0}
        ]
    };

}(typeof window !== "undefined" ? window : this));
