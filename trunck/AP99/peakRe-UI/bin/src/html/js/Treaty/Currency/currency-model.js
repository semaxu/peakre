/**
 * Created by Gordon.Cao on 10/20/2015.
 */
(function(context){
    var $page = $pt.getService(context,"$page");
    $page.model={
        reserveHisData:[
            {reserveType:"Loss(Indemnity)",outstandingLocal:"100HKD",chargeAmtLocal:"100HKD",outstandingUSD:"40USD",chargeAmtUSD:"40USD",status:"Open",date:"2015-01-01",category:"New Reserve",remark:"AAA"},
            {reserveType:"Loss(Indemnity)",outstandingLocal:"200HKD",chargeAmtLocal:"200HKD",outstandingUSD:"80USD",chargeAmtUSD:"80USD",status:"Open",date:"2015-01-01",category:"Update Reserve",remark:"AAA"},
            {reserveType:"Loss(Indemnity)",outstandingLocal:"0HKD",chargeAmtLocal:"200HKD",outstandingUSD:"0USD",chargeAmtUSD:"80USD",status:"Closed",date:"2015-01-01",category:"Close Reserve",remark:"Reserve Closed"},
        ],
        specificReinstatementData:[
          {currency:"1",mainCurrency:"checked",exchangeType:"Fixed Rate",rateOrAmount:"0.1",remarks:"This is a remark"}
        ]
    };
}(typeof window !== "undefined" ? window : this));
