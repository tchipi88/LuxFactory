(function() {
    'use strict';
    angular
        .module('app')
        .factory('Entrepot', Entrepot);

    Entrepot.$inject = ['$resource','DateUtils'];

    function Entrepot ($resource,DateUtils) {
        var resourceUrl =  'api/entrepots/:id';

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
