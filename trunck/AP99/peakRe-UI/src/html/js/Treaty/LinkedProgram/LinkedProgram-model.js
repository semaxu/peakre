(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.model = {
        contractTree: [
            {
                id : 1,
                text : "Contract 1 Name",
                selected : true,
                children : [
                    {
                        id : 2,
                        text : "Section 1 Name",
                        children : [
                            {
                                id : 3,
                                text : "Sub-section 1 Name"
                            }, {
                                id : 4,
                                text : "Sub-section 2 Name"
                            }
                        ]
                    }, {
                        id : 5,
                        "text": "Section 2 Name"
                    }, {
                        id : 6,
                        "text": "Section 3 Name"
                    }
                ]
            }, {
                id: 7,
                text : "Contract 2 Name"
            }, {
                id : 8,
                text : "Contract 3 Name"
            }
        ]
    };
}(typeof window !== "undefined" ? window : this));
