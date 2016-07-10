/**
 * Created by xinxin.yue on 2/22/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var ContractHomeValidator = jsface.Class({

        validatePercentage:function(){
            return {
                required : false,
                rule: function(model, value, settings) {
                    if (value == null) {
                        return true;
                    } else {
                        if (isNaN(value)) {
                            return 'Percentage  must be a numeric value.';
                        } else {
                            if(parseFloat(value) > parseFloat(9.9999)){
                                return 'Percentage  can\'t be large than 999.99%.';
                            }
                        }
                        return true;
                    }
                }
            }
        },

        contractHomeValidate : function(){
            return $pt.createModelValidator({
                //ContractCode:{required:true},
                ContractName:{required:true},
                Mainclass:{required:true},
                Subclass:{required:true},
                ContractType:{required:true},
                ContractNature:{required:true},
                ContractCategory:{required:true},
                ReinsStarting:{required:true},
                ReinsEnding:{required:true},
                UwYear:{required:true},
                Cedent:{required:true},
                MainCoverArea:{required:true},
                Underwriting:{required:true},
                UwCompany:{required:true},
                //Reviewed:{required:true},
                MainCurrency:{required:true},
                CedentCountry:{required:true},
                // PnocCedentDay:$helper.baseNumber(),
                // PnocCedentMonth:$helper.baseNumber(),
                // PnocReinsurerDay:$helper.baseNumber(),
                // PnocReinsurerMonth:$helper.baseNumber(),
                // DnocWarDay:$helper.baseNumber(),
                // DnocWarMonth:$helper.baseNumber(),
                // DnocPoliticalDay:$helper.baseNumber(),
                // DnocPoliticalMonth:$helper.baseNumber(),
                // DnocSanctionDay:$helper.baseNumber(),
                // DnocSanctionMonth:$helper.baseNumber(),
                // NoOfDay:$helper.baseNumber(),
                // SettlementDays:$helper.baseNumber(),
                // AccountDays:$helper.baseNumber(),
                ContractClaimConditionItemList : {
                    table : {
                        Percentage : this.validatePercentage()
                    }
                }
            })
        }
    });
    $page.validator = new ContractHomeValidator();
}(typeof window !== "undefined" ? window : this));
