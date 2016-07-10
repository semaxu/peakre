(function (context) {
    var $page = $pt.getService(context, '$page');
    var PricingModel = jsface.Class({
        getBasicModel : function(){
            return {
                ContractNature : null,
                ContCompId : null,
                ParentId : null,
                OperateType : null,
                WrittenPartner: 1,
                EarningPartner: 1
            }
        }
    });
    $page.model = new PricingModel();
}(typeof window !== "undefined" ? window : this));
