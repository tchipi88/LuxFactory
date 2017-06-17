(function() {
    'use strict';

    angular
        .module('app')
        .factory('DateUtils', DateUtils);

    DateUtils.$inject = ['$filter'];

    function DateUtils($filter) {

        var service = {
            convertDateTimeFromServer: convertDateTimeFromServer,
            convertLocalDateFromServer: convertLocalDateFromServer,
            convertLocalTimeFromServer: convertLocalTimeFromServer,
            convertLocalDateToServer: convertLocalDateToServer,
            convertLocalTimeToServer: convertLocalTimeToServer,
            dateformat: dateformat
        };

        return service;

        function convertDateTimeFromServer(date) {
            if (date) {
                return new Date(date);
            } else {
                return null;
            }
        }

        function convertLocalDateFromServer(date) {
            if (date) {
                var dateString = date.split('-');
                return new Date(dateString[0], dateString[1] - 1, dateString[2]);
            }
            return null;
        }
        function convertLocalTimeFromServer(date) {
            if (date) {
                var dateString = date.split(':');
                return new Date(1970, 0, 1,dateString[0], dateString[1], dateString[2]);
            }
            return null;
        }

        function convertLocalDateToServer(date) {
            if (date) {
                return $filter('date')(date, 'yyyy-MM-dd');
            } else {
                return null;
            }
        }
        function convertLocalTimeToServer(date) {
            if (date) {
                return $filter('date')(date, 'HH:mm:ss');
            } else {
                return null;
            }
        }

        function dateformat() {
            return 'yyyy-MM-dd';
        }
    }

})();
