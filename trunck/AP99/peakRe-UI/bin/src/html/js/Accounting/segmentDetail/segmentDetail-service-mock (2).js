/**
 * Created by brad.wu on 9/1/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $pt.mock({
        url: "http://localhost:8080/peakre/restlet/v1/accounting/ibnr/load/1635623",
        response: function (settings) {
            console.log(settings);
            this.responseText = {
                "CedentCountry": "2",
                "Cob": "1",
                "ContractNature": "2",
                "SegmentCode": "1115",
                "SegmentId": 1635623,
                "SegmentName": "Jangbi",
                "Status": 0
            };
        }
    });

    $pt.mock({
        url: "http://localhost:8080/peakre/restlet/v1/accounting/ibnr/loadIbnr/1635623",
        response: function (settings) {
            console.log(settings);
            this.responseText = [
                {
                    "FnQuarter": "2014Q4",
                    "Uy2013": 400000,
                    "Uy2014": 800000
                },
                {
                    "FnQuarter": "2014Q3",
                    "Uy2013": 300000,
                    "Uy2014": 700000
                },
                {
                    "FnQuarter": "2014Q2",
                    "Uy2013": 200000,
                    "Uy2014": 600000
                },
                {
                    "FnQuarter": "2014Q1",
                    "Uy2013": 100000,
                    "Uy2014": 500000
                }
            ];
        }
    });

}(typeof window !== "undefined" ? window : this));