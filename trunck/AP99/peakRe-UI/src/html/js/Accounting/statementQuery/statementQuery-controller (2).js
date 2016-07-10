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
        }
    };
    var restService = {
        doSearch: function () {
            var criteria = $.extend({}, this.model.getCurrentModel());
            delete criteria.queryResult;
            delete criteria.cachedCriteria;
            var _this = this;
            var afterloadSoa = function (data, textStatus, jqXHR) {
                this.model.mergeCurrentModel({
                    queryResult: data.Rows !== undefined ? data.Rows : [],
                    cachedCriteria: {
                        pageIndex: data.PageIndex,
                        pageCount: data.PageCount,
                        countPerPage: data.CountPerPage
                    }
                });
                this.model.getCurrentModel().cachedCriteria = $.extend(true,{},this.model.getCurrentModel().cachedCriteria,this.model.getCurrentModel().condition);
                if (this.form) {
                    this.form.forceUpdate();
                }
            }.bind(this);
            var searchCondition = this.model.getCurrentModel().condition;
            this.search(searchCondition, false, false, afterloadSoa);

        },
        search: function (criteria, quiet, async, done, fail) {
            $page.service.query(criteria, quiet, async, done, fail);
        },
        loadSoa: function (rowModel, type) {
            var soaId = rowModel.SoaId;
            var resource = {
                ResourceId: rowModel.SoaId,
                ResourceType: 3,
                ResourceNo: rowModel.SoaId,
                OwnerId: rowModel.TaskCreator
            };
            var statementTypeTemp = rowModel.StatementType;
            var statementType = '800004';
            if(statementTypeTemp=='2'){
                statementType = '800020';
            }else if(statementTypeTemp=='3'){
                statementType = '800021';
            }else {
                statementType = '800004';
            }
            var done =function(){
                window.location.href = "statementInput.html?soaModelType=continue&&soaId=" + soaId+"&&statementType="+ statementType;
            }.bind(this);
            this.lockInfo(resource, done);
        },
        doCancel: function (rowModel) {
            var _this = this;
            var soaId = rowModel.SoaId;
            var resource = {
                ResourceId: rowModel.SoaId,
                ResourceType: 3,
                ResourceNo: rowModel.SoaId,
                OwnerId: rowModel.TaskCreator
            };
            var done =function(){
                window.location.href = "statementView.html?soaId=" + soaId;
            }.bind(this);
            this.lockInfo(resource, done);
        },
        doView: function (rowModel) {
            var _this = this;
            var soaId = rowModel.SoaId;
            window.location.href = "statementView.html?soaId=" + soaId+"&&isView=true";
        },
        doWithdraw: function (rowModel) {
            var _this = this;
            var soaId = rowModel.SoaId;
            var resource = {
                ResourceId: rowModel.SoaId,
                ResourceType: 3,
                ResourceNo: rowModel.SoaId,
                OwnerId: rowModel.TaskCreator
            };
            var done =function(){
                window.location.href = "statementView.html?soaId=" + soaId;
            }.bind(this);
            this.lockInfo(resource, done);
        },
        loadSoaInfo: function (rowModel, type) {
            var resource = {
                ResourceId: rowModel.SoaId,
                ResourceType: 2,
                ResourceNo: rowModel.SoaId,
                OwnerId: rowModel.TaskCreator
            };

            var url = $pt.getURL('ui.claim.claimInfo');
            if (type == 5){
                var param = {
                    SoaId: rowModel.SoaId
                };
                var afterLoadXML = function (data, textStatus, jqXHR) {

                }.bind(this);
                $page.service.loadXMLService(rowModel.SoaId, false, false, afterLoadXML);

            }
        },
        reset: function(){
            this.model.set("condition_ContractCode",null);
            this.model.set("condition_ContractName",null);
            this.model.set("condition_Cedent",null);
            this.model.set("condition_Broke",null);
            this.model.set("condition_SoaId",null);
            this.model.set("condition_BizType",null);
            this.model.set("condition_CedentYear",null);
            this.model.set("condition_ReceivedDate",null);
            this.model.set("condition_TaskReleaser",null);
            this.model.set("condition_CedentQuarter",null);
            this.model.set("condition_FinancialYear",null);
            this.model.set("condition_TaskCreator",null);
            this.model.set("condition_UwYear",null);
            this.model.set("condition_FinancialQuarter",null);
            this.model.set("condition_SoaStatus",null);
            this.model.set("condition_CedentPeriod",null);
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface,initial,restService));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));