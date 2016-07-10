(function (context) {
    var $page = $pt.getService(context, '$page');


    var model = jsface.Class({
        createTempModel: function () {
            return {
                ContractCode: "1",
                FinancialYear: null,
                Cedent: null,
                SoaId: null,
                UwYear: null,
                Broke: null,
                ReceivedDate: null,
                DueDate: null,
                AccountLevel: "1",
                BizType: null,
                CedentYear: null,
                CedentQuarter: null,
                SoaText:"soaT",
                TaskcreatorName:null,
                TaskreleaserName:null,
                TakswithdrawName:null,
                Remarks:"test",
               // ReviewModel:this.crateReviewedBy(),
                ReviewedBy:"reviewTest",
                ReviewedFlag:"true"
            };
        }

    });



    $page.model = new model();

}(typeof window !== "undefined" ? window : this));