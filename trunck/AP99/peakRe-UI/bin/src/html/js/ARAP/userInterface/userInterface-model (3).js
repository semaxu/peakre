/**
 * Created by gang.wang on 10/14/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.model = {
        claimNo:"CL0001",

        searchTable: [
            {branch: "HQ BJ Branch",bankAccountNumber: "HSBC-9233",bankAccountName: "HSBC-9233-PP",currency: "1",bank: "HSBC",accountType: "1",status: "1"},
            {branch: "HQ SH Branch",bankAccountNumber: "HSBC-9233",bankAccountName: "HSBC-9233-PP",currency: "1",bank: "HSBC",accountType: "2",status: "2"},

        ],

        settlementArray : [
            {dateOfReceipt:1,
                sectionTable : [
                    {sectionName: "1",contractID: "program 1",uy: "20",cedent: "cedent 1",broker: "broker 1",section: "section 1",subsection: "sub 1",cedentRefer: "R 1",brokerRefer: "R 1",reserveType: "1",currency: "1",amountInOriginal: "500",amountInUSD: "400",posting:"1"},
                ]
            }
        ]

    };
    $page.model.settlementArrayTemplate = {
        dateOfReceipt : 1,
        sectionTable : [
            {sectionName: "1",contractID: "program 1",uy: "20",cedent: "cedent 1",broker: "broker 1",section: "section 1",subsection: "sub 1",cedentRefer: "R 1",brokerRefer: "R 1",reserveType: "1",currency: "1",amountInOriginal: "500",amountInUSD: "400",posting:"1"},
        ],
        settleTable:null,
        blank:null,
        summary:null,
        remarkPanel:null
    };
}(typeof window !== "undefined" ? window : this));
