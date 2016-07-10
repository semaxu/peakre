/**
 * Created by brad.wu on 9/1/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $pt.mock({
        url: "http://localhost:8080/peakre/restlet/v1/accounting/closing/load",
        response: function (settings) {
            this.responseText = {
                "ClosingDate": "05/06/2016",
                "ClosingStatus": 1,
                "Quarter": "2016Q2",
                "QuarterId": 2910324
            };
        }
    });

    $pt.mock({
        url: "http://localhost:8080/peakre/restlet/v1/accounting/closing/page",
        response: function (settings) {
            this.responseText = {
                "CountPerPage": 10,
                "Limit": 20,
                "PageCount": 1,
                "PageIndex": 1,
                "Results": 0,
                "Rows": [
                    {
                        "ClosedDate": "02/02/2016",
                        "ClosingDate": "22/03/2017",
                        "ClosingStatus": 3,
                        "Quarter": "2016Q1",
                        "QuarterId": 2848323,
                        "StartDate": "02/02/2016"
                    },
                    {
                        "ClosedDate": "31/12/2015",
                        "ClosingDate": "21/12/2015",
                        "ClosingStatus": 3,
                        "Quarter": "2015Q4",
                        "QuarterId": 12,
                        "StartDate": "21/12/2015"
                    },
                    {
                        "ClosedDate": "30/09/2015",
                        "ClosingDate": "20/09/2015",
                        "ClosingStatus": 3,
                        "Quarter": "2015Q3",
                        "QuarterId": 11,
                        "StartDate": "22/09/2015"
                    },
                    {
                        "ClosedDate": "30/06/2015",
                        "ClosingDate": "21/06/2015",
                        "ClosingStatus": 3,
                        "Quarter": "2015Q2",
                        "QuarterId": 10,
                        "StartDate": "21/06/2015"
                    },
                    {
                        "ClosedDate": "30/03/2015",
                        "ClosingDate": "21/03/2015",
                        "ClosingStatus": 3,
                        "Quarter": "2015Q1",
                        "QuarterId": 9,
                        "StartDate": "21/03/2015"
                    },
                    {
                        "ClosedDate": "31/12/2014",
                        "ClosingDate": "21/12/2014",
                        "ClosingStatus": 3,
                        "Quarter": "2014Q4",
                        "QuarterId": 8,
                        "StartDate": "21/12/2014"
                    },
                    {
                        "ClosedDate": "30/09/2014",
                        "ClosingDate": "20/09/2014",
                        "ClosingStatus": 3,
                        "Quarter": "2014Q3",
                        "QuarterId": 7,
                        "StartDate": "22/09/2014"
                    },
                    {
                        "ClosedDate": "30/06/2014",
                        "ClosingDate": "21/06/2014",
                        "ClosingStatus": 3,
                        "Quarter": "2014Q2",
                        "QuarterId": 6,
                        "StartDate": "21/06/2014"
                    },
                    {
                        "ClosedDate": "30/03/2014",
                        "ClosingDate": "21/03/2014",
                        "ClosingStatus": 3,
                        "Quarter": "2014Q1",
                        "QuarterId": 5,
                        "StartDate": "21/03/2014"
                    },
                    {
                        "ClosedDate": "31/12/2013",
                        "ClosingDate": "21/12/2013",
                        "ClosingStatus": 3,
                        "Quarter": "2013Q4",
                        "QuarterId": 4,
                        "StartDate": "21/12/2013"
                    }
                ],
                "Size": 10,
                "Start": 0
            };
        }
    });

    $pt.mock({
        url: "http://localhost:8080/peakre/restlet/v1/accounting/closing/setCutOffDate",
        response: function (settings) {
            this.responseText = {
                "result": "success"
            };
        }
    });

    $pt.mock({
        url: "http://localhost:8080/peakre/restlet/v1/accounting/closing/startClosing/2016Q2",
        response: function (settings) {
            this.responseText = {
                "result": "success"
            };
        }
    });

    $pt.mock({
        url: "http://localhost:8080/peakre/restlet/v1/accounting/closing/endClosing/2016Q2",
        response: function (settings) {
            this.responseText = {
                "result": "success"
            };
        }
    });

}(typeof window !== "undefined" ? window : this));