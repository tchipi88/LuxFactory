(function() {
    'use strict';
    angular
        .module('app')
        .factory('Unite', Unite);

    Unite.$inject = ['$resource','DateUtils'];

    function Unite ($resource,DateUtils) {
        var resourceUrl =  'api/unites/:id';

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
