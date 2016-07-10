/**
 * Created by dominic.han on 2/23/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.validator = $pt.createModelValidator({
        //brokerReferece:{
        //    "required":true
        //}

    });
}(typeof window !== "undefined" ? window : this));