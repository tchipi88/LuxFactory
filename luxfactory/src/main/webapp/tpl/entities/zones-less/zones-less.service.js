(function() {
    'use strict';
    angular
        .module('app')
        .factory('ZonesLess', ZonesLess);

    ZonesLess.$inject = ['$resource','DateUtils'];

    function ZonesLess ($resource,DateUtils) {
        var resourceUrl =  'api/zones-lesss/:id';

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
