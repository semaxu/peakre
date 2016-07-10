(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.linkedContractModel = $pt.createModel();

    $page.model = {
        LatestStatus: "1",
        LinkName: null,
        Fronting: '0',
        MainCurrency:'USD',
        DepositAccounting: '0',
        AccountFrequency: '3',
        ContractType: '1',
        ContractCategory: '1',
        UwCompany: '1',
        ProtectionBasic: '1',
        EarningPattern: '1',
        AccountingBasis: '1',
        ClaimBasis: '2',
        PortfolioIn:'false',
        PortfolioOut:'false',
        Leader:'0',
        AccountDays:'45',
        SettlementDays:'90',
        ContractClaimConditionItemList: [
            {CateId: 1},
            {CateId: 2},
            {CateId: 3},
            {CateId: 4},
            {CateId: 5},
            {CateId: 6},
            {CateId: 7}
        ],
        DeleteSectionList: null
    };

    $page.linkList = $page.codes.createMutableCodeTable();
    $page.linkStructure = [];

}(typeof window !== "undefined" ? window : this));
