(function (context) {
    var $page = $pt.getService(context, '$page');
    $pt.mock({
      url : $ri.getRestfulURL("action.contract.contractBusiness")+'/saveCommSliding',
      response: function (settings) {
          var data = $pt.parseJSON(settings.data);
          this.responseText = data;
        }
    });
    $pt.mock({
        url : $ri.getRestfulURL("action.contract.contractBusiness")+'/loadCommSliding/1',
        response: function (settings) {
            var data = $pt.parseJSON(settings.data);
            this.responseText = {
               "DeductionsCarried": [{"Name": "Profit", "Yn": "0", "Years": ""}, {
                   "Name": "Loss",
                   "Yn": "1",
                   "Years": "5"
               }],
               "DeductionsId": "1",
               "LossIncludeIBNR": "0",
               "FirstCal": "2",
               "SubsequentCalc": "1",
               "MinimumRISs": "0.40",
               "MaximumRISs": "0.45",
               "MaximumLossSs": "0.82",
               "MinimumLossSs": "0.70"
           }
        }
      });
}(typeof window !== "undefined" ? window : this));
