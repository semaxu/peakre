(function (context) {
    var $page = $pt.getService(context, "$page");
    $page.isSearched = false;
    var initOperation = function (column) {
        column.push(
            {
                //tooltip: "view",
                //click: function (rowModel) {
                //    var url = "contractHome.html?contCompId=" + rowModel.ContCompId + "&readOnly=true";
                //    window.location.href = url;
                //}
                //}, {
                tooltip: "edit",
                enabled: {
                    when: function (model) {
                        return model.get('LatestStatus') != '2' && model.get('LatestStatus') != '4';
                    },
                    depends: 'LatestStatus'
                },
                click: function (rowModel) {
                    var opeType = null;
                    var url = "contractHome.html?contCompId=" + rowModel.ContCompId;
                    if (rowModel.LatestStatus == '1') {
                        opeType = "1";
                    } else if (rowModel.LatestStatus == '3') {
                        opeType = "3";
                    }
                    url += "&opeType=" + opeType;
                    window.location.href = url;
                }
            }, {
                tooltip: "copy",
                click: function (rowModel) {
                    if ($page.controller.copyContract(rowModel.ContCompId)) {
                        var url = "contractHome.html?opeType=1&contCompId=" + this.getModel().get("ContCompId");
                        window.location.href = url;
                    }
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
                    var url = "contractHome.html?opeType=2&contCompId=" + rowModel.ContCompId;
                    window.location.href = url;
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
                        var url = "contractHome.html?opeType=1&contCompId=" + this.getModel().get("ContCompId");
                        window.location.href = url;
                    }
                }
            }, {
                tooltip: "endorsement",
                click: function (rowModel) {
                    var url = "endorsementLog.html?contCompId=" + rowModel.ContCompId + "&status=" + rowModel.LatestStatus;
                    window.location.href = url;
                }
            }
        );
    };
    var initEPIOperation = function (column) {
        column.push(
            {
                tooltip: "SUPI",
                enabled: {
                    depends: "contractNature",
                    when: function (rowModel) {
                        return rowModel.getCurrentModel().contractNature == 2;
                    }
                },
                click: function () {
                    window.location.href = "supiAdjustment.html";
                }
            }, {
                tooltip: "EPI Update",
                enabled: {
                    depends: "contractNature",
                    when: function (rowModel) {
                        return rowModel.getCurrentModel().contractNature == 1;
                    }
                },
                click: function () {
                    window.location.href = "epiRevision.html";
                }
            }, {
                tooltip: "Pricing Est. Update",
                enabled: {
                    depends: "contractNature",
                    when: function (rowModel) {
                        return rowModel.getCurrentModel().contractNature == 1;
                    }
                },
                click: function () {
                    window.location.href = "pricingEstimation.html";
                }
            }
        );
    };
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
            var epiSupi = urlData.epiSupi;
            this.model = $pt.createModel($page.model);
            var _self = this;
            var url = this.model.getCurrentModel().cachedCriteria.url;
            var searchModel = this.model.getCurrentModel().condition;
            this.search(url, searchModel, false, false, function (data, textStatus, jqXHR) {
                _self.updateSearchResult(data, false);
            }, function () {
            });
            //this.model = $pt.createModel($page.model);
            this.model.set("epiSupi", epiSupi);
            //this.model.set("condition_ContractType",'1');
            //this.model.set("condition_ContractCategory",'1');
            //this.model.set("condition_Fronting",'0');
            //this.model.set("condition_DepositAccounting",'0');

            if (epiSupi == "true") {
                initEPIOperation($page.columnData);
            } else {
                initOperation($page.columnData);
            }
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
            //this.model.set("condition_ContractType",'1');
            //this.model.set("condition_ContractCategory",'1');
            //this.model.set("condition_Fronting",'0');
            //this.model.set("condition_DepositAccounting",'0');
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
            this.search(url, searchModel, false, false, function (data) {
                _self.updateSearchResult(data, true);
            }, function () {
            });
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
        getRenewalStatus: function(renewalId){
            var isRenewed = false;
            var done = function (data) {
                isRenewed = data;
            }.bind(this);
            $page.service.getRenewalStatus({
                ContCompId: renewalId
            }, false, false, done, function () {
            });
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
            
            if(this.model.getCurrentModel().resultTable && this.model.getCurrentModel().resultTable.length > 0){
                $page.isSearched = true;
            }else{
                $page.isSearched = false;
            }
            if (updateUI) {
                this.form.forceUpdate();
            }
        },
        createContract: function(){
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
            var url = "contractHome.html?opeType=1";
            if(!$page.isSearched){
                url = url + "&initialData="+initialData;
            }
            window.location.href = url;
        }
    }));

    $page.controller = new Controller();
    $page.controller.initializePageType();
}(typeof window !== "undefined" ? window : this));