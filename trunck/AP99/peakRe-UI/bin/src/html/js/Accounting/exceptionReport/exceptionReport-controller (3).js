(function (context) {
    var $page = $pt.getService(context, '$page');

    var initial = {

        initializeData: function () {
            this.model = $pt.createModel($page.model.createModel());
            this.codes = $pt.createModel($page.codes);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout);
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
    };
    var restService = {
        doExecute: function () {
            var afterExecute = function (data, textStatus, jqXHR) {

            }.bind(this);
            this.execute(null, false, false, afterExecute);

        },
        execute: function (criteria, quiet, async, done, fail) {
            $page.service.execute(criteria, quiet, async, done, fail);
        },

        doSearch: function () {
            var _this = this;
            var searchCondition = this.model.getCurrentModel();
            var afterSearch = function (data, textStatus, jqXHR) {
                _this.model.mergeCurrentModel({
                    queryResult: data.Rows !== undefined ? data.Rows : [],
                    cachedCriteria: {
                        pageIndex: data.PageIndex,
                        pageCount: data.PageCount,
                        countPerPage: data.CountPerPage
                    }
                });

                if (_this.form) {
                    this.form.forceUpdate();
                }
            }.bind(this);

            if (searchCondition==undefined||searchCondition.financialYear==undefined||searchCondition.financialYear == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Financial Year']
                });
                return;
            }
            if (searchCondition==undefined||searchCondition.financialQuarter==undefined||searchCondition.financialQuarter == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Financial Quarter ']
                });
                return;
            }
            $page.service.search(searchCondition, false, false, afterSearch);
        },

        doView: function (rowModel) {
            var _this = this;
            var FNYear = this.model.getCurrentModel().financialYear;
            var FNQuarter = this.model.getCurrentModel().financialQuarter;
            window.location.href = "exceptionReportView.html?FNYear=" + FNYear+"&&FNQuarter="+FNQuarter;
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface,initial,restService));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));