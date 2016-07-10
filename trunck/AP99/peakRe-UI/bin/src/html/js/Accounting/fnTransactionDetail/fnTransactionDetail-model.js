(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.model = {
        accountTable: [
            {entryCode:"1000",entryItem: "Proportional Premium", text:"Q1 Estimation Reversal", inputDate: "01/12/2015", amount: "-$25000.00", inputUser: "System"},
            {entryCode:"1000",entryItem: "Proportional Premium", text:"SoA of Q1", inputDate: "05/01/2016", amount: "$22000.00", inputUser: "User01"},
            {entryCode:"1000",entryItem: "Proportional Premium", text:"Q2 Estimation ", inputDate: "23/02/2016", amount: "$26000.00", inputUser: "System"},
        ]
    };
}(typeof window !== "undefined" ? window : this));
