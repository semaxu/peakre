(function (context) {
    var $page = $pt.getService(context, '$page');
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, {
        initializeData: function () {
            var urlData = $pt.getUrlData();
            var contractCode = urlData.ContractCode;
            var uwYear = urlData.UwYear;
            this.model = $pt.createModel($page.model.createBaseModel());
            var _this = this;
            var successFunc = function (data) {
                $helper.lowerKeysOfJSON(data);
                _this.initialInfo(data);
            };
            var failFunc = function () {

            };
            this.load(contractCode, uwYear, successFunc, failFunc);

            $page.controller.model.mergeCurrentModel({
                contractCode: contractCode,
                uwYear: uwYear
            });
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        load: function (contractCode, uwYear, done, fail) {
            $page.service.load(contractCode, uwYear, done, fail);
        },
        initialInfo: function (treeCodesData) {
            var contractName = "ContractName";
            var cache = $.extend({}, this.model.getCurrentModel());
            if(null != treeCodesData || undefined != treeCodesData){
            treeCodesData.forEach(function (treeCodesData) {
                cache.contractCodes.push({id: treeCodesData.id, text: treeCodesData.text});
                var sectionTree = treeCodesData.children;
                sectionTree.forEach(function (sectionTree) {
                    cache.sectionCodes.push({id: sectionTree.id, text: sectionTree.text});
                });
            });}
        },
        toForecastAndEstimation: function (level, contCompId) {
            var cache = $.extend({}, this.model.getCurrentModel());
            var contractCode = cache.contractCode;
            var uwYear = cache.uwYear;
            var url = $pt.getURL('ui.accounting.cedentQuarterView') + "?Level=" + level + "&ContCompId=" + contCompId + "&ContractCode=" + contractCode + "&UwYear=" + uwYear;
            window.location.href = url;
        }
    }));

    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));