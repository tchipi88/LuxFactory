(function() {
    'use strict';
    angular
        .module('app')
        .factory('Caisse', Caisse);

    Caisse.$inject = ['$resource','DateUtils'];

    function Caisse ($resource,DateUtils) {
        var resourceUrl =  'api/caisses/:id';

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
