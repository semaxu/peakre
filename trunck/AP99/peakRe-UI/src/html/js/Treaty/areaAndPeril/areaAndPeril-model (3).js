/**
 * Created by gang.wang on 10/20/2015.
 */
(function(context){
    var $page = $pt.getService(context,"$page");
    // $page.countryModel = {
    //   countries:
    //     {
    //       selected: true,
    //       "1" : {
    //         selected: true,
    //         "11": {
    //           selected: true
    //         },
    //         "12": {
    //           selected: true
    //         },
    //         "13": {
    //           selected: true
    //         }
    //       },
    //       "2": {
    //         selected: true
    //       },
    //       "3": {
    //         selected: true
    //       },
    //       "4": {
    //         selected: true
    //       }
    //     }
    // };
    // $page.uwCountryModel = $pt.createModel($.extend(true, {}, $page.countryModel));
    // $page.coveredCountryModel = $pt.createModel($.extend(true, {}, $page.countryModel));
    // $page.perilCountryModel = $pt.createModel($.extend(true, {}, $page.countryModel));
    $page.model={
      area:"Area : ",
      uwCountries:[],
      coveredCountries:[]
    };
}(typeof window !== "undefined" ? window : this));
