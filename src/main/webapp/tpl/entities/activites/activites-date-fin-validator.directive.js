/* globals $ */
(function() {
    'use strict';

    angular
        .module('app')
        .directive('activitesDateFinValidator', ['DateUtils','$filter', function (DateUtils,$filter) {
              // body...
              return {
                require: '?ngModel',
                link: function(scope, elm, attrs, ctrl) {
                  // only apply the validator if ngModel is present and AngularJS has added the password validator
                  if (ctrl) {
                        
                   // this will overwrite the default AngularJS email validator
                    ctrl.$validators.validDateFin = function(modelValue) {
                      var dateFormat = 'yyyy-MM-dd';
                      //return modelValue >= $filter('date')(field_dateDebut.value, dateFormat);
                      //var dateFormat = 'yyyy-MM-dd';
                      var datedebut = $filter('date')(field_dateDebut.value, dateFormat);
                      var datefin = $filter('date')(modelValue, dateFormat);

                      return datefin >= datedebut;

                      //if( datefin >= datedebut)
                      //alert ('dateefin: ' + datefin + ' datedebut: ' + field_dateDebut.value + ' datedebutFormat : '+datedebut);
                     };
                   }

                  

                }
            };
        }]);

   
})();
