/**
 * Created by gang.wang on 10/14/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.model = {
        documentList: [
            {documentsName: "doc01",documentType: "type01",receivedDate: "2015-11-10",section: "section01",description: "XXXXXX"},
        ]
    };

}(typeof window !== "undefined" ? window : this));
