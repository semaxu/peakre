/**
 * Created by xinxin.yue on 2/22/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.validator = $pt.createModelValidator({
        ContractCode:{required:true},
        Cedent:{required:true},
        SoaId:{required:true},
        UwYear:{required:true},
        DueDate:{required:true},
      //  Broke:{required:true},
        ReceivedDate:{required:true},
      //  BizType:{required:true},
        CedentYear:{required:true},
        CedentQuarter:{required:true},
        SoaText:{required:true},
        CoBList:{required:true},
        UwAreaList:{required:true},
        ShareType:{required:true},
        CedentPeriod:{required:true}

    });
}(typeof window !== "undefined" ? window : this));
