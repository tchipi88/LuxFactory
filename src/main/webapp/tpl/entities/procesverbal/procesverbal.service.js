(function() {
    'use strict';
    angular
        .module('app')
        .factory('Procesverbal', Procesverbal);

    Procesverbal.$inject = ['$resource','DateUtils'];

    function Procesverbal ($resource,DateUtils) {
        var resourceUrl =  'api/procesverbals/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
             'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                          data.datePv =DateUtils.convertLocalDateFromServer(data.datePv);

                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.datePv =DateUtils.convertLocalDateToServer(copy.datePv);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.datePv =DateUtils.convertLocalDateToServer(copy.datePv);

                    return angular.toJson(copy);
                }
            }
        });
    }



})();
