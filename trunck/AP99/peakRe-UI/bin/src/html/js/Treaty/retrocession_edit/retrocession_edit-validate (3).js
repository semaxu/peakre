/**
 * Created by weiping.wang on 5/20/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    var RetrocessionValidator = jsface.Class({

    	retrocessionValidate : function(){
    		return $pt.createModelValidator({
                RetrocessionList: {
                    table : {
                        Sequence : {
                            maxlength: 2
                        }
                    }
                }
		    })
    	}

    });
    $page.validator = new RetrocessionValidator();
}(typeof window !== "undefined" ? window : this));
