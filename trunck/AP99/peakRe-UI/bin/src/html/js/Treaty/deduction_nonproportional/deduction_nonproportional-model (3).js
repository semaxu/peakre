/**
 * Created by gang.wang on 10/14/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.model = {
        table: [
            {item: "Tax"},
            {item: "Fronting Fee"},
            {item: "Cover Holder Fee"},
        ],
        tree: [
            {
                "id": 1,
                "text": "Languages",
                "selected": true,
                "children": [
                    {"id": 2, "text": "Java"},
                    {"id": 3, "text": "C#"}
                ]
            },
            {
                "id": 4,
                "text": "Costing",
                "children": [
                    {"id": 5, "text": "1,000"},
                    {"id": 6, "text": "2,000"}
                ]
            },
            {
                "id": 7,
                "text": "Others",
                "folder": true
            }
        ],
        carriedForwards: [
            {"name": "Debit"},
            {"name": "Credit"}
        ]
    };
}(typeof window !== "undefined" ? window : this));
