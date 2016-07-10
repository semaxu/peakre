/**
 * Created by brad.wu on 9/15/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var contractMenu = {
        text: 'Contract Management',
        icon: 'tasks',
        children: [
            {
                text: 'Contract Management',
                url: "ui.contract.management"
            }, {
                text: 'Pricing Estimate',
                url: "ui.contract.pricing"
            }, {
                text: 'SUPI Adjustment',
                url: "ui.contract.epi"
            }
        ]
    };
    var claimMenu = {
        text: 'Claim',
        icon: 'car',
        children: [
            {
                text: 'Loss Adjustment',
                url: 'ui.claim.claimQuery'
            }, {
                text: 'Event Management',
                url: 'ui.claim.eventSearch'
            },{
                text: "Contract Query",
                url: 'ui.queryView.search'
            }
        ]
    };


    var arapMenu = {
        text: 'ARAP',
        icon: 'money',
        children: [{
            text: "Collection",
            url: "ui.payment.collection"
        }, {
            text: "Payment",
            url: "ui.payment.payment"
        }, {
            text: "Internal Offset",
            url: "ui.payment.internalOffset"
        }, {
            text: "Transaction Reversal",
            url: "ui.payment.transactionReversal"
        }, {
            text: "Transaction Query",
            url: "ui.payment.transactionQuery"
        }, {
            text: "Bank Account Management",
            url: "ui.payment.bankAccountManagement"
        }, {
            text: "Exchange Rate Management",
            url: "ui.payment.exchangeRateManagement"
        }, {
            text: "General Ledger Query",
            url: "ui.gl.generalLedgerQuery"
        }, {
            text: "Sub Ledger Query",
            url: "ui.gl.subLedgerQuery"
        }]
    };
    var partnerMenu = {
        text: 'Business Partner',
        icon: 'tasks',
        children: [
            {
                text: 'Business Partner Maintenance',
                url: 'ui.partner.search'
            }
        ]
    };
    var generalQueryMenu = {
        text: "General Query",
        icon: 'search',
        children: [{
            text: "Contract Query",
            url: 'ui.queryView.search'
        }, {
            text: "GL Data Query",
            url: ''
        }]
    };

    var accountingMenu = {
        text: 'Technical Accounting',
        icon: 'user-secret',
        children: [
            {
                text: 'Statement Management',
                url: "ui.accounting.statementQuery"
            }, {
                text: 'Account Summary',
                url: "ui.accounting.accountSummaryQuery"
            },
            //{
            //    text: 'Actualization',
            //    url: "ui.accounting.actualization"
            //},
            {
                text: 'IBNR Management',
                url: "ui.accounting.segmentQuery"
            }, {
                text: 'Closing Setup',
                url: "ui.accounting.closingSetting"
            }, {
                text: "Exception Report",
                url: "ui.accounting.exceptionReport"
            }, {
                text: "Revaluation Batch Job",
                url: "ui.accounting.revaluationBatchJob"

            }
        ]
    };
    //var arapMenu = {
    //    text: 'ARAP',
    //    icon: 'money',
    //    children: [{
    //        text: "Collection",
    //        url: "action.payment.collection"
    //    }, {
    //        text: "Payment",
    //        url: "action.payment.payment"
    //    }, {
    //        text: "Internal Offset",
    //        url: "action.payment.internalOffset"
    //    }, {
    //        text: "Bank Account Setup",
    //        url: "action.payment.bankAccountManagement"
    //    }, {
    //        text: "Exchange Rate Setup",
    //        url: "action.payment.exchangeRateManagement"
    //    }, {
    //        text: "Transaction Reversal",
    //        url: "action.payment.transactionReversal"
    //    }, {
    //        text: "Transaction Query",
    //        url: "action.payment.transactionQuery"
    //    }]
    //};
    //
    //var partnerMenu = {
    //    text: 'Partner',
    //    icon: 'money',
    //    children: [
    //        {
    //            text: "Business Partner Search",
    //            url: "action.partner.search"
    //        },{
    //            text: "Business Partner Individual",
    //            url: "action.partner.individual"
    //        },{
    //            text: "Business Partner Company",
    //            url: "action.partner.company"
    //        }
    //    ]
    //};


    $page.menus = [contractMenu, claimMenu, accountingMenu, arapMenu,partnerMenu];//, generalQueryMenu, accountingMenu, arapMenu, partnerMenu];
}(typeof window !== "undefined" ? window : this));