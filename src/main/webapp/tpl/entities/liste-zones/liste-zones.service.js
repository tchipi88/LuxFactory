(function() {
    'use strict';
    angular
        .module('app')
        .factory('ListeZones', ListeZones);

    ListeZones.$inject = ['$resource','DateUtils'];

    function ListeZones ($resource,DateUtils) {
        var resourceUrl =  'api/liste-zoness/:id';

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
