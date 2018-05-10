(function() {
    'use strict';
    angular
        .module('app')
        .factory('ZoneProduit', ZoneProduit);

    ZoneProduit.$inject = ['$resource','DateUtils'];

    function ZoneProduit ($resource,DateUtils) {
        var resourceUrl =  'api/zone-produits/:id';

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
