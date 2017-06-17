(function() {
    'use strict';
    angular
        .module('app')
        .factory('Engin', Engin);

    Engin.$inject = ['$resource','DateUtils'];

    function Engin ($resource,DateUtils) {
        var resourceUrl =  'api/engins/:id';

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
