(function (context) {
    var $page = $pt.getService(context, "$page");
    $page.isSearched = false;
    var initOperation = function (column) {
        column.push(
            {
                tooltip: "view",
                click: function (rowModel) {
                    var url =  $pt.getURL("ui.contract.contractView") +"&ContCompId=" + rowModel.ContCompId;
                    window.location.href = url;
                }
            }, {
                tooltip: "edit",
                enabled: {
                    when: function (model) {
                        return model.get('LatestStatus') != '2' && model.get('LatestStatus') != '4' && model.get('LatestStatus') != '5';
                    },
                    depends: 'LatestStatus'
                },
                click: function (rowModel) {
                    var operateType = null;
                    var url = "contractHome.html?ContCompId=" + rowModel.ContCompId;
                    if (rowModel.LatestStatus == '1') {
                        operateType = "1";
                    } else if (rowModel.LatestStatus == '3') {
                        operateType = "3";
                    }
                    url += "&OperateType=" + operateType;

                    // access validate & lock contract
                    var resource = {
                        ResourceId: rowModel.ContCompId,
                        ResourceType: 1,
                        ResourceNo: rowModel.ContractCode,
                        OwnerId: rowModel.LastChanged
                    };
                    console.log('weiping');
                    console.log(resource);
                    var done =function(){
                        window.location.href = url;
                    }.bind(this);
                    $page.controller.lockInfo(resource, done);
                }
            }, {
                tooltip: "review",
                enabled: {
                    when: function (model) {
                        return model.get('LatestStatus') == '2';
                    },
                    depends: 'LatestStatus'
                },
                click: function (rowModel) {
                    var url = "contractHome.html?OperateType=2&ContCompId=" + rowModel.ContCompId;
                    window.location.href = url;
                }
            }, {
                tooltip: "copy",
                click: function (rowModel) {
                    if ($page.controller.copyContract(rowModel.ContCompId)) {
                        var url = "contractHome.html?OperateType=1&ContCompId=" + this.getModel().get("ContCompId");
                        window.location.href = url;
                    }
                }
            }, {
                tooltip: "renew",
                enabled: {
                    when: function (model) {
                        return model.get('LatestStatus') == '4' && !$page.controller.getRenewalStatus(model.get('ContCompId'));
                    }
                },
                click: function (rowModel) {
                    if ($page.controller.renewContract(rowModel.ContCompId)) {
                        var url = "contractHome.html?OperateType=1&ContCompId=" + this.getModel().get("ContCompId");
                        window.location.href = url;
                    }
                }
            }, {
                tooltip: "endorsement",
                click: function (rowModel) {
                    var url = "endorsementLog.html?ContCompId=" + rowModel.ContCompId + "&status=" + rowModel.LatestStatus;
                    window.location.href = url;
                }
            }, {
                tooltip: "termination",
                enabled: {
                    when: function (model) {
                        return model.get('LatestStatus') == '4' && !$page.controller.getRenewalStatus(model.get('ContCompId'));
                    }
                },
                click: function (rowModel) {
                    var url = "termination.html?ContCompId=" + rowModel.ContCompId;
                    window.location.href = url
                }
            }
        );
    };
    var initEPIOperation = function (column) {
        column.push(
            {
                tooltip: "SUPI Adjustment",
                enabled: {
                    when: function (model) {
                        return model.get("ContractNature") == 2 && (model.get("LatestStatus") == 4 || model.get("LatestStatus") == 5);
                    }
                },
                click: function (rowModel) {
                    var url = "supiAdjustment.html?OperateType=4&ContCompId=" + rowModel.ContCompId;
                    window.location.href = url;
                }
            }
        );
    };
    var initPricingOperation = function (column) {
        column.push(
            {
                tooltip: "Pricing Estimate",
                enabled: {
                    when: function (model) {
                        return model.get("LatestStatus") == 4 || model.get("LatestStatus") == 5;
                    }
                },
                click: function (rowModel) {
                    var url = "contractHome.html?OperateType=5&ContCompId=" + rowModel.ContCompId;
                    window.location.href = url;
                }
            }
        );
    }
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, {
        initializePageType: function () {
            var type = $pt.getUrlData();
            this.isManageClient = type && type.type == 'manage';
        },
        initializeErrorModel: function () {
            //this.errorModel = $pt.createModel($page.model.error);
            return true;
        },
        initializeData: function () {
            var urlData = $pt.getUrlData();
            var operateType = urlData.OperateType;
            this.model = $pt.createModel($page.model);
            if (operateType && operateType == "4") {
                this.model.set("condition_ContractNature", "2");
            }else if(operateType && operateType == "5"){
                this.model.set("condition_LatestStatus", "4");
            }
            var _self = this;
            var url = this.model.getCurrentModel().cachedCriteria.url;
            var searchModel = this.model.getCurrentModel().condition;
            this.search(url, searchModel, false, false, function (data, textStatus, jqXHR) {
                _self.updateSearchResult(data, false);

            }, null);
            this.model.set("OperateType", operateType);
            if (operateType && operateType == "4") {
                initEPIOperation($page.columnData);
            } else if (operateType && operateType == "5") {
                initPricingOperation($page.columnData);
            } else {
                initOperation($page.columnData);
            }
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        copyContract: function (contCompId) {
            var fail = function () {
                NConfirm.getConfirmModal().show({
                    title: 'Alert',
                    disableClose: true,
                    messages: ['Copy contract fail.']
                });
            }.bind(this);
            return this.copyOrRenewContract({
                ContCompId: contCompId,
                IsRenewal: false
            }, fail);
        },
        renewContract: function (contCompId) {
            var fail = function () {
                NConfirm.getConfirmModal().show({
                    title: 'Alert',
                    disableClose: true,
                    messages: ['Renew contract fail.']
                });
            }.bind(this);
            return this.copyOrRenewContract({
                ContCompId: contCompId,
                IsRenewal: true
            }, fail);
        },
        reset: function () {
            delete this.model.getCurrentModel().condition;
            this.form.forceUpdate();
            $page.controller.model.mergeCurrentModel({
                condition: {
                    CountPerPage: 10,
                    PageIndex: 1
                }
            });
        },
        doSearch: function () {
            var url = this.model.getCurrentModel().cachedCriteria.url;
            var searchModel = this.model.getCurrentModel().condition;
            var _self = this;
            console.log('contractQuery.search');
            console.log(JSON.stringify(searchModel));
            this.search(url, searchModel, false, false, function (data) {
                _self.updateSearchResult(data, true);
            }, null);
        },
        search: function (url, criteria, quiet, async, done, fail) {
            $page.service.query(url, criteria, quiet, async, done, fail);
        },
        copyOrRenewContract: function (criteria, fail) {
            var _self = this;
            var isSuccess = false;
            var done = function (data) {
                _self.model.mergeCurrentModel({
                    ContCompId: data.ContCompId
                });
                isSuccess = true;
            }.bind(this);
            $page.service.copyOrRenewContract(criteria, false, false, done, fail);
            return isSuccess;
        },
        getRenewalStatus: function (renewalId) {
            var isRenewed = false;
            var done = function (data) {
                isRenewed = data;
            }.bind(this);
            $page.service.getRenewalStatus({
                ContCompId: renewalId
            }, false, false, done, null);
            return isRenewed;
        },
        updateSearchResult: function (data, updateUI) {
            this.model.mergeCurrentModel({
                resultTable: data.Rows ? data.Rows : [],
                cachedCriteria: {
                    pageIndex: data.PageIndex,
                    pageCount: data.PageCount
                }
            });
            this.model.getCurrentModel().cachedCriteria = $.extend(true,{},this.model.getCurrentModel().cachedCriteria,this.model.getCurrentModel().condition);

            if (this.model.getCurrentModel().resultTable && this.model.getCurrentModel().resultTable.length > 0) {
                $page.isSearched = true;
            } else {
                $page.isSearched = false;
            }
            if (updateUI) {
                this.form.forceUpdate();
            }
        },
        createContract: function () {
            var initialModel = $.extend({}, this.model.getCurrentModel().condition);
            delete initialModel.PageIndex;
            delete initialModel.CountPerPage;
            delete initialModel.ContractCode;
            delete initialModel.CreatedBy;
            delete initialModel.CreatedOn;
            delete initialModel.LastChanged;
            delete initialModel.LastChangedOn;
            delete initialModel.LatestStatus;
            var initialData = JSON.stringify(initialModel);
            //var fetch = require('node-fetch');
            //fetch(url,{
            //    method:'post',
            //    body:initialData
            //});
            var url = "contractHome.html?OperateType=1";
            if (!$page.isSearched) {
                url = url + "&initialData=" + initialData;
            }
            window.location.href = url;
        }
    }));

    $page.controller = new Controller();
    $page.controller.initializePageType();
}(typeof window !== "undefined" ? window : this));