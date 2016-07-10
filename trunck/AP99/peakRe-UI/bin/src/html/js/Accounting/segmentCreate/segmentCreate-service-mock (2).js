/**
 * Created by brad.wu on 9/1/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $pt.mock({
        url: "http://localhost:8080/peakre/restlet/v1/accounting/ibnr/create",
        response: function (settings) {
            this.responseText = {
                "result": "success"
            };
        }
    });

    $pt.mock({
        url: "http://localhost:8080/peakre/restlet/v1/accounting/ibnr/generateCode",
        response: function (settings) {
            this.responseText = {
                "segmentCode": "00902"
            };
        }
    });

}(typeof window !== "undefined" ? window : this));