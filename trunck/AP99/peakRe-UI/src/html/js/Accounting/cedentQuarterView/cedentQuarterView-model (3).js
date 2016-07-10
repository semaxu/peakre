(function (context) {
    var $page = $pt.getService(context, '$page');

    var model = jsface.Class({
        createBaseModel: function () {
            return {
                contCompId: null,
                level: null,
                currency: "DEFAULT",
                currentFNQuarter: null,
                adjusting: "",
                date: null,
                cleanCut: false,
                quarterTree: null,
                currencies: [],
                tableColumns: [
                    //{text: "2015Q4", value: "cedentQ1"}
                ],
                forecastTable: [],
                estimationTable: [],
                reversalTable: [],
                actualTable: []
            };
        }
    });

    $page.model = new model();

}(typeof window !== "undefined" ? window : this));
