(function (context) {
    var $page = $pt.getService(context, '$page');
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, {
        initializeData: function () {
            var urlData = $pt.getUrlData();
            var contCompId = urlData.ContCompId;
            this.model = $pt.createModel($page.model.createBaseModel());

            var _this = this;
            var successFunc = function (data) {
                $helper.lowerKeysOfJSON(data);
                _this.initialInfo(data);
            };
            var failFunc = function () {

            };
            this.load(contCompId, successFunc, failFunc);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        load: function (level, conCompId, date, done, fail) {
            $page.service.load(level, conCompId, date, done, fail);
        },
        initialInfo: function (data) {
            var cache = $.extend({}, this.model.getCurrentModel());
            console.log(data);
            $page.controller.model.mergeCurrentModel({
                tableColumns: data.tableColumns,
                contractTable: data.contractTable,
                sectionTables: data.sectionTables
            });
        },
        showTransactionDetail: function (entryCode, cedentQuarter) {
            var url = $pt.getURL('ui.accounting.fnTransactionDetail') + "?contCompId=" + this.model.getCurrentModel().contCompId + "&entryCode=" + entryCode + "&cedentQuarter=" + cedentQuarter;
            window.open(url);
        }
    }));

    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));