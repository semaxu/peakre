/**
 * Created by Weiping.Wang on 5/31/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    var supiAdjustmentValidator = jsface.Class({

        supiAdjustmentValidate : function(){
    		return $pt.createModelValidator({
                SupiSecList:{
                    table:{
                        AdjustedSUPIList:{
                            table:{
                                Amount:{required: true}
                            }
                        }
                    }
                }
		    })
    	}
    });
    $page.validator = new supiAdjustmentValidator();
}(typeof window !== "undefined" ? window : this));
