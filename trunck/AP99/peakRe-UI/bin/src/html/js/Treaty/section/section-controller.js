(function (context) {
    var $page = $pt.getService(context, '$page');
    var initial = {
        initializeData: function () {
            var urlData = $pt.getUrlData();
            $page.exitBool = urlData.Exit;
            this.model = $pt.createModel($page.model.createBaseModel(), $page.validator.sectionValidate());
            this.viewModelForArea = $pt.createModel({});
            this.viewModelForInstallNo = $pt.createModel({1: null,2:null,3:null});
            this.model.mergeCurrentModel(urlData);
            // Initial BusinessConditionVO
            this.model.set("BusinessConditionVO_OperateType", urlData.OperateType);
            this.model.set("BusinessConditionVO_DefinedIn", '3');
            this.model.set("BusinessConditionVO_ShareDefine", '1');
            this.model.set("BusinessConditionVO_PremiumCalcMethod", '1');
            this.model.set("BusinessConditionVO_LossCalcMethod", '1');
            this.model.set("BusinessConditionVO_ClausesRequiredList", [11, 12, 13, 14, 15, 16]);

            var _self = this;
            if (this.model.get("OperateId")) {
                this.loadSectionInfoForLog({
                    ContCompId: this.model.get("ContCompId"),
                    OperateId: this.model.get("OperateId")
                }, false, false, function (data, textStatus, jqXHR) {
                    _self.model.mergeCurrentModel(data);
                    _self.model.set("BusinessConditionVO_ContractNature", data.ContractNature);
                    if (_self.model.get("BusinessConditionVO_ContractNature") == '2') {
                        _self.mergeReinstate(data);
                        if(_self.model.get("BusinessConditionVO_PremiumType")){
                          _self.viewModelForInstallNo.set(_self.model.get("BusinessConditionVO_PremiumType"),_self.model.get("BusinessConditionVO_NoOfInstallment"));
                        }
                    }
                    _self.resetViewModelForArea();
                }, null);

            } else {
                this.loadSectionInfo({
                    ContCompId: this.model.get("ContCompId"),
                    ParentId: this.model.get("ParentId")
                }, false, false, function (data, textStatus, jqXHR) {
                
                    _self.model.mergeCurrentModel(data);
                    _self.model.set("BusinessConditionVO_ContractNature", data.ContractNature);
                    if (_self.model.get("BusinessConditionVO_ContractNature") == '2') {
                        _self.mergeReinstate(data);
                        if(_self.model.get("BusinessConditionVO_PremiumType")){
                          _self.viewModelForInstallNo.set(_self.model.get("BusinessConditionVO_PremiumType"),_self.model.get("BusinessConditionVO_NoOfInstallment"));
                        }
                      }
                    _self.resetViewModelForArea();
                }, null);
            }
            $page.StartDate = this.model.get("ReinsStarting");
            /*
             * if contractNature is non-proportional,Cession Type is defaulted as XOL
             */
            if (this.model.get("ContractNature") == '2' && (this.model.get("ShareType") == undefined || this.model.get("ShareType") == null)) {
                this.model.set("ShareType", '3');
            }
            if (this.model.get("BusinessConditionVO_Bcempty") == undefined || this.model.get("BusinessConditionVO_Bcempty") == null || this.model.get("BusinessConditionVO_Bcempty") != false) {
                this.initialBCInfo(this.model.get("MainCurrency"));
            }

        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            if (this.model.get("OperateType") == 0 || this.model.get("OperateType") == 5) {
                layout = $pt.createFormLayout($page.layout.createLayout());
                form = <NForm model={this.model} layout={layout} view={true}/>;
            }
            this.form = ReactDOM.render(form, document.getElementById('main'));
            /**
             * SupiRate -> Rate -> FixedRate
             */
            var _self = this;
            if ($pt.getUrlData().ContractNature == 2) {
              this.model.addPostChangeListener("BusinessConditionVO_ReinNum", function (model) {
                  var reinNum = model.new;
                  var current = _self.model.get("BusinessConditionVO_ReinstateSpecificList");
                  var i;
                  for(i=0;i<current.length;i++){
                    if(current[i] && current[i].ItemId){
                      _self.model.add("BusinessConditionVO_DeleteReinstateItemList",current[i]);
                    }
                  }
                  _self.model.set("BusinessConditionVO_ReinstateSpecificList", []);
                  for (i = 0; i < reinNum; i++) {
                      _self.model.add("BusinessConditionVO_ReinstateSpecificList", $.extend(true, {}, {
                          ReinRate: 1, Reinstate: i + 1,
                          ReinTime: '0', ReinAmount: '1'
                      }));
                  }
              });
              this.model.addPostChangeListener("BusinessConditionVO_NoOfInstallment", function (model) {
                  var setFlag = $page.businessCalculator.getTheScheduleSetFlag($page.controller.model.get("ParentId"));
                  $page.controller.viewModelForInstallNo.set($page.controller.model.get("BusinessConditionVO_PremiumType") , model.new);
                  return $page.businessCalculator.depositSchedule(model, setFlag);
              });
              this.model.addPostChangeListener("BusinessConditionVO_Rate", function (model) {
                  return $page.businessCalculator.calculatePremiumRate(model);
              });
              this.model.addPostChangeListener("BusinessConditionVO_PremiumType", function (model) {
                  if(model.old && model.old != model.new){
                    NConfirm.getConfirmModal().show({
                        title: 'Alert',
                        disableClose: true,
                        messages: ['Premium Type is changed, please check and update premium information of this section.']
                    });
                  }
              });
              /**
               * SupiRate -> Prov.R -> SwingRate
               */
              this.model.addPostChangeListener("BusinessConditionVO_ProvisionalRate", function (model) {
                  return $page.businessCalculator.calculatePremiumProvisionalRate(model);
              });
            }
            else if ($pt.getUrlData().ContractNature == 1) {
                this.model.addPostChangeListener("BusinessConditionVO_Ceded", function (evt) {
                    return $page.businessCalculator.calculationCeded(evt);
                });
                this.model.addPostChangeListener("BusinessConditionVO_CedentRetention", function (evt) {
                    return $page.businessCalculator.calcualtionCedentRetention(evt);
                });
                this.model.addPostChangeListener("BusinessConditionVO_WrittenShare1", function (model) {
                    return $page.businessCalculator.calculationFrom100Share("BusinessConditionVO_WrittenShare2", model);
                });
                this.model.addPostChangeListener("BusinessConditionVO_WrittenShare2", function (model) {
                    return $page.businessCalculator.calculationFromCededShare("BusinessConditionVO_WrittenShare1", model);
                });
                this.model.addPostChangeListener("BusinessConditionVO_SignedShares1", function (model) {
                    return $page.businessCalculator.calculationFrom100Share("BusinessConditionVO_SignedShares2", model);
                });
                this.model.addPostChangeListener("BusinessConditionVO_SignedShares2", function (model) {
                    return $page.businessCalculator.calculationFromCededShare("BusinessConditionVO_SignedShares1", model);
                });
            }
        },
        mergeReinstate: function (data) {
            var _self = this;

           if(data.BusinessConditionVO !=undefined){
               var reinstateList = data.BusinessConditionVO.ReinstateList ? data.BusinessConditionVO.ReinstateList : [];
               var reinstateSpecificList = [];
               var reinstateUnlimitedList = [];
               if (data.BusinessConditionVO.ReinType == '2') {
                   reinstateSpecificList = reinstateList;
               } else {
                   reinstateUnlimitedList = reinstateList;
               }
               _self.model.mergeCurrentModel({
                   BusinessConditionVO: {
                       ReinstateSpecificList: reinstateSpecificList,
                       ReinstateUnlimitedList: reinstateUnlimitedList
                   }
               });
           }

        },
        resetViewModelForArea : function(){
          var coveredList = this.model.get("CoveredList");
          var tempCoveredList = [];
          var i = 0;
          if(coveredList && coveredList.length > 0){
            var coveredListCodes = $page.codes.CoveredArea.listAllChildren();
            var tempCovered;
            var trasver = function(item){
              var temp = coveredListCodes[item]
              tempCoveredList.push(item);
              if(temp &&temp.children != 'undefined' && temp.children){
                temp.children.forEach(function(index){
                  trasver(index.id);
                })
              }
            }
            for(i; i<coveredList.length;i++){
               trasver(coveredList[i]);
            }
            tempCoveredList = tempCoveredList.unique();
          }
          this.viewModelForArea.mergeCurrentModel({"CoveredList":tempCoveredList});
        },
          copyAreaInfoToModel : function(){
            var coveredList = this.viewModelForArea.get("CoveredList");
            var finalList = [];
            if(coveredList && coveredList.length > 0){
              finalList = coveredList.slice();
              var coveredListCodes = $page.codes.CoveredArea.listAllChildren();
              var tempCoveredList = [];
              var trasver = function(item){
                var temp = coveredListCodes[item];
                if(temp && temp.children != 'undefined' && temp.children){
                  temp.children.forEach(function(index){
                    trasver(index);
                    tempCoveredList.push(index.id);
                  })
                }
              }
              var i = 0;
              for(;i<coveredList.length;i++){
                trasver(coveredList[i]);
              }
              tempCoveredList.forEach(function(index){
                var deleteIndex = finalList.findIndex(function(item){return item == index;});
                if(deleteIndex != -1){
                  finalList.splice(deleteIndex,1);
                }
              })
              finalList = finalList.unique();
            }
            this.model.mergeCurrentModel({"CoveredList":finalList});
          },
    };
    var restService = {
        loadSectionInfo: function (criteria, quiet, async, done, fail) {
            $page.service.loadSectionInfo(criteria, quiet, async, done, fail);
        },
        loadSectionInfoForLog: function (criteria, quiet, async, done, fail) {
            $page.service.loadSectionInfoForLog(criteria, quiet, async, done, fail);
        },
        isVisible: function () {
            var isVisible = true;
            if (this.model.get("OperateType") == 0 || this.model.get("OperateType") == 5) {
                isVisible = false;
            }
            return isVisible;
        },
        validate: function (model) {
            var isNormal = true;
            if (model.get("ShareType") == undefined || model.get("ShareType") == null) {
                NConfirm.getConfirmModal().show({
                    title: 'Attention',
                    disableClose: true,
                    messages: ['Please input Cession Type.']
                });
                isNormal = false;
            } else {
                if (model.get("SectionName") == undefined || model.get("SectionName") == null || model.get("SectionName") == "") {
                    NConfirm.getConfirmModal().show({
                        title: 'Attention',
                        disableClose: true,
                        messages: ['Please input Section Name.']
                    });
                    isNormal = false;
                }
            }
            return isNormal;
        },
        isExchangeType: function () {
            var _self = this;
            var isSaved = true;
            _self.model.get("BusinessConditionVO_CurrencyList").forEach(function (item) {
                if (item.ExchangeType == '1' && (item.CurrencyRate == undefined || item.CurrencyRate == null || item.CurrencyRate == "")) {
                    isSaved = false;
                } else if (item.ExchangeType == '2' && (item.CurrencyDate == undefined || item.CurrencyDate == null || item.CurrencyDate == "")) {
                    isSaved = false;
                }
            });
            return isSaved;
        },
        save: function (needAlter) {
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
            var isSaved = false;
            if (!this.isExchangeType()) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in Currency Rate or Currency Date.']
                });
                return false;
            }
            this.copyAreaInfoToModel();
            var modelVO = this.model.__model.BusinessConditionVO;
            if($page.businessCalculator.clearUnusedBCInfo(modelVO)){
              var afterSave = function (data, textStatus, jqXHR) {
                  if (needAlter) {
                      NConfirm.getConfirmModal().show({
                          title: 'Message',
                          disableClose: true,
                          messages: ['Save successful.']
                      });
                  }
                  _self.model.mergeCurrentModel(data);
                  isSaved = true;
              }.bind(this);
              $page.service.save(this.model.getCurrentModel(), false, false, afterSave);
            }
            if(_self.model.get("BusinessConditionVO_ContractNature") == 2){
              _self.mergeReinstate(_self.model.getCurrentModel());
              _self.viewModelForInstallNo = $pt.createModel({1:null,2:null,3:null});
              if(_self.model.get("BusinessConditionVO_PremiumType")){
                _self.viewModelForInstallNo.set(_self.model.get("BusinessConditionVO_PremiumType"),_self.model.get("BusinessConditionVO_NoOfInstallment"));
              }
            }
            _self.form.forceUpdate();
            return isSaved;
        },
        retrocessionClick: function (model) {
            if (model.get("OperateType") != 0) {
                if (!this.validate(model) || !this.save()) {
                    return;
                }
            }
            var url = "retrocession_edit.html?ContCompId=" + model.get("ContCompId")
                + "&ContractNature=" + model.get("ContractNature")
                + "&OperateType=" + model.get("OperateType")
                + "&SubmitType=1";
            if (model.get("OperateId")) {
                url += "&OperateId=" + model.get("OperateId");
            }
            if ($page.exitBool == 1) {
                url += "&Exit=1";
            }
            window.location.href = url;
        },
        pricingClick: function (model) {
            if (model.get("OperateType") != 0) {
                if (!this.validate(model) || !this.save()) {
                    return;
                }
            }
            var url = "pricingEstimation.html?ContCompId=" + model.get("ContCompId")
                + "&OperateType=" + model.get("OperateType");
            if ($page.exitBool == 1) {
                url += "&Exit=1";
            }
            window.location.href = url;
        },
        isCurrency: function (mainCurrency) {
            // if (mainCurrency == 'USD') {
            //     return 1;
            // }
            // else {
            //     return '';
            // }
            return 1;
        },

        loadContractInfo: function (criteria, quiet, async, done) {
            $page.service.loadContractInfo(criteria, quiet, async, done);
        },
        getInstementDate: function (month) {
            var startDate = new Date($page.StartDate);
            var endDate = new Date();
            var dueDate = new Date();
            console.log(startDate);
            var arr = $page.StartDate.split("-");
            if ((arr[1] + month) > 12) {
                dueDate.setFullYear(startDate.getFullYear() + 1);
                dueDate.setMonth(startDate.getMonth() + month - 12);
            } else {
                dueDate.setMonth(startDate.getMonth() + month);
            }
            return dueDate;
        },
        initialBCInfo: function (mainCurrency) {

            var _self = this;
            if (this.model.get("BusinessConditionVO_ContractNature") == '1') {
                this.model.add("BusinessConditionVO_CurrencyList", $.extend(true, {}, {
                    CurrencyType: mainCurrency, MainCurrencyType: true, ExchangeType: '1',
                    CurrencyRate: $page.controller.isCurrency(mainCurrency)
                }));
                this.model.add("BusinessConditionVO_EpiList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "1"
                }));
                this.model.add("BusinessConditionVO_TerrorismList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "2"
                }));
                this.model.add("BusinessConditionVO_LimitEventQsList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "1"
                }));
                this.model.add("BusinessConditionVO_LimitEventSurplusList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "2"
                }));
                this.model.add("BusinessConditionVO_LimitQsList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "1"
                }));
                this.model.add("BusinessConditionVO_LimitSurplusList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "2"
                }));
                this.model.add("BusinessConditionVO_LimitCbList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "7"
                }));
                this.model.add("BusinessConditionVO_LimitEventCbList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "7"
                }));
            } else if (this.model.get("BusinessConditionVO_ContractNature") == '2') {
                this.model.add("BusinessConditionVO_CurrencyList", $.extend(true, {}, {
                    CurrencyType: mainCurrency, MainCurrencyType: true, ExchangeType: '1',
                    CurrencyRate: $page.controller.isCurrency(mainCurrency)
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
                this.model.add("BusinessConditionVO_MinimumPremiumList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "7"
                }));
                this.model.add("BusinessConditionVO_DepositPremiumList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "8"
                }));

                this.model.add("BusinessConditionVO_LimitRegularList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "3"
                }));
                this.model.add("BusinessConditionVO_LimitStopList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "5"
                }));
                this.model.add("BusinessConditionVO_LimitStopPerList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "6"
                }));
                // this.model.add("BusinessConditionVO_LimitEventList", $.extend(true, {}, {Currency: mainCurrency}));
                this.model.add("BusinessConditionVO_LimitEventXolList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "3"
                }));
                this.model.add("BusinessConditionVO_LimitEventStoplossList", $.extend(true, {}, {
                    Currency: mainCurrency,
                    ItemType: "4"
                }));

            }


        }

    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, initial, restService));
    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));
