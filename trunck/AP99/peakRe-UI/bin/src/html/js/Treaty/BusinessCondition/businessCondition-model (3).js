(function (context) {
    var $page = $pt.getService(context, "$page");
    var model = jsface.Class({
        //getRequiredCodeTable: function () {
        //    if (!this.requiredCodeTable) {
        //        this.requiredCodeTable = $pt.createCodeTable([]);
        //    }
        //    return this.requiredCodeTable;
        //},
        //createTemplate: function () {
        //    $page.model.all = $.extend(true, {}, $page.model.base, $page.model.premium, $page.model.cancellation,
        //        $page.model.reserve, $page.model.share,$page.model.clause,$page.model.currency,$page.model.reinstatement,$page.model.limit);
        //    return {
        //        base: this.createBaseModel(),
        //        premium: this.createPremiumModel(),
        //        share : this.createShareModel(),
        //        reserve: this.createReserveModel(),
        //        loss : this.createLossModel(),
        //        cancellation : this.createCancellationModel(),
        //        clause:this.createClauseModel(),
        //        currency:this.createCurrencyModel(),
        //        reinstatement:this.createReinstatementModel(),
        //        limit:this.createLimitModel()
        //    };
        //},
        createBaseModel: function () {
            return {
                BusinessConditionVO: {
                    CurrencyList: [],
                    DeleteCurrencyList: [],
                    DeletePremiumList:[],
                    DeleteLimitItemList:[],
                    DeleteLimitEventList:[],
                    DeleteDeductionsList:[],
                    DeleteReinstateItemList:[],
                    EpiList: [],
                    TerrorismList: [],
                    LimitQsList: [],
                    LimitRegularList: [],
                    LimitStopList: [],
                    LimitEventList: [],
                    LimitSurplusList: [],
                    DeductionsList: [],
                    ReinstateList: [],
                    SupiList: [],
                    FloatPremiumList: [],
                    FixRateList: [],
                    SwingRateList: [],
                    MinimumList: [],
                    DepositPremiumList: [],
                    DepositScheduleList: [],
                    PaymentScheduleList: []
                }
            }
        },
        //createPremiumModel: function () {
        //    return {
        //        PremiumId: null,
        //        EpiType: null,
        //        PremiumType: null,
        //        Rate:0,
        //        DefinedIn: null,
        //        LossrateFrom: 0,
        //        LossrateTo: 0,
        //        PremiumrateFrom: 0,
        //        PremiumrateTo: 0,
        //        ReinRate:100,
        //        ProvisionalRate: 0,
        //        Remark: null,
        //        EpiList: [],
        //        TerrorismList: [],
        //        SupiList: [],
        //        FloatPremiumList: [],
        //        FixRateList: [],
        //        SwingRateList: [],
        //        MinimumList: [],
        //        DepositPremiumList: [],
        //        DepositScheduleList: [],
        //        PaymentScheduleList: [],
        //        DeletePaymentList: []
        //    }
        //},
        //createShareModel: function(){
        //    return {
        //        ShareId: null,
        //        CedantRetention: null,
        //        Ceded: null,
        //        WrittenShare1: 0,
        //        WrittenShare2: 0,
        //        WrittenShare3: 0,
        //        SignedShares1: 0,
        //        SignedShares2: 0,
        //        SignedShares3: 0,
        //        ShareDefine: 0,
        //        Comments1: null,
        //        Comments2: null,
        //        Comments3: null,
        //        Coinsurance: null
        //    }
        //},
        //createReserveModel: function(){
        //    return {
        //        ReserveId: null,
        //        PremiumCalcMethod: null,
        //        PremiumReserve: 0,
        //        InterestRate: 0,
        //        LossCalcMethod: null,
        //        LossReserves: 0,
        //        NoClaimBonus: 0,
        //        RebatePercent: 0,
        //        LossRatioFrom: 0,
        //        LossRatioTo: 0,
        //        CalcDate: null,
        //        ExpirationYear: null,
        //        ExpirationMonth: null,
        //        ExpirationDays: null,
        //        LrCalcYears: null,
        //        RebateRemark: null,
        //    }
        //},
        //createLossModel: function(){
        //    return {
        //        LossId: null,
        //        ParticipBase: null,
        //        MinRatio: 0,
        //        MaxRatio: 0,
        //        CedentParticip: 0,
        //        FirstCalcAfter: null,
        //        SubCalcEvery: null
        //    }
        //},
        //createLoosCorridorModel: function(){
        //    return {
        //        LoosCorridorId: null,
        //        ParticipationBase: null,
        //        MinimumRatio: 0,
        //        MaxinumRatio: 0,
        //        CedentParticipation: 0,
        //        FirstCalc: null,
        //        SubseqCalc: null
        //    }
        //},
        //createCancellationModel : function(){
        //    return {
        //        CancelId : null,
        //        PnocCedentDay : null,
        //        PnocCedentMonth : null,
        //        PnocReinsurerMonth : null,
        //        PnocReinsurerDay : null,
        //        PnocAutomatic : null,
        //        DnocWarMonth : null,
        //        DnocWarDay : null,
        //        DnocSanctionMonth : null,
        //        DnocSanctionDay : null,
        //        DnocPoliticalDay : null,
        //        DnocPoliticalMonth : null,
        //    }
        //},
        //createClauseModel:function(){
        //     return {
        //        ClausesId:null,
        //        ClausesRequired:null,
        //        ClausesRecommend: null
        //    }
        //},
        //createCurrencyModel:function(){
        //    return {
        //        CurrencyList:[]
        //    }
        //},
        //createReinstatementModel:function(){
        //    return {
        //        ReinId:null,
        //        ReinType:null,
        //        ReinNum:null,
        //        ReinCurrency:null,
        //        ExchCalc:null,
        //        ReinstateList:[],
        //        ReinTime:"0",
        //    }
        //},
        //createLimitModel:function(){
        //    return {
        //        LimitId :null,
        //        LimitType:null,
        //        PerRisk:null,
        //        Unlimit:"false",
        //        AmountType:null,
        //        LimitRegularList:[],
        //        LimitStopList:[],
        //        LimitEventList:[],
        //        LimitQsList:[],
        //        LimitSurplusList:[]
        //    }
        //},

        getEpiListTemplate: function(){
            return {
                ItemId: null,
                Currency: null,
                Amount: null,
                OurSignedShare: null,
                OurWrittenShare: null,
                ItemType: "1"
            }
        },
        getTerrorismListTemplate: function(){
            return {
                ItemId: null,
                Currency: null,
                Amount: null,
                OurSignedShare: null,
                OurWrittenShare: null,
                ItemType: "2"
            }
        },
        getSupiListTemplate: function(){
            return {
                ItemId: null,
                Currency: null,
                Amount: null,
                ItemType: "3"
            }
        },
        getFloatPremiumListTemplate: function(){
            return {
                ItemId: null,
                Currency: null,
                Amount: null,
                OurWrittenShare: null,
                ItemType: "4"
            }
        },
        getFixRateListTemplate: function(){
            return {
                ItemId: null,
                Currency: null,
                Amount: null,
                OurWrittenShare: null,
                ItemType: "5"
            }
        },
        getSwingRateListTemplate: function(){
            return {
                ItemId: null,
                Currency: null,
                Amount: null,
                OurWrittenShare: null,
                ItemType: "6"
            }
        },
        getMinimumListTemplate: function(){
            return {
                ItemId: null,
                Currency: null,
                DefinedIn: null,
               
                Amount: null,
                OurWrittenShare: null,
                ItemType: "7"
            }
        },
        getDepositPremiumListTemplate: function(){
            return {
                ItemId: null,
                Currency: null,
                DefinedIn: null,

                ItemType: "8"
            }
        },
        getDepositScheduleListTemplate: function(){
            return {
                InstalNo: null,
                ItemId: null,
                DueDate: null,
                Currency: null,
                ItemType: "9"
            }
        },
        getPaymentScheduleListTemplate: function(){
            return {
                InstalPayNo: null,
                ItemId: null,
                DueDate: null,
                Currency: null,
                ItemType: "10"
            }
        },
        getCurrencyListTemplate:function(){
           return  {
                CurrencyId:null,
                CurrencyType:null,
                CurrencyRate:0,
                CurrencyData:null,
                MainCurrency:"false",
                ExchangeType:null,
                CurrencyRemarks:null
            };
        },
        getReinstateListTemplate:function(){
            return {
                ItemId:null,
                ReinRate:1,
                ReinTime:"0",
                ReinAmount:1
            }
        },
        getLimitRegularListTemplate:function(){
            return {
                ItemId:null,
                Currency:null,
                LimitLayer:null,
                LimitWrittenLayer:null,
                Deductible:null,
                Aad:null,
                Aal:null,
                LayerShare:null,
                LayerWrittenShare:null,
                AalWrittenShare:null,
                AalShare:null
            }
        },
        getLimitStopListTemplate:function(){
            return {
                ItemId:null,
                    Currency:null,
                    LimitLayer:null,
                    LimitWrittenLayer:null,
                    Deductible:null,

            }
        },
        getLimitQsListTemplate:function(){
            return  {
                ItemId:null,
                Currency:null,
                SumInsured:null,
                Qs:null,
                Liability:null,
                LiabilitySignedShare:null,
                LiabilityWrittenShare:null
            }
        },
        getLimitSurplusListTemplate:function(){
            return {
                ItemId:null,
                Currency:null,
                Retente:null,
                NoOfLines:null,
                Liability:null,
                LiabilitySignedShare:null,
                LiabilityWrittenShare:null
            }
        },
        getLimitCbListTemplate:function(){
            return  {
                ItemId:null,
                Currency:null,
                SumInsured:null,
                Qs:null,
                NoOfLines:null,
                Liability:null,
                LiabilitySignedShare:null,
                LiabilityWrittenShare:null
            }
        },
        getLimitEventListTemplate:function(){
            return {
                ItemId:null,
                Currency:null,
                Event:null,
                Liability:null,
                LimitHundred:null,
                LiabilitySignedShare:null,
                LiabilityWrittenShare:null,
                CommRemark:null
            }
        },
    });

    $page.model = new model();


}(typeof window !== "undefined" ? window : this));
