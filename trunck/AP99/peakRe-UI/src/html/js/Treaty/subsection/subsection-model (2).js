(function (context) {
    var $page = $pt.getService(context, '$page');
    var model = jsface.Class({
        createBaseModel: function () {
            return {
                ContractNature: null,
                ContCompId: null,
                ParentId: null,
                ContractCategory: null,
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
                    LimitSurplusList: [],
                    LimitRegularList: [],
                    LimitStopList: [],
                    LimitStopPerList:[],
                    LimitCbList: [],
                    LimitEventQsList: [],
                    LimitEventSurplusList: [],
                    LimitEventXolList: [],
                    LimitEventStoplossList: [],
                    LimitEventCbList: [],
                    DeductionsList: [],
                    ReinstateList: [],
                    SupiList: [],
                    FloatPremiumList: [],
                    FixRateList: [],
                    SwingRateList: [],

                    MinimumPremiumList:[],
                    DepositPremiumList:[],
                    DepositScheduleList:[],

                    ReinstateSpecificList:[],
                    ReinstateUnlimitedList:[]
                }
            };
        },
        getCurrencyListTemplate: function () {
            return {
                CurrencyId: null,
                CurrencyType: null,
                CurrencyRate: null,
                CurrencyDate: null,
                MainCurrencyType: "false",
                ExchangeType: null,
                CurrencyRemarks: null
            };
        },
        getEpiListTemplate: function () {
            return {
                ItemId: null,
                Currency: null,
                Amount: null,
                OurSignedShare: null,
                OurWrittenShare: null,
                ItemType: "1"
            }
        },
        getTerrorismListTemplate: function () {
            return {
                ItemId: null,
                Currency: null,
                Amount: null,
                OurSignedShare: null,
                OurWrittenShare: null,
                ItemType: "2"
            }
        },
        getSupiListTemplate: function () {
            return {
                ItemId: null,
                Currency: null,
                Amount: null,
                ItemType: "3"
            }
        },
        getFloatPremiumListTemplate: function () {
            return {
                ItemId: null,
                Currency: null,
                Amount: null,
                OurWrittenShare: null,
                ItemType: "4"
            }
        },
        getFixRateListTemplate: function () {
            return {
                ItemId: null,
                Currency: null,
                Amount: null,
                OurWrittenShare: null,
                ItemType: "5"
            }
        },
        getSwingRateListTemplate: function () {
            return {
                ItemId: null,
                Currency: null,
                Amount: null,
                OurWrittenShare: null,
                ItemType: "6"
            }
        },
        getMinimumPremiumListTemplate: function () {
            return {
                ItemId: null,
                Currency: null,
                DefinedIn: null,
                Amount: null,
                OurWrittenShare: null,
                ItemType: "7"
            }
        },
        getDepositPremiumFixListTemplate: function () {
            return {
                ItemId: null,
                Currency: null,
                DefinedIn: null,
                ItemType: "8"
            }
        },

        getDepositPremiumSwingListTemplate: function () {
            return {
                ItemId: null,
                Currency: null,
                DefinedIn: null,
                ItemType: "12"
            }
        },
        getDepositScheduleListTemplate: function () {
            return {
                InstalNo: null,
                ItemId: null,
                DueDate: null,
                Currency: null,
                ItemType: "9"
            }
        },

        getReinstateSpecificListTemplate: function () {
            return {
                ItemId: null,
                ReinRate: 1,
                ReinTime: "0",
                ReinAmount: 1,
                ReinType:2
            }
        },

        getReinstateUnlimitedListTemplate: function () {
            return {
                ItemId: null,
                ReinRate: 1,
                ReinTime: "0",
                ReinAmount: 1,
                ReinType:3
            }
        },

        getLimitQsListTemplate: function () {
            return {
                ItemType: "1"
            }
        },
        getLimitSurplusListTemplate: function () {
            return {
                ItemType: "2"
            }
        },
        getLimitEventQsListTemplate: function () {
            return {
                ItemType: "1"
            }
        },
        getLimitEventCbListTemplate: function () {
            return {
                ItemType: "7"
            }
        },
        getLimitEventSurplusListTemplate: function () {
            return {
                ItemType: "2"
            }
        },
        getLimitRegularListTemplate: function () {
            return {
                ItemType: "3"
            }
        },
        getLimitStopListTemplate: function () {
            return {
                ItemType: "5"
            }
        },
        getLimitStopPerListTemplate: function () {
            return {
                ItemType: "6"
            }
        },
        getLimitEventXolListTemplate: function () {
            return {
                ItemType: "3"
            }
        },
        getLimitEventStoplossListTemplate: function () {
            return {
                ItemType: "4"
            }
        }
    });
    $page.model = new model();
}(typeof window !== "undefined" ? window : this));
