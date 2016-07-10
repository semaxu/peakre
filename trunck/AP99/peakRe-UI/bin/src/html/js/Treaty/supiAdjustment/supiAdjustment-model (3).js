(function (context) {
    var $page = $pt.getService(context, '$page');

    var model = jsface.Class({
        createBaseModel: function () {
            return {
                ContractCode: null,
                UwYear: null,
                ContractCategory: null,
                Broker: null,
                CoBroker: null,
                Mga: null,
                SupiSecList:[]
            };
        },
        getAdjustedSUPITemplate: function () {
            return {
                AdjustmentDate: moment().format('YYYY-MM-DDTHH:mm:ss'),
                UserBy: null,
                Currency: null,
                Amount: null
            }
        }
    });

    $page.model = new model();

}(typeof window !== "undefined" ? window : this));
