(function (context) {
    var $page = $pt.getService(context, '$page');

    var initial = {

        initializeData: function () {
            var urlData = $pt.getUrlData();
            this.model = $pt.createModel($page.model);
            var _this = this;
                var loadCondition = null;
                var toLoad = function (data, textStatus, jqXHR){
                    if (_this.form) {
                        _this.form.forceUpdate();
                    }
                }.bind(this);
                this.doView(loadCondition, false, false, toLoad);

        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout);
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        }
    };
    var restService = {
        doDetailView: function () {
            var afterExecute = function (data, textStatus, jqXHR) {
                console.log("BDC");
                //window.open("../fileUpload/"+rowModel.FilePath+rowModel.FileName);
               // window.open(" D:/Revaluation/REVALUATION_RECORD20160322.xls");
                 var exportUrl = "../"+data.FilePath;
                 console.log(exportUrl);
                window.open(exportUrl);

            }.bind(this);
            this.detailView(null, false, false, afterExecute);

        },
        detailView: function (criteria, quiet, async, done, fail) {
            $page.service.detailView(criteria, quiet, async, done, fail);
        },
        doView: function () {
            var _this = this;
            var afterView = function (data, textStatus, jqXHR) {
                console.log("after view");
                console.log(data);
                this.model.mergeCurrentModel({
                    queryResult: data.Rows !== undefined ? data.Rows : [],
                    cachedCriteria: {
                        pageIndex: data.PageIndex,
                        pageCount: data.PageCount,
                        countPerPage: data.CountPerPage
                    }

                });
            }.bind(this);
            this.view(null, false, false, afterView);
        },
        view: function (criteria, quiet, async, done, fail) {
            $page.service.view(criteria, quiet, async, done, fail);
        }

    };
    var forExcel = {
        exportExcel: function () {
            var excelParams = {
                refId: this.model.get("ClaimId")
            };
            var afterExport = function (data, textStatus, jqXHR) {
                window.open(data);
            }.bind(this);
            $page.service.exportService(excelParams, false, false, afterExport);

        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface,initial,restService,forExcel));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));