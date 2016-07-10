/**
 * Created by Weiping.Wang on 01/16/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var me = {
        initializeErrorModel: function () {
            return true;
        },
        initializeData: function () {
            var urlData = $pt.getUrlData();
            // var contractNature = urlData.ContractNature;
            // var submitType = urlData.submitType;
            this.model = $pt.createModel($page.model, $page.validator.retrocessionValidate());
            this.model.mergeCurrentModel(urlData);
            var _this = this;
            if(this.model.get("OperateId")){
                this.loadRetrocessionForLog({
                    CompId: this.model.get("ContCompId"),
                    OperateId: this.model.get("OperateId")
                }, false, false, function (data) {
                    _this.model.mergeCurrentModel({
                        RetrocessionList: data
                    });
                    if (_this.form) {
                        _this.form.forceUpdate();
                    }
                }, null);
            }else{
                this.loadRetrocession({
                    CompId: this.model.get("ContCompId")
                }, false, false, function (data) {
                    _this.model.mergeCurrentModel({
                        RetrocessionList: data
                    });
                    if (_this.form) {
                        _this.form.forceUpdate();
                    }
                }, null);
            }
        },
        loadRetrocession: function (criteria, quiet, async, done, fail) {
            $page.service.loadRetrocession(criteria, quiet, async, done, fail);
        },
        loadRetrocessionForLog: function (criteria, quiet, async, done, fail) {
            $page.service.loadRetrocessionForLog(criteria, quiet, async, done, fail);
        },
        updateTable: function (data, updateUI) {
            this.model.mergeCurrentModel(data);
            if(this.form){
                this.form.forceUpdate();
            }
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            if (this.model.get("OperateType") == 0 || this.model.get("OperateType") == 5) {
                layout = $pt.createFormLayout($page.layout.createFormLayout());
                form = <NForm model={this.model} layout={layout} view={true}/>;
            }
            this.form = ReactDOM.render(form, document.getElementById('main'));
            console.debug("rendering ........");
        }
    }
    var addSection = {
        // Search contract by contractId and uwyear
        onAddContractSectionSearch: function (dialogModel) {
            $page.controller.findContract(dialogModel);
        },
        findContract: function (dialogModel) {
            var _this = this;
            if(!dialogModel.get("ContractCode")){
                NConfirm.getConfirmModal().show({
                    title: 'Attention',
                    disableClose: true,
                    messages: ['Please input Contract ID.']
                });
                return;
            }
            if(!dialogModel.get("UnderWritingYear")){
                NConfirm.getConfirmModal().show({
                    title: 'Attention',
                    disableClose: true,
                    messages: ['Please input UW Year.']
                });
                return;
            }
            var params = {
                ContractCode: dialogModel.get("ContractCode"),
                UnderWritingYear: dialogModel.get("UnderWritingYear")
            }
            var findContract = function (data, textStatus, jqXHR) {
                if(!data){
                    NConfirm.getConfirmModal().show({
                        title: 'Attention',
                        disableClose: true,
                        messages: ['No contract found.Please alter the search criteria.']
                    });
                }
                $helper.lowerKeysOfJSON(data);
                _this.addContractSection(data, dialogModel);
            }.bind(this);
            var findContractError = function () {
                NConfirm.getConfirmModal().show({
                    title: 'Attention',
                    disableClose: true,
                    messages: ['No contract found.Please alter the search criteria.']
                });
            }
            $page.service.findContractSer(params, false, false, findContract, findContractError);
        },
        /**
         *
         * @param treeCodesData {*} JSON object
         * @param dialogModel {ModelInterface} previous dialog model, optional
         */
        addContractSection: function (treeCodesData, dialogModel) {
            var _self = this;
            var sectionForm = this.getContractSection();
            var sectionModel = dialogModel ? dialogModel : $pt.createModel({});
            var treeCodes = $pt.createCodeTable(treeCodesData ? treeCodesData : []);
            var sectionLayout = $pt.createFormLayout($page.layout.createAddContractSectionDialogLayout(treeCodes));
            if (treeCodesData != null) {
                $page.controller.checkedSection(dialogModel);
            }
            sectionModel.addPostChangeListener("ContractRecords", function(evt){

                sectionModel.set("ContractCode",evt.model.getCurrentModel().ContractRecords.ContractCode);
                sectionModel.set("UnderWritingYear",evt.model.getCurrentModel().ContractRecords.UwYear);

                //this.model.set("ContractID",_this.model.getCurrentModel().ContractRecords.ContractCode);
                //this.model.set("UnderwritingYear",_this.model.getCurrentModel().ContractRecords.UwYear);

            });
            sectionForm.show(
                {
                    model: sectionModel,
                    layout: sectionLayout,
                    buttons: {
                        reset: false,
                        validate: false,
                        cancel: false,
                        right: [
                            {
                                text: "Submit",
                                style: "primary",
                                click: function (dialogModel) {
                                    var retrocessionList = _self.model.get("RetrocessionList");
                                    var DMSectree = dialogModel.get("SectionTree");
                                    if (DMSectree == undefined || DMSectree == null || DMSectree.length == 0) {
                                        NConfirm.getConfirmModal().show({
                                            title: 'Attention',
                                            disableClose: true,
                                            messages: ['No section/subsection is selected.']
                                        });
                                        return;
                                    }
                                    var addSectionTree = [];
                                    var i = 0;
                                    if (DMSectree != undefined && DMSectree != null && DMSectree.length > 0) {
                                        if (retrocessionList != undefined && retrocessionList != null && retrocessionList.length > 0) {
                                          var retrocessionLoadFunc = function (item) {
                                              if (DMSectree[i] == item.RetroCompId) {
                                                  isSame = true;
                                              }
                                          }.bind(this);
                                            for (i=0; i < DMSectree.length; i++) {
                                                var isSame = false;
                                                retrocessionList.forEach(retrocessionLoadFunc);
                                                if (!isSame) {
                                                    addSectionTree.push(DMSectree[i]);
                                                }
                                            }
                                        } else {
                                            addSectionTree = DMSectree;
                                        }
                                    }
                                    if (addSectionTree != undefined && addSectionTree != null && addSectionTree.length > 0) {
                                        var retroCompIdTree = "";
                                        for ( i = 0; i < addSectionTree.length; i++) {
                                            retroCompIdTree = retroCompIdTree + (i == 0 ? "" : ",") + addSectionTree[i];
                                        }
                                        $page.controller.generateRetrocession(retroCompIdTree);
                                    }
                                    if ($page.controller.form) {
                                        $page.controller.form.forceUpdate();
                                    }
                                    sectionForm.hide();
                                }
                            }
                        ]
                    }
                }
            );
        },
        getContractSection: function () {
            if (!this.addContractSectionDialog) {
                this.addContractSectionDialog = NModalForm.createFormModal("Choose Sections");
            }
            return this.addContractSectionDialog;
        },
        checkedSection: function (dialogModel) {
            var retrocessionList = this.model.get("RetrocessionList");
            var checkList = [];
            if (retrocessionList != undefined && retrocessionList != null) {
                retrocessionList.forEach(function(item){
                    checkList.push(item.RetroCompId);
                });
            }
            dialogModel.set("SectionTree", checkList);
        },
        /**
         * Generate retrocession info by retro section ids
         * @param retroCompIdTree
         */
        generateRetrocession: function (retroCompIdTree) {
            var _self = this;
            var generateRetroSection = function (data, textStatus, jqXHR) {
                if(data != undefined && data != null && data.length > 0){
                    for (var i = 0; i < data.length; i++) {
                        if (_self.model.get("RetrocessionList") == undefined || _self.model.get("RetrocessionList") == null) {
                            _self.model.set("RetrocessionList",[]);
                        }
                        (_self.model.get("RetrocessionList")).push(data[i]);
                    }
                }
            }.bind(this);

            $page.service.generateRetrocession({RetroCompIdTree: retroCompIdTree}, false, false, generateRetroSection);
        },
        isVisible: function () {
            var isVisible = true;
            if (this.model.get("OperateType") == 0 || this.model.get("OperateType") == 5) {
                isVisible = false;
            }
            return isVisible;
        },
        validate: function () {
            var isNormal = true;
            var retrocessionList = this.model.get("RetrocessionList");
            if(retrocessionList != null && retrocessionList != undefined){
                retrocessionList.forEach(function(retrocession){
                    if(retrocession.Sequence == undefined || retrocession.Sequence == null || retrocession.Sequence == ""){
                        isNormal = false;
                    }
                });
                if(!isNormal){
                    NConfirm.getConfirmModal().show({
                        title: 'Attention',
                        disableClose: true,
                        messages: ['Please input sequence.']
                    });
                }
            }
            if(isNormal){
                for (var i = 0; retrocessionList != null && retrocessionList != undefined && i < retrocessionList.length; i++) {
                    for (var j = (i+1); j < retrocessionList.length; j++) {
                        if (retrocessionList[i].Sequence == retrocessionList[j].Sequence) {
                            isNormal = false;
                        }
                    }
                }
                if(!isNormal){
                    NConfirm.getConfirmModal().show({
                        title: 'Attention',
                        disableClose: true,
                        messages: ['Do not input the same sequence.']
                    });
                }
            }
            return isNormal;
        },
        onAddRetroSectionSubmit: function () {
            this.model.validate();
            if (this.model.hasError() == true) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all mandatory information.']
                });
                return false;
            }

            var isSaved = false;
            var saveRetroSection = function (data, textStatus, jqXHR) {
                isSaved = true;
            }.bind(this);
            $page.service.saveRetrocession(this.model.getCurrentModel(), false, false, saveRetroSection);
            return isSaved;
        }
    }

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me, addSection));
    $page.controller = new Controller();
    //for layout purpose
    //$page.control.initializeErrorModel();
    //$page.control.initialize();
}(typeof window !== "undefined" ? window : this));
