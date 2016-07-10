(function (context) {
    var $page = $pt.getService(context, '$page');
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, {
        initializeData: function () {
            var urlData = $pt.getUrlData();
            var contCompId = urlData.contCompId;
            var entryCode = urlData.entryCode;
            var cedentQuarter = urlData.cedentQuarter;
            var status = urlData.status;
            this.model = $pt.createModel($page.model.createBaseModel());
            var _this = this;
            var successFunc = function (data) {
                $helper.lowerKeysOfJSON(data);
                _this.initialInfo(data);
            };
            var failFunc = function () {

            };
            this.load(contCompId, entryCode, cedentQuarter, status, successFunc, failFunc);
            $page.controller.model.mergeCurrentModel({
                item: entryCode,
                quarterSeq: cedentQuarter,
                status: status
            });
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createLayout());
            var form = <NForm model={this.model} layout={layout} view={true}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        load: function (contCompId, entryCode, cedentQuarter, status, done, fail) {
            $page.service.load(contCompId, entryCode, cedentQuarter, status, done, fail);
        },
        initialInfo: function (data) {
            var urlData = $pt.getUrlData();
            var status = urlData.status;
            if (status != 5) {
                $page.controller.model.mergeCurrentModel({
                    accountTable: data
                });
            } else {
                var mainCurrency;
                if (data.length > 0) {
                    mainCurrency = data[0].mainCurrency;
                }
                $page.controller.model.mergeCurrentModel({
                    accountTable: data,
                    mainCurrency: mainCurrency
                });
            }

        }
    }));

    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));