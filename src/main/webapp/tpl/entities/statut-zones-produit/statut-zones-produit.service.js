(function() {
    'use strict';
    angular
        .module('app')
        .factory('StatutZonesProduit', StatutZonesProduit);

    StatutZonesProduit.$inject = ['$resource','DateUtils'];

    function StatutZonesProduit ($resource,DateUtils) {
        var resourceUrl =  'api/statut-zones-produits/:id';

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
