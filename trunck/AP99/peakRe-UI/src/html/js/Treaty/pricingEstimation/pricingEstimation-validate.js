(function (context) {
    var $page = $pt.getService(context, '$page');
    var PricingValidator = jsface.Class({
        isPortional : function(){
          return {
              _when: function (model) {
                  return $page.controller.model.get('ContractNature') == '1';
              },
              rule: true
          }
        },
        checkPricingValidate : function(){
            return $pt.createModelValidator({
                PricingItemList : {
                  table : {
                    PricingDate : {required:true},
                    Epi : {required : this.isPortional()},
                    Commission : {required : this.isPortional()},
                    Brokerage : {required : this.isPortional()},
                    Taxs : {required : this.isPortional()},
                    LossRatio : {required : true}
                  }
                }
            })
        }
    });
    $page.validator = new PricingValidator();
}(typeof window !== "undefined" ? window : this));
