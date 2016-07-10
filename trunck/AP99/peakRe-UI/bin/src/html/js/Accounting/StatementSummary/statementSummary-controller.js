/**
 * Create By Elvira.Du 10/15/2015
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var me = {
        initializeErrorModel: function () {
            return true;
        },
        initializeData: function () {
            this.model = $pt.createModel($page.model);
            var urlData = $pt.getUrlData();
            var soaId = urlData.soaId;
            this.model.set("SoaID", soaId);
            var _this = this;
            var toSummary = function (data, textStatus, jqXHR) {
                data.forEach(function (data) {
                    _this.tableColumnCount = data.SectionCount;//data.columnCount;
                    _this.model.add('Currency', {
                        ContractCurrecy: data.ContractCurrecy,
                        queryResult: data.Rows !== undefined ? data.Rows : []
                    });
                });
            }.bind(this);
            var summaryCondition ={SoaIdRead:  soaId};
            this.loadSummary(summaryCondition, false, false, toSummary);
        },
        renderContent: function () {
            this.layout = $page.layoutHelper.createFormLayout(this.tableColumnCount);
            var form = <NForm model={this.model} layout={this.layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        loadSummary: function (criteria, quiet, async, done, fail) {
            $page.service.loadSummary(criteria, quiet, async, done, fail);
        },
        exportSummary: function (criteria, quiet, async, done, fail) {
            $page.service.loadSummary(criteria, quiet, async, done, fail);
        }

    };
    var forExcel = {
        exportExcel: function () {
            var excelParams = {
                refId: $page.controller.model.get('SoaID')
            };
            $pt.generateFile(19,excelParams);
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me,forExcel));
    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));