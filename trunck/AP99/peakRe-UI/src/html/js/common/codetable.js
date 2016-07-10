/**
 * Created by brad.wu on 9/4/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var codes = $pt.getService($page, 'codes');
    var MutableCodeTable = $pt.extendCodeTable({
        add: function (item) {
            if (this.get(item.id)) {
                // already existed, do nothing
            } else {
                this._codes.push(item);
                this._map[item.id] = item;
                this.render();
                this.sort();
            }
            return this;
        },
        remove: function (id) {
            var index = this._codes.findIndex(function (item) {
                return item.id == id;
            });
            if (index != -1) {
                this._codes.splice(index, 1);
                delete this._map[id];
            }
            return this;
        },
        reset: function (items) {
            this.__initCodesArray(items, this._renderer, this._sorter);
            this._initialized = true;
            return this;
        }
    });
    codes.createMutableCodeTable = function (items, renderer, sorter) {
        return new MutableCodeTable(items ? items : [], renderer, sorter);
    };

    codes.Boolean = $pt.createCodeTable([
        {
            id: '1',
            text: 'Yes'
        }, {
            id: '0',
            text: 'No'
        }
    ]);

    codes.Actualized = $pt.createCodeTable([
        {
            id: '0',
            text: 'N'
        }, {
            id: '1',
            text: 'Y'
        }
    ]);

    codes.Country = $pt.createCodeTable([
        {
            id: "1",
            text: "Asia",
            children: [
                {
                    id: "11",
                    text: "China"
                }, {
                    id: "12",
                    text: "HongKong"
                }, {
                    id: "13",
                    text: "Korea"
                }
            ]
        },
        {id: "2", text: "America"},
        {id: "3", text: "Europ"},
        {id: "4", text: "Africa"}
    ]);
    codes.ReserveType = $pt.createCodeTable([
        {id: "1", text: "Loss(Indemnity)"},
        {id: "2", text: "Expense"},
        {id: "3", text: "Additional case reserve"},
        {id: "4", text: "RIP provision"},
        {id: "5", text: "Tax"},
        {id: "6", text: "Others"}
    ]);

    codes.ReserveStatus = $pt.createCodeTable([
        {id: "1", text: "Open"},
        {id: "2", text: "Close"}
    ]);
    codes.SettlementType = $pt.createCodeTable([
        {id: "1", text: "Loss(Indemnity)"},
        {id: "2", text: "Expense"},
        // {id: "3", text: "Additional case reserve"},
        {id: "4", text: "RIP provision"},
        {id: "5", text: "Tax"},
        {id: "6", text: "Others"}
    ]);
    codes.SettlementApprovalDecision = $pt.createCodeTable([
        {id: "1", text: "Approve"},
        {id: "2", text: "Reject"}
    ]);

    codes.TypeOfReinstatement = $pt.createCodeTable([
        {
            id: '1',
            text: 'No Reinstatement'
        }, {
            id: '2',
            text: 'Specific Reinstatement'
        }, {
            id: '3',
            text: 'Unlimited Reinstatement'
        }
    ]);

    codes.Currency = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+800010});
    codes.GL_PostStatus = $pt.createCodeTable([
        {
            id: '0',
            text: 'Wait For Post'
        }, {
            id: '2',
            text: 'Post Successfully'
        }, {
            id: '3',
            text: 'Post Failed'
        }
    ]);
    codes.GL_Indicator = $pt.createCodeTable([
        {
            id: '1',
            text: 'Estimate'
        }, {
            id: '2',
            text: 'Actual'
        }, {
            id: '3',
            text: 'Estimate Reversal'
        }
    ]);
    codes.GL_BusinessType = $pt.createCodeTable([
        {
            id: '1',
            text: 'Claim Reserve'
        }, {
            id: '2',
            text: 'Claim Settlement'
        }, {
            id: '3',
            text: 'Statement'
        }, {
            id: '4',
            text: 'IBNR Upload'
        }, {
            id: '5',
            text: 'Estimation'
        }, {
            id: '6',
            text: 'Collection'
        }, {
            id: '7',
            text: 'Payment'
        }, {
            id: '8',
            text: 'Internal Offset'
        }, {
            id: '9',
            text: 'Transaction Reversal'
        }, {
            id: '10',
            text: 'Revaluation'
        }
    ]);

    codes.CedentPeriod = $pt.createCodeTable([
        {
            id: "1",
            text: "Q1"
        }, {
            id: "2",
            text: "Q2"
        }, {
            id: "3",
            text: "Q3"
        }, {
            id: "4",
            text: "Q4"
        }, {
            id: "5",
            text: "1Halfyear"
        }, {
            id: "6",
            text: "2Halfyear"
        }, {
            id: "7",
            text: "Yearly"
        }, {
            id: "8",
            text: "January"
        }, {
            id: "9",
            text: "February"
        }, {
            id: "10",
            text: "March"
        }, {
            id: "11",
            text: "April"
        }, {
            id: "12",
            text: "May"
        }, {
            id: "13",
            text: "June"
        }, {
            id: "14",
            text: "July"
        }, {
            id: "15",
            text: "August"
        }, {
            id: "16",
            text: "September"
        }, {
            id: "17",
            text: "October"
        }, {
            id: "18",
            text: "November"
        }, {
            id: "19December",
            text: "December"
        }
    ]);
    codes.StatementType = $pt.createCodeTable([
        {
            id: "1",
            text: "Quarterly statement and others"
        }, {
            id: "2",
            text: "Portfolio transfer in"
        }, {
            id: "3",
            text: "Portfolio transfer out"
        }
    ]);

    codes.CurrencySpecial = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+800022});
    codes.Bank = $pt.createCodeTable([
        {
            id: '1',
            text: 'Standard Chartered Bank (Hong Kong) Limited'
        }
    ]);
    codes.LatestStatus = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810030});
    codes.ContractNature = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810001});
    codes.ContractCategory = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810002});
    codes.ContractType = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810003});
    codes.ShareCate = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810004});
    codes.ShareType = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810005});
    codes.ClaimLimitCate = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810006});
    codes.WeatherIndexs = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810007});
    codes.PriceIndexs = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810008});
    codes.TerminationCondition = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810009});
    codes.ExchangeType = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810010});
    codes.EPIType = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810011});
    codes.PremiumDefinedIn = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810012});
    codes.DeductionItem = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810013});
    codes.Event = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810014});
    codes.ProtectionBasis = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810015});
    codes.ClaimBasis = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810016});
    codes.AccountingBasis = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810017});
    codes.EarningPattern = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810018});
    codes.WrittenPattern = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810019});
    codes.AccountFreq = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810020});
    codes.ShareDefinedIn = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810021});
    codes.PremiumCalcMethod = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810022});
    codes.PerRiskEvent = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810023});
    codes.RICommission = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810024});
    codes.SystemUser = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+-106});
    codes.UwCompany = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810027});
    codes.required = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810031});
    codes.recommend = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810032});

    codes.ParticipationBase = $pt.createCodeTable([
        {
            id: "1",
            text: "Loss Ratio"
        }, {
            id: "2",
            text: "Combined Ratio"
        }
    ]);
    codes.EventClaim = $pt.createCodeTable([
        {
            id: '1641143',
            text: 'EN001C'
        }, {
            id: '1642173',
            text: 'EN002C'
        }
    ]);
    codes.UWYear = $pt.createCodeTable([
        {
            id: '2015',
            text: '2015'
        }, {
            id: '2016',
            text: '2016'
        }
    ]);

    codes.Cause = $pt.createCodeTable([
        {
            id: 'ARSN',
            text: 'Arson'
        },
        {
            id: 'AVAL',
            text: 'Avalanch'
        },
        {
            id: 'BORD',
            text: 'Bordereau'
        },
        {
            id: 'BUFR',
            text: 'Bush Fire/Wildfire'
        },
        {
            id: 'CAPS',
            text: 'Capsizing'
        },
        {
            id: 'CLOS',
            text: 'Cargo loss/damage'
        },
        {
            id: 'COLL',
            text: 'Collapse'
        },
        {
            id: 'COLS',
            text: 'Collision'
        },
        {
            id: 'DERA',
            text: 'Derailment'
        },
        {
            id: 'DROT',
            text: 'Drought'
        },
        {
            id: 'EARTH',
            text: 'Earthquake'
        },
        {
            id: 'ENV',
            text: 'Environmental Pollution'
        },
        {
            id: 'EXPL',
            text: 'Explosion'
        },
        {
            id: 'FDES',
            text: 'Faulty Design'
        },
        {
            id: 'FIRE',
            text: 'Fire'
        },
        {
            id: 'FLOOD',
            text: 'Flood'
        },
        {
            id: 'HAIL',
            text: 'Hail'
        },
        {
            id: 'HIPI',
            text: 'Hijack/Piracy'
        },
        {
            id: 'HUM',
            text: 'Human Error'
        },
        {
            id: 'LDSL',
            text: 'Landslide'
        },
        {
            id: 'LIGT',
            text: 'Lightning'
        },
        {
            id: 'MACH',
            text: 'Machinery Breakdown'
        },
        {
            id: 'MEDM',
            text: 'Medical Malpractice'
        },
        {
            id: 'NATUR',
            text: 'Natural Disaster'
        },
        {
            id: 'OILC',
            text: 'Oil contamination (cargo)'
        },
        {
            id: 'OTHR',
            text: 'Others'
        },
        {
            id: 'PAND',
            text: 'Pandemic'
        },
        {
            id: 'PIA',
            text: 'Personal Injury/Fatal Injury'
        },
        {
            id: 'POLL',
            text: 'Pollution'
        },
        {
            id: 'STRM',
            text: 'Rainstorm'
        },
        {
            id: 'RIOT',
            text: 'Riot/ Strike'
        },
        {
            id: 'ROBB',
            text: 'Robbery'
        },
        {
            id: 'INFI',
            text: 'Staff/director infidelity'
        },
        {
            id: 'TECH',
            text: 'Technical Failure'
        },
        {
            id: 'TERR',
            text: 'Terrorism'
        },
        {
            id: 'THEFT',
            text: 'Theft / Robbery'
        },
        {
            id: 'TORN',
            text: 'Tornado'
        },
        {
            id: 'TFAC',
            text: 'Traffic accident'
        },
        {
            id: 'TSUN',
            text: 'Tsunami'
        },
        {
            id: 'UNKNO',
            text: 'Unknown'
        },
        {
            id: 'VAND',
            text: 'Vandalism / Malicious Damage'
        },
        {
            id: 'GROU',
            text: 'Vessel Grounded / Stranded'
        },
        {
            id: 'VULC',
            text: 'Vulcanic Eruption'
        },
        {
            id: 'TROP',
            text: 'Windstorm'
        },
        {
            id: 'WSTR',
            text: 'Winter Storm'
        },
        {
            id: 'WINJ',
            text: 'Work Injury'
        }
    ]);
  codes.BusinessType = $pt.createCodeTable([
        {
            id: '1',
            text: 'Direct'
        }, {
            id: '2',
            text: 'Indirect'
        }
    ]);
    codes.Class = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810028});

    codes.SubClass = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810029});

    codes.CoveredArea = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generateTree")+800019+"/"+0+"/"+0});
    codes.Peril = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generateByCondition")+810026,data:{PARENT_ID:100}});
    codes.CedentCountry = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generateByCondition")+800019,data:{COUNTRY_LEVEL:3}});
    codes.LimitPeril =  $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generateByCondition")+810026,data:{PERIL_LEVEL:2}});
    codes.MainCoveredArea = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+800019});

    codes.DeficitCarryForward = $pt.createCodeTable([
        {
            id: "1",
            text: "To Extinction"
        }, {
            id: "2",
            text: "Number of Years"
        }
    ]);
    codes.SettlementStatus = $pt.createCodeTable([
        {
            id: "1",
            text: "Normal"
        }, {
            id: "-1",
            text: "Reversed"
        }
    ]);
    codes.TempUser = $pt.createCodeTable([
          {
              id: "-99",
              text: "ADMIN"
          }
      ]);
    codes.PaymentMethod = $pt.createCodeTable([
        {
            id: "1",
            text: "Wire Transfer"
        }, {
            id: "2",
            text: "Cheque"
        }
    ]);
    codes.BalanceType = $pt.createCodeTable([
        {
            id: "1",
            text: "Contract Balance"
        }, {
            id: "2",
            text: "Business Partner Balance"
        }
    ]);
    codes.ReversalTransactionType = $pt.createCodeTable([
        {
            id: "1",
            text: "Collection"
        }, {
            id: "2",
            text: "Payment"
        }, {
            id: "3",
            text: "Internal Offset"
        }
    ]);
    codes.TransactionType = $pt.createCodeTable([
        {
            id: "1",
            text: "Collection"
        }, {
            id: "2",
            text: "Payment"
        }, {
            id: "3",
            text: "Internal Offset"
        }, {
            id: "4",
            text: "Reversal"
        }
    ]);
    codes.ExchangeRateReference = $pt.createCodeTable([
        {
            id: "1",
            text: "Value Date"
        }, {
            id: "2",
            text: "Operation Date"
        }
    ]);
    codes.AccountStatus = $pt.createCodeTable([
        {
            id: "0",
            text: "Invalid"
        }, {
            id: "1",
            text: "Valid"
        }
    ]);
    codes.ExchangeRateStatus = $pt.createCodeTable([
        {
            id: "0",
            text: "Invalid"
        }, {
            id: "1",
            text: "Valid"
        }
    ]);
    codes.ClaimStatus = $pt.createCodeTable([
        {
            id: "0",
            text: "Open"
        }, {
            id: "1",
            text: "Close"
        }
    ]);
    codes.AccountType = $pt.createCodeTable([
        {
            id: "1",
            text: "Collection Account"
        }, {
            id: "2",
            text: "Payment Account"
        }, {
            id: "3",
            text: "Both"
        }
    ]);
    codes.SettleStatus = $pt.createCodeTable([{id: "1", text: "New"}, {id: "2", text: "Pending Approved"}, {
        id: "3",
        text: "Approved"
    }, {id: "4", text: "Reject"}]);
    codes.ARAPType = $pt.createCodeTable([
        {
            id: "1",
            text: "Claim Payment"
        }, {
            id: "2",
            text: "Claim Paid"
        }, {
            id: "3",
            text: "RIP"
        }
    ]);
    codes.EndorsementType = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810025});
    codes.RateType = $pt.createCodeTable([
        {
            id: "1",
            text: "General Exchange Rate"
        }, {
            id: "2",
            text: "Month-End Exchange Rate"
        }
    ]);
    codes.ReversalReason = $pt.createCodeTable([
        {
            id: "0",
            text: "Others"
        }
    ]);
    codes.Branch = $pt.createCodeTable([
        {
            id: "1",
            text: "HongKong"
        }, {
            id: "2",
            text: "Zurich"
        }
    ]);
    codes.Cob = $pt.createCodeTable([
        {
            id: "1",
            text: "Agriculture"
        }, {
            id: "2",
            text: "Aviation/Space"
        }, {
            id: "3",
            text: "Casualty"
        }, {
            id: "4",
            text: "Credit and Bonds"
        }, {
            id: "5",
            text: "Engineering"
        }, {
            id: "6",
            text: "Financial Lines"
        }, {
            id: "7",
            text: "Life"
        }, {
            id: "8",
            text: "Marine"
        }, {
            id: "9",
            text: "Miscellaneous Accident"
        }, {
            id: "10",
            text: "Motor"
        }, {
            id: "11",
            text: "Property"
        }
    ]);
    codes.OrganizationIdType = $pt.createCodeTable([
        {
            id: "1",
            text: "Commercial Registration"
        }, {
            id: "2",
            text: "Dummy"
        }, {
            id: "3",
            text: "Other"
        }
    ]);
    codes.AccountHolderType = $pt.createCodeTable([
        {
            id: "1",
            text: "Individual Account"
        }, {
            id: "2",
            text: "Company Account"
        }
    ]);

    codes.RelationshipType = $pt.createCodeTable([
        {
            id: "1",
            text: "Subsidiary"
        }
    ]);
    codes.Gender = $pt.createCodeTable([
        {
            id: "1",
            text: "Male"
        }, {
            id: "2",
            text: "Female"
        }
    ]);
    // codes.Country = $pt.createCodeTable([
    //     {
    //         id: "1",
    //         text: "China"
    //     }, {
    //         id: "2",
    //         text: "United States"
    //     }, {
    //         id: "3",
    //         text: "United Kingdom"
    //     }
    // ]);
    codes.IDType = $pt.createCodeTable([
        {
            id: "1",
            text: "Brith Certificate"
        }, {
            id: "2",
            text: "Drive License"
        }, {
            id: "3",
            text: "Dummy"
        }, {
            id: "4",
            text: "Passport"
        }, {
            id: "5",
            text: "ID Card"
        }, {
            id: "6",
            text: "Military ID"
        }, {
            id: "7",
            text: "Others"
        }

    ]);
    codes.CoBType = $pt.createCodeTable([
        {
            id: "1",
            text: "Fire"
        }, {
            id: "2",
            text: "Internal Update"
        }, {
            id: "3",
            text: "Special Acceptance"
        }
    ]);
    codes.UwArea = $pt.createCodeTable([
        {
            id: "1",
            text: "HongKong"
        }, {
            id: "2",
            text: "Internal Update"
        }, {
            id: "3",
            text: "Special Acceptance"
        }
    ]);
    //Australia, Hong Kong, Japan, USA,

    codes.ccountry = $pt.createCodeTable([
        {
            id: "1",
            text: "Australia"
        }, {
            id: "2",
            text: "Hong Kong"
        }, {
            id: "3",
            text: "Japan"
        }, {
            id: "4",
            text: "USA"
        }
    ]);
    codes.AccountLevel = $pt.createCodeTable([
        {
            id: "1",
            text: "Our Share"
        }, {
            id: "2",
            text: "Internal Update"
        }, {
            id: "3",
            text: "Special Acceptance"
        }
    ]);

    codes.CedentQuarter = $pt.createCodeTable([
        {
            id: "1",
            text: "Q1"
        }, {
            id: "2",
            text: "Q2"
        }, {
            id: "3",
            text: "Q3"
        }, {
            id: "4",
            text: "Q4"
        }
    ]);
    codes.FNQuarter = $pt.createCodeTable([
        {
            id: "1",
            text: "Q1"
        }, {
            id: "2",
            text: "Q2"
        }, {
            id: "3",
            text: "Q3"
        }, {
            id: "4",
            text: "Q4"
        }
    ]);

    codes.FNYear = $pt.createCodeTable([
        {
            id: "2015",
            text: "2015"
        }, {
            id: "2016",
            text: "2016"
        }, {
            id: "2017",
            text: "2017"
        }, {
            id: "2018",
            text: "2018"
        }, {
            id: "2019",
            text: "2019"
        }, {
            id: "2020",
            text: "2020"
        }
    ]);

    codes.ReviewedFlag = $pt.createCodeTable([
        {
            id: "1",
            text: "Y"
        }, {
            id: "2",
            text: "N"
        }, {
            id: "",
            text: "N"
        }
    ]);

    codes.CedentYear = $pt.createCodeTable([
        {
            id: "2015",
            text: "2015"
        }, {
            id: "2016",
            text: "2016"
        }, {
            id: "2017",
            text: "2017"
        }, {
            id: "2018",
            text: "2018"
        }, {
            id: "2019",
            text: "2019"
        }, {
            id: "2020",
            text: "2020"
        }
    ]);

    codes.UwYear = $pt.createCodeTable([
        {
            id: "2015",
            text: "2015"
        }, {
            id: "2016",
            text: "2016"
        }, {
            id: "2017",
            text: "2017"
        }, {
            id: "2018",
            text: "2018"
        }, {
            id: "2019",
            text: "2019"
        }, {
            id: "2020",
            text: "2020"
        }
    ]);

    codes.RevaluationStatus = $pt.createCodeTable([
        {
            id: "1",
            text: "In Process"
        }, {
            id: "2",
            text: "Complete"
        }
    ]);


    codes.SoaStatus = $pt.createCodeTable([
        {
            id: "1",
            text: "DataInput"
        }, {
            id: "2",
            text: "Released"
        }, {
            id: "3",
            text: "WithDrawal"
        }
    ]);
    codes.CurrencyType = $pt.createCodeTable([
        {id: '1', text: "USD"},
        {id: '2', text: "CNY"},
        {id: '3', text: 'HKD'}
    ]);
    codes.BusinessTransType = $pt.createCodeTable([
        {
            id: '1',
            text: 'Claim Reserve'
        }, {
            id: '2',
            text: 'Claim Settlement'
        }, {
            id: '3',
            text: 'Statement'
        }, {
            id: '4',
            text: 'IBNR Upload'
        }, {
            id: '5',
            text: 'Estimation'
        }, {
            id: '6',
            text: 'Collection'
        }, {
            id: '7',
            text: 'Payment'
        }, {
            id: '8',
            text: 'Internal Offset'
        }, {
            id: '9',
            text: 'Transaction Reversal'
        }, {
            id: '10',
            text: 'Revaluation'
        }
    ]);
    codes.LossCalcMethod = $pt.createCodeTable([
        {
            id: "1",
            text: "NIL"
        }, {
            id: "2",
            text: "Including IBNR"
        }, {
            id: "3",
            text: "Excluding IBNR"
        }
    ]);
    codes.AdjustedQuarter = $pt.createCodeTable([
        {
            id: "1",
            text: "4th Quarter "
        }, {
            id: "2",
            text: "Every Quarter"
        }
    ]);

    codes.partnerRole = $pt.createCodeTable([
        {id:"1",text:"Cedent"},
        {id:"2",text:"Broker"},
        {id:"3", text:"Insured"},
        {id:"4", text:"Retrocessionaire"},
        {id:"5", text:"MGA(Managing General Agent)"}
    ]);
    codes.BusinessPartnerCategory = $pt.createCodeTable([
        {
            id: "1",
            text: "Individual"
        }, {
            id: "2",
            text: "Organization"
        }
    ]);
    codes.BusinessPartnerStatus = $pt.createCodeTable([
        {
            id: "1",
            text: "Active"
        }, {
            id: "2",
            text: "Inactive"
        }
    ]);
    codes.entryCode = $pt.createCodeTable([
        {id:"1000",text:"Premium prop"},
        {id:"1010",text:"XoL Premium"},
        {id:"1011",text:"XoL Premium Adjustment"},
        {id:"1020",text:"Reinstatement Premium"},
        {id:"1050",text:"EPI"},
        {id:"1060",text:"GNPI"},
        {id:"2000",text:"Commission"},
        {id:"2010",text:"Provisonal Commission"},
        {id:"2020",text:"Sliding Scale Commission"},
        {id:"2030",text:"Profit Commission"},
        {id:"2040",text:"Overriding Commission"},
        {id:"2050",text:"Acquisition Costs"},
        {id:"2060",text:"Administrative Expensins"},
        {id:"2070",text:"Fire Brigade Tax"},
        {id:"2071",text:"Tax Others"},
        {id:"2072",text:"Other Expenses"},
        {id:"2073",text:"Reinsurance Premium Costs"},
        {id:"2075",text:"Interests on Accounts"},
        {id:"2080",text:"No Claims Bonus"},
        {id:"2090",text:"Brokerage"},
        {id:"3000",text:"Loss Paid"},
        {id:"3020",text:"Loss Participation"},
        {id:"3030",text:"LAE Paid (Loss Adj. Expenses)"},
        {id:"3040",text:"Cash Call Payment"},
        {id:"3041",text:"Cash Call Offsetting Entry"},
        //{id:"4011",text:"UPR Opening"},
        //{id:"4012",text:"UPR Closing"},
        {id:"4012",text:"UPR"},
        //{id:"4015",text:"DAC Opening"},
        //{id:"4016",text:"DAC Closing"},
        {id:"4016",text:"DAC"},
        //{id:"4021",text:"Loss Reserve Opening"},
        //{id:"4022",text:"Loss Reserve Closing"},
        {id:"4022",text:"Loss Reserve"},
        //{id:"4023",text:"Large Loss Reserve Opening"},
        //{id:"4024",text:"Large Loss Reserve Closing"},
        {id:"4024",text:"Large Loss Reserve"},
        //{id:"4025",text:"Additional Loss Reserve Opening"},
        //{id:"4026",text:"Additional Loss Reserve Closing"},
        {id:"4026",text:"Additional Loss Reserve"},
        //{id:"4041",text:"LAE Reserve Opening"},
        //{id:"4042",text:"LAE Reserve Closing"},
        {id:"4042",text:"LAE Reserve"},
        //{id:"4051",text:"Provision Sliding Scale Comm. Openening"},
        //{id:"4052",text:"Provision Sliding Scale Comm. Closing"},
        {id:"4052",text:"Provision Sliding Scale Comm."},
        //{id:"4053",text:"Provision Profit Commission Opening"},
        //{id:"4054",text:"Provision Profit Commission Closing"},
        {id:"4054",text:"Provision Profit Commission"},
        //{id:"4055",text:"Provision Loss Participation Openening"},
        //{id:"4056",text:"Provision Loss Participation Closing"},
        {id:"4056",text:"Provision Loss Participation"},
        //{id:"4061",text:"IBNR Opening"},
        //{id:"4062",text:"IBNR Closing"},
        {id:"4062",text:"IBNR"},

        //{id:"4111",text:"HKAS UPR Opening"},
        //{id:"4112",text:"HKAS UPR Closing"},
        {id:"4112",text:"HKAS UPR"},

        //{id:"4115",text:"HKAS DAC Opening"},
        //{id:"4116",text:"HKAS DAC Closing"},
        {id:"4116",text:"HKAS DAC"},

        //{id:"4121",text:"HKAS Loss Reserve Opening"},
        //{id:"4122",text:"HKAS Loss Reserve Closing"},
        {id:"4122",text:"HKAS Loss Reserve"},

        //{id:"4123",text:"HKAS Large Loss Reserve Opening"},
        //{id:"4124",text:"HKAS Large Loss Reserve Closing"},
        {id:"4124",text:"HKAS Large Loss Reserve"},

        //{id:"4125",text:"HKAS Additional Loss Reserve Opening"},
        //{id:"4126",text:"HKAS Additional Loss Reserve Closing"},
        {id:"4126",text:"HKAS Additional Loss Reserve"},

        //{id:"4141",text:"HKAS LAE Reserve Opening"},
        //{id:"4142",text:"HKAS LAE Reserve Closing"},
        {id:"4142",text:"HKAS LAE Reserve"},

        //{id:"4151",text:"HKAS Provision Sliding Scale Comm. Open."},
        //{id:"4152",text:"HKAS Provision Sliding Scale Comm. Clos."},
        {id:"4152",text:"HKAS Provision Sliding Scale Comm."},

        //{id:"4153",text:"HKAS Provision Profit Commission Open."},
        //{id:"4154",text:"HKAS Provision Profit Commission Clos."},
        {id:"4154",text:"HKAS Provision Profit Commission"},

        //{id:"4155",text:"HKAS Provision Loss Participation Open."},
        //{id:"4156",text:"HKAS Provision Loss Participation  Clos."},
        {id:"4156",text:"HKAS Provision Loss Participation"},

        //{id:"4161",text:"HKAS IBNR Opening"},
        //{id:"4162",text:"HKAS IBNR Closing"},
        {id:"4162",text:"HKAS IBNR"},

        //{id:"4171",text:"HKAS PDR Opening"},
        //{id:"4172",text:"HKAS PDR Closing"},
        {id:"4172",text:"HKAS PDR"},

        //{id:"4181",text:"HKAS Math Res Opening"},
        //{id:"4182",text:"HKAS Math Res Closing"},
        {id:"4182",text:"HKAS Math Res"},

        {id:"5010",text:"Premium Ptf In"},
        {id:"5011",text:"Premium Ptf Out"},
        {id:"5020",text:"Commission Ptf In"},
        {id:"5021",text:"Commission Ptf Out"},
        {id:"5030",text:"Loss Paid Ptf In"},
        {id:"5031",text:"Loss Paid Ptf Out"},
        {id:"5040",text:"Loss Reserve Ptf In"},
        {id:"5041",text:"Loss Reserve Ptf Out"},
        {id:"6111",text:"Premium Reserve Withheld"},
        {id:"6112",text:"Premium Reserve Release"},
        {id: "EP02",text: "EP"},
        {id: "9999",text: "Total"}
    ]);
    codes.detailEntry = $pt.createCodeTable([
        {id:"1000",text:"Premium prop"},
        {id:"1010",text:"XoL Premium"},
        {id:"1011",text:"XoL Premium Adjustment"},
        {id:"1020",text:"Reinstatement Premium"},
        {id:"1050",text:"EPI"},
        {id:"1060",text:"GNPI"},
        {id:"2000",text:"Commission"},
        {id:"2010",text:"Provisonal Commission"},
        {id:"2020",text:"Sliding Scale Commission"},
        {id:"2030",text:"Profit Commission"},
        {id:"2040",text:"Overriding Commission"},
        {id:"2050",text:"Acquisition Costs"},
        {id:"2060",text:"Administrative Expensins"},
        {id:"2070",text:"Fire Brigade Tax"},
        {id:"2071",text:"Tax Others"},
        {id:"2072",text:"Other Expenses"},
        {id:"2073",text:"Reinsurance Premium Costs"},
        {id:"2075",text:"Interests on Accounts"},
        {id:"2080",text:"No Claims Bonus"},
        {id:"2090",text:"Brokerage"},
        {id:"3000",text:"Loss Paid"},
        {id:"3020",text:"Loss Participation"},
        {id:"3030",text:"LAE Paid (Loss Adj. Expenses)"},
        {id:"3040",text:"Cash Call Payment"},
        {id:"3041",text:"Cash Call Offsetting Entry"},
        {id:"4011",text:"UPR Opening"},
        {id:"4012",text:"UPR Closing"},
        {id:"4015",text:"DAC Opening"},
        {id:"4016",text:"DAC Closing"},
        {id:"4021",text:"Loss Reserve Opening"},
        {id:"4022",text:"Loss Reserve Closing"},
        {id:"4023",text:"Large Loss Reserve Opening"},
        {id:"4024",text:"Large Loss Reserve Closing"},
        {id:"4025",text:"Additional Loss Reserve Opening"},
        {id:"4026",text:"Additional Loss Reserve Closing"},
        {id:"4041",text:"LAE Reserve Opening"},
        {id:"4042",text:"LAE Reserve Closing"},
        {id:"4051",text:"Provision Sliding Scale Comm. Openening"},
        {id:"4052",text:"Provision Sliding Scale Comm. Closing"},
        {id:"4053",text:"Provision Profit Commission Opening"},
        {id:"4054",text:"Provision Profit Commission Closing"},
        {id:"4055",text:"Provision Loss Participation Openening"},
        {id:"4056",text:"Provision Loss Participation Closing"},
        {id:"4061",text:"IBNR Opening"},
        {id:"4062",text:"IBNR Closing"},
        {id:"4111",text:"HKAS UPR Opening"},
        {id:"4112",text:"HKAS UPR Closing"},
        {id:"4115",text:"HKAS DAC Opening"},
        {id:"4116",text:"HKAS DAC Closing"},
        {id:"4121",text:"HKAS Loss Reserve Opening"},
        {id:"4122",text:"HKAS Loss Reserve Closing"},
        {id:"4123",text:"HKAS Large Loss Reserve Opening"},
        {id:"4124",text:"HKAS Large Loss Reserve Closing"},
        {id:"4125",text:"HKAS Additional Loss Reserve Opening"},
        {id:"4126",text:"HKAS Additional Loss Reserve Closing"},
        {id:"4141",text:"HKAS LAE Reserve Opening"},
        {id:"4142",text:"HKAS LAE Reserve Closing"},
        {id:"4151",text:"HKAS Provision Sliding Scale Comm. Open."},
        {id:"4152",text:"HKAS Provision Sliding Scale Comm. Clos."},
        {id:"4153",text:"HKAS Provision Profit Commission Open."},
        {id:"4154",text:"HKAS Provision Profit Commission Clos."},
        {id:"4155",text:"HKAS Provision Loss Participation Open."},
        {id:"4156",text:"HKAS Provision Loss Participation Clos."},
        {id:"4161",text:"HKAS IBNR Opening"},
        {id:"4162",text:"HKAS IBNR Closing"},
        {id:"4171",text:"HKAS PDR Opening"},
        {id:"4172",text:"HKAS PDR Closing"},
        {id:"4181",text:"HKAS Math Res Opening"},
        {id:"4182",text:"HKAS Math Res Closing"},
        {id:"5010",text:"Premium Ptf In"},
        {id:"5011",text:"Premium Ptf Out"},
        {id:"5020",text:"Commission Ptf In"},
        {id:"5021",text:"Commission Ptf Out"},
        {id:"5030",text:"Loss Paid Ptf In"},
        {id:"5031",text:"Loss Paid Ptf Out"},
        {id:"5040",text:"Loss Reserve Ptf In"},
        {id:"5041",text:"Loss Reserve Ptf Out"},
        {id:"6111",text:"Premium Reserve Withheld"},
        {id:"6112",text:"Premium Reserve Release"},
        {id: "EP01",text: "HKAS EP Opening"},
        {id: "EP02",text: "HKAS EP Closing"},
        {id: "9999",text: "Total"}
    ]);
    codes.DocumentBusinessType = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+800015});
    codes.DocumentType = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+800016});

//     codes.CedentCountry = $pt.createCodeTable([
//       {id:"AD",text:"Andorra"},
//       {id:"AE",text:"United Arab Emirates"},
//       {id:"AF",text:"Afghanistan"},
//       {id:"AG",text:"Antigua and Barbuda"},
//       {id:"AI",text:"Anguilla"},
//       {id:"AL",text:"Albania"},
//       {id:"AM",text:"Armenia"},
//       {id:"AN",text:"Dutch Antilles"},
//       {id:"AO",text:"Angola"},
//       {id:"AQ",text:"Antarctica"},
//       {id:"AR",text:"Argentina"},
//       {id:"AS",text:"American Samoa"},
//       {id:"AT",text:"Austria"},
//       {id:"AU",text:"Australia"},
//       {id:"AW",text:"Aruba"},
//       {id:"AZ",text:"Azerbaijan"},
//       {id:"BA",text:"Bosnia and Herzegovina"},
//       {id:"BB",text:"Barbados"},
//       {id:"BD",text:"Bangladesh"},
//       {id:"BE",text:"Belgium"},
//       {id:"BF",text:"Burkina Faso"},
//       {id:"BG",text:"Bulgaria"},
//       {id:"BH",text:"Bahrain"},
//       {id:"BI",text:"Burundi"},
//       {id:"BJ",text:"Benin"},
//       {id:"BL",text:"St. Barts"},
//       {id:"BM",text:"Bermuda"},
//       {id:"BN",text:"Brunei Darussalam"},
//       {id:"BO",text:"Bolivia"},
//       {id:"BR",text:"Brazil"},
//       {id:"BS",text:"Bahamas"},
//       {id:"BT",text:"Bhutan"},
//       {id:"BV",text:"Bouvet Islands"},
//       {id:"BW",text:"Botswana"},
//       {id:"BY",text:"Belarus"},
//       {id:"BZ",text:"Belize"},
//       {id:"CA",text:"Canada"},
//       {id:"CC",text:"Coconut Islands"},
//       {id:"CD",text:"Democratic Republic of the Congo"},
//       {id:"CF",text:"Central African Republic"},
//       {id:"CG",text:"Republic of the Congo"},
//       {id:"CH",text:"Switzerland"},
//       {id:"CI",text:"Cote d'Ivoire"},
//       {id:"CK",text:"Cook Islands"},
//       {id:"CL",text:"Chile"},
//       {id:"CM",text:"Cameroon"},
//       {id:"CN",text:"China"},
//       {id:"CO",text:"Colombia"},
//       {id:"CR",text:"Costa Rica"},
//       {id:"CS",text:"Serbia and Montenegro"},
//       {id:"CU",text:"Cuba"},
//       {id:"CV",text:"Cape Verde"},
//       {id:"CX",text:"Christmas Islnd"},
//       {id:"CY",text:"Cyprus"},
//       {id:"CZ",text:"Czech Republic"},
//       {id:"DE",text:"Germany"},
//       {id:"DJ",text:"Djibouti"},
//       {id:"DK",text:"Denmark"},
//       {id:"DM",text:"Dominica"},
//       {id:"DO",text:"Dominican Republic"},
//       {id:"DZ",text:"Algeria"},
//       {id:"EC",text:"Ecuador"},
//       {id:"EE",text:"Estonia"},
//       {id:"EG",text:"Egypt"},
//       {id:"EH",text:"West Sahara"},
//       {id:"ER",text:"Eritrea"},
//       {id:"ES",text:"Spain"},
//       {id:"ET",text:"Ethiopia"},
//       {id:"EU",text:"European Union"},
//       {id:"FI",text:"Finland"},
//       {id:"FJ",text:"Fiji"},
//       {id:"FK",text:"Falkland Islands"},
//       {id:"FM",text:"Micronesia"},
//       {id:"FO",text:"Faroe Islands"},
//       {id:"FR",text:"France"},
//       {id:"GA",text:"Gabon"},
//       {id:"GB",text:"United Kingdom"},
//       {id:"GD",text:"Grenada"},
//       {id:"GE",text:"Georgia"},
//       {id:"GF",text:"French Guyana"},
//       {id:"GG",text:"Guernsey"},
//       {id:"GH",text:"Ghana"},
//       {id:"GI",text:"Gibraltar"},
//       {id:"GL",text:"Greenland"},
//       {id:"GM",text:"Gambia"},
//       {id:"GN",text:"Guinea"},
//       {id:"GP",text:"Guadeloupe"},
//       {id:"GQ",text:"Equatorial Guinea"},
//       {id:"GR",text:"Greece"},
//       {id:"GS",text:"South Georgia and the Southern Sandwich Islands"},
//       {id:"GT",text:"Guatemala"},
//       {id:"GU",text:"Guam"},
//       {id:"GW",text:"Guinea-Bissau"},
//       {id:"GY",text:"Guyana"},
//       {id:"HK",text:"Hong Kong"},
//       {id:"HM",text:"Heard and McDonald Islands"},
//       {id:"HN",text:"Honduras"},
//       {id:"HR",text:"Croatia"},
//       {id:"HT",text:"Haiti"},
//       {id:"HU",text:"Hungary"},
//       {id:"ID",text:"Indonesia"},
//       {id:"IE",text:"Ireland"},
//       {id:"IL",text:"Israel"},
//       {id:"IN",text:"India"},
//       {id:"IO",text:"British Indian Ocean Territory"},
//       {id:"IQ",text:"Iraq"},
//       {id:"IR",text:"Iran"},
//       {id:"IS",text:"Iceland"},
//       {id:"IT",text:"Italy"},
//       {id:"JM",text:"Jamaica"},
//       {id:"JO",text:"Jordan"},
//       {id:"JP",text:"Japan"},
//       {id:"KE",text:"Kenya"},
//       {id:"KG",text:"Kyrgyzstan"},
//       {id:"KH",text:"Cambodia"},
//       {id:"KI",text:"Kiribati"},
//       {id:"KM",text:"Comoros"},
//       {id:"KN",text:"Saint Kitts and Nevis"},
//       {id:"KP",text:"North Korea"},
//       {id:"KR",text:"South Korea"},
//       {id:"KW",text:"Kuwait"},
//       {id:"KY",text:"Cayman Islands"},
//       {id:"KZ",text:"Kazakhstan"},
//       {id:"LA",text:"Laos"},
//       {id:"LB",text:"Lebanon"},
//       {id:"LC",text:"St. Lucia"},
//       {id:"LI",text:"Liechtenstein"},
//       {id:"LK",text:"Sri Lanka"},
//       {id:"LR",text:"Liberia"},
//       {id:"LS",text:"Lesotho"},
//       {id:"LT",text:"Lithuania"},
//       {id:"LU",text:"Luxembourg"},
//       {id:"LV",text:"Latvia"},
//       {id:"LY",text:"Libya"},
//       {id:"MA",text:"Morocco"},
//       {id:"MC",text:"Monaco"},
//       {id:"MD",text:"Moldova"},
//       {id:"ME",text:"Montenegro"},
//       {id:"MF",text:"Saint Martin (French Part)"},
//       {id:"MG",text:"Madagascar"},
//       {id:"MH",text:"Marshall Islands"},
//       {id:"MK",text:"Macedonia"},
//       {id:"ML",text:"Mali"},
//       {id:"MM",text:"Burma"},
//       {id:"MN",text:"Mongolia"},
//       {id:"MO",text:"Macau"},
//       {id:"MP",text:"North Mariana Islands"},
//       {id:"MQ",text:"Martinique"},
//       {id:"MR",text:"Mauretania"},
//       {id:"MS",text:"Montserrat"},
//       {id:"MT",text:"Malta"},
//       {id:"MU",text:"Mauritius"},{id:"MV",text:"Maldives"},{id:"MW",text:"Malawi"},{id:"MX",text:"Mexico"},
//       {id:"MY",text:"Malaysia"},{id:"MZ",text:"Mozambique"},{id:"NA",text:"Namibia"},{id:"NC",text:"New Caledonia"},
//       {id:"NE",text:"Niger"},{id:"NF",text:"Norfolk Islands"},{id:"NG",text:"Nigeria"},{id:"NI",text:"Nicaragua"},
//       {id:"NL",text:"Netherlands"},{id:"NO",text:"Norway"},{id:"NP",text:"Nepal"},{id:"NR",text:"Nauru"},
//       {id:"NT",text:"NATO"},{id:"NU",text:"Niue"},{id:"NZ",text:"New Zealand"},{id:"OM",text:"Oman"},
//       {id:"OR",text:"Orange"},{id:"PA",text:"Panama"},{id:"PE",text:"Peru"},{id:"PF",text:"French Polynesia"},
//       {id:"PG",text:"Papua New Guinea"},{id:"PH",text:"Philippines"},{id:"PK",text:"Pakistan"},{id:"PL",text:"Poland"},
//       {id:"PM",text:"St. Pierre and Miquelon"},{id:"PN",text:"Pitcairn Islands"},{id:"PR",text:"Puerto Rico"},
//       {id:"PS",text:"Palestine"},{id:"PT",text:"Portugal"},{id:"PW",text:"Palau"},
//       {id:"PY",text:"Paraguay"},{id:"QA",text:"Qatar"},{id:"RE",text:"Reunion"},
//       {id:"RO",text:"Romania"},{id:"RS",text:"Republic of Serbia"},{id:"RU",text:"Russian Federation"},
//       {id:"RW",text:"Rwanda"},{id:"SA",text:"Saudi Arabia"},{id:"SB",text:"Solomon Islands"},
//       {id:"SC",text:"Seychelles"},
// {id:"SD",text:"Sudan"},
// {id:"SE",text:"Sweden"},
// {id:"SG",text:"Singapore"},
// {id:"SH",text:"Saint Helena"},
// {id:"SI",text:"Slovenia"},
// {id:"SJ",text:"Svalbard"},
// {id:"SK",text:"Slovakia"},
// {id:"SL",text:"Sierra Leone"},
// {id:"SM",text:"San Marino"},
// {id:"SN",text:"Senegal"},
// {id:"SO",text:"Somalia"},
// {id:"SR",text:"Suriname"},
// {id:"ST",text:"Sao Tome and Principe"},
// {id:"SV",text:"El Salvador"},
// {id:"SX",text:"Sint Maarten (Dutch)"},
// {id:"SY",text:"Syria"},
// {id:"SZ",text:"Swaziland"},
// {id:"TC",text:"Turks and Caicos Islands"},
// {id:"TD",text:"Chad"},
// {id:"TF",text:"French Southern and Antarctic Lands"},
// {id:"TG",text:"Togo"},
// {id:"TH",text:"Thailand"},
// {id:"TJ",text:"Tajikistan"},
// {id:"TK",text:"Tokelau Islands"},
// {id:"TL",text:"East Timor"},
// {id:"TM",text:"Turkmenistan"},
// {id:"TN",text:"Tunisia"},
// {id:"TO",text:"Tonga"},
// {id:"TP",text:"East Timor"},
// {id:"TR",text:"Turkey"},
// {id:"TT",text:"Trinidad and Tobago"},
// {id:"TV",text:"Tuvalu"},
// {id:"TW",text:"Taiwan"},
// {id:"TZ",text:"Tanzania"},
// {id:"UA",text:"Ukraine"},
// {id:"UG",text:"Uganda"},
// {id:"UM",text:"American Minor Outlying Islands"},
// {id:"UN",text:"United Nations"},
// {id:"US",text:"USA"},
// {id:"UY",text:"Uruguay"},
// {id:"UZ",text:"Uzbekistan"},
// {id:"VA",text:"Vatican City"},
// {id:"VC",text:"St. Vincent and the Grenadines"},
// {id:"VE",text:"Venezuela"},
// {id:"VG",text:"British Virgin Islands"},
// {id:"VI",text:"American Virgin Islands"},
// {id:"VN",text:"Vietnam"},
// {id:"VU",text:"Vanuatu"},
// {id:"WF",text:"Wallis and Futuna Islands"},
// {id:"WS",text:"Samoa"},
// {id:"XK",text:"Republic of Kosovo"},
// {id:"YE",text:"Yemen"},
// {id:"YT",text:"Mayotte"},
// {id:"ZA",text:"South Africa"},
// {id:"ZM",text:"Zambia"},
// {id:"ZW",text:"Zimbabwe"}
//     ]);
}(typeof window !== "undefined" ? window : this));
