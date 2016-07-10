(function (context) {
    var $page = $pt.getService(context, '$page');

    var model = jsface.Class({
        createBaseModel: function () {
            return {
                tableColumns: [
                    //{text: "2015Q4", value: "cedentQ1"}
                ],
                contractTable:[

                ],
                sectionTables:[
                    //{
                    //    sectionName:"Protoss",
                    //    sectionId: null,
                    //    sectionTable:[
                    //        {item:"1000",s2015Q1:"2500",s2015Q2:"2500",s2015Q3:"",s2015Q4:"",grandTotal:"5000"},
                    //        {item:"2000",s2015Q1:"(300)",s2015Q2:"(100)",s2015Q3:"",s2015Q4:"",grandTotal:"(400)"},
                    //        {item:"2090",s2015Q1:"(100)",s2015Q2:"(50)",s2015Q3:"",s2015Q4:"",grandTotal:"(150)"},
                    //        {item:"2071",s2015Q1:"(200)",s2015Q2:"(200)",s2015Q3:"",s2015Q4:"",grandTotal:"(400)"},
                    //        {item:"4122",s2015Q1:"(200)",s2015Q2:"(200)",s2015Q3:"",s2015Q4:"",grandTotal:"(400)"},
                    //        {item:"4112",s2015Q1:"(200)",s2015Q2:"(200)",s2015Q3:"",s2015Q4:"",grandTotal:"(400)"},
                    //        {item:"4116",s2015Q1:"(200)",s2015Q2:"(200)",s2015Q3:"",s2015Q4:"",grandTotal:"(400)"}
                    //    ]
                    //}
                ]
            };
        }
    });

    $page.model = new model();

}(typeof window !== "undefined" ? window : this));
