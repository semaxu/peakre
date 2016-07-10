/**
 * Created by brad.wu on 9/1/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $pt.mock({
        url: "http://localhost:8080/peakre/restlet/v1/contract/contractStructure/searchContractBasic",
        response: function (settings) {
            console.log(settings);
            var data = $pt.parseJSON(settings.data);
            this.responseText = {
                "Condition": {},
                "CountPerPage": 10,
                "Limit": 20,
                "PageCount": 1,
                "PageIndex": 1,
                "Results": 0,
                "Rows": [
                    {
                        "BrokerRef": "Jaedong",
                        "CedentRef": "Flash",
                        "ContCompId": 1638213,
                        "ContractNature": "1",
                        "UwYear": 2015
                    },
                    {
                        "BrokerRef": "Weiping",
                        "CedentRef": "Vinson",
                        "ContCompId": 1639063,
                        "ContractNature": "1",
                        "Fronting": "1",
                        "UwYear": 2016
                    },
                    {
                        "BrokerRef": "Jangbi",
                        "CedentRef": "Stork",
                        "ContCompId": 1639513,
                        "ContractNature": "1"
                    },
                    {
                        "BrokerRef": "Bisu",
                        "CedentRef": "Fantasy",
                        "ContCompId": 1640173,
                        "ContractNature": "1"
                    },
                    {
                        "BrokerRef": "Faker",
                        "CedentRef": "Marine",
                        "ContCompId": 1638034,
                        "ContractNature": "1",
                        "Fronting": "1",
                        "UwYear": 2015
                    },
                    {
                        "Broker": "22",
                        "BrokerRef": "Bengi",
                        "Cedent": "11",
                        "CedentRef": "Wolf",
                        "ContCompId": 1640303,
                        "ContractNature": "1",
                        "Fronting": "1",
                        "UwYear": 2014
                    },
                    {
                        "ContCompId": 1643715,
                        "ContractNature": "1"
                    },
                    {
                        "BrokerRef": "123",
                        "CedentRef": "123",
                        "ContCompId": 2133324,
                        "ContractNature": "1",
                        "Fronting": "0"
                    },
                    {
                        "ContCompId": 2243323,
                        "ContractNature": "1"
                    },
                    {
                        "ContCompId": 2907323,
                        "Fronting": "0"
                    }
                ],
                "Size": 10,
                "Start": 0
            };
        }
    });
}(typeof window !== "undefined" ? window : this));