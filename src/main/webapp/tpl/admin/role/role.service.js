(function() {
    'use strict';
    angular
        .module('app')
        .factory('Role', Role);

    Role.$inject = ['$resource','DateUtils'];

    function Role ($resource,DateUtils) {
        var resourceUrl =  'api/roles/:id';

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
