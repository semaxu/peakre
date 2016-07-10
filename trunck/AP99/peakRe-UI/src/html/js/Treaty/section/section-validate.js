/**
 * Created by xinxin.yue on 2/23/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    var SectionValidator = jsface.Class({
    	isNumberOfYearsRequired:function(){
            return {
                _when: function (model) {
                    return $page.controller.model.get('BusinessConditionVO_DeficitCarryForward') == '2';
                },
                rule: true
            }
        },
        baseNumber:function(){
            return {
                _fly: function (model, value, settings) {
                    if(value){
                        var reg = /^0$|^[1-9][0-9]{0,3}$/;
                        if(!reg.test(value)){
                            return "%1 must be number and length cann't be more than 4.";
                        }
                    }

                },
                required: this.isNumberOfYearsRequired()
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
                    if($page.controller.model.get('BusinessConditionVO_ReinType') == '2' && value){
                        var reg = /^[1-9][0-9]{0,1}$/;
                        if(!reg.test(value)){
                            return "%1 must be between 1 and 99.";
                        }
                    }
                }
            }
        },

    	sectionValidate : function(){
    		return $pt.createModelValidator({
				ShareType:{required:true},
		        SectionName:{required:true},
            BusinessConditionVO_ReinNum:  {
                    maxlength : {
                      _when: function(model){
                        return model.get("BusinessConditionVO_ReinType") == 2;
                      },
                        rule : 2
                    }
                },
                BusinessConditionVO_NumberOfYears: this.baseNumber(),
                BusinessConditionVO_ReinstateSpecificList : {
                    table : {
                      Reinstate : {
                        maxlength : {
                          _when: function(model){
                            return model.parent().get("BusinessConditionVO_ReinType") == 2;
                          },
                          rule : 2
                        }
                      }
                    }
                },
                BusinessConditionVO_ReinstateUnlimitedList : {
                  table : {
                    Reinstate : {
                      maxlength : {
                        _when: function(model){
                          return model.parent().get("BusinessConditionVO_ReinType") == 3;
                        },
                        rule : 2
                      }
                    }
                  }
                }
		    })
    	}
    });
    $page.validator = new SectionValidator();
}(typeof window !== "undefined" ? window : this));
