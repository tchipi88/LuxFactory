/* globals $ */
(function() {
    'use strict';

    angular
        .module('app')
        .directive('confirmpasswordValidator', confirmPasswordValidator);

    function confirmPasswordValidator(){

    return {
        require: '?ngModel',
        link: function(scope, elm, attrs, ctrl) {
          // only apply the validator if ngModel is present and AngularJS has added the password validator
          if (ctrl || ctrl.$validators.validpassword) {
                
           // this will overwrite the default AngularJS email validator
            ctrl.$validators.validpassword = function(modelValue) {
              return modelValue == password.value;
             };
           }

          

        }
    };

 };

})();
