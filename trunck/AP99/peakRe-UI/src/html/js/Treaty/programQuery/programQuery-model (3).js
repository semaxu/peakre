(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.model = {
        resultTable: [
            {programID: "1", programName: "SKT1", cedant: "Faker", broker: "Marine", mainClass: "A", uy: "2015"},
            {programID: "2", programName: "EDG", cedant: "ClearLove", broker: "Pawn", mainClass: "B", uy: "2014"},
            {programID: "3", programName: "Sumsung", cedant: "Imp", broker: "Imp", mainClass: "C", uy: "2013"},
            {programID: "4", programName: "WE", cedant: "xiye", broker: "caomei", mainClass: "C", uy: "2012"},
            {programID: "5", programName: "IG", cedant: "PDD", broker: "xiaoxiao", mainClass: "C", uy: "2011"},
            {programID: "6", programName: "QG", cedant: "Doinb", broker: "Lovelin", mainClass: "C", uy: "2010"},
            {programID: "7", programName: "Snake", cedant: "Cryst4l", broker: "Flander", mainClass: "C", uy: "2009"},
            {programID: "8", programName: "OG", cedant: "Huni", broker: "Huni", mainClass: "C", uy: "2008"},
            {programID: "9", programName: "VG", cedant: "Mata", broker: "Mata", mainClass: "C", uy: "2007"},
            {programID: "10", programName: "RNG", cedant: "MLXG", broker: "MLXG", mainClass: "C", uy: "2006"},
            {programID: "11", programName: "M3", cedant: "Dade", broker: "Looper", mainClass: "C", uy: "2005"},
            {programID: "12", programName: "NaJing", cedant: "Watch", broker: "Watch", mainClass: "C", uy: "2004"}
        ],
        criteria: {
            pageIndex: 1,
            pageCount: 1,
            countPerPage: 5,
            mode: null,
            column: null
        }
    };

    //$page.model.all = $.extend(true, {}, $page.model.result);
}(typeof window !== "undefined" ? window : this));
