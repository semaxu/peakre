/**
 * Created by Gordon.Cao on 10/14/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.model = {
        table: [
            {idType: "1", idNumber: "111111111", name: "AAAAAAAAAA", gender: "F", remark: "AAA"},
            {idType: "2", idNumber: "222222222", name: "BBBBBBBBBB", gender: "M", remark: "BBB"},
            {idType: "1", idNumber: "333333333", name: "CCCCCCCCCC", gender: "M", remark: "CCC"},
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
        "interTable": [
          {"name": "Jack"},
          {"name": "Mary"}
        ]
    };
}(typeof window !== "undefined" ? window : this));
