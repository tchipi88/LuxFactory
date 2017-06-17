(function() {
    'use strict';
    angular
        .module('app')
        .factory('Marque', Marque);

    Marque.$inject = ['$resource','DateUtils'];

    function Marque ($resource,DateUtils) {
        var resourceUrl =  'api/marques/:id';

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
