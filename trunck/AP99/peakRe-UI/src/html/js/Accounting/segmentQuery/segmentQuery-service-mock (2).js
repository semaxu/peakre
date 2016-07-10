/**
 * Created by brad.wu on 9/1/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $pt.mock({
        url: "http://localhost:8080/peakre/restlet/v1/accounting/ibnr/page",
        response: function (settings) {
            console.log(settings);
            var data = $pt.parseJSON(settings.data);
            this.responseText = {
                "Condition": {
                    "SegmentId": 0
                },
                "CountPerPage": 10,
                "Limit": 20,
                "PageCount": 3,
                "PageIndex": 1,
                "Results": 0,
                "Rows": [
                    {
                        "CedentCountry": "2",
                        "Cob": "1",
                        "ContractNature": "2",
                        "SegmentCode": "1115",
                        "SegmentId": 1635623,
                        "SegmentName": "Jangbi",
                        "Status": 0
                    },
                    {
                        "CedentCountry": "2",
                        "Cob": "1",
                        "ContractNature": "2",
                        "SegmentCode": "1116",
                        "SegmentId": 1635624,
                        "SegmentName": "Best",
                        "Status": 1
                    },
                    {
                        "CedentCountry": "2",
                        "Cob": "1",
                        "ContractNature": "2",
                        "SegmentCode": "1117",
                        "SegmentId": 1635625,
                        "SegmentName": "Fantasy",
                        "Status": 1
                    },
                    {
                        "CedentCountry": "2",
                        "Cob": "1",
                        "ContractNature": "2",
                        "SegmentCode": "1118",
                        "SegmentId": 1635626,
                        "SegmentName": "Nada",
                        "Status": 1
                    },
                    {
                        "CedentCountry": "2",
                        "Cob": "1",
                        "ContractNature": "2",
                        "SegmentCode": "1119",
                        "SegmentId": 1635627,
                        "SegmentName": "Free",
                        "Status": 1
                    },
                    {
                        "CedentCountry": "2",
                        "Cob": "1",
                        "ContractNature": "2",
                        "SegmentCode": "1120",
                        "SegmentId": 1635628,
                        "SegmentName": "Leta",
                        "Status": 1
                    },
                    {
                        "CedentCountry": "2",
                        "Cob": "1",
                        "ContractNature": "2",
                        "SegmentCode": "1121",
                        "SegmentId": 1635629,
                        "SegmentName": "Movie",
                        "Status": 1
                    },
                    {
                        "CedentCountry": "2",
                        "Cob": "1",
                        "ContractNature": "2",
                        "SegmentCode": "1122",
                        "SegmentId": 1635630,
                        "SegmentName": "Effort",
                        "Status": 1
                    },
                    {
                        "CedentCountry": "2",
                        "Cob": "1",
                        "ContractNature": "2",
                        "SegmentCode": "1123",
                        "SegmentId": 1635631,
                        "SegmentName": "Kingdom",
                        "Status": 1
                    },
                    {
                        "CedentCountry": "2",
                        "Cob": "1",
                        "ContractNature": "2",
                        "SegmentCode": "1124",
                        "SegmentId": 1635632,
                        "SegmentName": "Reach",
                        "Status": 1
                    }
                ],
                "Size": 10,
                "Start": 0
            };
        }
    });
}(typeof window !== "undefined" ? window : this));