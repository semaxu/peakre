(function (context) {
    var $page = $pt.getService(context, '$page');
    var BusinessCalculator = jsface.Class({

      checkSharePanel : function(evt){
        var _self = evt.model.__parent.getCurrentModel();
        var writtenShare = _self.BusinessConditionVO.WrittenShare1;
        var signedShare = _self.BusinessConditionVO.SignedShares1;
        if(!writtenShare && !signedShare){
          NConfirm.getConfirmModal().show({
              title: 'Alert',
              disableClose: true,
              messages: ['Complete Necessary Item of Share Panel First.']
          });
          return false;
        }else {
          return true;
        }
      },
      calculationPremiumAmount : function(writtenName,signedName,evt){
        var _self = evt.model.__parent.getCurrentModel();
        if(_self.BusinessConditionVO.ShareDefine != 2){
        var writtenShare = _self.BusinessConditionVO.WrittenShare1;
        var signedShare = _self.BusinessConditionVO.SignedShares1;
        var amount = evt.new;
        if(!amount || isNaN(amount)){
          evt.model.set(writtenName,"");
          evt.model.set(signedName,"");
        }else if(this.checkSharePanel(evt)){
          amount = parseFloat(amount).toFixed(2);
          if(writtenShare){
            var OurWrittenShare = evt.model.get(writtenName);
            var newWrittenAmount = (amount * parseFloat(writtenShare)).toFixed(2);
            var oldWrittenAmount = !OurWrittenShare ? 0 : parseFloat(OurWrittenShare).toFixed(2);
            if((oldWrittenAmount / parseFloat(writtenShare)).toFixed(2) != amount){
              evt.model.set(writtenName,(newWrittenAmount+"").currencyFormat(2));
            }
          }
          if(signedShare){
            var OurSignedShare = evt.model.get(signedName);
            var newSignedAmount = (amount * parseFloat(signedShare)).toFixed(2);
            var oldSignedAmount = !OurSignedShare ? null : parseFloat(OurSignedShare).toFixed(2)
            if((oldSignedAmount / parseFloat(signedShare)).toFixed(2) != amount){
              evt.model.set(signedName,(newSignedAmount+"").currencyFormat(2))
            }
          }
        }
      }
      },
      calculationPremiumAmountWithPer: function(writtenName,signedName,percentageName,evt){
        var _self = evt.model.__parent.getCurrentModel();
        if(_self.BusinessConditionVO.ShareDefine != 2){
        var writtenShare = _self.BusinessConditionVO.WrittenShare1;
        var signedShare = _self.BusinessConditionVO.SignedShares1;
        var amount = evt.new;
        if(!amount || isNaN(amount)){
          evt.model.set(writtenName,"");
          evt.model.set(signedName,"");
          evt.model.set(percentageName,"");
        }else if(this.checkSharePanel(evt)){
          amount = parseFloat(amount).toFixed(2);
          if(writtenShare){
            var OurWrittenShare = evt.model.get(writtenName);
            var newWrittenAmount = (amount * parseFloat(writtenShare)).toFixed(2);
            var oldWrittenAmount = !OurWrittenShare ? 0 : parseFloat(OurWrittenShare).toFixed(2);
            if((oldWrittenAmount / parseFloat(writtenShare)).toFixed(2) != amount){
              evt.model.set(writtenName,(newWrittenAmount+"").currencyFormat(2));
            }
          }
          if(signedShare){
            var OurSignedShare = evt.model.get(signedName);
            var newSignedAmount = (amount * parseFloat(signedShare)).toFixed(2);
            var oldSignedAmount = !OurSignedShare ? null : parseFloat(OurSignedShare).toFixed(2)
            if((oldSignedAmount / parseFloat(signedShare)).toFixed(2) != amount){
              evt.model.set(signedName,(newSignedAmount+"").currencyFormat(2));
            }
          }
          var premiumType = _self.BusinessConditionVO.PremiumType;
          var fixRateList = _self.BusinessConditionVO.FixRateList;
          var swingRateList = _self.BusinessConditionVO.SwingRateList;
          var currency = evt.model.get("Currency");
          if(premiumType == 2){
            fixRateList.forEach(function(item){
              if(currency == item.Currency){
                if(item.Amount && item.Amount != 0){
                  var newPer = (evt.new / parseFloat(item.Amount)).toFixed(4);
                  evt.model.set(percentageName,(newPer+"").currencyFormat(4));
                }
              }
            })
          }else if(premiumType == 3){
            swingRateList.forEach(function(item){
              if(currency == item.Currency){
                if(item.Amount && item.Amount != 0){
                  var newPer = (evt.new / parseFloat(item.Amount)).toFixed(4);
                  evt.model.set(percentageName,(newPer+"").currencyFormat(4));
                }
              }
            })
          }
        }
      }
      },
      calculationWrittenPremium : function(totalAmountName,signedName,evt){
        var _self = evt.model.__parent.getCurrentModel();
        if(_self.BusinessConditionVO.ShareDefine != 2){
        var writtenShare = _self.BusinessConditionVO.WrittenShare1;
        var signedShare = _self.BusinessConditionVO.SignedShares1;
        var OurWrittenShare = evt.new;
        if(!OurWrittenShare || isNaN(OurWrittenShare)){
          evt.model.set(totalAmountName,"");
          evt.model.set(signedName,"");
        }else if(this.checkSharePanel(evt)){
          if(writtenShare){
            var amount = evt.model.get(totalAmountName);
            var newTotalAmount = (parseFloat(OurWrittenShare) / parseFloat(writtenShare)).toFixed(2);
            var oldTotalAmount = amount ? parseFloat(amount).toFixed(2) : null;
            if((oldTotalAmount * parseFloat(writtenShare)).toFixed(2) != parseFloat(OurWrittenShare).toFixed(2)){
              evt.model.set(totalAmountName,newTotalAmount);
              if(signedShare){
                var OurSignedShare = evt.model.get(signedName);
                var newSignedAmount = (newTotalAmount * parseFloat(signedShare)).toFixed(2);
                var oldSignedAmount = !OurSignedShare ? null : parseFloat(OurSignedShare).toFixed(2)
                if((oldSignedAmount / parseFloat(signedShare)).toFixed(2) != newTotalAmount){
                  evt.model.set(signedName,(newSignedAmount+"").currencyFormat(2))
                }
              }
            }
          }

        }
      }
      },
      calculationSignedPremium : function(totalAmountName,writtenName,evt){
        var _self = evt.model.__parent.getCurrentModel();
        if(_self.BusinessConditionVO.ShareDefine != 2){
        var writtenShare = _self.BusinessConditionVO.WrittenShare1;
        var signedShare = _self.BusinessConditionVO.SignedShares1;
        var OurSignedShare = evt.new;
        if(!OurSignedShare || isNaN(OurSignedShare)){
          evt.model.set(totalAmountName,"");
          evt.model.set(writtenName,"");
        }else if(this.checkSharePanel(evt)){
          if(signedShare){
            var amount = evt.model.get(totalAmountName);
            var newTotalAmount = (parseFloat(OurSignedShare) / parseFloat(signedShare)).toFixed(2);
            var oldTotalAmount = amount ? parseFloat(amount).toFixed(2) : null;
            if((oldTotalAmount * parseFloat(signedShare)).toFixed(2) != parseFloat(OurSignedShare).toFixed(2)){
              evt.model.set(totalAmountName,newTotalAmount);
              if(writtenShare){
                var OurWrittenShare = evt.model.get(writtenName);
                var newWrittenShare = (newTotalAmount * parseFloat(writtenShare)).toFixed(2);
                var oldWrittenShare = !OurWrittenShare ? null : parseFloat(OurWrittenShare).toFixed(2)
                if((oldWrittenShare / parseFloat(writtenShare)).toFixed(2) != newTotalAmount){
                  evt.model.set(signedName,(newWrittenShare+"").currencyFormat(2))
                }
              }
            }
          }
        }
      }
      },
            calculationRateAmount:function(evt){
                var currency = evt.model.get("Currency");
                var _self = evt.model.__parent;
                if(_self.get("BusinessConditionVO_ShareDefine") != 2){
                var supiList = _self.get("BusinessConditionVO_SupiList");
                var rate = _self.get("BusinessConditionVO_Rate");
                var writtenShare = _self.get("BusinessConditionVO_WrittenShare1");
                var signedShare = _self.get("BusinessConditionVO_SignedShares1");
                var premiumType = _self.get("BusinessConditionVO_PremiumType");
                if(premiumType == 3){
                  rate = _self.get("BusinessConditionVO_ProvisionalRate");
                }
                var signedAmount;
                var writtenAmount;
                var totalAmount;

                if(premiumType == 2 || premiumType == 3){
                    supiList.forEach(function(supiRecord){
                        if(currency == supiRecord.Currency){
                          if(!supiRecord.Amount || isNaN(supiRecord.Amount) || !rate || isNaN(rate)){
                            signedAmount = "";
                            writtenAmount = "";
                            totalAmount = "";
                          } else {
                            totalAmount = (parseFloat(rate) * parseFloat(supiRecord.Amount)+ "").currencyFormat(2);
                            if (0 != writtenShare && undefined != writtenShare && null != writtenShare) {
                                writtenAmount =  ((totalAmount * parseFloat(writtenShare)) + "").currencyFormat(2);
                            }
                            if (0 != signedShare && undefined != signedShare && null != signedShare) {
                                signedAmount = ((totalAmount * parseFloat(signedShare)) + "").currencyFormat(2);
                            }
                          }
                          evt.model.set("Amount",totalAmount);
                          evt.model.set("OurWrittenShare",writtenAmount);
                          evt.model.set("OurSignedShare",signedAmount);
                        }
                    })
                }
                $page.controller.form.forceUpdate();
              }
            },
      calculationSUPIAmountChange : function(evt){
        var amount = evt.model.get("Amount");
        var currency = evt.model.get("Currency");
        var _self = evt.model.__parent;
        if(_self.get("BusinessConditionVO_ShareDefine") != 2){
        var premiumList = _self.get("BusinessConditionVO_FixRateList");
        var rate = _self.get("BusinessConditionVO_Rate");
        if(premiumType == 3){
          rate = _self.get("BusinessConditionVO_ProvisionalRate");
          premiumList = _self.get("BusinessConditionVO_SwingRateList");
        }
        var writtenShare = _self.get("BusinessConditionVO_WrittenShare1");
        var signedShare = _self.get("BusinessConditionVO_SignedShares1");
        var premiumType = _self.get("BusinessConditionVO_PremiumType");
        var signedAmount;
        var writtenAmount;
        var totalAmount;
        if(!amount || isNaN(amount) || !rate || isNaN(rate)){
          signedAmount = "";
          writtenAmount = "";
          totalAmount = "";
        } else {
          totalAmount = (parseFloat(rate) * parseFloat(amount)+ "").currencyFormat(2);
          if (0 != writtenShare && undefined != writtenShare && null != writtenShare) {
              writtenAmount =  ((totalAmount * parseFloat(writtenShare)) + "").currencyFormat(2);
          }
          if (0 != signedShare && undefined != signedShare && null != signedShare) {
              signedAmount = ((totalAmount * parseFloat(signedShare)) + "").currencyFormat(2);
          }
        }
        if(premiumType == 2 || premiumType == 3){
            premiumList.forEach(function(fixRecord){
                if (currency == fixRecord.Currency) {
                    fixRecord.Amount = totalAmount;
                    fixRecord.OurWrittenShare = writtenAmount;
                    fixRecord.OurSignedShare = signedAmount;
                }});
        }
        $page.controller.form.forceUpdate();
      }
      },
      calcualtionCedentRetention :function (model) {
              var CedentRetention = model.new;
              var Ceded = $page.controller.model.get("BusinessConditionVO_Ceded");
              if(CedentRetention){
                var tempCeded = parseFloat(1 - parseFloat(CedentRetention));
                if(!Ceded || parseFloat(Ceded).toFixed(4) != tempCeded.toFixed(4)){
                      $page.controller.model.set('BusinessConditionVO_Ceded', (tempCeded + '').currencyFormat(4));
                }
              }
      },
      calculationCeded : function(model){
        var ceded = model.new;
        var CedentRetention = $page.controller.model.get('BusinessConditionVO_CedentRetention');
        if (ceded) {
          var tempCedentRetention = parseFloat(1 - parseFloat(ceded));
          if(!CedentRetention || parseFloat(CedentRetention).toFixed(4) != tempCedentRetention.toFixed(4)){
                $page.controller.model.set('BusinessConditionVO_CedentRetention', (tempCedentRetention + '').currencyFormat(4));
          }
        }
      },
      calculationFrom100Share : function(calculateName,model){
        var WrittenShare1 = model.new;
        var _self = $page.controller.model;
        var Ceded = _self.get("BusinessConditionVO_Ceded");
        if (WrittenShare1 && Ceded){
          var WrittenShare2 =    _self.get(calculateName);
          var tempWrittenShare = parseFloat(parseFloat(WrittenShare1) / parseFloat(Ceded));
          if(!WrittenShare2 || (parseFloat(WrittenShare2).toFixed(4)!= tempWrittenShare.toFixed(4))){
            _self.set(calculateName,(tempWrittenShare+'').currencyFormat(4));
          }
        }
      },
      calculationFromCededShare : function(calculateName,model){
        var WrittenShare1 = model.new;
        var _self = $page.controller.model;
        var Ceded = _self.get("BusinessConditionVO_Ceded");
        if (WrittenShare1 && Ceded){
          var WrittenShare2 =    _self.get(calculateName);
          var tempWrittenShare = parseFloat(parseFloat(WrittenShare1) * parseFloat(Ceded));
          if(!WrittenShare2 || (parseFloat(WrittenShare2).toFixed(4)!= tempWrittenShare.toFixed(4))){
            _self.set(calculateName,(tempWrittenShare+'').currencyFormat(4));
          }
        }
      },
      calculatePremiumRate : function(model){
        if(!model.new || isNaN(model.new)){
          return;
        }
        var _self = $page.controller;
        if(_self.model.get("BusinessConditionVO_ShareDefine") != 2){
          var supiList =  _self.model.get("BusinessConditionVO_SupiList");
          var fixRateList = _self.model.get("BusinessConditionVO_FixRateList");
          var WrittenShare1 = _self.model.get("BusinessConditionVO_WrittenShare1");
          var SignedShare1 = _self.model.get("BusinessConditionVO_SignedShares1");

          if(supiList && fixRateList){
              supiList.forEach(function(supiRecord){
                  fixRateList.forEach(function(fixRecord){
                      if(supiRecord.Currency == fixRecord.Currency){
                          fixRecord.Amount = ((parseFloat(model.new)*parseFloat(supiRecord.Amount)) + "").currencyFormat(2);
                          if(WrittenShare1) {
                              fixRecord.OurWrittenShare = ((fixRecord.Amount * WrittenShare1) + "").currencyFormat(2);
                          }
                          if(SignedShare1){
                              fixRecord.OurSignedShare = ((fixRecord.Amount * SignedShare1)+"").currencyFormat(2);
                          }
                      }
                  })
              })
          }
          _self.form.forceUpdate();
        }
      },
      calculatePremiumProvisionalRate : function(model){
          var _self = $page.controller;
          if(_self.model.get("BusinessConditionVO_ShareDefine") != 2){
        var WrittenShare1 = _self.model.get("BusinessConditionVO_WrittenShare1");
        var SignedShare1 = _self.model.get("BusinessConditionVO_SignedShares1");
        var swingList = _self.model.get("BusinessConditionVO_SwingRateList");
        var supiList = _self.model.get("BusinessConditionVO_SupiList");
        if (swingList && supiList) {
            supiList.forEach(function (supiRecord) {
                swingList.forEach(function (swingRecord) {
                    if (supiRecord.Currency == swingRecord.Currency) {
                        swingRecord.Amount = ((parseFloat(model.new) * parseFloat(supiRecord.Amount))+"").currencyFormat(2);
                        if (WrittenShare1) {
                            swingRecord.OurWrittenShare = ((swingRecord.Amount * WrittenShare1)+"").currencyFormat(2);
                        }
                        if (SignedShare1) {
                            swingRecord.OurSignedShare = ((swingRecord.Amount * SignedShare1) + "").currencyFormat(2);
                        }
                    }
                });
            });
        }
        _self.form.forceUpdate();
      }
      },
      change100ShareAlterinfo: function(oldValue, newValue){
        if($page.controller.model.get("BusinessConditionVO_ShareDefine") == 2){
          return true;
        }
        if(oldValue && oldValue != newValue){
          NConfirm.getConfirmModal().show({
              title: 'Attention',
              disableClose: true,
              messages: ['Share proportion is changed, please update premium and limit information of this section. ']
          });
        }
        return true;
      },
            getInstementDate: function (month) {
                var startDate = new Date($page.StartDate);
                var installDateArray = [];
                installDateArray.push(startDate.dateFormat());
                if(month != 1){
                    var addAmonth = 12/month;
                    var i = 1;
                    var tempDate = startDate;
                    for(i;i<month;i++){
                        tempDate = new Date($page.StartDate);
                        tempDate.setMonth(startDate.getMonth() + addAmonth * i);
                        //startDate.setDate(startDate.getDate()+1);
                        //tempDate.setDate(startDate.getDate());
                        installDateArray.push(tempDate.dateFormat());
                    }
                }
                return installDateArray;
            },
            depositSchedule : function (model,setFlag) {
                 var _self = $page.controller.model;
                 var premiumType = _self.get("BusinessConditionVO_PremiumType");
                 var depositPremiumList = _self.get("BusinessConditionVO_DepositPremiumList");
                 var floatPremiumList = _self.get("BusinessConditionVO_FloatPremiumList");
                 var depositScheduleList = _self.get("BusinessConditionVO_DepositScheduleList");
                 // var depositSwingScheduleList = _self.get("BusinessConditionVO_DepositScheduleSwingList");
                 // var depositSwingPremiumList = _self.get("BusinessConditionVO_DepositSwingPremiumList");
                 var noInstallment = _self.get("BusinessConditionVO_NoOfInstallment");
                 var installDate = $page.businessCalculator.getInstementDate(noInstallment);
                 var i, j, x,m=1;
                 var depositAmount = 0;
                 // if(premiumType == 2) {
                 depositScheduleList.forEach(function(fixRecord){
                     if(fixRecord && fixRecord.ItemId && fixRecord.ItemId != 0){
                         _self.add("BusinessConditionVO_DeletePremiumList", fixRecord);
                     }
                 });
                  _self.set("BusinessConditionVO_DepositScheduleList",[]);
                 if(premiumType == 2 || premiumType == 3){
                   for ( i = 0; i < depositPremiumList.length; i++) {
                       m=1;
                       if(depositPremiumList[i].OurSignedShare && !isNaN(depositPremiumList[i].OurSignedShare)){
                         depositAmount = depositPremiumList[i].OurSignedShare / noInstallment;
                       } else if(depositPremiumList[i].OurWrittenShare && !isNaN(depositPremiumList[i].OurWrittenShare)){
                         depositAmount = depositPremiumList[i].OurWrittenShare / noInstallment;
                       }
                       for (j = 0; j < installDate.length; j++) {
                           _self.add("BusinessConditionVO_DepositScheduleList", $.extend(true, {}, {
                               Currency: depositPremiumList[i].Currency,
                               InstalNo: m++,
                               DueDate: setFlag ? installDate[j] : null,
                               ItemType: "9",
                               Amount: (depositAmount + "").currencyFormat(2),
                               Percentage: 1 / noInstallment
                           }));
                       }
                   }
                 }
                  if(premiumType == 1){
                    for(i = 0; i < floatPremiumList.length; i++){
                      m = 1;
                      if(floatPremiumList[i].OurSignedShare && !isNaN(floatPremiumList[i].OurSignedShare)){
                       depositAmount = floatPremiumList[i].OurSignedShare / noInstallment;
                     } else if(floatPremiumList[i].OurWrittenShare && !isNaN(floatPremiumList[i].OurWrittenShare)){
                       depositAmount = floatPremiumList[i].OurWrittenShare / noInstallment;
                     }
                     for (j = 0; j < installDate.length; j++) {
                         _self.add("BusinessConditionVO_DepositScheduleList", $.extend(true, {}, {
                             Currency: floatPremiumList[i].Currency,
                             InstalNo: m++,
                             DueDate: setFlag ? installDate[j] : null,
                             ItemType: "9",
                             Amount: (depositAmount + "").currencyFormat(2),
                             Percentage: 1 / noInstallment
                         }));
                     }
                    }
                  }
             },
             getTheScheduleSetFlag : function(contractId){
               var nowEndDate = null;
               $page.controller.loadContractInfo({
                   ContCompId: contractId
               }, false, false, function (data, textStatus, jqXHR) {
                   nowEndDate = data.ReinsEnding;
               });
               var startDate = new Date($page.controller.model.get("ReinsStarting"));
               var endDate = new Date(startDate);
               endDate.setFullYear(endDate.getFullYear() + 1);
               endDate.setDate(endDate.getDate() - 1);
               endDate = endDate.dateFormat();
               var setFlag = false;
               if (nowEndDate >= endDate) {
                   setFlag = true;
               }
               return setFlag;
             },
             clearUnusedBCInfo : function(modelVO){
               var _self = $page.controller;
               if (modelVO.LimitType == 4 && modelVO.AmountType == 1) {
                   _self.model.set("BusinessConditionVO_DeleteLimitItemList", modelVO.LimitStopPerList);
                   modelVO.LimitStopPerList = [];
               } else if (modelVO.LimitType == 4 && modelVO.AmountType == 2) {
                   _self.model.set("BusinessConditionVO_DeleteLimitItemList", modelVO.LimitStopList);
                   modelVO.LimitStopList = [];
               }
               if (modelVO.LimitType == 1) {
                   modelVO.LimitSurplusList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitItemList").push(item);
                       }
                   });
                   modelVO.LimitSurplusList = [];
                   modelVO.LimitEventSurplusList.forEach(function (item) {
                       if (item.EventId && item.EventId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitEventList").push(item);
                       }
                   });
                   modelVO.LimitEventSurplusList = [];
                   //LimitCbList
                   modelVO.LimitCbList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitItemList").push(item);
                       }
                   });
                   modelVO.LimitCbList = [];
                   //LimitEventCbList
                   modelVO.LimitEventCbList.forEach(function (item) {
                       if (item.EventId && item.EventId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitEventList").push(item);
                       }
                   });
                   modelVO.LimitEventCbList = [];
               } else if (modelVO.LimitType == 2) {
                   modelVO.LimitQsList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitItemList").push(item);
                       }
                   });
                   modelVO.LimitQsList = [];
                   modelVO.LimitEventQsList.forEach(function (item) {
                       if (item.EventId && item.EventId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitEventList").push(item);
                       }
                   });
                   modelVO.LimitEventQsList = [];
                   //LimitCbList
                   modelVO.LimitCbList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitItemList").push(item);
                       }
                   });
                   modelVO.LimitCbList = [];
                   //LimitEventCbList
                   modelVO.LimitEventCbList.forEach(function (item) {
                       if (item.EventId && item.EventId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitEventList").push(item);
                       }
                   });
                   modelVO.LimitEventCbList = [];
               } else if (modelVO.LimitType == 7) {
                   modelVO.LimitQsList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitItemList").push(item);
                       }
                   });
                   modelVO.LimitQsList = [];
                   modelVO.LimitEventQsList.forEach(function (item) {
                       if (item.EventId && item.EventId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitEventList").push(item);
                       }
                   });
                   modelVO.LimitEventQsList = [];

                   modelVO.LimitSurplusList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitItemList").push(item);
                       }
                   });
                   modelVO.LimitSurplusList = [];
                   modelVO.LimitEventSurplusList.forEach(function (item) {
                       if (item.EventId && item.EventId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitEventList").push(item);
                       }
                   });
                   modelVO.LimitEventSurplusList = [];
               } else if (modelVO.LimitType == 3) {
                   modelVO.LimitStopList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitItemList").push(item);
                       }
                   });
                   modelVO.LimitStopList = [];
                   modelVO.LimitStopPerList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitItemList").push(item);
                       }
                   });
                   modelVO.LimitStopPerList = [];
                   modelVO.LimitEventStoplossList.forEach(function (item) {
                       if (item.EventId && item.EventId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitEventList").push(item);
                       }
                   });
                   modelVO.LimitEventStoplossList = [];
               } else if (modelVO.LimitType == 4) {
                   modelVO.LimitRegularList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitItemList").push(item);
                       }
                   });
                   modelVO.LimitRegularList = [];
                   modelVO.LimitEventXolList.forEach(function (item) {
                       if (item.EventId && item.EventId != 0) {
                           _self.model.get("BusinessConditionVO_DeleteLimitEventList").push(item);
                       }
                   });
                   modelVO.LimitEventXolList = [];
                   if (modelVO.AmountType == 1) {
                       modelVO.LimitStopPerList.forEach(function (item) {
                           if (item.ItemId && item.ItemId != 0) {
                               _self.model.get("BusinessConditionVO_DeleteLimitItemList").push(item);
                           }
                       });
                       modelVO.LimitStopPerList = [];
                   } else if (modelVO.AmountType == 2) {
                       modelVO.LimitStopList.forEach(function (item) {
                           if (item.ItemId && item.ItemId != 0) {
                               _self.model.get("BusinessConditionVO_DeleteLimitItemList").push(item);
                           }
                       });
                       modelVO.LimitStopList = [];
                   }
               }

               if (modelVO.PremiumType == 1) {

                   modelVO.FixRateList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeletePremiumList").push(item);
                       }
                   });
                   modelVO.FixRateList = [];

                   modelVO.SwingRateList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeletePremiumList").push(item);
                       }
                   });
                   modelVO.SwingRateList = [];

                   modelVO.MinimumPremiumList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeletePremiumList").push(item);
                       }
                   });
                   modelVO.MinimumPremiumList = [];

                   modelVO.DepositPremiumList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeletePremiumList").push(item);
                       }
                   });
                   modelVO.DepositPremiumList = [];

                   delete modelVO.PremiumrateFrom;
                   delete modelVO.PremiumrateTo;
                   delete modelVO.ProvisionalRate;
                   delete modelVO.LossrateFrom;
                   delete modelVO.LossrateTo;
                   delete modelVO.Rate;
                   // modelVO.NoOfInstallment = null;

               } else if (modelVO.PremiumType == 2) {

                   modelVO.FloatPremiumList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeletePremiumList").push(item);
                       }
                   });
                   modelVO.FloatPremiumList = [];

                   modelVO.SwingRateList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeletePremiumList").push(item);
                       }
                   });
                   modelVO.SwingRateList = [];

                   delete modelVO.PremiumrateFrom;
                   delete modelVO.PremiumrateTo;
                   delete modelVO.ProvisionalRate;
                   delete modelVO.LossrateFrom;
                   delete modelVO.LossrateTo;
                   // modelVO.NoOfPayment = null;
               } else if (modelVO.PremiumType == 3) {

                   modelVO.FloatPremiumList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeletePremiumList").push(item);
                       }
                   });
                   modelVO.FloatPremiumList = [];

                   modelVO.FixRateList.forEach(function (item) {
                       if (item.ItemId && item.ItemId != 0) {
                           _self.model.get("BusinessConditionVO_DeletePremiumList").push(item);
                       }
                   });
                   modelVO.FixRateList = [];

                   delete modelVO.Rate;
               }


               //ReinstateSpecificList:[],
               //    ReinstateUnlimitedList:[]
               var reinType = modelVO.ReinType;
               var reinstateSpecificList = modelVO.ReinstateSpecificList ? modelVO.ReinstateSpecificList : [];
               var reinstateUnlimitedList = modelVO.ReinstateUnlimitedList ? modelVO.ReinstateUnlimitedList : [];

               if (reinType == 1) {
                   reinstateSpecificList.forEach(function (item) {
                       if (item && item.ItemId) {
                           _self.model.get("BusinessConditionVO_DeleteReinstateItemList").push(item);
                       }
                   });
                   reinstateUnlimitedList.forEach(function (item) {
                       if (item && item.ItemId) {
                           _self.model.get("BusinessConditionVO_DeleteReinstateItemList").push(item);
                       }
                   });
                   modelVO.ReinstateList = [];
                   modelVO.ReinCurrency = null;
                   // modelVO.ReinNum = null;
                   delete modelVO.ReinNum;
                   modelVO.ExchCalc = null;
               } else if (reinType == 2) {
                   reinstateUnlimitedList.forEach(function (item) {
                       if (item && item.ItemId) {
                           _self.model.get("BusinessConditionVO_DeleteReinstateItemList").push(item);
                       }
                   });
                   modelVO.ReinstateList = reinstateSpecificList;
               } else if (reinType == 3) {
                   modelVO.ReinNum = null;
                   reinstateSpecificList.forEach(function (item) {
                       if (item && item.ItemId) {
                           _self.model.get("BusinessConditionVO_DeleteReinstateItemList").push(item);
                       }
                   });
                   modelVO.ReinstateList = reinstateUnlimitedList;
               }
               delete modelVO.ReinstateSpecificList;
               delete modelVO.ReinstateUnlimitedList;
               return true;
             }
         }
     );
     $page.businessCalculator = new BusinessCalculator();
}(typeof window !== "undefined" ? window : this));
