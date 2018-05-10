(function() {
    'use strict';
    angular
        .module('app')
        .factory('EmployeFonction', EmployeFonction);

    EmployeFonction.$inject = ['$resource','DateUtils'];

    function EmployeFonction ($resource,DateUtils) {
        var resourceUrl =  'api/employe-fonctions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
