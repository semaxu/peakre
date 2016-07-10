/**
 * Created by gang.wang on 10/20/2015.
 */
(function(context){
    var $page = $pt.getService(context,"$page");
    $page.model={};
    $page.model.nonProportionalShare={
      shareData:[
        {shareCate:"1",writtenShare:0,signedShare:0,comment:""},
        {shareCate:"2",writtenShare:0,signedShare:0,comment:""},
        {shareCate:"3",writtenShare:0,signedShare:0,comment:""}
      ]
    };
    $page.model=$.extend(true, {}, $page.model.nonProportionalShare);
}(typeof window !== "undefined" ? window : this));
