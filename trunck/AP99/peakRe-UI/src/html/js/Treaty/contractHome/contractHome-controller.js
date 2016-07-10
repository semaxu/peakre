(function (context) {
    var $page = $pt.getService(context, '$page');
    var initialPage = {
        initializeErrorModel: function () {
            return true;
        },
        initializeData: function () {
            var urlData = $pt.getUrlData();
            var initialData = null;
            if (typeof urlData.initialData != undefined && urlData.initialData != null) {
                initialData = JSON.parse(urlData.initialData);
            }
            this.model = $pt.createModel($page.model, $page.validator.contractHomeValidate());
            this.model.mergeCurrentModel(urlData);
            this.model.set("CreatedOn", moment().format('YYYY-MM-DDTHH:mm:ss'));
            this.model.set("LastChangedOn", moment().format('YYYY-MM-DDTHH:mm:ss'));
            var _self = this;
            if(this.model.get("OperateId")){
                this.loadContractInfoForLog({
                    ContCompId: this.model.get("ContCompId"),
                    OperateId: this.model.get("OperateId")
                }, false, false, function (data, textStatus, jqXHR) {
                    _self.model.mergeCurrentModel(data);
                    _self.initialInfo(_self.model);
                });
            }else{
                this.loadContractInfo({
                    ContCompId: this.model.get("ContCompId")
                }, false, false, function (data, textStatus, jqXHR) {
                    _self.model.mergeCurrentModel(data);
                    _self.initialInfo(_self.model);
                });
            }
            if (this.model.get("ContractCode") == undefined || this.model.get("ContractCode") == null) {
                _self.getContractCode(null, false, false, function (data) {
                    _self.model.mergeCurrentModel(data);
                    if (typeof initialData != undefined && initialData != null) {
                        _self.model.mergeCurrentModel(initialData);
                    }
                })
            }
            this.model.set("Reviewed", null);
            delete this.model.getCurrentModel().initialData;
            delete this.model.getCurrentModel().backType;
            delete this.model.getCurrentModel().code;
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            if (this.model.get("OperateType") == 0 || this.model.get("OperateType") == 5) {
                layout = $pt.createFormLayout($page.layout.createLayout());
                form = <NForm model={this.model} layout={layout} view={true}/>;
            }
            this.form = ReactDOM.render(form, document.getElementById('main'));
            var _this = this;
            this.model.addPostChangeListener("Cedent", function(model){

              if(model && model.new.length == 6){
                $page.service.loadPartner({businessPartnerId: model.new} ,
                  function (data) {
                    _this.model.mergeCurrentModel({CedentCountry : data.Country});
                    _this.form.forceUpdate();
                  });
              }
            });
            this.model.addPostChangeListener("ContractNature", function (model) {
                if(_this.model.get("SectionList") && _this.model.get("SectionList").length >= 1){
                    _this.model.mergeCurrentModel({ContractNature:model.old});
                    _this.form.forceUpdate();
                    NConfirm.getConfirmModal().show({
                        title: 'Alert',
                        disableClose: true,
                        messages: ['Please delete exist section first.']
                    });
                    return;
                }
                if (model.new == '2') {
                    _this.model.set('Posting', true);
                    _this.model.set('AccountingBasis', 3);
                    _this.model.set('ClaimBasis', 1);
                } else {
                    _this.model.set('Posting', false);
                    _this.model.set('AccountingBasis', 1);
                    _this.model.set('ClaimBasis', 2);
                }
            });

            this.model.addPostChangeListener("MainCurrency", function (model) {
                if(_this.model.get("SectionList") && _this.model.get("SectionList").length >= 1){
                    _this.model.mergeCurrentModel({MainCurrency:model.old});
                    _this.form.forceUpdate();
                    NConfirm.getConfirmModal().show({
                        title: 'Alert',
                        disableClose: true,
                        messages: ['Please delete exist section first.']
                    });
                    return;
                }

            });

            this.model.addPostChangeListener("ContractCategory", function (model) {
                if(_this.model.get("SectionList") && _this.model.get("SectionList").length >= 1){


                    console.log(model.new);
                    if(model.new ==2){
                        console.log(_this.model.get("ContCompId"));

                        var afterCategoryChange = function (data) {
                            console.log(strctureList);

                            var strctureList = data ? data : [];
                            console.log(strctureList);
                            if(strctureList.length >0 ){

                                var message = [];
                                strctureList.forEach(function (strcture) {
                                    if(strcture.Type == 2){
                                        message.push("Secition "+strcture.Name +" is ceded out,please clear that retrocession arrangement. ");
                                    }else{
                                        message.push("SubSecition "+strcture.Name +" is ceded out,please clear that retrocession arrangement. ");
                                    }
                                });

                                _this.model.mergeCurrentModel({ContractCategory:model.old});
                                _this.form.forceUpdate();
                                NConfirm.getConfirmModal().show({
                                    title: 'Alert',
                                    disableClose: true,
                                    messages: message
                                });
                                return;
                            }
                        }.bind(this);
                        $page.service.categoryChangeService(_this.model.get("ContCompId"),  false, false,afterCategoryChange);



                    }

                }

            });

            this.model.addPostChangeListener("ReinsStarting", function (model) {
              if(_this.model.get("ReinsStarting") && "" != _this.model.get("ReinsStarting")){
                var startDate = moment(_this.model.get("ReinsStarting"));
                var endDate = moment(_this.model.get("ReinsStarting"));
                endDate = endDate.add(1,'y');
                endDate = endDate.subtract(1,'d');
                _this.model.mergeCurrentModel({
                    ReinsEnding: endDate.format()
                });
                _this.model.set("UwYear", startDate.year());
                _this.form.forceUpdate();
              }

            });
        },
        getContractCode: function (model, quiet, async, done) {
            $page.service.getContractCode(model, quiet, async, done);
        },
        isExpanded: function () {
            var urlData = $pt.getUrlData();
            var backType = urlData.backType;
            if (backType != undefined && backType != null && backType == 'true') {
                return false;
            } else {
                return true;
            }
        },
        isVisible: function () {
            var isVisible = true;
            if (this.model.get("OperateType") == 0 || this.model.get("OperateType") == 5) {
                isVisible = false;
            }
            return isVisible;
        },

        save: function (needAlert) {
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
                _self.initialInfo(_self.model);
                _self.form.forceUpdate();
                isSaved = true;
            }.bind(this);
            $page.service.save(this.model.getCurrentModel(), false, false, afterSave);
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
        submit: function (isSpecialSubmit) {
            this.model.validate();
            if (this.model.hasError() == true) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all mandatory information.']
                });
                return false;
            }
            var _this = this;
            var isSaved = false;
            var submitSuccess = function(data){
              _this.model.mergeCurrentModel({
                  ContCompId: data.ContCompId
              });
              isSaved = true;
            }.bind(this);
            var submitFail =  function () {
                NConfirm.getConfirmModal().show({
                    title: 'Alert',
                    disableClose: true,
                    messages: ['Error occur. Please comfirm the contract information.']
                });
            }.bind(this);
            if(isSpecialSubmit){
              $page.service.submitSpecial(this.model.getCurrentModel(), false, false, submitSuccess);
            } else {
              $page.service.submit(this.model.getCurrentModel(), false, false, submitSuccess);
            }
            return isSaved;
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
        isPricingEstimated: function(){
            var isPricingEstimated = false;
            var done = function (data) {
                isPricingEstimated = data;
            }.bind(this);
            var deleteSectionList = this.model.get("DeleteSectionList");

            $page.service.isPricingEstimated(
                {"ContCompId": this.model.get("ContCompId"), "DeleteSectionList":JSON.stringify(deleteSectionList)}, false, false, done);
            return isPricingEstimated;
        },
        getNonRetroList: function(){
            var _self=this;
            var nonRetroList = [];
            var fronting=_self.model.get('Fronting');
            var category=_self.model.get('ContractCategory');
            var done = function (data) {
                if(fronting=='1'&& category=='1'){
                    nonRetroList = data;
                }
            }.bind(this);
            $page.service.getNonRetroList({ContCompId: this.model.get("ContCompId")}, false, false,done);

            return nonRetroList;
        },
        loadContractInfo: function (criteria, quiet, async, done) {
            $page.service.loadContractInfo(criteria, quiet, async, done);
        },
        loadContractInfoForLog: function (criteria, quiet, async, done) {
            $page.service.loadContractInfoForLog(criteria, quiet, async, done);
        },
        initialInfo: function (model) {
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

        updateTable: function (data, updateUI) {
            this.model.mergeCurrentModel({
                SectionList: data ? data : []
            });
        },
        doShowLinkContractDialog: function (initValues) {
            if (initValues.linkName == null || initValues.contractId == null) {
                this.actualShowLinkContractDialog(null, initValues);
                return;
            }
            var afterLoadLinkTree = function (data) {
                $helper.lowerKeysOfJSON(data);
                this.actualShowLinkContractDialog(data, initValues);
            }.bind(this);
            $page.service.loadLinkTree(initValues, afterLoadLinkTree);
        },
        viewContractDocument: function (initValues) {
            //var filePath = null;
            //var fileName = null;
            //var afterLoadDocument = function (data) {
            //    if (data.Rows[0] != null && data.Rows[0] != '' && data.Rows[0] != undefined) {
            //        filePath = data.Rows[0].FilePath;
            //        fileName = data.Rows[0].FileName;
            //        var reg = '/\\/g';
            //        filePath = filePath.replace(reg, '/');
            //        fileName = fileName.replace(reg, '/');
            //    }
            //}.bind(this);
            //$page.service.queryDocument(initValues, afterLoadDocument);
            //window.open("../fileUpload/" + filePath + fileName);
            //$pt.downloadFile(initValues.DocumentId);
            window.open($ri.getRestfulURL("action.public.file")+'/download/'+initValues.DocumentId+'?'+ $.sessionStorage.get($page.constants.CAS_KEY));
        },
        actualShowLinkContractDialog: function (data, initValues) {
            var sectionForm = this.getLinkContractDialog();
            var sectionModel = $pt.createModel(data);
            var sectionLayout = {
                linkStructures: {
                    label: 'Normal Panel',
                    comp: {
                        type: $pt.ComponentConstants.Tab,
                        justified: false,
                        tabs: $page.layout.createLinkStructureTabs(data)
                    },
                    pos: {
                        width: 12
                    }
                }
            };
            sectionForm.show({
                model: sectionModel,
                layout: $pt.createFormLayout(sectionLayout),
                buttons: {
                    reset: false,
                    validate: false,
                    cancel: false,
                    right: [{
                        text: 'Close',
                        style: 'warning',
                        click: function (model) {
                            sectionForm.hide();
                        }
                    }]
                },
                draggable: false
            });
        },
        getLinkContractDialog: function () {
            if (!this.linkContractDialog) {
                this.linkContractDialog = NModalForm.createFormModal("Linked Contract");
            }
            return this.linkContractDialog;
        },

        addSection: function (model) {
            if (model.get("ContractNature") == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please choose the Contract Nature']
                });
                return;
            }
            var isSaved = $page.controller.save();
            if (!isSaved) {
                return;
            }
            var url = "section.html?ParentId=" + model.get("ContCompId")
                + "&ContractNature=" + model.get("ContractNature")
                + "&OperateType=" + model.get("OperateType");
            window.location.href = url;
        },
        AMLCheck: function (model) {
            var AMLflag = true;
            var AfterGetAmlCheckResult = function (data, textStatus, jqXHR) {
                var amlMessageList = data ? data : [];
                if(amlMessageList.length > 0){
                    AMLflag = false;
                    var messages = [];
                    amlMessageList.forEach(function (amlMessage) {
                        messages.push(amlMessage.Msg);
                    });

                    NConfirm.getConfirmModal().show({
                        title: 'System Message',
                        disableClose: true,
                        messages: messages
                    });
                }

            }.bind(this);
            $page.service.AMLCheckService(this.model.getCurrentModel(), false, false, AfterGetAmlCheckResult);

            return AMLflag;
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, initialPage));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));
