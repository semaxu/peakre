/**
 * Created by brad.wu on 9/1/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $pt.mock({
        url: $ri.getRestfulURL("action.arap.collection.queryCollection"),
        response: function (settings) {
            this.responseText = {
                code: '0001'
            };
        }
    });

    $pt.mock({
        url: $ri.getRestfulURL("action.arap.collection.saveCollection"),
        response: function (settings) {
            this.responseText = {
                code: '0001'
            };
        }
    });

    $pt.mock({
        url: $ri.getRestfulURL("action.arap.collection.calGainLossInUSD"),
        response: function (settings) {
            this.responseText = {
                code: '0001'
            };
        }
    });

}(typeof window !== "undefined" ? window : this));