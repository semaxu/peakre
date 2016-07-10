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
            this.model = $pt.createModel($page.model.createTempModel());
            var urlData = $pt.getUrlData();
            var soaId = urlData.soaId;
            $page.isView =  urlData.isView;
            var _this = this;
            var toSummary = function (data) {
                        data.forEach(function (data) {
                            if (data.SectionCount =='NO') {
                                _this.model.mergeCurrentModel(data.SoaM);
                                _this.model.mergeCurrentModel(data);
                            }else{
                                _this.tableColumnCount = data.SectionCount;
                                _this.model.add('Currency', {
                                    ContractCurrecy: data.ContractCurrecy,
                                    queryResult: data.Rows !== undefined ? data.Rows : []
                                });
                                _this.model.mergeCurrentModel(data.SoaM);
                                _this.model.mergeCurrentModel(data);
                            }
                        });
                if(_this.form){
                    _this.form.forceUpdate();
                }
            }.bind(this);
            var summaryCondition ={SoaIdRead:  soaId};
            this.load(summaryCondition, false, false, toSummary);
        },
        renderContent: function () {
            console.log(this.tableColumnCount);
            this.layout = $page.layoutHelper.createFormLayout(this.tableColumnCount);
            var form = <NForm model={this.model} layout={this.layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },

        load: function (criteria, quiet, async, done, fail) {
            $page.service.load(criteria, quiet, async, done, fail);
        },
        doSaveSoaView: function (criteria, quiet, async, done, fail) {
            $page.service.saveSoaView(criteria, quiet, async, done, fail);
            NConfirm.getConfirmModal().show({
                title: 'Message',
                disableClose: true,
                messages: ['Successful']
            });

        },
        doCancel: function (criteria) {
            var cancelRow = function (item) {
                if (true) {
                    var toCancel = function () {
                        window.location.href = "statementQuery.html";
                    }.bind(this);

                    this.cancel(criteria, false, false, toCancel);
                }
                $pt.Components.NConfirm.getConfirmModal().hide();
            };
            $pt.Components.NConfirm.getConfirmModal().show(NTable.REMOVE_CONFIRM_TITLE, "Are you sure to cancel the statement ?", cancelRow.bind(this, null));
        },
        cancel: function (criteria, quiet, async, done, fail) {
            $page.service.cancel(criteria, quiet, async, done, fail);
        },
        doWithdraw: function (criteria) {
            var beforeWithdraw = function (data, textStatus, jqXHR) {
                if(!$page.controller.WithdrawOrderCheck(criteria)){
                    return;
                }
                if(data.IsOverCutOffDate=='overDate'){
                    var withdrawRow1 = function (item) {
                        //if (true) {
                        var toWithdraw1 = function () {
                            window.location.href = "statementQuery.html";
                        }.bind(this);

                        this.withdraw(criteria, false, false, toWithdraw1);
                        //}
                        $pt.Components.NConfirm.getConfirmModal().hide();
                    };
                    $pt.Components.NConfirm.getConfirmModal().show(NTable.REMOVE_CONFIRM_TITLE, "The current financial quarter is closing, transaction will not be posted until opening of next financial quarter, are you sure to withdrawal ?", withdrawRow1.bind(this, null));

                }else{
                    var withdrawRow = function (item) {
                        //if (true) {
                        var toWithdraw = function () {
                            window.location.href = "statementQuery.html";
                        }.bind(this);

                        this.withdraw(criteria, false, false, toWithdraw);
                        //}
                        $pt.Components.NConfirm.getConfirmModal().hide();
                    };
                    $pt.Components.NConfirm.getConfirmModal().show(NTable.REMOVE_CONFIRM_TITLE, "Are you sure to withdraw the statement ?", withdrawRow.bind(this, null));

                }
            }.bind(this);
            this.toBeforeWithdraw(null, false, false, beforeWithdraw);
        },
        toBeforeWithdraw: function (updateData, quiet, async, done, fail) {
            $page.service.doBeforeReversal(updateData, quiet, async, done, fail);
        },
        withdraw: function (criteria, quiet, async, done, fail) {
            $page.service.withdraw(criteria, quiet, async, done, fail);
        },
        doIgnoringWithdraw: function (criteria) {
            if(!$page.controller.WithdrawOrderCheck(criteria)){
                return;
            }
            var withdrawRow = function (item) {
                //if (true) {
                var toWithdraw = function () {
                    window.location.href = "statementQuery.html";
                }.bind(this);

                this.ignoringWithdraw(criteria, false, false, toWithdraw);
                //}
                $pt.Components.NConfirm.getConfirmModal().hide();
            };
            $pt.Components.NConfirm.getConfirmModal().show(NTable.REMOVE_CONFIRM_TITLE, "Are you sure to withdraw the statement ?", withdrawRow.bind(this, null));

        },
        ignoringWithdraw: function (criteria, quiet, async, done, fail) {
            $page.service.ignoringWithdraw(criteria, quiet, async, done, fail);
        },
        exit: function () {
            var _this = this;
            if($page.isView){
                window.location.href = "statementQuery.html";
            }else{
                var soaId =_this.model.getCurrentModel().SoaId;
                var resource = {
                    ResourceId: _this.model.getCurrentModel().SoaId,
                    ResourceType: 3,
                    ResourceNo:  _this.model.getCurrentModel().SoaId,
                    OwnerId:  _this.model.getCurrentModel().TaskCreator
                };
                var done = function () {
                    window.location.href = "statementQuery.html";
                }.bind(this);
                this.unlock(resource, done);
            }
        },
        WithdrawOrderCheck: function (model) {
            var flag = true;
            var AfterGetWithdrawOrderCheckResult = function (data, textStatus, jqXHR) {
                if(!data){
                    flag = false;
                    NConfirm.getConfirmModal().show({
                        title: 'System Message',
                        disableClose: true,
                        messages: "Please withdrawal the last statement first"
                    });
                }

            }.bind(this);
            $page.service.WithdrawOrderCheck(this.model.getCurrentModel(), false, false, AfterGetWithdrawOrderCheckResult);

            return flag;
        }

    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));