(function (context) {
    var $page = $pt.getService(context, "$page");
    var initial = {
        initializeErrorModel: function () {
            return true;
        },
        initializeData: function () {
            var urlData = $pt.getUrlData();
            var operateType = urlData.operateType;
            var contractNature = urlData.contractNature;
            var contCompId = urlData.contCompId;
            var parentId = urlData.parentId;
            var contId = urlData.contId;
            var mainCurrency = urlData.mainCurrency;
            this.model = $pt.createModel($page.model.createBaseModel(), $page.validator);
            this.model.set("BusinessConditionVO_ContractNature", contractNature);
            this.model.set("BusinessConditionVO_OperateType", operateType);
            this.model.set("BusinessConditionVO_ContId", contId);
            this.model.set("BusinessConditionVO_ParentId", parentId);
            this.model.set("BusinessConditionVO_ContCompId", contCompId);
            this.model.set("BusinessConditionVO_DefinedIn", '3');
            this.model.set("BusinessConditionVO_ShareDefine", '1');
            this.load(this.model);
            if (this.model.get("BusinessConditionVO_IsBCEmpty") == undefined || this.model.get("BusinessConditionVO_IsBCEmpty") == null || this.model.get("BusinessConditionVO_IsBCEmpty") != false) {
                this.initialBCInfo(mainCurrency);
            }
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            if (this.model.get("BusinessConditionVO_OperateType") == 0) {
                layout = $pt.createFormLayout($page.layout.createLayout());
                form = <NForm model={this.model} layout={layout} view={true}/>;
            }
            this.form = ReactDOM.render(form, document.getElementById('main'));
            var _self = this;
            this.model.addPostChangeListener("BusinessConditionVO_Ceded",function(model){
              setTimeout(
                function(model){
                  if (_self.model.get("BusinessConditionVO_Ceded")) {
                    _self.model.set('BusinessConditionVO_CedentRetention', parseFloat(1 - parseFloat(_self.model.get("BusinessConditionVO_Ceded"))).toFixed(4));
                      var Ceded=_self.model.get("BusinessConditionVO_Ceded");
                      var WrittenShare1 = _self.model.get("BusinessConditionVO_WrittenShare1");
                      //var SignedShare1 = _self.model.get("BusinessConditionVO_SignedShare1");
                      var WrittenShare2 = _self.model.get("BusinessConditionVO_WrittenShare2");
                      var SignedShare1 = _self.model.get("BusinessConditionVO_SignedShares1");
                      var SignedShare2 = _self.model.get("BusinessConditionVO_SignedShares2");
                      if(SignedShare1 && SignedShare2
                          &&_self.model.get("BusinessConditionVO_Ceded")
                          &&_self.model.get("BusinessConditionVO_CedentRetention")){
                          if(parseFloat(SignedShare1).toFixed(4) != parseFloat(parseFloat(Ceded) * parseFloat(SignedShare2)).toFixed(4));
                          _self.model.set('BusinessConditionVO_SignedShares1',parseFloat(parseFloat(Ceded) * parseFloat(SignedShare2)).toFixed(4));
                      }
                      if(WrittenShare1 && WrittenShare2
                          && _self.model.get("BusinessConditionVO_Ceded")
                          && _self.model.get("BusinessConditionVO_CedentRetention") ){
                          if(parseFloat(WrittenShare1).toFixed(4) != parseFloat(parseFloat(Ceded) * parseFloat(WrittenShare2)).toFixed(4));
                          _self.model.set('BusinessConditionVO_WrittenShare1',parseFloat(parseFloat(Ceded) * parseFloat(WrittenShare2)).toFixed(4));
                      }

                  }},300)});
            this.model.addPostChangeListener("BusinessConditionVO_CedentRetention", function(model){setTimeout(function (model) {
                var CedentRetention = _self.model.get("BusinessConditionVO_CedentRetention");
                var Ceded = _self.model.get("BusinessConditionVO_Ceded");
                if (Ceded && CedentRetention) {
                    if (parseFloat(Ceded).toFixed(4) != parseFloat(1 - parseFloat(CedentRetention)).toFixed(4)) {
                        _self.model.set('BusinessConditionVO_Ceded', parseFloat(1 - parseFloat(CedentRetention)).toFixed(4));
                    }
                } else {
                    if (CedentRetention) {
                        _self.model.set('BusinessConditionVO_Ceded', parseFloat(1 - parseFloat(CedentRetention)).toFixed(4));
                    }
                }
                var WrittenShare1 = _self.model.get("BusinessConditionVO_WrittenShare1");
                //var SignedShare1 = _self.model.get("BusinessConditionVO_SignedShare1");
                var WrittenShare2 = _self.model.get("BusinessConditionVO_WrittenShare2");
                var SignedShare1 = _self.model.get("BusinessConditionVO_SignedShares1");
                var SignedShare2 = _self.model.get("BusinessConditionVO_SignedShares2");
                if(SignedShare1 && SignedShare2
                    &&_self.model.get("BusinessConditionVO_Ceded")
                    &&_self.model.get("BusinessConditionVO_CedentRetention")){
                    if(parseFloat(SignedShare1).toFixed(4) != parseFloat(parseFloat(Ceded) * parseFloat(SignedShare2)).toFixed(4));
                    _self.model.set('BusinessConditionVO_SignedShares1',parseFloat(parseFloat(Ceded) * parseFloat(SignedShare2)).toFixed(4));
                }
                if(WrittenShare1 && WrittenShare2
                    && _self.model.get("BusinessConditionVO_Ceded")
                    && _self.model.get("BusinessConditionVO_CedentRetention") ){
                    if(parseFloat(WrittenShare1).toFixed(4) != parseFloat(parseFloat(Ceded) * parseFloat(WrittenShare2)).toFixed(4));
                    _self.model.set('BusinessConditionVO_WrittenShare1',parseFloat(parseFloat(Ceded) * parseFloat(WrittenShare2)).toFixed(4));
                }
            },300)});
            //======
            if( $pt.getUrlData().contractNature == 1){
                this.model.addPostChangeListener("BusinessConditionVO_WrittenShare1",function(model){
                  setTimeout(function(model){
                    console.log(_self.model.get("BusinessConditionVO_WrittenShare1"),
                                    _self.model.get("BusinessConditionVO_Ceded"),
                    _self.model.get("BusinessConditionVO_CedentRetention"));
                    var WrittenShare1 = _self.model.get("BusinessConditionVO_WrittenShare1");
                    if(_self.model.get("BusinessConditionVO_WrittenShare1") &&
                      _self.model.get("BusinessConditionVO_Ceded")
                      && _self.model.get("BusinessConditionVO_CedentRetention") )
                      {
                        _self.model.set('BusinessConditionVO_WrittenShare2',parseFloat(parseFloat(_self.model.get("BusinessConditionVO_WrittenShare1"))/(_self.model.get("BusinessConditionVO_Ceded"))).toFixed(4));
                    }
                },300)});
                this.model.addPostChangeListener("BusinessConditionVO_WrittenShare2",function(model){setTimeout(function(model){
                    var WrittenShare1 = _self.model.get("BusinessConditionVO_WrittenShare1");
                    var WrittenShare2 = _self.model.get("BusinessConditionVO_WrittenShare2");
                    var Ceded = _self.model.get("BusinessConditionVO_Ceded");
                    if(WrittenShare1 && WrittenShare2
                        && _self.model.get("BusinessConditionVO_Ceded")
                          && _self.model.get("BusinessConditionVO_CedentRetention") ){
                        if(parseFloat(WrittenShare1).toFixed(4) != parseFloat(parseFloat(Ceded) * parseFloat(WrittenShare2)).toFixed(4));
                        _self.model.set('BusinessConditionVO_WrittenShare1',parseFloat(parseFloat(Ceded) * parseFloat(WrittenShare2)).toFixed(4));
                    }
                },300)});
                this.model.addPostChangeListener("BusinessConditionVO_SignedShares1",function(model){setTimeout(function(model){
                    var WrittenShare1 = _self.model.get("BusinessConditionVO_SignedShares1");
                    console.log(model);
                    if(_self.model.get("BusinessConditionVO_SignedShares1")
                        && _self.model.get("BusinessConditionVO_Ceded")
                            &&_self.model.get("BusinessConditionVO_CedentRetention")) {
                        _self.model.set('BusinessConditionVO_SignedShares2',parseFloat(parseFloat(_self.model.get("BusinessConditionVO_SignedShares1"))/(_self.model.get("BusinessConditionVO_Ceded"))).toFixed(4));
                    }
                },300)});
                this.model.addPostChangeListener("BusinessConditionVO_SignedShares2",function(model){setTimeout(function(model){
                    var SignedShare1 = _self.model.get("BusinessConditionVO_SignedShares1");
                    var SignedShare2 = _self.model.get("BusinessConditionVO_SignedShares2");
                    var Ceded = _self.model.get("BusinessConditionVO_Ceded");
                    if(SignedShare1 && SignedShare2
                        &&_self.model.get("BusinessConditionVO_Ceded")
                        &&_self.model.get("BusinessConditionVO_CedentRetention")){
                        if(parseFloat(SignedShare1).toFixed(4) != parseFloat(parseFloat(Ceded) * parseFloat(SignedShare2)).toFixed(4));
                        _self.model.set('BusinessConditionVO_SignedShares1',parseFloat(parseFloat(Ceded) * parseFloat(SignedShare2)).toFixed(4));
                    }
                },300)});
                $page.controller.perValidate(_self.model, "BusinessConditionVO_RiPercentage");
                $page.controller.perValidate(_self.model, "BusinessConditionVO_ProfitPercentage");
                $page.controller.perValidate(_self.model, "BusinessConditionVO_ExpensesPercentage");
                $page.controller.perValidate(_self.model, "BusinessConditionVO_PercentOfPremium");
                $page.controller.perValidate(_self.model, "BusinessConditionVO_Ceded");
                $page.controller.perValidate(_self.model, "BusinessConditionVO_CedentRetention");
                $page.controller.perValidate(_self.model, "BusinessConditionVO_WrittenShare1");
                $page.controller.perValidate(_self.model, "BusinessConditionVO_SignedShares1");
                $page.controller.perValidate(_self.model, "BusinessConditionVO_WrittenShare2");
                $page.controller.perValidate(_self.model, "BusinessConditionVO_SignedShares2");

            }
        }
    };

    var restService = {
        isEnabled: function () {
            var isEnabled = true;
            if (this.model.get("BusinessConditionVO_OperateType") == 0) {
                isEnabled = false;
            }
            return isEnabled;
        },
        isVisible: function () {
            var isVisible = true;
            if (this.model.get("BusinessConditionVO_OperateType") == 0) {
                isVisible = false;
            }
            return isVisible;
        },

        perValidate: function (_model, data) {
            var num=data;
            this.model.addPostChangeListener(num, function(model){setTimeout(function (model) {
                var _data = _model.get(num);
                if (_data > 1) {
                    NConfirm.getConfirmModal().show({
                        title: 'System Message',
                        disableClose: true,
                        messages: ['Percentage must be not more than 100%.']
                    });
                    _model.set(num, "");
                }
            },300)});

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
            var isSaved = true;
            var _self = this;
            //var riPercentage=_self.model.get("RiPercentage");
            //_self.model.set("RiPercentage",riPercentage/100);
            var done = function (data) {
                isSaved = true;
                if (typeof needAlert == 'undefined' || needAlert) {
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Save Successfully.']
                    });
                }
                delete data.deletePremiumList;
                delete data.deleteCurrencyList;
                delete data.deleteDeductionsList;
                delete data.deleteReinstateItemList;
                delete data.deleteLimitItemList;
                delete data.deleteLimitEventList;
                _self.model.mergeCurrentModel({BusinessConditionVO: data});
                _self.form.forceUpdate();
            }.bind(this);
            var fail = function (data) {
                NConfirm.getConfirmModal().show({
                    title: 'Alert',
                    disableClose: true,
                    messages: ['Save fail.']
                });
                isSaved = false;
            }.bind(this);
            console.log('Business Condition.Save:');
            console.log(JSON.stringify(this.model.getCurrentModel().BusinessConditionVO));
            $page.service.save(this.model.getCurrentModel().BusinessConditionVO, false, false, done, fail);
            return isSaved;
        },
        submit: function () {
            //this.model.validate();
            //if (this.model.hasError() == true) {
            //    NConfirm.getConfirmModal().show({
            //        title: 'System Message',
            //        disableClose: true,
            //        messages: ['Please fill in all mandatory information.']
            //    });
            //    return false;
            //}
            if (this.save(true)) {
                this.exit();
            }
        },
        exit: function () {
            var urlData = $pt.getUrlData();
            var type = urlData.Type;
            var contractCategory = urlData.contractCategory;
            var url = "";
            if (type && type == '1') {
                url = "contractHome.html?contCompId=" + this.model.get("BusinessConditionVO_ContCompId")
                    + "&operateType=" + this.model.get("BusinessConditionVO_OperateType");
            } else if (type && type == '2') {
                url = "section.html?contractNature=" + this.model.get("BusinessConditionVO_ContractNature")
                    + "&operateType=" + this.model.get("BusinessConditionVO_OperateType")
                    + "&parentId=" + this.model.get("BusinessConditionVO_ParentId")
                    + "&contCompId=" + this.model.get("BusinessConditionVO_ContCompId")
                    + "&contractCategory=" + contractCategory;
            } else if (type && type == '3') {
                url = "subsection.html?contractNature=" + this.model.get("BusinessConditionVO_ContractNature")
                    + "&operateType=" + this.model.get("BusinessConditionVO_OperateType")
                    + "&contId=" + this.model.get("BusinessConditionVO_ContId")
                    + "&parentId=" + this.model.get("BusinessConditionVO_ParentId")
                    + "&contCompId=" + this.model.get("BusinessConditionVO_ContCompId")
                    + "&contractCategory=" + contractCategory;
            }
            window.location.href = url;
        },
        load: function (model) {
            var _self = this;
            var afterLoad = function (data) {
                _self.model.mergeCurrentModel({BusinessConditionVO: data});
                _self.form.forceUpdate();
            }.bind(this);
            $page.service.load(model.getCurrentModel().BusinessConditionVO, afterLoad);
        },
        initialBCInfo: function (mainCurrency) {
            var _self = this;
            if (this.model.get("BusinessConditionVO_ContractNature") == '1') {
                this.model.add("BusinessConditionVO_CurrencyList", $.extend(true, {}, {
                    CurrencyType: mainCurrency,
                    MainCurrencyType: true
                }));
                this.model.add("BusinessConditionVO_EpiList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "1"
                }));
                this.model.add("BusinessConditionVO_TerrorismList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "2"
                }));
            } else if (this.model.get("BusinessConditionVO_ContractNature") == '2') {
                this.model.add("BusinessConditionVO_CurrencyList", $.extend(true, {}, {
                    CurrencyType: mainCurrency,
                    MainCurrencyType: true
                }));
                this.model.add("BusinessConditionVO_SupiList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "3"
                }));
                this.model.add("BusinessConditionVO_FloatPremiumList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "4"
                }));
                this.model.add("BusinessConditionVO_FixRateList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "5"
                }));
                this.model.add("BusinessConditionVO_SwingRateList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "6"
                }));
                this.model.add("BusinessConditionVO_MinimumList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "7"
                }));
            }
            this.model.addPostChangeListener("BusinessConditionVO_NoOfPayment", function(model){setTimeout(function (model) {
                //var noPayment=_self.model.get("NoOfPayment");
                var noPayment = _self.model.get("BusinessConditionVO_NoOfPayment");
                if (noPayment == 3) {
                    noPayment = 4;
                }
                else if (noPayment == 4) {
                    noPayment = 12;
                }
                var paymentScheduleList = _self.model.get("BusinessConditionVO_PaymentScheduleList");
                var current = _self.model.get("BusinessConditionVO_CurrencyList");
                //var current = _self.model.getCurrentModel().BusinessConditionVO.CurrencyList[0].CurrencyType;
                //console.log(current);
                _self.model.set("BusinessConditionVO_PaymentScheduleList", []);
                var i, j;
                        for (j=0; j < current.length; j++) {
                            //console.log(current.length);
                            for (i = 0; i < noPayment; i++) {
                            _self.model.add("BusinessConditionVO_PaymentScheduleList", $.extend(true, {}, {
                                Currency:_self.model.getCurrentModel().BusinessConditionVO.CurrencyList[j].CurrencyType,
                                ItemType: "8"
                            }));
                        }
                }

            },300)});
            this.model.addPostChangeListener("BusinessConditionVO_NoOfInstallment", function(model){setTimeout(function (model) {
                var noInstallment = _self.model.get("BusinessConditionVO_NoOfInstallment");
                var depositScheduleList = _self.model.get("BusinessConditionVO_DepositScheduleList");
                var currency = _self.model.get("BusinessConditionVO_CurrencyList");
                _self.model.set("BusinessConditionVO_DepositScheduleList", []);
                if (noInstallment == 3) {
                    noInstallment = 4;
                }
                else if (noInstallment == 4) {
                    noInstallment = 12;
                }
                var i, j;
                for (j = 0; j < currency.length; j++) {
                    for (i = 0; i < noInstallment; i++) {
                            _self.model.add("BusinessConditionVO_DepositScheduleList", $.extend(true, {}, {
                                Currency: _self.model.getCurrentModel().BusinessConditionVO.CurrencyList[j].CurrencyType,
                                ItemType: "9"
                            }));

                    }
                }
            },300)});
        }
    }

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, initial, restService));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));
