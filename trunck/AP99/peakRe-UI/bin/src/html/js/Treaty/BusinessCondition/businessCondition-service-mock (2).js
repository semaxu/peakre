(function (context) {
    var $page = $pt.getService(context, '$page');
    $pt.mock({
      url : $ri.getRestfulURL("action.contract.contractBusiness")+'/save',
      response: function (settings) {
          var data = $pt.parseJSON(settings.data);
          this.responseText = data;
        }
    });
    $pt.mock({
      url : $ri.getRestfulURL("action.contract.contractBusiness")+'/recalculate',
      response: function (settings) {
          var data = $pt.parseJSON(settings.data);
          this.responseText = data;
        }
    });
//Section BC-QS
    $pt.mock({
        url : $ri.getRestfulURL("action.contract.contractBusiness")+'/load/1/1',
        response: function (settings) {
            var data = $pt.parseJSON(settings.data);
            this.responseText = {
              //share info mock
              ShareDefine: 1,
              CedantRetention: 0.40,
              Ceded: 0.60,
              WrittenShare1: 0.10,
              WrittenShare2: 0.25,
              SignedShares1: 0.10,
              SignedShares2: 0.25,
              //Currency Info mock
              CurrencyList:[
                {
                  CurrencyType : "CNY",
                  MainCurrencyType: true,
                  ExchangeType : "1",
                  CurrencyRate : "1",
                  CurrencyData : "",
                  CurrencyRemarks : ""
                },
                {
                  CurrencyType : "USD",
                  MainCurrencyType: "true",
                  ExchangeType : "1",
                  CurrencyRate : "6.5",
                  CurrencyData : "",
                  CurrencyRemarks : ""
                }
              ],
              //Premium Info mock
              EpiType: 1,
              DefinedIn: 1,
              EpiList: [{
                Currency: "CNY",
                Amount: 1476923.1,
                OurShare: 147692.31,
                ItemType: "1"
              },{
                Currency: "USD",
                Amount: 62485.21,
                OurShare: 6248.52,
                ItemType: "1"
              }],
              TerrorismList: [{
                Currency: "CNY",
                Amount: 73846.16,
                OurShare: 7384.62,
                ItemType: "2"
              },{
                Currency: "USD",
                Amount: 62485.21,
                OurShare: 62485.21,
                ItemType: "2"
              }],
              //Limit Info Mock
              LimitType:"1",
              LimitQsList:[
                {
                  Currency:"CNY",
                  SumInsured:460000000,
                  Qs:10,
                  Liability:46000000,
                  OurShare:46000000
                },{
                  Currency:"USD",
                  SumInsured:52000000,
                  Qs:10,
                  Liability:5200000,
                  OurShare:5200000
                }
              ],
              LimitEventList:[
                {
                  Currency:"CNY",
                  Event:"4",
                  Liability:1325000000,
                  OurShare:132500000
                },{
                  Currency:"USD",
                  Event:"4",
                  Liability:500000000,
                  OurShare:50000000
              }],
              //Deductions Info Mock
              RICommission:"1",
              DeductionsId:1,
              PercentOfPremium: "0.5",
              DeficitCarryForward: "1",
              NumberOfYears:"5",
              Percentage: "1",
              Percentage1: "2",
              Percentage2: "1",
              DeductionsList:[{
                AmountOurShare: "1358.64",
                AmountPercent: "53280",
                Item: "1",
                Percentage: "0.0255"
              },{
                AmountOurShare: "600",
                AmountPercent: "24000",
                Item: "2",
                Percentage: "0.0255"
              }],
              //Reserve&Rebates info mock
              PremiumCalcMethod: '1',
              PremiumReserve: 0.15,
              InterestRate: 0.022,
              LossCalcMethod: '1',
              LossReserves: 0.10,
              NoClaimBonus: 0.08,
              RebatePercent: 0.10,
              LossRatioFrom: 0.60,
              LossRatioTo: 0.70,
              CalcDate: "2016-10-11",
              ExpirationYear: 1,
              ExpirationMonth: null,
              ExpirationDays: null,
              LrCalcYears: 1,
              //Loss Info Mock
              ParticipBase: 1,
              MinRatio: 0.80,
              MaxRatio: 0.90,
              CedentParticip: 0.10,
              FirstCalcAfter: 2,
              SubCalcEvery: 1,
              //Clause Info mock
              ClausesRequiredList:["11","12","13"],
              ClausesRecommendList: ["11","12","13"]
            }
        }
    }
    );
//Section BC-Surplus
        $pt.mock({
            url : $ri.getRestfulURL("action.contract.contractBusiness")+'/load/2/1',
            response: function (settings) {
                var data = $pt.parseJSON(settings.data);
                this.responseText = {
                  //share info mock
                  ShareDefine: 1,
                  CedantRetention: 0.40,
                  Ceded: 0.60,
                  WrittenShare1: 0.10,
                  WrittenShare2: 0.25,
                  SignedShares1: 0.10,
                  SignedShares2: 0.25,
                  //Currency Info mock
                  CurrencyList:[
                    {
                      CurrencyType : "USD",
                      MainCurrencyType: true,
                      ExchangeType : "1",
                      CurrencyRate : "6.5",
                      CurrencyData : "",
                      CurrencyRemarks : ""
                    }
                  ],
                  //Premium Info mock
                  EpiType: 1,
                  DefinedIn: 1,
                  EpiList: [{
                    Currency: "USD",
                    Amount: 57600000,
                    OurShare: 57600000,
                    ItemType: "1"
                  }],
                  TerrorismList: [{
                    Currency: "USD",
                    Amount: 2880000,
                    OurShare: 2880000,
                    ItemType: "2"
                  }],
                  //Limit Info Mock
                  LimitType:"2",
                  LimitSurplusList:[{
                    Currency:"USD",
                    Retente:46000000,
                    NoOfLines:6,
                    Liability:2760000,
                    OurShare:2760000
                    }
                  ],
                  LimitEventList:[{
                      Currency:"USD",
                      Event:"1",
                      Liability:1900000000,
                      OurShare:1900000000
                  }],
                  //Deductions Info Mock
                  RICommission:"2",
                  PercentOfPremium: "0.5",
                  DeficitCarryForward: "1",
                  NumberOfYears:"5",
                  Percentage: "0.5",
                  Percentage1: "2",
                  Percentage2: "1",
                  DeductionsList:[{
                    AmountPercent: "146880",
                    Item: "1",
                    Percentage: "0.0255"
                  },{
                    AmountPercent: "144000",
                    Item: "2",
                    Percentage: "0.0255"
                  }],
                  //Reserve&Rebates info mock
                  PremiumCalcMethod: '1',
                  PremiumReserve: 0.15,
                  InterestRate: 0.022,
                  LossCalcMethod: '1',
                  LossReserves: 0.10,
                  NoClaimBonus: 0.08,
                  RebatePercent: 0.10,
                  LossRatioFrom: 0.60,
                  LossRatioTo: 0.70,
                  CalcDate: "2016-10-11",
                  ExpirationYear: 1,
                  ExpirationMonth: null,
                  ExpirationDays: null,
                  LrCalcYears: 1,
                  //Loss Info Mock
                  ParticipBase: 1,
                  MinRatio: 0.80,
                  MaxRatio: 0.90,
                  CedentParticip: 0.10,
                  FirstCalcAfter: 2,
                  SubCalcEvery: 1,
                  //Clause Info mock
                  ClausesRequiredList:["11","12","13"],
                  ClausesRecommendList: ["11","12","13"]
                }
            }
        }
        );
//BC-XOL-L1 50M xs 50M
      $pt.mock(
        {
            url : $ri.getRestfulURL("action.contract.contractBusiness")+'/load/1/2',
            response: function (settings) {
                var data = $pt.parseJSON(settings.data);
                this.responseText = {
                   "ContractNature": "2",
                   "ContCompId": "1",
                   "Coinsurance": "",
                   "WrittenShare1": "0.03",
                   "SignedShares1": "0.03",
                   "CurrencyList": [{
                       "CurrencyType": "USD",
                       "MainCurrencyType": true,
                       "ExchangeType": "1",
                       "CurrencyRate": "1"
                   }],
                   "DefinedIn": "2",
                   "SupiList": [{"ItemId": null, "Currency": "USD", "Amount": "331196292", "ItemType": "3"}],
                   "PremiumType": "2",
                   "FloatPremiumList": [{
                       "ItemId": null,
                       "Currency": "USD",
                       "Amount": "8749875",
                       "OurShare": "262496",
                       "ItemType": "4"
                   }],
                   "Rate": "0.026419",
                   "FixRateList": [{
                       "ItemId": null,
                       "Currency": "USD",
                       "Amount": "8749875",
                       "OurShare": "262496",
                       "ItemType": "5"
                   }],
                   "MinimumList": [{
                       "ItemId": null,
                       "Currency": "USD",
                       "DefinedIn": "1",
                       "Amount": "7000000",
                       "OurShare": "210000.00",
                       "MinimumAmount": 0,
                       "ItemType": "7"
                   }],
                   "DepositPremiumList": [{
                       "ItemId": null,
                       "Currency": "USD",
                       "DefinedIn": "1",
                       "Amount": "7000000",
                       "OurShare": "210000.00",
                       "ItemType": "8"
                   }],
                   "DepositScheduleList": [{
                       "InstalNo": "1",
                       "ItemId": null,
                       "DueDate": "2016-03-01T00:00:00",
                       "Currency": "USD",
                       "Amount": "52500",
                       "Percentage":"0.25",
                       "ItemType": "9"
                   }, {
                       "InstalNo": "2",
                       "ItemId": null,
                       "DueDate": "2016-06-01T00:00:00",
                       "Currency": "USD",
                       "Amount": "52500",
                       "Percentage":"0.25",
                       "ItemType": "9"
                   }, {
                       "InstalNo": "3",
                       "ItemId": null,
                       "DueDate": "2016-09-01T00:00:00",
                       "Currency": "USD",
                       "Amount": "52500",
                       "Percentage":"0.25",
                       "ItemType": "9"
                   }, {
                       "InstalNo": "4",
                       "ItemId": null,
                       "DueDate": "2016-12-01T00:00:00",
                       "Currency": "USD",
                       "Amount": "52500",
                       "Percentage":"0.25",
                       "ItemType": "9"
                   }],
                   "LimitType": "3",
                   "PerRisk": "2",
                   "LimitRegularList": [{
                       "Currency": "USD",
                       "LimitLayer": "50000000",
                       "Deductible": "50000000",
                       "Aad": "150000000.00",
                       "Aal": "150000000",
                       "LayerShare": "1500000",
                       "AalShare": "4500000"
                   }],
                   "LimitEventList": [{
                       "Currency": "USD",
                       "Event": "2",
                       "LimitHundred": "50000000",
                       "LimitShare": "1500000"
                   }],
                   "ReinType": "2",
                   "ReinNum": "3",
                   "ReinstateList": [{
                       "Reinstate": "1",
                       "ReinRate": "1.00",
                       "ReinTime": "0",
                       "ReinAmount": "1"
                   }, {"Reinstate": "2", "ReinRate": "0.50", "ReinTime": "0", "ReinAmount": "1"}],
                   "ReinCurrency": "USD",
                   "ExchCalc": "1",
                   "Brokerage": "11.5",
                   "DeductionsList": [{
                       "Item": "1",
                       "Percentage": "3",
                       "AmountPercent": "7335.06",
                       "AmountOurShare": ""
                   }, {"Item": "2", "Percentage": "2.5", "AmountPercent": "6112.55"}],
                   "PremiumCalcMethod": "1",
                   "LossCalcMethod": "1",
                   "PremiumReserve": "0.15",
                   "LossReserves": "0.10",
                   "InterestRate": "0.022",
                   "NoClaimBonus": "0.08",
                   "RebatePercent": "0.10",
                   "LossRatioFrom": "0.60",
                   "LossRatioTo": "0.70",
                   "CalcDate": "2016-10-11T00:00:00",
                   "ExpirationYear": "1",
                   "LrCalcYears": "1",
                   "ParticipBase": "1",
                   "MinRatio": "0.80",
                   "MaxRatio": "0.90",
                   "CedentParticip": "0.10",
                   "FirstCalcAfter": "2",
                   "SubCalcEvery": "1",
                   "ClausesRequiredList": ["11", "12", "13"],
                   "ClausesRecommendList": ["11", "12", "13"]
               }
          }
      });
//BC-XOL-L2 100M xs 100M
      $pt.mock(
        {
            url : $ri.getRestfulURL("action.contract.contractBusiness")+'/load/2/2',
            response: function (settings) {
                var data = $pt.parseJSON(settings.data);
                this.responseText = {
                "ContractNature": "2",
                "ContCompId": "1",
                "Coinsurance": "",
                "WrittenShare1": "0.03",
                "SignedShares1": "0.03",
                "CurrencyList": [{
                    "CurrencyType": "USD",
                    "MainCurrencyType": true,
                    "ExchangeType": "1",
                    "CurrencyRate": "1"
                }],
                "DefinedIn": "2",
                "SupiList": [{"ItemId": null, "Currency": "USD", "Amount": "331196292", "ItemType": "3"}],
                "PremiumType": "2",
                "FloatPremiumList": [{
                    "ItemId": null,
                    "Currency": "USD",
                    "Amount": "8749875",
                    "OurShare": "262496",
                    "ItemType": "4"
                }],
                "Rate": "0.024608",
                "FixRateList": [{
                    "ItemId": null,
                    "Currency": "USD",
                    "Amount": "8150078",
                    "OurShare": "244502",
                    "ItemType": "5"
                }],
                "MinimumList": [{
                    "ItemId": null,
                    "Currency": "USD",
                    "DefinedIn": "1",
                    "Amount": "6520000",
                    "OurShare": "195600",
                    "MinimumAmount": 0,
                    "ItemType": "7"
                }],
                "DepositPremiumList": [{
                    "ItemId": null,
                    "Currency": "USD",
                    "DefinedIn": "1",
                    "Amount": "6520000",
                    "OurShare": "6520000",
                    "ItemType": "8"
                }],
                "DepositScheduleList": [{
                    "InstalNo": "1",
                    "ItemId": null,
                    "DueDate": "2016-03-01T00:00:00",
                    "Currency": "USD",
                    "Amount": "48900",
                    "Percentage": "0.25",
                    "ItemType": "9"
                }, {
                    "InstalNo": "2",
                    "ItemId": null,
                    "DueDate": "2016-06-01T00:00:00",
                    "Currency": "USD",
                    "Amount": "48900",
                    "Percentage": "0.25",
                    "ItemType": "9"
                }, {
                    "InstalNo": "3",
                    "ItemId": null,
                    "DueDate": "2016-09-01T00:00:00",
                    "Currency": "USD",
                    "Amount": "48900",
                    "Percentage": "0.25",
                    "ItemType": "9"
                }, {
                    "InstalNo": "4",
                    "ItemId": null,
                    "DueDate": "2016-12-01T00:00:00",
                    "Currency": "USD",
                    "Amount": "48900",
                    "Percentage": "0.25",
                    "ItemType": "9"
                }],
                "LimitType": "3",
                "PerRisk": "2",
                "LimitRegularList": [{
                    "Currency": "USD",
                    "LimitLayer": "100000000",
                    "Deductible": "100000000",
                    "Aad": "300000000.00",
                    "Aal": "150000000",
                    "LayerShare": "3000000",
                    "AalShare": "6000000"
                }],
                "LimitEventList": [{
                    "Currency": "USD",
                    "Event": "2",
                    "LimitHundred": "100000000",
                    "LimitShare": "3000000"
                }],
                "ReinType": "2",
                "ReinNum": "3",
                "ReinstateList": [{
                    "Reinstate": "1",
                    "ReinRate": "1.00",
                    "ReinTime": "0",
                    "ReinAmount": "1"
                }, {"Reinstate": "2", "ReinRate": "0.50", "ReinTime": "0", "ReinAmount": "1"}],
                "ReinCurrency": "USD",
                "ExchCalc": "1",
                "Brokerage": "11.5",
                "DeductionsList": [{
                    "Item": "1",
                    "Percentage": "3",
                    "AmountPercent": "7335.06",
                    "AmountOurShare": ""
                }, {"Item": "2", "Percentage": "2.5", "AmountPercent": "6112.55"}],
                "PremiumCalcMethod": "1",
                "LossCalcMethod": "1",
                "PremiumReserve": "0.15",
                "LossReserves": "0.10",
                "InterestRate": "0.022",
                "NoClaimBonus": "0.08",
                "RebatePercent": "0.10",
                "LossRatioFrom": "0.60",
                "LossRatioTo": "0.70",
                "CalcDate": "2016-10-11T00:00:00",
                "ExpirationYear": "1",
                "LrCalcYears": "1",
                "ParticipBase": "1",
                "MinRatio": "0.80",
                "MaxRatio": "0.90",
                "CedentParticip": "0.10",
                "FirstCalcAfter": "2",
                "SubCalcEvery": "1",
                "ClausesRequiredList": ["11", "12", "13"],
                "ClausesRecommendList": ["11", "12", "13"]
            }
          }
      });
//BC-XOL-L2 200M xs 200M
      $pt.mock(
        {
            url : $ri.getRestfulURL("action.contract.contractBusiness")+'/load/3/2',
            response: function (settings) {
                var data = $pt.parseJSON(settings.data);
                this.responseText = {
                "ContractNature": "2",
                "ContCompId": "1",
                "Coinsurance": "",
                "WrittenShare1": "0.03",
                "SignedShares1": "0.03",
                "CurrencyList": [{
                    "CurrencyType": "USD",
                    "MainCurrencyType": true,
                    "ExchangeType": "1",
                    "CurrencyRate": "1"
                }],
                "DefinedIn": "2",
                "SupiList": [{"ItemId": null, "Currency": "USD", "Amount": "331196292", "ItemType": "3"}],
                "PremiumType": "2",
                "Rate": "0.019928",
                "FixRateList": [{
                    "ItemId": null,
                    "Currency": "USD",
                    "Amount": "6600080",
                    "OurShare": "198002",
                    "ItemType": "5"
                }],
                "MinimumList": [{
                    "ItemId": null,
                    "Currency": "USD",
                    "DefinedIn": "1",
                    "Amount": "5280000",
                    "OurShare": "158400",
                    "MinimumAmount": 0,
                    "ItemType": "7"
                }],
                "DepositPremiumList": [{
                    "ItemId": null,
                    "Currency": "USD",
                    "DefinedIn": "1",
                    "Amount": "5280000",
                    "OurShare": "158400",
                    "ItemType": "8"
                }],
                "DepositScheduleList": [{
                    "InstalNo": "1",
                    "ItemId": null,
                    "DueDate": "2016-03-01T00:00:00",
                    "Currency": "USD",
                    "Amount": "39600",
                    "Percentage": "0.25",
                    "ItemType": "9"
                }, {
                    "InstalNo": "2",
                    "ItemId": null,
                    "DueDate": "2016-06-01T00:00:00",
                    "Currency": "USD",
                    "Amount": "39600",
                    "Percentage": "0.25",
                    "ItemType": "9"
                }, {
                    "InstalNo": "3",
                    "ItemId": null,
                    "DueDate": "2016-09-01T00:00:00",
                    "Currency": "USD",
                    "Amount": "39600",
                    "Percentage": "0.25",
                    "ItemType": "9"
                }, {
                    "InstalNo": "4",
                    "ItemId": null,
                    "DueDate": "2016-12-01T00:00:00",
                    "Currency": "USD",
                    "Amount": "39600",
                    "Percentage": "0.25",
                    "ItemType": "9"
                }],
                "LimitType": "3",
                "PerRisk": "2",
                "LimitRegularList": [{
                    "Currency": "USD",
                    "LimitLayer": "200000000",
                    "Deductible": "200000000",
                    "Aad": "600000000.00",
                    "Aal": "400000000",
                    "LayerShare": "6000000",
                    "AalShare": "6000000"
                }],
                "LimitEventList": [{
                    "Currency": "USD",
                    "Event": "2",
                    "LimitHundred": "200000000",
                    "LimitShare": "8000000"
                }],
                "ReinType": "2",
                "ReinNum": "3",
                "ReinstateList": [{
                    "Reinstate": "1",
                    "ReinRate": "1.00",
                    "ReinTime": "0",
                    "ReinAmount": "1"
                }, {"Reinstate": "2", "ReinRate": "0.50", "ReinTime": "0", "ReinAmount": "1"}],
                "ReinCurrency": "USD",
                "ExchCalc": "1",
                "Brokerage": "11.5",
                "DeductionsList": [{
                    "Item": "1",
                    "Percentage": "3",
                    "AmountPercent": "7335.06",
                    "AmountOurShare": ""
                }, {"Item": "2", "Percentage": "2.5", "AmountPercent": "6112.55"}],
                "PremiumCalcMethod": "1",
                "LossCalcMethod": "1",
                "PremiumReserve": "0.15",
                "LossReserves": "0.10",
                "InterestRate": "0.022",
                "NoClaimBonus": "0.08",
                "RebatePercent": "0.10",
                "LossRatioFrom": "0.60",
                "LossRatioTo": "0.70",
                "CalcDate": "2016-10-11T00:00:00",
                "ExpirationYear": "1",
                "LrCalcYears": "1",
                "ParticipBase": "1",
                "MinRatio": "0.80",
                "MaxRatio": "0.90",
                "CedentParticip": "0.10",
                "FirstCalcAfter": "2",
                "SubCalcEvery": "1",
                "ClausesRequiredList": ["11", "12", "13"],
                "ClausesRecommendList": ["11", "12", "13"]
            }
          }
      });
}(typeof window !== "undefined" ? window : this));
