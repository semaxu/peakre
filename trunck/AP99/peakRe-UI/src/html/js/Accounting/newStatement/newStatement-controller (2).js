(function (context) {
    var $page = $pt.getService(context, '$page');

    var initial = {
        initializeData: function () {
            this.model = $pt.createModel($page.model,$page.validator);
            var _this = this;
            this.codes = $pt.createModel($page.codes);
            _this.model.set("condition_StatementType",1);
            this.model.addPostChangeListener("ContractRecords", function(evt){
                _this.model.set("condition_ContractCode",_this.model.getCurrentModel().ContractRecords.ContractCode);
                _this.model.set("condition_UwYear",_this.model.getCurrentModel().ContractRecords.UwYear);
            });
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout);
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        }
    };
    var restService ={
        doCreate: function () {
            var _this = this;
            var statementType = '800004';
            var toValidateSoa = function (data, textStatus, jqXHR) {
                 if(data.Result=='wrongContract'){
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Contract status is invalided (Not in status of in-force or cancelled).System cannot create the statement.']
                    });

                }else if(data.Result=='wrongQuarter'){
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['The  cedent quarter should not be earlier than the underwriting year of this contract.']
                    });

                }else if(data.Result=='noEstimateInfo'){
                     NConfirm.getConfirmModal().show({
                         title: 'Message',
                         disableClose: true,
                         messages: ['There is no estimation generated under this cedant quarter. Please post estimation and then try again.']
                     });


                 }else if(data.Result=='duplicate'){
                    var confirm1 = function () {
                        var toCreate = function(data, textStatus, jqXHR){
                            window.location.href = "statementInput.html?soaModelType=create&&contractId=" + data.ContractId+"&&soaId="+ data.SoaId+"&&statementType="+ statementType;
                        }.bind(this);
                        var createCodition =this.model.getCurrentModel().condition;
                        this.create(createCodition, false, false, toCreate);

                    };
                    $pt.Components.NConfirm.getConfirmModal().show('Message', 'There is a duplicate statement under this cedant quarter. Are you sure to continue?', confirm1.bind(this));


                }else if(data.Result=='warnQuarter'){
                    var confirm2 = function () {
                        var toCreate = function(data, textStatus, jqXHR){
                            window.location.href = "statementInput.html?soaModelType=create&&contractId=" + data.ContractId+"&&soaId="+ data.SoaId+"&&statementType="+ statementType;
                        }.bind(this);
                        var createCodition =this.model.getCurrentModel().condition;
                        this.create(createCodition, false, false, toCreate);
                    };
                    $pt.Components.NConfirm.getConfirmModal().show('Message', 'Previous cedent quarter(s) has not been inputted yet. Please confirm to continue.', confirm2.bind(this));


                }else{
                    var toCreate = function(data, textStatus, jqXHR){
                        window.location.href = "statementInput.html?soaModelType=create&&contractId=" + data.ContractCode+"&&soaId="+ data.SoaId+"&&statementType="+ statementType;
                    }.bind(this);
                    var createCodition =this.model.getCurrentModel().condition;
                    this.create(createCodition, false, false, toCreate);

                }

            }.bind(this);
            if ($page.controller.model.get("condition")==undefined||$page.controller.model.get("condition").ContractCode==undefined||$page.controller.model.get("condition").ContractCode == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Contract ID']
                });
                return;
            }
            if ($page.controller.model.get("condition").UwYear == undefined||$page.controller.model.get("condition").UwYear==null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the UW Year']
                });
                return;
            }
            if ($page.controller.model.get("condition").CedentYear == undefined||$page.controller.model.get("condition").CedentYear==null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent Year']
                });
                return;
            }
            if ($page.controller.model.get("condition").CedentQuarter == undefined||$page.controller.model.get("condition").CedentQuarter==null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent Quarter']
                });
                return;
            }
            if ($page.controller.model.get("condition").StatementType == undefined||$page.controller.model.get("condition").StatementType==null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Statement Type']
                });
                return;
            }
            if ($page.controller.model.get("condition").CedentPeriod == undefined||$page.controller.model.get("condition").CedentPeriod==null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent Period']
                });
                return;
            }
            if (isNaN($page.controller.model.get("condition").UwYear)){
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Please input valid UW Year' ]
                });
                return;
            }
            this.validateSoa($page.controller.model.get("condition"), false, false, toValidateSoa);
    },
        doCreatePTF: function () {
            var _this = this;
            var statementType = '800004';
            var toValidateSoa = function (data, textStatus, jqXHR) {
                if(data.Result=='wrongContract'){
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Contract status is invalided (Not in status of in-force or cancelled).System cannot create the statement.']
                    });

                }else if(data.Result=='duplicate'){
                    var confirm1 = function () {
                        var toCreate = function(data, textStatus, jqXHR){
                            window.location.href = "statementInput.html?soaModelType=create&&contractId=" + data.ContractId+"&&soaId="+ data.SoaId+"&&statementType="+ statementType;
                        }.bind(this);
                        var createCodition =this.model.getCurrentModel().condition;
                        this.create(createCodition, false, false, toCreate);

                    };
                    $pt.Components.NConfirm.getConfirmModal().show('Message', 'There is a duplicate statement under this cedant quarter. Are you sure to continue?', confirm1.bind(this));


                }else{
                    var toCreate = function(data, textStatus, jqXHR){
                        window.location.href = "statementInput.html?soaModelType=create&&contractId=" + data.ContractId+"&&soaId="+ data.SoaId+"&&statementType="+ statementType;
                    }.bind(this);
                    var createCodition =this.model.getCurrentModel().condition;
                    this.create(createCodition, false, false, toCreate);
                }

            }.bind(this);
            if ($page.controller.model.get("condition")==undefined||$page.controller.model.get("condition").ContractCode==undefined||$page.controller.model.get("condition").ContractCode == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Contract ID']
                });
                return;
            }
            if ($page.controller.model.get("condition").UwYear == undefined||$page.controller.model.get("condition").UwYear==null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the UW Year']
                });
                return;
            }
            if ($page.controller.model.get("condition").CedentYear == undefined||$page.controller.model.get("condition").CedentYear==null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent Year']
                });
                return;
            }
            if ($page.controller.model.get("condition").CedentQuarter == undefined||$page.controller.model.get("condition").CedentQuarter==null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent Quarter']
                });
                return;
            }
            if ($page.controller.model.get("condition").StatementType == undefined||$page.controller.model.get("condition").StatementType==null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Statement Type']
                });
                return;
            }
            if ($page.controller.model.get("condition").CedentPeriod == undefined||$page.controller.model.get("condition").CedentPeriod==null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent Period']
                });
                return;
            }
            if ($page.controller.model.get("condition").StatementType == '2') {
                statementType = '800020';
            }else if($page.controller.model.get("condition").StatementType == '3'){
                statementType = '800021';
            }

            if (isNaN($page.controller.model.get("condition").UwYear)){
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Please input valid UW Year' ]
                });
                return;
            }
            this.validatePTFSoa($page.controller.model.get("condition"), false, false, toValidateSoa);
        },
    create: function (criteria, quiet, async, done, fail) {
        $page.service.create(criteria, quiet, async, done, fail);
    },
     validateSoa: function (validateCondition, quiet, async, done, fail) {
            $page.service.validateCurrentSoa(validateCondition, quiet, async, done, fail);
      },
        validatePTFSoa: function (validateCondition, quiet, async, done, fail) {
            $page.service.validateCurrentPTFSoa(validateCondition, quiet, async, done, fail);
       },

    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, initial,restService));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));