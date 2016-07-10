(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.classModel = $pt.createModel({
        classes : [
            {
                id:"1",
                text:"Property"
            }, {
                id:"2",
                text:"Motor"
            }, {
                id:"3",
                text:"Marine"
            }, {
                id:"4",
                text:"Aviation/Space"
            }, {
                id:"5",
                text:"Financial Lines"
            }
        ]
    });

    $page.subclassModel = $pt.createModel({
        subclasses : [
            {
                id:"1",
                text:"Property All Risks"
            }, {
                id:"2",
                text:"Natural"
            }, {
                id:"3",
                text:"General Third Party"
            }, {
                id:"4",
                text:"Products Liability"
            }, {
                id:"5",
                text:"Workmen's"
            }
        ]
    });

    $page.currencyModel = $pt.createModel({
        currency : [
            {
                id:"1",
                text:"Property All Risks"
            }, {
                id:"2",
                text:"Natural"
            }, {
                id:"3",
                text:"General Third Party"
            }, {
                id:"4",
                text:"Products Liability"
            }, {
                id:"5",
                text:"Workmen's"
            }
        ]
    });

    $page.countryModel = $pt.createModel({
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
            }, {
                id:"2",
                text:"America"
            }, {
                id:"3",
                text:"Europ"
            }, {
                id:"4",
                text:"Africa"
            }
        ]
    });

    $page.coveredAreaModel = $pt.createModel({
        coveredArea:[
            {
                id:"1",
                text:"Asia",
                children:[
                    {
                        id:"11",
                        text:"China"
                    },{
                        id:"12",
                        text:"Japan"
                    },{
                        id:"13",
                        text:"Korea"
                    }
                ]
            }, {
                id:"2",
                text:"America"
            }, {
                id:"3",
                text:"Europ"
            }, {
                id:"4",
                text:"Africa"
            }
        ]
    });

    $page.model = {
        sectionTable : [
            {sectionNo: "1", sectionType: "QS", sectionName: "Treaty QS 2015", mainCurrency: "2", ourShare: "20%", premium: "200000.00", deductions: "5000.00"},
            {sectionNo: "2", sectionType: "Surplus", sectionName: "Treaty surplus 2015", mainCurrency: "1", ourShare: "20%", premium: "500000.00", deductions: "6000.00"}
        ],
        logTable : [
            {contractStatus: "Signed", updateTime: "2015-11-11", updateBy: "Faker", remarks: ""},
            {contractStatus: "UWR Review", updateTime: "2015-11-11", updateBy: "Faker", remarks: "from Signed to UWR Review"},
        ]
    };

}(typeof window !== "undefined" ? window : this));
