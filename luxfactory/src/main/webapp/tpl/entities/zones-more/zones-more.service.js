(function() {
    'use strict';
    angular
        .module('app')
        .factory('ZonesMore', ZonesMore);

    ZonesMore.$inject = ['$resource','DateUtils'];

    function ZonesMore ($resource,DateUtils) {
        var resourceUrl =  'api/zones-mores/:id';

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
