(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.buttonVisible = false;
    var me = {
        //initialize: function () {
        //},
        initializeData: function () {
           // console.log($page.model.createModel());
            this.model = $pt.createModel($page.model.createModel());
            this.codes = $pt.createModel($page.codes);
            var urlData = $pt.getUrlData();
            this.model.mergeCurrentModel(urlData);
            this.loadInfo(1);
            this.loadFn();
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createQueryViewLayout());
            var form = <NForm model={this.model} layout={layout} view={true}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        loadInfo: function (currentTabValue) {
            var result = false;
            if (!currentTabValue || currentTabValue == 1) {
                this.loadContractInfo();
                result = true;
            } else if (currentTabValue == 2) {
                this.loadEndorsementList();
            } else if (currentTabValue == 3) {
                this.loadClaimList();
            } else if (currentTabValue == 4) {
                this.searchStatement();
            } else if (currentTabValue == 5) {
                this.loadUw();
            } else if (currentTabValue == 7) {
                this.loadCreditnote();
            }
            $page.buttonVisible = result;
        },
        loadContractInfo: function (criteria, quiet, async, done, fail) {
            var _this = this;
            $page.service.loadContractInfo({
                ContCompId: _this.model.get("ContCompId")
            } , false, false, function (data, textStatus, jqXHR) {
                _this.model.mergeCurrentModel(data);
                if (_this.form) {
                    _this.form.forceUpdate();
                }
            }, function () {
            });

        },
        pdfSummary: function(){
            var fileUrl = null;
            var done = function(data){
                fileUrl = data.fileUrl;
            }.bind(this);
            var criteria = {
                ContCompId: this.model.get("ContCompId")
            }
            if(this.model.get("ContractNature") == 1){
                $pt.generateFile(14, criteria);
            }else if(this.model.get("ContractNature") == 2){
                $pt.generateFile(13, criteria);
            }
            // $page.service.pdfSummary(criteria, done);
            // window.open(fileUrl);
        },
        saveContract: function (needAlert) {
            this.model.validate();
            if (this.model.hasError() == true) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all mandatory information.']
                });
                return false;
            }
            var _self = this;
            $page.finalDate = _self.model.get("ReinsEnding");
            _self.model.set("ReinsEnding", moment($page.finalDate).format("YYYY-MM-DD HH:mm:ss"));
            var isSaved = false;
            var afterSave = function (data) {
                _self.model.mergeCurrentModel(data);
                _self.initialInfoContract(_self.model);
                _self.form.forceUpdate();
                isSaved = true;
            }.bind(this);
            var savedFail = function () {
                isSaved = false;
                NConfirm.getConfirmModal().show({
                    title: 'Alert',
                    disableClose: true,
                    messages: ['Saved fail.']
                });
            }.bind(this);

            $page.service.save(this.model.getCurrentModel(), false, false, afterSave, savedFail);
            if (isSaved) {
                if (needAlert) {
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Save Successfully.']
                    });
                }
            }
            return isSaved;
        },
        initialInfoContract: function (model) {
            var sunsetCheck = model.get('SunsetCheck');
            if (sunsetCheck != undefined && sunsetCheck != null && sunsetCheck == 'true') {
                model.set('SunsetCheck', true);
            } else {
                model.set('SunsetCheck', false);
            }
            var posting = model.get('Posting');
            if (posting != undefined && posting != null && posting == 'true') {
                model.set('Posting', true);
            } else {
                model.set('Posting', false);
            }
            var portfolioIn = model.get('PortfolioIn');
            if (portfolioIn != undefined && portfolioIn != null && portfolioIn == 'true') {
                model.set('PortfolioIn', true);
            } else {
                model.set('PortfolioIn', false);
            }
            var portfolioOut = model.get('PortfolioOut');
            if (portfolioOut != undefined && portfolioOut != null && portfolioOut == 'true') {
                model.set('PortfolioOut', true);
            } else {
                model.set('PortfolioOut', false);
            }
            var contractClaimConditionItemList = model.get("ContractClaimConditionItemList");
            if (contractClaimConditionItemList != undefined && contractClaimConditionItemList != null) {
                contractClaimConditionItemList.forEach(function (item) {
                    var isCheck = item.IsCheck;
                    if (isCheck && isCheck == 'true') {
                        item.IsCheck = true;
                    } else {
                        item.IsCheck = false;
                    }
                })
            }
        },
        loadEndorsementList: function (criteria, quiet, async, done, fail) {
            var _this = this;
            $page.service.loadEndorsementList({
                ContCompId: _this.model.get("ContCompId")
            }, false, false, function (data, textStatus, jqXHR) {
                _this.model.mergeCurrentModel({
                    EndorsementList: data
                });
                if (_this.form) {
                    _this.form.forceUpdate();
                }
            }, function () {
            });
        },
        loadClaimList: function (criteria, quiet, async, done, fail) {
            var _this = this;
            $page.service.loadClaimList(_this.model.get("ContCompId"), false, false, function (data, textStatus, jqXHR) {
                _this.model.mergeCurrentModel({
                    claimTable: data
                });
                if (_this.form) {
                    _this.form.forceUpdate();
                }
            }, function () {
            });
        },
        searchStatement: function (criteria, quiet, async, done, fail) {
            var _this = this;
            $page.service.queryStatement( _this.model.get("ContractCode"), false, false, function (data, textStatus, jqXHR) {
                _this.model.mergeCurrentModel({
                    statementTable: data
                });
                console.log('Statement log:+++');
                console.log(data);
                if (_this.form) {
                    _this.form.forceUpdate();
                }
            }, function () {
            });
        },
        loadUw: function () {
            var _this = this;
            var contractCode = _this.model.get("ContractCode");
            var uwYear = _this.model.get("UwYear");

            this.load(contractCode, uwYear, function (data) {
                $helper.lowerKeysOfJSON(data);
                _this.initialInfo(data);
            }, function () {

            });
        },
        loadFn: function () {
            var _this = this;
            var contCompId = _this.model.get("ContCompId");
            this.loadFnPage(contCompId, function (data) {
                $helper.lowerKeysOfJSON(data);
                _this.initialFnInfo(data);
            }, function () {

            });
        },
        loadCreditnote: function (criteria, quiet, async, done, fail) {
            var _this = this;
            $page.service.selectCollection({
                ContractIds: _this.model.get("ContractCode")
            }, false, false, function (data, textStatus, jqXHR) {
                _this.model.mergeCurrentModel(data[0]);
                //e.log("=======");
               // console.log(data[0]);
                if (_this.form) {
                    _this.form.forceUpdate();
                }
            }, function () {
            })
        },
        isEnabled: function () {
            var isEnabled = true;
            if ($page.status == "4") {
                isEnabled = false;
            }
            return isEnabled;
        },
        loadClaim: function (claimId, type, opcode) {
            var url = $pt.getURL('ui.claim.claimInfo');

            var loadClaimFu = function (data, textStatus, jqXHR) {
                if (data.OperatingBy == undefined || data.OperatingBy == null) {
                    window.open( url + "?claimId=" + claimId + "&pageType=" + type + "&Exit=1");
                } else if (data.OperatingBy != opcode) {
                    NConfirm.getConfirmModal().show({
                        title: 'System Message',
                        disableClose: true,
                        messages: [data.OperatingBy + ' is operating this claim, you cannot edit it.']
                    });
                } else {
                    window.open( url + "?claimId=" + claimId + "&pageType=" + type + "&Exit=1");
                }
            }.bind(this);
            $page.service.loadClaim(claimId, false, false, loadClaimFu);
        },
        //view Statement
        doView: function (rowModel) {
            var _this = this;
            var url = $pt.getURL('ui.accounting.statementView');
            var soaId = rowModel.SoaId;
            window.open(url + "?soaId=" + soaId + "&Exit=1");
        },
        //load Uw
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
        load: function (contractCode, uwYear, done, fail) {
            $page.service.load(contractCode, uwYear, done, fail);
        },
        toForecastAndEstimation: function (level, contCompId) {
            window.open($pt.getURL('ui.accounting.cedentQuarterView') + "?Level=" + level + "&ContCompId=" + contCompId + "&Exit=1");
        },
        loadFnPage: function (level, conCompId, date, done, fail) {
            $page.service.loadFnPage(level, conCompId, date, done, fail);
        },
        initialFnInfo: function (data) {
            var cache = $.extend({}, this.model.getCurrentModel());
           console.log(data);
            $page.controller.model.mergeCurrentModel({
                tableColumns: data.tableColumns,
                contractTable: data.contractTable,
                sectionTables: data.sectionTables
            });
            console.log("model");
            console.log($page.controller.model.get("tableColumns"));
        },
        showTransactionDetail: function (entryCode, cedentQuarter) {
            var url = $pt.getURL('ui.accounting.fnTransactionDetail') + "?contCompId=" + this.model.getCurrentModel().contCompId + "&entryCode=" + entryCode + "&cedentQuarter=" + cedentQuarter;
            window.open(url);
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));

