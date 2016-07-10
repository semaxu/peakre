/**
 * Created by Weiping.Wang on 5/20/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    var CommSlidingValidator = jsface.Class({
        validateNumberOfYears:function(){
            return {
                _fly: function (model, value, settings) {
                    if(value){
                        var reg = /^0$|^[1-9][0-9]{0,3}$/;
                        if(!reg.test(value)){
                            return "Number Of Years must be number and length cann't be more than 4.";
                        }
                    }
                    
                }
            }
        },
        validateNumber:function(){
            return {
                _fly: function (model, value, settings) {
                    var reg = /^0$|^[1-9][0-9]{0,1}$/;
                    if(!reg.test(value)){
                        return "%1 must be between 0 and 99.";
                    }
                }
            }
        },
        validateReinNum:function(){
            return {
                _fly: function (model, value, settings) {
                    if(value){
                        var reg = /^[1-9][0-9]{0,1}$/;
                        if(!reg.test(value)){
                            return "%1 must be between 1 and 99.";
                        }
                    }
                }
            }
        },

    	commSlidingValidate : function(){
    		return $pt.createModelValidator({
                // DeductionsCarried : {
                //     table : {
                //         Years : this.validateNumberOfYears()
                //     }
                // }
                // FirstCal:$helper.baseNumber(),
                // SubsequentCalc:$helper.baseNumber()
		    })
    	}
    });
    $page.validator = new CommSlidingValidator();
}(typeof window !== "undefined" ? window : this));
