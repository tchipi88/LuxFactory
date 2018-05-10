(function() {
    'use strict';
    angular
        .module('app')
        .factory('DecompteZonesProduits', DecompteZonesProduits);

    DecompteZonesProduits.$inject = ['$resource','DateUtils'];

    function DecompteZonesProduits ($resource,DateUtils) {
        var resourceUrl =  'api/decompte-zones-produitss/:id';

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
